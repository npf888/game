package com.core.codec;

import org.apache.mina.common.IoSession;

public class WSToolKit {
	 private WSToolKit() {  
	    }  
	  
	    static enum WSSessionState {  
	        Handshake, Connected;  
	        public final static String ATTRIBUTE_KEY = "__SESSION_STATE";  
	    }  
	  
	    static <T> T nvl(T t1, T t2) {  
	        return t1 == null ? t2 : t1;  
	    }  
	      
	    static WSSessionState getSessionState (IoSession session) {  
	        WSSessionState state = (WSSessionState) session  
	                .getAttribute(WSSessionState.ATTRIBUTE_KEY);  
	          
	        if (state == null) {  
	            state = WSSessionState.Handshake;  
	            session.setAttribute(WSSessionState.ATTRIBUTE_KEY, state);  
	        }  
	          
	        return state;  
	    }  
	      
	    static String getMessageColumnValue(String[] headers, String headerTag) {  
	        for (String header : headers) {  
	            if( header.startsWith(headerTag) )  
	                return header.substring(headerTag.length(), header.length())  
	                        .trim();  
	        }  
	  
	        return null;  
	    }  
	      
	    static String subString(String src, int order, String flag) {  
	        for (int i = 1, j = 0, k = 0;; i++) {  
	            j = src.indexOf(flag, k);  
	            if (i < order) {  
	                if (j == -1)  
	                    return "";  
	                else  
	                    k = j + 1;  
	            } else {  
	                if (j == -1)  
	                    return src.substring(k, src.length());  
	                else  
	                    return src.substring(k, j);  
	            }  
	        }  
	    }  
}
