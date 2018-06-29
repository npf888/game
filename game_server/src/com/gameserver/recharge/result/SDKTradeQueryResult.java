package com.gameserver.recharge.result;

import java.util.ArrayList;
import java.util.List;

public class SDKTradeQueryResult {

	private String ReturnCode;
	private String ReturnMsg;
	
	private List<ListSDKTradeQueryData> ListSDKTradeQuery = new ArrayList<ListSDKTradeQueryData>();

	public String getReturnCode() {
		return ReturnCode;
	}

	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}

	public String getReturnMsg() {
		return ReturnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		ReturnMsg = returnMsg;
	}

	public List<ListSDKTradeQueryData> getListSDKTradeQuery() {
		return ListSDKTradeQuery;
	}

	public void setListSDKTradeQuery(List<ListSDKTradeQueryData> listSDKTradeQuery) {
		ListSDKTradeQuery = listSDKTradeQuery;
	}
	
	
	
}
