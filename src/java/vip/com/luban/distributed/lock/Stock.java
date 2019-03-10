package com.luban.distributed.lock;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class Stock {

    private static Integer COUNT = 1;

    public boolean reduceStock() {
        if (COUNT > 0) {
            COUNT--;
            return true;
        }

        return false;
    }
}
