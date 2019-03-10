package com.luban.client.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class ZookeeperClientTest {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 默认的watch
        ZooKeeper client = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("连接的时候" + event);
            }
        });

        Stat stat = new Stat();
        client.getData("/testW", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                    if (Event.EventType.NodeDataChanged.equals(event.getType())) {
                        System.out.println("数据发送了改变");
                    }
            }
        }, stat);


//        String s = new String(client.getData("/data", false, stat));

//        client.create("/data", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

//        client.getData("/data", false, new AsyncCallback.DataCallback() {
//            @Override
//            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
//                System.out.println("123123123");
//            }
//        }, null);


//        client.exists("/data", new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                System.out.println("watch:" + event);
//            }
//        });

        System.in.read();
    }
}
