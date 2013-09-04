package com.yufei.extractor.component.pExtractor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.extractor.core.UrlExtractor;
import com.yufei.extractor.entity.PropertyMatch;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.PatternUtils;

public class CommonPExtractor implements PExtractor {
	public static final Log mLog = LogFactory.getLog(CommonPExtractor.class);


	/**
	 * @see com.yufei.extractor.component.pExtractor.PExtractor#extract()
	 * 
	 *  
	 */
	@Override
	public Object extract(PropertyMatch propertyMatch, String source) {
		// TODO Auto-generated method stub
		
		
		// TODO Auto-generated method stub
				String regex=(String) propertyMatch.getMatch().get(PropertyMatch.regex_key);
				String value = PatternUtils.getStrByRegex(source,regex );
				// 如果匹配到空值则直接返回部队此链接进行处理认为此链接为非内容链接
			if (CommonUtil.isEmptyOrNull(value)) {
					mLog.info(""+regex+" match null");
					return null;
					
		       
					//mLog.debug("内容为："+htmlContent);
					//return false;
				}
			//对于特殊价格(比如:12-23)
			//value=value.replaceAll("[\\s\\r\\n]", "");
			/*if(value.matches("[\\d.]{0,}-[\\d.]{0,}")){
				mLog.info("处理特殊价格"+value+"");
				value=value.substring(0, value.indexOf("-"));
				
				
				
			}*/
		return value;
	}

}
