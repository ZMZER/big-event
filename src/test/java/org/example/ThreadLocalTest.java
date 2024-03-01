package org.example;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void testThreadLocal() {

        ThreadLocal threadLocal = new ThreadLocal();

        //开启两个线程
        new Thread(()->{
            threadLocal.set("张三");
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
        },"蓝色").start();

        new Thread(()->{
            threadLocal.set("李四");
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
        },"绿色").start();
    }
}
