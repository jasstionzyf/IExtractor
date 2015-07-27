/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yufei.extractor.core;

import org.jsoup.nodes.Document;

/**
 *
 * @author jasstion
 */
public class JsoupDocumentThreadLocal {

    public final static ThreadLocal<Document> DC_THREAD_LOCAL = new ThreadLocal<Document>();

    public static void setDocument(Document document) {
        DC_THREAD_LOCAL.set(document);
    }

    public static void unSetDocument(Document document) {
        DC_THREAD_LOCAL.remove();
    }

    public static Document getDocument() {
        return DC_THREAD_LOCAL.get();
    }
}
