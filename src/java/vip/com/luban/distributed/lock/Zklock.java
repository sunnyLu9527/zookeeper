package com.luban.distributed.lock;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class Zklock implements Lock {


    private ThreadLocal<ZooKeeper> zk = new ThreadLocal<>();

    private String LOCK_NAME = "/LOCK";
    private ThreadLocal<String> CURRENT_NODE = new ThreadLocal<>() ;
    //

    public void init() {
        if (zk.get() ==null) {
            try {
                zk.set(new ZooKeeper("localhost:2181", 300, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        // ..
                    }
                }));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lock() {
        init();

        //
        if (tryLock()) {
            System.out.println(Thread.currentThread().getName()+"已经获取到锁了....");
        }

    }

    public boolean tryLock() {  // 阻塞
        String nodeName = LOCK_NAME + "/zk_";

        try {
            // /LOCK/zk_1
            CURRENT_NODE.set(zk.get().create(nodeName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL));


            List<String> list = zk.get().getChildren(LOCK_NAME, false); // zk_1, zk_2
            Collections.sort(list);

            String minNodeName = list.get(0);

            if (CURRENT_NODE.get().equals(LOCK_NAME+"/"+minNodeName)) {
                return true;
            } else {
                // currentIndex,
                // currentIndex-1

                String currentNodeSimpleName = CURRENT_NODE.get().substring(CURRENT_NODE.get().lastIndexOf("/")+1);
                Integer currentNodeIndex = list.indexOf(currentNodeSimpleName);
                String prevNodeName = list.get(currentNodeIndex-1);

                // 阻塞
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                zk.get().exists(LOCK_NAME + "/" + prevNodeName, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {

                        if (Event.EventType.NodeDeleted.equals(event.getType())) {
                            //....

                            countDownLatch.countDown();
                            System.out.println(Thread.currentThread().getName()+"被唤醒...");


                        }
                    }
                });

                System.out.println(Thread.currentThread().getName()+"阻塞住...");
                countDownLatch.await();
                return true;

            }


        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void unlock() {
        try {
            zk.get().delete(CURRENT_NODE.get(), -1);
            CURRENT_NODE.set(null);
            zk.get().close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
