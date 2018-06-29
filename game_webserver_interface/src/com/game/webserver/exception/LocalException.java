package com.game.webserver.exception;

public class LocalException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3413182453872160700L;
	
	 public LocalException(String msg)
	  {
	    super(msg);
	  }

	  public LocalException(Exception e) {
	    super(e);
	  }

	  public LocalException(String msg, Exception e) {
	    super(msg, e);
	  }

}
