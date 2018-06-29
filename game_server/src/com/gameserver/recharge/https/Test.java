package com.gameserver.recharge.https;

import com.alibaba.fastjson.JSON;
import com.gameserver.recharge.result.AuthCodeResultData;

public class Test {

	public static void main(String[] args) {
		/*Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("FacServiceId", "nfeiGC");
    	paramMap.put("FacTradeSeq", "6199123640648895490");
    	paramMap.put("TradeType", "1");
    	paramMap.put("CustomerId", "10002");
    	paramMap.put("ProductName", "goddesscasino.gems.1");
    	paramMap.put("Amount", "300");
    	paramMap.put("Currency", "TWD");
    	paramMap.put("SandBoxMode", "true");
    	
    	String FacServiceId = (String)paramMap.get("FacServiceId");
    	String FacTradeSeq = (String)paramMap.get("FacTradeSeq");
    	String TradeType = (String)paramMap.get("TradeType");
    	String CustomerId = (String)paramMap.get("CustomerId");
    	String ProductName =(String) paramMap.get("ProductName");
    	String Amount = (String)paramMap.get("Amount");
    	String Currency = (String)paramMap.get("Currency");
    	String SandBoxMode = (String)paramMap.get("SandBoxMode");
    	
    	String preHashValue = FacServiceId+FacTradeSeq+TradeType+CustomerId+ProductName+Amount+Currency+SandBoxMode+Parameter.key;
    	
    	String urlEncode = HttpUtil.encode(preHashValue);
    	
    	System.out.println(urlEncode);
    	
    	String hash = SHA.SHA256(urlEncode);
    	
    	System.out.println(hash);*/
    	
    	String resultJson = "{'InGameSaveType':'2','ReturnCode':'1','ReturnMsg':'授權成功 ','AuthCode':'7B5489E16FD2A173011AFC8153866FB0562351AA142ADA16E8313D6C8DC E8324DA481EE10EC36EE2FF734067FD9A45E5998842E09D6DD5F5039129ADAD58DD23','TradeSeq':'KDS1512080000050'} ";
    	
    	AuthCodeResultData info = JSON.parseObject(resultJson, AuthCodeResultData.class);
    	
    	System.out.println(info.getInGameSaveType());
    	System.out.println(info.getReturnCode());
    	System.out.println(info.getReturnMsg());
    	System.out.println(info.getAuthCode());
    	System.out.println(info.getTradeSeq());
    	
    	System.out.println(Test.class.getName());
    	
	}

}
