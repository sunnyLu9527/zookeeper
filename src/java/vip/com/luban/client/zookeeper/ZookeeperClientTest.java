package com.luban.client.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.util.List;

public class ZookeeperClientTest {

    public static void main(String[] args) throws Exception {

        ZooKeeper zk = new ZooKeeper("localhost:2181", 10000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });

        // 持久节点
        zk.create("/zk", "zhouyu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 临时节点
//        zk.create("/zk", "zhouyu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);


        // 持久顺序节点
//        zk.create("/zk_", "zhouyu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
//        zk.create("/zk_", "zhouyu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

zk.getData()

        // 临时顺序节点
        String nodeName = zk.create("/zk", "zhouyu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
//        zk.create("/zkclient_EPHEMERAL_", "zhouyu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

//        System.out.println(nodeName);
//
//        System.out.println(zk.exists("/lubanwatch", false));


//        zk.exists("/lubanwatch", new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                if (event.getType().equals(Event.EventType.NodeDeleted)) {
//                    System.out.println("数据被删了");
//                }
//            }
//        });

        List<String> list = zk.getChildren("/", false);
        System.out.println(list);

        System.in.read();

//        zk.close();
    }


}
