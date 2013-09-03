package com.yufei.extractor.component.pExtractor;

import com.yufei.extractor.entity.PropertyMatch;

/**
 * @author jasstion
 * just used to parse text by details method(such as regex ...)
 * return a matched object 
 */
public interface PExtractor {

	public abstract Object extract(PropertyMatch propertyMatch,String source);

}
