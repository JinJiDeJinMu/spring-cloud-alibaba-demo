package com.hjb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CloudTestApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        int size = 1024 * 1024 * 10;
        List<byte[]> list = new ArrayList<>();
        for (Integer i = 0; i < 1024; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new byte[size]);
        }
    }

}
