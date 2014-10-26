/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yufei.extractor.core;

import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 * @author jasstion
 */
public class SearchEngine {

    public static void main(String[] args) {
        String url = "https://www.google.com.sg/search?q=most+famous+university&oq=most+famous+university&aqs=chrome..69i57j0l5.1800j0j9&sourceid=chrome&es_sm=93&ie=UTF-8#q=most+famous+university&start=10";
       // Shanghai university
        //top 500  university 
        //most famous university
        String url1="https://www.google.com.sg/search?q=Shanghai+university&oq=Shanghai+university&aqs=chrome..69i57j0j69i59j0l3.1038j0j4&sourceid=chrome&es_sm=93&ie=UTF-8#q=top+500++university&start=10";
        String url2="https://www.google.com.sg/search?q=most+famous+university&oq=most+famous+university&aqs=chrome..69i57j0l5.1800j0j9&sourceid=chrome&es_sm=93&ie=UTF-8&start=10";
        List<String> urlList=Lists.newArrayList();
        List<String> url1List=Lists.newArrayList();
        List<String> url2List=Lists.newArrayList();
        
    
    
    
    }

}
