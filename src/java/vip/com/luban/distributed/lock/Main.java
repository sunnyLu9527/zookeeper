package com.luban.distributed.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
        Thread thread1 = new Thread(new UserThread(), "user1");
        Thread thread2 = new Thread(new UserThread(), "user2");

        thread1.start();
        thread2.start();

    }

//    static Lock lock = new ReentrantLock();
    static Lock lock = new Zklock();

    static class UserThread implements Runnable {
        @Override
        public void run() {
            new Order().createOder();
//            lock.tryLock(); // 阻塞
            lock.lock();
            Boolean result = new Stock().reduceStock();
            lock.unlock();
            if (result) {
                System.out.println(Thread.currentThread().getName()+"减库存成功");
                new Pay().pay();
            } else {
                System.out.println(Thread.currentThread().getName()+"减库存失败");
            }

        }
    }
}
