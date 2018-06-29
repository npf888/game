package com.game.webserver.login;

/**
 * 登录服返回
 * @author wayne
 *
 */


public class LoginServerResponse {
	
	private static final int HTTP_ERROR = 500;
	private int errorCode;
	private String result;
	
	public LoginServerResponse()
	{
		
	}
	
	public LoginServerResponse(int errorCode, String result) {
		this.errorCode = errorCode;
		this.result = result;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	public static LoginServerResponse httpErrorResponse()
	{
		return new LoginServerResponse(HTTP_ERROR,"http连接失败");
	}
}
