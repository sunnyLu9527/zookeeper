package com.luban.client.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class CreateNode {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                // ...

                System.out.println("connection"+watchedEvent.getPath());
            }
        });

//        zk.create("/luban4445", "zhouyu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);



        Stat stat = new Stat();
        zk.getData("/luban4445", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("get");
            }
        }, stat);
//        zk.setData(name, "new".getBytes(), stat.getVersion(), new AsyncCallback.StatCallback() {
//            @Override
//            public void processResult(int rc, String path, Object ctx, Stat stat) {
//                System.out.println("set成功了");
//            }
//        }, null);

        System.in.read();
    }
}
