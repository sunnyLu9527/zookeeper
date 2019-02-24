package com.luban.client.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;

public class CreateNode {

    public static void main(String[] args) throws IOException {
        ZkClient zc = new ZkClient("localhost:2181", 10000, 10000, new SerializableSerializer());
        System.out.println("conneted ok!");


        User u = new User();
        u.setId(1);
        u.setName("test");
//        String path = zc.create("/test111_PERSISTENT", u, CreateMode.PERSISTENT);
//        String path = zc.create("/test111_EPHEMERAL", u, CreateMode.EPHEMERAL);
//        String path = zc.createPersistentSequential("/test111_PERSISTENT_", u);
//        String path1 = zc.createPersistentSequential("/test111_PERSISTENT_", u);
//        String path2 = zc.createPersistentSequential("/test111_PERSISTENT_", u);

        String path = zc.createEphemeralSequential("/test111_EPHEMERAL_", u);
        String path1 = zc.createEphemeralSequential("/test111_EPHEMERAL_", u);
        String path2 = zc.createEphemeralSequential("/test111_EPHEMERAL_", u);
        System.out.println("created path:" + path);

        System.in.read();
    }
}
