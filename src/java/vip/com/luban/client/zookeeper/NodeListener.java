package com.luban.client.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NodeListener {

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("localhost:2181", 1000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });

        zk.exists("/luban/1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType().equals(Event.EventType.NodeDataChanged)) {
                    System.out.println("zookeeper发现数据改变了");
                }
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}
