package com.yufei.extractor.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.index.Indexed;

import com.yufei.annotation.QueryEnable;
import com.yufei.entity.Entity;
public class UfLink extends Entity {

	private static Log mLog = LogFactory.getLog(UfLink.class);
	private Long seedsiteId;
	@Indexed(unique=true)

	private String mallItemId=null;
    private Long mallId=0L;



	//唯一索引
	@Indexed(unique=true)
	private String link;
	private Integer status;
	private Integer depth;
	private String upLink;



	@QueryEnable(enable = true)
	private Date findTime=null;
	private Date updateTime=null;
	private String contentHashCode=null;
	/**
	 * 是否被处理（比如：里面的属性的抽取,或者采集到的网页信息发生变化需要重新进行信息提取等）
	 */
	private boolean isProcessed=false;
	public boolean getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	
	public Long getMallId() {
		return mallId;
	}
	public void setMallId(Long mallId) {
		this.mallId = mallId;
	}
	public String getContentHashCode() {
		return contentHashCode;
	}
	public void setContentHashCode(String contentHashCode) {
		this.contentHashCode = contentHashCode;
	}
	public Date getFindTime() {
		return findTime;
	}
	public Long getSeedsiteId() {
		return seedsiteId;
	}
	public void setSeedsiteId(Long seedsiteId) {
		this.seedsiteId = seedsiteId;
	}
	public void setOhc(String ohc) {
		this.ohc = ohc;
	}
	public void setFindTime(Date findTime) {
		this.findTime = findTime;
	}
    public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getStatus() {
		return status;
	}

	public String getMallItemId() {
		return mallItemId;
	}
	public void setMallItemId(String mallItemId) {
		this.mallItemId = mallItemId;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getUpLink() {
		return upLink;
	}

	public void setUpLink(String upLink) {
		this.upLink = upLink;
	}
	
  
	
	
	private String ohc=null;
	public Date getUpdateTime() {
		return updateTime;
		
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOhc() {
		return ohc;
	}
	@XmlElement(name = "ohc")
    public void sethc(String originalHtmlContent) {
		this.ohc = originalHtmlContent;
	}
	
    


	
}
