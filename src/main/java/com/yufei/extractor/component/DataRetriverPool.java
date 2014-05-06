package com.yufei.extractor.component;

import com.yufei.dataget.dataretriver.DataRetrieverFactory;
import com.yufei.dataget.dataretriver.DataRetrieverFeatures;
import com.yufei.dataget.dataretriver.HttpDataRetriever;
import java.util.Queue;


import org.eclipse.jetty.util.ArrayQueue;

public class DataRetriverPool {
	private static DataRetriverPool dataRetriverPool=null;
    public static int defaultSize=10;
	public synchronized  static DataRetriverPool   getInstance(DataRetrieverFeatures dataRetrieverFeatures){
	if(dataRetriverPool==null){
		dataRetriverPool=new DataRetriverPool();
		for(int i=0;i<defaultSize;i++){
			HttpDataRetriever dataRetriever = DataRetrieverFactory
					.createDataRetriever(dataRetrieverFeatures);
		dataRetriverPool.freeUserInfos.add(dataRetriever);
		}
		
	}
	return dataRetriverPool;
	}
	private DataRetriverPool() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Queue<HttpDataRetriever> freeUserInfos=new ArrayQueue<HttpDataRetriever>(50);
    public synchronized void release(HttpDataRetriever amazonUserInfo){
    	freeUserInfos.add(amazonUserInfo);
    }
	public synchronized HttpDataRetriever get(){
    	return freeUserInfos.poll();
    }
}
