package com.luban.distributed.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class Config {


    private Map<String, String> cache = new HashMap<>();
    private CuratorFramework client;
    private static final String CONFIG_PREFIX = "/CONFIG";


    public Config() {
        this.client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(3, 1000));
        client.start();

        init();
    }

    public void init() {
        //
        try {
            List<String> childrenNames = client.getChildren().forPath(CONFIG_PREFIX);
            for (String name : childrenNames) {
                String value = new String(client.getData().forPath(CONFIG_PREFIX + "/" + name));
                cache.put(name, value);
            }


            // 绑定一个监听器
            // /CONFIG
            // 增加，修改,删除

            PathChildrenCache watcher = new PathChildrenCache(client, CONFIG_PREFIX, true);

            watcher.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    String path = event.getData().getPath();

                    if (path.startsWith(CONFIG_PREFIX)) {
                        String key = path.replace(CONFIG_PREFIX + "/", "");
                        if (PathChildrenCacheEvent.Type.CHILD_ADDED.equals(event.getType()) ||
                                PathChildrenCacheEvent.Type.CHILD_UPDATED.equals(event.getType())) {
                            cache.put(key, new String(event.getData().getData()));
                        } else if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(event.getType())) {
                            cache.remove(key);
                        }
                    }

                }
            });


            watcher.start();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // 新增，update
    public void save(String name, String value) {
        // zk
        // cache
        String configFullName = CONFIG_PREFIX + "/" + name;

        try {
            Stat stat = client.checkExists().forPath(configFullName);
            if (stat == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(configFullName, value.getBytes());
            } else {
                client.setData().forPath(configFullName, value.getBytes());
            }

            cache.put(name, value);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // watch

    public String get(String name) {
        // cache
        return cache.get(name);
    }
}
