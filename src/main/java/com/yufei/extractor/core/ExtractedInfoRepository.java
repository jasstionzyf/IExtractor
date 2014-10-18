/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yufei.extractor.core;

import com.yufei.extractor.entity.UfLink;
import java.util.Iterator;

/**
 *
 * @author jasstion
 */
public interface ExtractedInfoRepository {
    public void saveInfo(UfLink ufLink);
    public boolean isRepeatInfo(String mallId,String mallItemId);
    public UfLink getInfo(String link);
    public Iterator<UfLink> iteratInfo();
    
}
