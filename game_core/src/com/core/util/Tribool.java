package com.core.util;

/**
 * 三态bool
 * @author Thinker
 *
 */
public enum Tribool
{
	Unknow,
	
	False,
	
	True;
	
	public static Tribool wrap(boolean bool) {
		return bool ? True : False;
	}
}
