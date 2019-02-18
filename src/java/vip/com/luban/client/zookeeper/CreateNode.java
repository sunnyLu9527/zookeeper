package com.luban.client.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class CreateNode {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("localhost:2181,localhost:2182/test", 3000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                // ...
            }
        });

        zk.create("/luban", "zhouyu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.in.read();
    }
}
