package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/register")
    //spring validation 参数校验
    //使用全局异常处理器处理参数校验失败的异常
    public Result register(@Pattern(regexp = "^[a-zA-Z0-9]{5,16}$") String username,
                           @Pattern(regexp = "^[a-zA-Z0-9]{5,16}$") String password) {

        //查询用户
        User user = userService.findUserByUsername(username);
        //注册用户
        if (user != null) {
            //被占用
            return Result.error("用户名被占用");
        } else {
            //没有被占用
            userService.register(username, password);
            return Result.success();
        }
    }
//    public Result register(@Pattern(regexp = "^[a-zA-Z0-9]{5,16}$") String username, @Pattern(regexp = "^[a-zA-Z0-9]{5,16}$")String password) {
//
//        //注册用户
//        if (username != null && username.matches("^[a-zA-Z0-9]{5,16}$")
//                && password != null && password.matches("^[a-zA-Z0-9]{5,16}$")) {
//
//            //查询用户
//            User user = userService.findUserByUsername(username);
//
//            if (user != null) {
//                //被占用
//                return Result.error("用户名被占用");
//            } else {
//                //没有被占用
//                userService.register(username, password);
//                return Result.success();
//            }
//        } else return Result.error("参数不合法");
//    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^[a-zA-Z0-9]{5,16}$") String username,
                        @Pattern(regexp = "^[a-zA-Z0-9]{5,16}$") String password) {

        //根据用户名查询用户
        User user = userService.findUserByUsername(username);
        //判断该用户是否存在
        if (user == null) {
            return Result.error("用户不存在");
        } else {
            //判断密码是否正确
            if (Md5Util.getMD5String(password).equals(user.getPassword())) {
                //登陆成功
                //生成令牌
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", user.getId());
                claims.put("username", user.getUsername());
                String token = JwtUtil.genToken(claims);
                //把令牌存入Redis
                redisTemplate.opsForValue().set(token, token, 12, TimeUnit.HOURS);

                return Result.success(token);
            } else {
                return Result.error("密码错误");
            }
        }
    }

    @GetMapping("/userInfo")
    public Result userInfo(/*@RequestHeader("Authorization") String token*/) {
        //获取用户信息
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
        //在ThreadLocalUtil中获取username
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findUserByUsername(username);

        return Result.success(user);
    }

    //更新用户信息
    //@Validated 开启实体类校验
    //@RequestBody 传递Json格式
    @PutMapping("/update")
    public Result updateUser(@RequestBody @Validated User user) {
        userService.updateUser(user);
        return Result.success();
    }

    //更新用户头像
    //@RequestParam 请求头中获取
    //@URL 开启Url校验
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //更新密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("参数不合法");
        }

        //获取原密码
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findUserByUsername(username);

        //判断旧密码是否正确
        if (!Md5Util.getMD5String(oldPwd).equals(user.getPassword())) {
            return Result.error("旧密码错误");
        } else if (!newPwd.equals(rePwd)){
            return Result.error("两次输入的密码不一致");
        }
        //修改密码
        userService.updatePwd(newPwd);
        //删除Redis中的token
        redisTemplate.delete(token);

        return Result.success();
    }


}
