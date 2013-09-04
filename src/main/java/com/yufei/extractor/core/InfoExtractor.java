package com.yufei.extractor.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.extractor.component.pExtractor.PExtractor;
import com.yufei.extractor.component.pExtractor.PExtractorFactory;
import com.yufei.extractor.entity.PropertyMatch;
import com.yufei.extractor.entity.Seedsite;
import com.yufei.extractor.entity.UfLink;
import com.yufei.pfw.service.PfwService;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;


/**
 * @author jasstion
 * only used to parse the html content and get needed infomation
 */
public class InfoExtractor implements Extractor {
	public static final Log mLog = LogFactory.getLog(InfoExtractor.class);

	private Seedsite seedSite;
	private Class savedClass=null;
    private Object savedObj;

	public InfoExtractor(Seedsite seedSite) {
		super();
		this.seedSite = seedSite;
	}

private void prepare(){
	 String entityClaName=null;//seedSite.getEntity();
	 if(CommonUtil.isEmptyOrNull(entityClaName)){
		 String message = "entityClaName配置为空,请重新配置!";
			mLog.error(message);
	    	throw new RuntimeErrorException(new Error(message));
	 }
	
		try {
			savedClass=Class.forName(entityClaName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String message = "实例化类名："+entityClaName+"的类出错！+"+ExceptionUtil.getExceptionDetailsMessage(e)+"";
			mLog.error(message);
	    	throw new RuntimeErrorException(new Error(message));
		} 
	
}

/**
	 * @see com.yufei.extractor.core.Extractor#extract()
	 * 
	 *  
	 */
	public void extract() {
		prepare();
		Map map=new HashMap();
		map.put("seedsiteId:=", seedSite.getId());
		map.put("isProcessed:=", false);

		

     List<UfLink> ufLinks=PfwService.pfwService.query(map, UfLink.class);
     if(CommonUtil.isEmptyOrNull(ufLinks)){
    	 return;
     }
     for(UfLink ufLink:ufLinks){
    	 try{
    		 
    	 if(CommonUtil.isEmptyOrNull(ufLink.getOhc())){
    		 continue;
    	 }
    	 
    	List<PropertyMatch> propertyMatches =seedSite.getInfoExtractorCfg().getPropertyMatches();
 		List<Field> fileds = CommonUtil.getAllDeclareFields(savedClass);
 		String htmlContent =ufLink.getOhc();
 		String fieldName = null;
 		String value = null;
 		PropertyMatch propertyMatch = null;
 		String fieldRegex = null;
 	    savedObj=savedClass.newInstance();

 		for (Field field : fileds) {
 			savedClass=null;
 			// collection
 			propertyMatch = PropertyMatch.getPropertyMatchFromListByPropertyName(
 					field.getName(), propertyMatches);

 			if (propertyMatch == null) {
 				continue;
 			}
 			PExtractor  pExtractor=PExtractorFactory.getPExtractor(propertyMatch);
 			if(pExtractor==null){
 				mLog.info("PExtractors not support the type of "+propertyMatch.getMatchType()+"");
 				continue;
 			}
 			Object result=pExtractor.extract(propertyMatch, htmlContent);
 			if(result!=null){
 				
 			} 			CommonUtil.setPropertyForEntity(savedClass, value, fieldName);


 		}//fields set end
		PfwService.pfwService.save(savedClass);

    	 }catch(Exception e){
    		 mLog.info("when parser the "+ufLink.getLink()+" error!");
    		 mLog.debug("error message is "+ExceptionUtil.getExceptionDetailsMessage(e)+"");

    	 }

    	 
     }//ufLinks ends

	}

}
