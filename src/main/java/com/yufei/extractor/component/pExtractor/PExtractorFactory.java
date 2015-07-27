package com.yufei.extractor.component.pExtractor;

import java.util.HashMap;
import java.util.Map;

import com.mchange.v2.log.MLog;
import com.yufei.extractor.entity.PropertyMatch;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;

public class PExtractorFactory {
	static Map<String,String>  matchTypePExtractors=new HashMap<String, String>();
	static{
		matchTypePExtractors.put(PropertyMatch.matchType_common, "com.yufei.infoExtractor.extractor.PExtractor.CommonPExtractor");
		matchTypePExtractors.put(PropertyMatch.matchType_collection, "com.yufei.infoExtractor.extractor.PExtractor.CollectionPExtractor");
		matchTypePExtractors.put(PropertyMatch.matchType_collection_na_assembleUrl, "com.yufei.infoExtractor.extractor.PExtractor.CollectionPExtractor");
		matchTypePExtractors.put(PropertyMatch.matchType_collection_na_regUrl, "com.yufei.infoExtractor.extractor.PExtractor.CollectionPExtractor");
		matchTypePExtractors.put(PropertyMatch.matchType_collection_ua_assembleUrl, "com.yufei.infoExtractor.extractor.PExtractor.CollectionPExtractor");
		matchTypePExtractors.put(PropertyMatch.matchType_collection_ua_regUrl, "com.yufei.infoExtractor.extractor.PExtractor.CollectionPExtractor");

		matchTypePExtractors.put(PropertyMatch.matchType_FilterContent, "com.yufei.infoExtractor.extractor.PExtractor.CRNPExtractor");

		matchTypePExtractors.put(PropertyMatch.matchType_media, "com.yufei.infoExtractor.extractor.PExtractor.MediaPExtractor");

		matchTypePExtractors.put(PropertyMatch.matchType_pictureNumber, "com.yufei.infoExtractor.extractor.PExtractor.PictureNumberPExtractor");



		
	}
public static PExtractor getPExtractor(PropertyMatch propertyMatch){
	if(CommonUtil.isEmptyOrNull(propertyMatch.getMatchType())){
		throw new  IllegalArgumentException();
	}
	PExtractor PExtractor=null;
	String className=matchTypePExtractors.get(propertyMatch.getMatchType());
	if(className==null){
		MLog.info("not support PExtractor! the fieldName is "+propertyMatch.getProperty()+"; the matchType is "+propertyMatch.getMatchType());
		return null;
	}
	try {
		PExtractor=(PExtractor) Class.forName(className).newInstance();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		MLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
	}
	return PExtractor;
}
}
