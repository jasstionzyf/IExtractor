package com.yufei.extractor.core;

import com.google.common.collect.Lists;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.component.repetition.IsRepetive;
import com.yufei.component.repetition.IsRepetiveBatSet;
import com.yufei.dataget.dataretriver.HttpDataRetriever;
import com.yufei.dataget.entity.PaginationRule;
import com.yufei.dataget.utils.HtmlUtil;
import com.yufei.extractor.component.DataRetriverPool;
import com.yufei.extractor.entity.Seedsite;
import com.yufei.extractor.entity.UfLink;
import com.yufei.pfw.service.PfwService;
import com.yufei.pfw.service.PfwServiceFactory;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.PatternUtils;
import com.yufei.utils.StringUtils;
import java.util.Iterator;

public class UrlExtractor implements Extractor {

    public static final Log mLog = LogFactory.getLog(UrlExtractor.class);

    private List<String> currentDepthUrls = new ArrayList<String>();
    private List<String> nextDepthUrls = new ArrayList<String>();
    private int currentDepth;
    private int depth;
    private String url0 = null;
    private Seedsite seedSite;
    private DataRetriverPool dataRetriverPool = null;

    private ExtractedInfoRepository extractedInfoRepository = null;

    private void prepare() {
        depth = seedSite.getUrlExtractorCfg().getDepth();
        url0 = seedSite.getSiteName();
        currentDepth = 0;
        dataRetriverPool = DataRetriverPool.getInstance(seedSite.getDataRetrieverFeatures());
        currentDepthUrls.add(url0);
        PaginationRule paginationRule = seedSite.getUrlExtractorCfg().getPaginationRule();
        if (!(paginationRule == null || paginationRule.getPaginationTemplate() == null || paginationRule.getUrlParameters().size() == 0)) {
            List<String> urls = null;
            urls = HtmlUtil.generateUrlsByPaginationRule(paginationRule);
            currentDepthUrls.addAll(urls);
        }

    }

    public UrlExtractor(Seedsite seedSite, ExtractedInfoRepository extractedInfoRepository) {
        super();

        this.seedSite = seedSite;
        this.extractedInfoRepository = extractedInfoRepository;
    }

    private List<String> getLinks(String upLink) {
        List<String> links = new ArrayList<String>();

        String htmlContent = null;
        HttpDataRetriever httpDataRetriever = dataRetriverPool.get();
        htmlContent = getHtmlContent(upLink, httpDataRetriever);
        if (htmlContent == null) {
            return links;
        }
        List<String> filterregexes = this.seedSite.getUrlExtractorCfg().getUrlRegexes();
        if (CommonUtil.isEmptyOrNull(filterregexes)) {
            String message = "UrlRegexes配置为空,请重新配置!";
            //mLog.error(message);
            throw new RuntimeErrorException(new Error(message));
        }
        List<String> filterUrls = PatternUtils.getListStrByRegex(htmlContent, CommonUtil.LinkStringWithSpecialSymbol(filterregexes, "!"));
        for (String filterUrl : filterUrls) {
            try {
                if (!CommonUtil.isValidUrl(filterUrl)) {
                    continue;
                }
                filterUrl = CommonUtil.formatUrl(filterUrl);
                filterUrl = CommonUtil.normalizeUrl(filterUrl, upLink);
                //mLog.info("经过规范之后的url："+filterUrl+"");
//			if(isRepetive.isRepetive(filterUrl)){
//				continue;
//			}
                List<String> ulLinkRegexes = seedSite.getUrlExtractorCfg().getUlLinkRegexes();
                if (CommonUtil.isEmptyOrNull(ulLinkRegexes)) {
                    String message = "ulLinkRegexes配置为空,请重新配置!";
                    //mLog.error(message);
                    throw new RuntimeErrorException(new Error(message));
                }
                if (CommonUtil.isEmptyOrNull(filterUrl)) {
                    continue;
                }
                if (PatternUtils.match(filterUrl, CommonUtil.LinkStringWithSpecialSymbol(ulLinkRegexes, "!"))) {
                    //提取商品标识查看商品是否已经收录
                    String mallItemId = seedSite.getUrlExtractorCfg().getIdRegex();
                    String mallId = seedSite.getMallId();
                    if (!StringUtils.isEmpty(mallItemId) && !StringUtils.isEmpty(mallId)) {
                        if (extractedInfoRepository.isRepeatInfo(mallId, mallItemId)) {
                            continue;
                        }
                    }

//                Map map=new HashMap();
//                map.put("mallId:=", mallId);
//                map.put("mallItemId:=", mallItemId);
//                List<UfLink> ufs=PfwService.pfwService.query(map, UfLink.class);
//                if(!CommonUtil.isEmptyOrNull(ufs)){
//                	continue;
//                }
                    UfLink ufLink = new UfLink();
                    ufLink.setFindTime(new Date());
                    String htmlContent1 = getHtmlContent(filterUrl, dataRetriverPool.get());
                    if (!StringUtils.isEmpty(htmlContent1)) {
                        htmlContent1 = HtmlUtil.cleanHtml(htmlContent1);
                        htmlContent1 = StringUtils.formatWhiteSpace(htmlContent1);
                    }

                    ufLink.sethc(htmlContent1);
                    ufLink.setLink(filterUrl);
                    ufLink.setSeedsiteId(seedSite.getId());
                    ufLink.setUpLink(upLink);
                    ufLink.setMallItemId(PatternUtils.getStrByRegex(filterUrl, mallItemId));
                    ufLink.setMallId(Long.parseLong(mallId));
                    //PfwService.pfwService.save(ufLink);
                    if (StringUtils.isEmpty(ufLink.getMallItemId())) {
                        ufLink.setMallItemId(filterUrl);

                    }
                    extractedInfoRepository.saveInfo(ufLink);
                    continue;
                }
                links.add(filterUrl);
            } catch (Exception e) {
                mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
            }
        }

        return links;
    }

    private String getHtmlContent(String upLink,
            HttpDataRetriever httpDataRetriever) {
        String htmlContent = null;
        try {
            httpDataRetriever.setUrl(new URL(upLink));
            httpDataRetriever.connect();
            htmlContent = httpDataRetriever.getHtmlContent();
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
        if (depth == -1) {
            depth = 1000;
        }
        int newUrls = 0;

        for (int i = 0; i < depth; i++) {
            int totalExtractUrls = 0;
            List<String> tempExtractedUrlsByOneUrl = null;
            for (String url : currentDepthUrls) {
                tempExtractedUrlsByOneUrl = getLinks(url);
                totalExtractUrls += tempExtractedUrlsByOneUrl.size();
                nextDepthUrls.addAll(tempExtractedUrlsByOneUrl);
            }
            if (totalExtractUrls == 0) {
                break;
            }

            currentDepthUrls.clear();
            for (String url : nextDepthUrls) {
                currentDepthUrls.add(url);
            }
            nextDepthUrls.clear();

        }

    }

    public static void main(String[] args) {

        // TODO Auto-generated method stub
        String url = "http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&ch=&tn=baidu&bar=&wd=top+500+university&rn=";

        Seedsite seedsite = (Seedsite) CommonUtil.getObjectFromXml(Thread.currentThread().getContextClassLoader().getResourceAsStream("meishi.xml"), Seedsite.class);
        //seedsite.setSiteName(url);
        seedsite.getDataRetrieverFeatures().setRequestTimeout(7*1000);
       // seedsite.getUrlExtractorCfg().setDepth(1);
       // seedsite.getUrlExtractorCfg().setUlLinkRegexes(Lists.newArrayList("(.*?)"));
        seedsite.getDataRetrieverFeatures().setRequestExecuteJs(true);
        ExtractedInfoRepository extractedInfoRepository = new MemExtractedInfoRepository();

        UrlExtractor urlExtractor = new UrlExtractor(seedsite, extractedInfoRepository);
        urlExtractor.extract();
        Iterator<UfLink> ufLinks = extractedInfoRepository.iteratInfo();
        while (ufLinks.hasNext()) {
            UfLink ufLink = ufLinks.next();
            if (StringUtils.isEmpty(ufLink.getOhc())) {
                continue;
            }
            mLog.info(ufLink.getLink());
        }

    }

}
