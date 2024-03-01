package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen() {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");

        //生成Jwt代码
        String token = JWT.create()
                .withClaim("user", claims)  //添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))   //设置过期时间
                .sign(Algorithm.HMAC256("zmer"));   //指定签名算法 配置密钥

        System.out.println(token);
    }

    @Test
    public void testParse() {
        //定义token 模拟传过来的token
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" + //Header 头 记录令牌类型和加密算法和密钥
                ".eyJleHAiOjE3MDY5NzcxNDksInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoi5byg5LiJIn19" + //PayLoad 载荷 记录信息
                ".mvS0ZpZ2JOzb-b2knqHt0mR3AwCjfIPNc6Zh2D295rg"; //Signature 签名 对头和载荷加密计算得来

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("zmer")).build();   //生成解密工具

        DecodedJWT decodedJWT = jwtVerifier.verify(token);  //验证token生成一个解析后的JWT对象

        Map<String, Claim> claims = decodedJWT.getClaims();

        System.out.println(claims.get("user"));

    }
}
