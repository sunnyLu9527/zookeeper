package com.luban.client.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class CreateSession {

    public static void main(String[] args) throws InterruptedException {
//        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
        RetryPolicy retryPolicy = new RetryNTimes(5, 1000);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        Thread.sleep(Integer.MAX_VALUE);
    }
}
