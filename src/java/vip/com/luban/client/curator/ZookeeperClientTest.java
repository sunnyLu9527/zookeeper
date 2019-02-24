package com.luban.client.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class ZookeeperClientTest {

    public static void main(String[] args) throws Exception {

        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework zk = CuratorFrameworkFactory.newClient("localhost:2181",5000,5000, retryPolicy);
        zk.start();

        // 持久节点
//        zk.create().withMode(CreateMode.PERSISTENT).forPath("/curator", "zhouyu".getBytes());

        // 临时节点
        zk.create().withMode(CreateMode.EPHEMERAL).forPath("/curator", "zhouyu".getBytes());


        // 持久顺序节点
        zk.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/curator_", "zhouyu".getBytes());
        zk.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/curator_", "zhouyu".getBytes());


        // 临时顺序节点
        String nodeName = zk.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/curator_EPHEMERAL_", "zhouyu".getBytes());
        zk.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/curator_EPHEMERAL_", "zhouyu".getBytes());

        System.out.println(nodeName);

        Stat stat = zk.checkExists().forPath("/curator");

        final NodeCache cache = new NodeCache(zk, "/luban/1");
        cache.start();
//
//        zk.getCuratorListenable().addListener(new CuratorListener() {
//            @Override
//            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
//                if (curatorEvent.getType().equals(CuratorEventType.DELETE)) {
//                    System.out.println("数据被删除了");
//                }
//            }
//        });



        cache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                byte[] ret = cache.getCurrentData().getData();
                System.out.println("new data:" + new String(ret));
            }


        });

//        zk.subscribeDataChanges("/lubanwatch", new IZkDataListener() {
//            @Override
//            public void handleDataChange(String dataPath, Object data) throws Exception {
//
//            }
//
//            @Override
//            public void handleDataDeleted(String dataPath) throws Exception {
//                System.out.println("数据被删了");
//            }
//        });
//
//        List<String> list = zk.getChildren("/");
//        System.out.println(list);

        System.in.read();

//        zk.close();
    }


}
