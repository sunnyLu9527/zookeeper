package com.luban.client.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NodeListener {

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(5);

        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
//        CuratorFramework client = CuratorFrameworkFactory
//                .newClient("192.168.1.105:2181",5000,5000, retryPolicy);

        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        final NodeCache cache = new NodeCache(client, "/luban/1");
        cache.start();

        cache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                byte[] ret = cache.getCurrentData().getData();
                System.out.println("curator发现数据改变了" + new String(ret));
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}
