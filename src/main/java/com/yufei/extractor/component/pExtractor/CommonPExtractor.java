package com.yufei.extractor.component.pExtractor;

import com.yufei.extractor.core.JsoupDocumentThreadLocal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.extractor.entity.PropertyMatch;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.PatternUtils;
import com.yufei.utils.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        if(!StringUtils.hasLength(source)){
            return null;
        }
        String value = null;
        // TODO Auto-generated method stub
        String regex = (String) propertyMatch.getMatch().get(PropertyMatch.regex_key);
        if (StringUtils.hasLength(regex)) {
            value = PatternUtils.getStrByRegex(source, regex);
            // 如果匹配到空值则直接返回部队此链接进行处理认为此链接为非内容链接
            if (CommonUtil.isEmptyOrNull(value)) {
                mLog.info("" + regex + " match null");
                return null;

            }
        }
        String xpath=(String) propertyMatch.getMatch().get(PropertyMatch.xpath_key);
          if (StringUtils.hasLength(xpath)) {
             Document dc=JsoupDocumentThreadLocal.getDocument();
             if(dc==null){
                 JsoupDocumentThreadLocal.setDocument(Jsoup.parse(source));
                 dc=JsoupDocumentThreadLocal.getDocument();
                value=dc.select(value).first().text();
             }
            // 如果匹配到空值则直接返回部队此链接进行处理认为此链接为非内容链接
            if (CommonUtil.isEmptyOrNull(value)) {
                mLog.info("" + xpath + " match null");
                return null;

            }
        }

        return value;
    }

}
