package com.kstarrain.utils;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: DongYu
 * @create: 2019-11-15 09:26
 * @description: 简单的分布式锁的实现
 */
@Slf4j
public class HighPerformanceZkLock{

	//zk地址和端口
	private static final String ZK_ADDRESSES = "127.0.0.1:2181";

	private ZkClient zkClient = new ZkClient(ZK_ADDRESSES, 1000);


	private static final String PARENT_PATH = "/highPerformance_lock";
	//当前节点路径
	private String currentPath;
	//前一个节点的路径
	private String beforePath;
	
	private CountDownLatch countDownLatch = null;

	
	public HighPerformanceZkLock() {
		zkClient.createPersistent(PARENT_PATH, true);
	}

	public void getLock() {
		if (!tryLock()) {
			//等待获取锁
			waitLock();
			//递归重新获取锁
			getLock();
		}
	}


	private boolean tryLock() {
		//如果currentPath为空则为第一次尝试加锁，第一次加锁赋值currentPath
		if (StringUtils.isBlank(currentPath)) {
			//在path下创建一个临时的顺序节点
			currentPath = zkClient.createEphemeralSequential(PARENT_PATH + "/", "lock");
		}
		//获取所有的临时节点，并排序
		List<String> childrens = zkClient.getChildren(PARENT_PATH);
		Collections.sort(childrens);
		if (currentPath.equals(PARENT_PATH + "/" + childrens.get(0))) {
			log.info(Thread.currentThread().getName()+"-------获取锁成功");
			return true;
		}else {//如果当前节点不是排名第一，则获取它前面的节点名称，并赋值给beforePath
			int pathLength = PARENT_PATH.length();
			int wz = Collections.binarySearch(childrens, currentPath.substring(pathLength + 1));
			beforePath = PARENT_PATH + "/" + childrens.get(wz -1);
			return false;
		}

	}

	private void waitLock() {
		IZkDataListener lIZkDataListener = new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				if (null != countDownLatch){
					countDownLatch.countDown();
				}
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {

			}
		};
		//监听前一个节点的变化
		zkClient.subscribeDataChanges(beforePath, lIZkDataListener);

		if (zkClient.exists(beforePath)) {
			log.info(Thread.currentThread().getName()+"-获取锁失败 等待获取锁...");
			countDownLatch = new CountDownLatch(1);
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		zkClient.unsubscribeDataChanges(beforePath, lIZkDataListener);
	}

	public void releaseLock() {
		if (null != zkClient) {
			zkClient.delete(currentPath);
	        zkClient.close();
		}

	}



}