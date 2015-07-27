/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yufei.extractor.core;

import com.google.common.collect.Sets;
import com.yufei.extractor.entity.UfLink;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author jasstion
 */
public class MemExtractedInfoRepository implements ExtractedInfoRepository{
    public Set<UfLink> ufLinks=Sets.newHashSet();

    public MemExtractedInfoRepository() {
        super();
    }

    @Override
    public void saveInfo(UfLink ufLink) {
        ufLinks.add(ufLink);
    }

    @Override
    public boolean isRepeatInfo(String mallId, String mallItemId) {
        UfLink ufLink=new UfLink(Long.parseLong(mallId), mallItemId);
        if(ufLinks.contains(ufLink)){
            return true;
        }
        return false;
    }

    @Override
    public UfLink getInfo(String link) {
        Iterator<UfLink> iterator=ufLinks.iterator();
        while (iterator.hasNext()) {
            UfLink ufLink = iterator.next();
            if(ufLink.getLink().equals(link)){
                return ufLink;
            }
            
        }
        return null;
    }

    @Override
    public Iterator<UfLink> iteratInfo() {
        return ufLinks.iterator();
    }
    
}
