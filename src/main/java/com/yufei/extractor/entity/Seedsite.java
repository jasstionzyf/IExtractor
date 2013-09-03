package com.yufei.extractor.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.yufei.dataretriver.DataRetrieverFeatures;
import com.yufei.pfw.entity.Entity;

@XmlRootElement
public class Seedsite extends Entity {
final public static Integer UN_CRAWLERED=0; 
final public static Integer IS_CRAWLERING=1; 
final public static Integer IS_CRAWLERED=2; 
//0表示未被回传，1表示已经回传
final public static Integer ISFETCHED=0;
	//种子网址地址
	private String siteName;
	//种子网址域名
	private String domain;
	//爬取状态
	private Integer status=UN_CRAWLERED;
	private Integer isFetched=ISFETCHED;
	private String siteDesc="";
	private String mallId=null;
	
	//指定被刺处理所用的Job实现类完整名称包含包名，类名
	
	

	
	//表示任务执行周期
	private String crawlerTime;
	public String getCrawlerTime() {
		return crawlerTime;
	}
	public void setCrawlerTime(String crawlerTime) {
		this.crawlerTime = crawlerTime;
	}
	
	
	//是否已回传
	public Integer getIsFetched() {
		return isFetched;
	}
	public void setIsFetched(Integer isFetched) {
		this.isFetched = isFetched;
	}
	public String getSiteDesc() {
		return siteDesc;
	}
	public void setSiteDesc(String siteDesc) {
		this.siteDesc = siteDesc;
	}
	
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}




	public String getMallId() {
		return mallId;
	}
	public void setMallId(String mallId) {
		this.mallId = mallId;
	}




	//标识上次爬取结束时间
	private Date lastCrawleredTime;
	//标识上次更新结束时间
	private Date lastUpdatedTime;
	public Date getLastCrawleredTime() {
		return lastCrawleredTime;
	}

	public void setLastCrawleredTime(Date lastCrawleredTime) {
		this.lastCrawleredTime = lastCrawleredTime;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	

	

	
	
	

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/** default constructor */
	public Seedsite() {
		super();
	}

	

	


	
	public String getSiteName() {
		return siteName;
	}
@XmlElement
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDomain() {
		return domain;
	}
@XmlElement
	public void setDomain(String domain) {
		this.domain = domain;
	}

	

	

private UrlExtractorCfg urlExtractorCfg;
private InfoExtractorCfg infoExtractorCfg;
private DataRetrieverFeatures dataRetrieverFeatures=null;






public UrlExtractorCfg getUrlExtractorCfg() {
	return urlExtractorCfg;
}
public void setUrlExtractorCfg(UrlExtractorCfg urlExtractorCfg) {
	this.urlExtractorCfg = urlExtractorCfg;
}
public InfoExtractorCfg getInfoExtractorCfg() {
	return infoExtractorCfg;
}
public void setInfoExtractorCfg(InfoExtractorCfg infoExtractorCfg) {
	this.infoExtractorCfg = infoExtractorCfg;
}
public DataRetrieverFeatures getDataRetrieverFeatures() {
	return dataRetrieverFeatures;
}
@XmlElement
public void setDataRetrieverFeatures(DataRetrieverFeatures dataRetrieverFeatures) {
	this.dataRetrieverFeatures = dataRetrieverFeatures;
}






}
