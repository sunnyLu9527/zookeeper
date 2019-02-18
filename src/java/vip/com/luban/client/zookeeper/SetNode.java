package com.luban.client.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class SetNode {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("localhost:2181,localhost:2182/test", 3000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                // ...

            }
        });

        String name = "/luban";

        Stat stat = new Stat();
        zk.getData(name, null, stat);
        zk.setData(name, "new".getBytes(), stat.getVersion(), new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                System.out.println("set成功了");
            }
        }, null);

        System.in.read();
    }
}
