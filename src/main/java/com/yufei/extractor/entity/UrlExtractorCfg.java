package com.yufei.extractor.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.yufei.entity.PaginationRule;
@XmlRootElement
public class UrlExtractorCfg {
private   PaginationRule paginationRule=null;
private List<String> urlRegexes=null;	
private List<String> ulLinkRegexes=null;	
private List<String> catalogLinkRegexes=null;	

private String idRegex=null;

	
	/**
	 * 如果等于-1表示全网抓取
	 */
	private Integer depth;



	public Integer getDepth() {
		return depth;
	}
	@XmlElement
	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public PaginationRule getPaginationRule() {
		return paginationRule;
	}

	public List<String> getUrlRegexes() {
		return urlRegexes;
	}
	
	public String getIdRegex() {
		return idRegex;
	}
	public void setIdRegex(String idRegex) {
		this.idRegex = idRegex;
	}
	@XmlElementWrapper(name="urlRegexes")
	@XmlElement(name="urlRegex")
	public void setUrlRegexes(List<String> urlRegexes) {
		this.urlRegexes = urlRegexes;
	}

	public List<String> getCatalogLinkRegexes() {
		return catalogLinkRegexes;
	}
	@XmlElementWrapper(name="catalogLinkRegexes")
	@XmlElement(name="catalogLinkRegex")
	public void setCatalogLinkRegexes(List<String> catalogLinkRegexes) {
		this.catalogLinkRegexes = catalogLinkRegexes;
	}
	@XmlElement
	public void setPaginationRule(PaginationRule paginationRule) {
		this.paginationRule = paginationRule;
	}
    
	public List<String> getUlLinkRegexes() {
		return ulLinkRegexes;
	}
	@XmlElementWrapper(name="ulLinkRegexes")
	@XmlElement(name="ulLinkRegex")
	public void setUlLinkRegexes(List<String> ulLinkRegexes) {
		this.ulLinkRegexes = ulLinkRegexes;
	}


}
