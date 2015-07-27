package com.yufei.extractor.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class InfoExtractorCfg {
	private List<PropertyMatch> propertyMatches=new ArrayList<PropertyMatch>();
	public List<PropertyMatch> getPropertyMatches() {
		return propertyMatches;
	}
	@XmlElementWrapper(name="propertyMatches")
	@XmlElement(name="propertyMatch")
	public void setPropertyMatches(List<PropertyMatch> propertyMatches) {
		this.propertyMatches = propertyMatches;
	}

}
