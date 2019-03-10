package com.luban.distributed.config;

import java.util.concurrent.TimeUnit;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        config.save("timeout", "100");

        for (int i=0; i<100; i++) {
            System.out.println(config.get("timeout"));

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
