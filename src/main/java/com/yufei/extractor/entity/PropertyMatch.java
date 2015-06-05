package com.yufei.extractor.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.yufei.pfw.entity.Entity;


@XmlRootElement()
@XmlType(propOrder={"property","match","matchType"})
public class PropertyMatch extends Entity{
public final static String regex_key="regex";
public final static String xpath_key="xpath";
	/**
	 * match 格式为：regex!regex!...;index
	 * 程序在处理的时候只需要更具regex去匹配目标内容返回字符串即可
	 * 如果有配置Index，并且如果匹配到多个字符串的话那么会去index位置的字符串
	 */
	public final static String matchType_common="commonMatchType";
	
	/**
	 * match 格式为：regex;regex;...
	 * 针对多媒体内容的提取（比如：图片额下载等）
	 */
	public final static String matchType_media="mediaMatchType";
	
	/**
	 * 格式为：目标字符串正则表达式:分页url正则表达式
	 * 针对集合信息的提取比如提取商品的评论信息那么正则表达式应该为：评论信息匹配正则表达式:评论url正则表达式
	 */
	public final static String matchType_collection="collectionMatchType";
	/**
	 * 格式为：评论信息正则表达式:分页url模板:m每页数目:记录总数
	 */
	public final static String matchType_collection_ua_assembleUrl="collection_ua_assembleUrl";
	/**
	 * 格式为：评论信息正则表达式:分页Url匹配正则表达式
	 */
	public final static String matchType_collection_ua_regUrl="collection_ua_regUrl";
	/**
	 * 格式为：评论信息正则表达式:分页url模板:m每页数目:记录总数
	 */
	public final static String matchType_collection_na_assembleUrl="collection_na_assembleUrl";
	/**
	 * 格式为：评论信息正则表达式:分页Url匹配正则表达式
	 */
	public final static String matchType_collection_na_regUrl="collection_na_regUrl";

	public final static String matchType_pictureNumber="pictureNumberMatchType";
	public final static String matchType_FilterContent="filterContentMatchType";

	@Override
	public String toString() {
		return "PropertyMatch [property=" + property + ", match=" + match + "]";
	}
	public PropertyMatch() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PropertyMatch(String property, Map<String, Object> match) {
		super();
		this.property = property;
		this.match = match;
	}
	private String property=null; 
	private Map<String,Object> match=new HashMap<String,Object>();
	private String matchType=PropertyMatch.matchType_common;
	public PropertyMatch(String property, Map<String, Object> match, String matchType) {
		super();
		this.property = property;
		this.match = match;
		this.matchType = matchType;
	}
	public String getMatchType() {
		return matchType;
	}
	@XmlElement
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getProperty() {
		return property;
	}
	@XmlElement
	public void setProperty(String property) {
		this.property = property;
	}
	public Map<String, Object> getMatch() {
		return match;
	}
    @XmlJavaTypeAdapter(MapAdapter.class)  

	public void setMatch(Map<String, Object> match) {
		this.match = match;
	}
	public static PropertyMatch getPropertyMatchFromListByPropertyName(String fieldName,
	List<PropertyMatch> propertyMatchs) {
		PropertyMatch propertyMatch =null;
		for(PropertyMatch pro:propertyMatchs){
			if(pro.getProperty().equalsIgnoreCase(fieldName)){
				propertyMatch=pro;
				break;
			}
		}
		return propertyMatch;
	}
	
		
 }