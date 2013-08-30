package com.yufei.extractor.core;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.component.repetition.IsRepetive;
import com.yufei.component.repetition.IsRepetiveBatSet;
import com.yufei.dataretriver.HttpDataRetriever;
import com.yufei.entity.PaginationRule;
import com.yufei.extractor.component.DataRetriverPool;
import com.yufei.extractor.entity.Seedsite;
import com.yufei.extractor.entity.UfLink;
import com.yufei.pfw.service.PfwService;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.PatternUtils;

public class UrlExtractor implements Extractor {
	public static final Log mLog = LogFactory.getLog(UrlExtractor.class);

	private List<String> currentDepthUrls=new ArrayList<String>();
	private   List<String> nextDepthUrls=new ArrayList<String>();
	private int currentDepth;
	private  int depth;
	private String  url0=null;
	private  Seedsite seedSite;
	private DataRetriverPool dataRetriverPool=null;
	private IsRepetive isRepetive=IsRepetiveBatSet.getInstance();
    private void prepare() {
    	depth=seedSite.getUrlExtractorCfg().getDepth();
    	url0=seedSite.getSiteName();
    	currentDepth=0;
    	dataRetriverPool=DataRetriverPool.getInstance(seedSite.getDataRetrieverFeatures());
    	currentDepthUrls.add(url0);
    	PaginationRule paginationRule=seedSite.getUrlExtractorCfg().getPaginationRule();
		if(!(paginationRule==null||paginationRule.getPaginationTemplate()==null||paginationRule.getUrlParameters().size()==0)){
			List<String> urls=null;
            urls=CommonUtil.generateUrlsByPaginationRule(paginationRule);
            currentDepthUrls.addAll(urls);
		}
		
	}

	public UrlExtractor(Seedsite seedSite) {
		super();
		this.seedSite = seedSite;
	}
    private List<String> getLinks(String upLink){
    	List<String> links=new ArrayList<String>();
    
    	String	 htmlContent=null;
		HttpDataRetriever httpDataRetriever = dataRetriverPool.get();
		htmlContent = getHtmlContent(upLink,  httpDataRetriever);
		if(htmlContent==null){
			return links;
		}
	    List<String> filterregexes = this.seedSite.getUrlExtractorCfg().getUrlRegexes();
	    if(CommonUtil.isEmptyOrNull(filterregexes)){
	    	String message = "UrlRegexes配置为空,请重新配置!";
			mLog.error(message);
	    	throw new RuntimeErrorException(new Error(message));
	    }
		List<String> filterUrls = PatternUtils.getListStrByRegex(htmlContent, CommonUtil.LinkStringWithSpecialSymbol(filterregexes, "!"));
		for(String filterUrl:filterUrls){
			try{
        if (!CommonUtil.isValidUrl(filterUrl)) {
			continue;
			}
            filterUrl = CommonUtil.formatUrl(filterUrl);
			filterUrl = CommonUtil.normalizeUrl(filterUrl, upLink);
			mLog.info("经过规范之后的url："+filterUrl+"");
			if(isRepetive.isRepetive(filterUrl)){
				continue;
			}
			List<String> ulLinkRegexes = seedSite.getUrlExtractorCfg().getUlLinkRegexes();
			if(CommonUtil.isEmptyOrNull(ulLinkRegexes)){
				String message = "ulLinkRegexes配置为空,请重新配置!";
				mLog.error(message);
		    	throw new RuntimeErrorException(new Error(message));
			}
			if(CommonUtil.isEmptyOrNull(filterUrl)){
				continue;
			}
			if(PatternUtils.match(filterUrl, CommonUtil.LinkStringWithSpecialSymbol(ulLinkRegexes, "!"))){
				//提取商品标识查看商品是否已经收录
			    String mallItemId = seedSite.getUrlExtractorCfg().getIdRegex();
			    String mallId=seedSite.getMallId();
                Map map=new HashMap();
                map.put("mallId:=", mallId);
                map.put("mallItemId:=", mallItemId);
                List<UfLink> ufs=PfwService.pfwService.query(map, UfLink.class);
                if(!CommonUtil.isEmptyOrNull(ufs)){
                	continue;
                }
				UfLink ufLink=new UfLink();
				ufLink.setFindTime(new Date());
			    //ufLink.sethc(getHtmlContent(filterUrl, dataRetriverPool.get()));
			    ufLink.setLink(filterUrl);
			    ufLink.setSeedsiteId(seedSite.getId());
			    ufLink.setUpLink(upLink);
				ufLink.setMallItemId(PatternUtils.getStrByRegex(filterUrl, mallItemId));
				ufLink.setMallId(Long.parseLong(mallId));
				PfwService.pfwService.save(ufLink);
				continue;
			}
			links.add(filterUrl);
		}catch(Exception e){
			mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
		}
		}
		
		return links;
    }

	private String getHtmlContent(String upLink,
			HttpDataRetriever httpDataRetriever) {
		String htmlContent=null;
		try {
			httpDataRetriever.setUrl(new URL(upLink));
			httpDataRetriever.connect();
			htmlContent=httpDataRetriever.getHtmlContent();
			httpDataRetriever.disconnect();
			dataRetriverPool.release(httpDataRetriever);
		} catch (Exception e) {
		} 

		return htmlContent;
	}
	/**
	 * @see com.yufei.extractor.core.Extractor#extract()
	 * 
	 *  
	 */
	public void extract() {
		prepare();
		if(depth==-1){
			depth=1000;
		}
		int newUrls=0;
	
		for(int i=0;i<depth;i++){
			int totalExtractUrls=0;
			List<String> tempExtractedUrlsByOneUrl=null;
		     for(String url:currentDepthUrls){
		    	 tempExtractedUrlsByOneUrl=getLinks(url);
		    	 totalExtractUrls+=tempExtractedUrlsByOneUrl.size();
		    	 nextDepthUrls.addAll(tempExtractedUrlsByOneUrl);
		     }
		     if(totalExtractUrls==0){
		    	 break;
		     }
			i++;
			currentDepthUrls.clear();
			for(String url:nextDepthUrls){
				currentDepthUrls.add(url);
			}
			nextDepthUrls.clear();
			
		}
          
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      Seedsite seedsite=(Seedsite) CommonUtil.getObjectFromXml(Thread.currentThread().getContextClassLoader().getResourceAsStream("amazone.xml"), Seedsite.class);
      UrlExtractor urlExtractor=new UrlExtractor(seedsite);
      urlExtractor.extract();
      
	
	}

    
}
