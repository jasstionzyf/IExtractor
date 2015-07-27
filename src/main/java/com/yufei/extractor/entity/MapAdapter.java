package com.yufei.extractor.entity;



import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;




public class MapAdapter 
	extends XmlAdapter<MapType, Map<String, String>> {  
        @Override  
        public MapType marshal(Map<String, String> map) {  
            MapType mapType = new MapType();  
            for (Entry<String, String> entry : map.entrySet()) {  
                MapEntry mapEntry = new MapEntry();  
                mapEntry.key = entry.getKey();  
                mapEntry.value = entry.getValue();  
                mapType.entryList.add(mapEntry);  
            }  
            return mapType;  
        }  
        @Override  
        public Map<String, String> unmarshal(MapType type) throws Exception {  
            Map<String, String> map = new HashMap<String, String>();  
            for (MapEntry entry : type.entryList) {   
                map.put(entry.key, entry.value);  
            }  
            return map;  
        }  


    }  
