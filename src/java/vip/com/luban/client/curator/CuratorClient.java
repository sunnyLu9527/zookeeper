package com.luban.client.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class CuratorClient {

    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",
                new RetryNTimes(3, 1000));
        client.start();

//        client.create().withMode(CreateMode.EPHEMERAL).forPath("/data", "3".getBytes());


        String path = "/data";
//        NodeCache nodeCache = new NodeCache(client, path);
//        nodeCache.start(false);
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("123123");
//            }
//        });

        client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("用的是watch");
            }
        }).forPath(path);

        System.in.read();


    }
}
