package com.luban.client.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryUntilElapsed;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NodeChildrenListener {

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

        final PathChildrenCache cache = new PathChildrenCache(client, "/luban", true);
        cache.start();

        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED:" + pathChildrenCacheEvent.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED:" + pathChildrenCacheEvent.getData());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED:" + pathChildrenCacheEvent.getData());
                        break;
                    default:
                        break;
                }
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}
