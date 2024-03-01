package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

//在测试用例执行之前 会先初始化Spring容器
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet() {
//        往Redis中存储一个键值对 StringRedisTemplate.set("key", "value");
        stringRedisTemplate.opsForValue().set("username", "zhangsan");

    }

    @Test
    public void testGet() {
        stringRedisTemplate.delete("username");
        System.out.println(stringRedisTemplate.opsForValue().get("username"));
    }

}
