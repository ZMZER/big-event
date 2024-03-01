package org.example.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
//lombok 在编译阶段生成get set toString等方法
@Data
public class User {


    @NotNull    //不能为null
    private Integer id;//主键ID

    private String username;//用户名

    @JsonIgnore //在User被转换为Json字符串的时候 忽略password
    private String password;//密码

    @NotEmpty   //不能为null且不能为空
    private String nickname;//昵称

    @NotEmpty   //不能为null且不能为空
    @Email      //邮箱格式注解
    private String email;//邮箱

    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

}
