package com.kstarrain;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * @author: DongYu
 * @create: 2019-11-12 09:59
 * @description: 使用Curator操作zooKeeper
 *               参考文档：https://www.cnblogs.com/jinjian91/p/9226977.html
 *                       https://www.cnblogs.com/houzheng/p/9773949.html
 *                       https://www.jianshu.com/p/b789d7d6b3b2
 */
@Slf4j
public class CuratorTest {

    private static final String ZK_ADDRESSES = "127.0.0.1:2181";
    private static CuratorFramework curatorClint = null;


    @Before
    public void init() throws Exception {
        curatorClint  = CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDRESSES) //zkClint连接地址
                .connectionTimeoutMs(2000) //连接超时时间
                .sessionTimeoutMs(10000) //连接超时时间 //会话超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))//重试策略
                .build();
        curatorClint.start();
    }


    /**
     * 创建数据节点到zk中
     */
    @Test
    public void add() throws Exception {

//        curatorClint.create().forPath("/test002","儿子".getBytes());//路径、数据内容

       //creatingParentsIfNeeded:同时创建父节点  withMode:指定节点类型（不加withMode默认为持久类型节点）
        curatorClint.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/test002/test2","儿子".getBytes());//路径、数据内容

    }


    /**
     * 获取子节点
     */
    @Test
    public void getChildren() throws Exception{

        List<String> childrens = curatorClint.getChildren().forPath("/test002");
        for (String child : childrens) {
            log.info(child);
        }
    }


    /**
     * 判断节点是否存在
     */
    @Test
    public void exist() throws Exception{

        Stat stat = curatorClint.checkExists().forPath("/test002");

        System.out.println(stat != null ? "存在" : "不存在");
    }


    /**
     * 获取节点数据
     */
    @Test
    public void queryData() throws Exception{

        byte[] data = curatorClint.getData().forPath("/test002");
        System.out.println(new String(data));
    }


    /**
     * 修改节点数据
     */
    @Test
    public void update() throws Exception{

//        curatorClint.setData().forPath("/test002", "貂蝉".getBytes());
        curatorClint.setData().forPath("/test002/test2", "貂蝉".getBytes());


    }


    /**
     * 删除节点
     */
    @Test
    public void delete() throws Exception{

//        curatorClint.delete().forPath("/test002");

        curatorClint.delete().deletingChildrenIfNeeded().forPath("/test002/test2"); //递归删除子节点

    }



    /**
     * 监听节点数据变化
     */
    @Test
    public void watchData() throws Exception{

        String node = "/test002";

        //建立一个cache缓存,监听节点本身数据的变化
        NodeCache nodeCache = new NodeCache(curatorClint, node);
        nodeCache.start(true);//第一次启动的时候就会立刻在Zookeeper上读取对应节点的数据内容，并保存在Cache中
        if (nodeCache.getCurrentData() != null) {
            System.out.println("节点初始化数据为：" + new String(nodeCache.getCurrentData().getData()));
        } else {
            System.out.println("节点初始化数据为空...");
        }

        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged(){
                if (nodeCache.getCurrentData() == null) {
                    log.warn("监听到 节点:{} 已经不存在", node);
                    return;
                }
                log.warn("监听到 节点:{} 的数据 更改为 {}", nodeCache.getCurrentData().getPath(), new String(nodeCache.getCurrentData().getData()));
            }
        });


        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

    }


    /**
     * 监听子节点变化(只能监听子节点，孙节点无效)
     */
    @Test
    public void watchChild() throws Exception {

        String childNode = "/test002";

        //建立一个cache缓存,监听子节点变化
        PathChildrenCache childrenCache = new PathChildrenCache(curatorClint, childNode, true);

        /**
         * StartMode: 初始化方式
         * POST_INITIALIZED_EVENT：异步初始化，初始化之后会触发事件
         * NORMAL：异步初始化
         * BUILD_INITIAL_CACHE：同步初始化
         */
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        List<ChildData> childDataList = childrenCache.getCurrentData();
        System.out.println("当前数据节点的子节点数据列表：");
        for (ChildData cd : childDataList) {
            String childData = new String(cd.getData());
            System.out.println(childData);
        }

        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {

                switch (event.getType()) {
                    case CHILD_ADDED:
                        log.warn("添加子节点 : {}", event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        log.warn("更新子节点 : {} - {}" , event.getData().getPath(), new String(event.getData().getData()));
                        break;
                    case CHILD_REMOVED:
                        log.warn("删除子节点 : {}", event.getData().getPath());
                        break;
                    default:
                        break;
                }
            }

        });

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }


}
