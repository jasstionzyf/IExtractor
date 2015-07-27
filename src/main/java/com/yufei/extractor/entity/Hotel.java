/**

 * 
 */
package com.yufei.extractor.entity;

import com.yufei.annotation.CollectionElementType;
import com.yufei.annotation.ExcelFieldOrder;
import com.yufei.annotation.ExcelHeaders;
import com.yufei.annotation.QueryEnable;
import com.yufei.pfw.entity.Entity;
import java.util.ArrayList;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created by zhaoyufei at 2012-8-5 下午12:25:22
 */
@XmlRootElement
@ExcelFieldOrder(fieldOrders="hotelName,oprice,discount,area,descripton,pictureNameMedia")
@ExcelHeaders(headers="酒店名称,原价,折扣,地址,描述,照片文件名称")
public class Hotel extends  Entity<Long>{



	
	/**
	 * 
	 */
	public Hotel() {
		// TODO Auto-generated constructor stub
	}
@QueryEnable(enable = true)
private String hotelName=null;
private String city=null;
private String leftTimeAvailabled;
private Integer orderedNumber;
//酒店地址，公交路线等
private String area;
private String price;
private String oprice;
public String getOprice() {
	return oprice;
}
public void setOprice(String oprice) {
	this.oprice = oprice;
}
private String discount;
//pictureNameMedia
private String pictureNameMedia;
@CollectionElementType(type=String.class)
private List<String> comments=new ArrayList<String>();
public List<String> getComments() {
	return comments;
}
private Integer commentsCount=null;
public Integer getCommentsCount() {
	return commentsCount;
}
@XmlElement
public void setCommentsCount(Integer commentsCount) {
	this.commentsCount = commentsCount;
}
@XmlElementWrapper(name="comments")
@XmlElement(name="comment")
public void setComments(List<String> comments) {
	this.comments = comments;
}
public String getPictureNameMedia() {
	return pictureNameMedia;
}
@XmlElement
public void setPictureNameMedia(String pictureNameMedia) {
	this.pictureNameMedia = pictureNameMedia;
}
//简短的介绍，描述
private String descripton=null;
public String getDescripton() {
	return descripton;
}
@XmlElement
public void setDescripton(String descripton) {
	this.descripton = descripton;
}
public Hotel(String hotelName, String city, String leftTimeAvailabled,
		Integer orderedNumber, String area, String price, String oPrice,
		String discount) {
	super();
	this.hotelName = hotelName;
	this.city = city;
	this.leftTimeAvailabled = leftTimeAvailabled;
	this.orderedNumber = orderedNumber;
	this.area = area;
	this.price = price;
	this.oprice = oPrice;
	this.discount = discount;
}
public String getHotelName() {
	return hotelName;
}
@XmlElement
public void setHotelName(String hotelName) {
	this.hotelName = hotelName;
}
public String getCity() {
	return city;
}
@XmlElement
public void setCity(String city) {
	this.city = city;
}
public String getLeftTimeAvailabled() {
	return leftTimeAvailabled;
}
@XmlElement
public void setLeftTimeAvailabled(String leftTimeAvailabled) {
	this.leftTimeAvailabled = leftTimeAvailabled;
}
public Integer getOrderedNumber() {
	return orderedNumber;
}
@XmlElement
public void setOrderedNumber(Integer orderedNumber) {
	this.orderedNumber = orderedNumber;
}
public String getArea() {
	return area;
}
@XmlElement
public void setArea(String area) {
	this.area = area;
}
public String getPrice() {
	return price;
}
@XmlElement
public void setPrice(String price) {
	this.price = price;
}

public String getDiscount() {
	return discount;
}
@XmlElement
public void setDiscount(String discount) {
	this.discount = discount;
}
@Override
public String toString() {
	return "Hotel [hotelName=" + hotelName + ", city=" + city
			+ ", leftTimeAvailabled=" + leftTimeAvailabled
			+ ", orderedNumber=" + orderedNumber + ", area=" + area
			+ ", price=" + price + ", oPrice=" + oprice + ", discount="
			+ discount + ", pictureNameMedia=" + pictureNameMedia
			+ ", descripton=" + descripton + ", toString()="
			+ super.toString() + "]";
}

public void addComments(String comment){
	this.comments.add(comment);
}
}
