package com.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author Thinker
 *
 */
public class ListJsonUtil
{
	
	private static final String voJsonStart = "{";
	private static final String voJsonEnd = "}";
	private static final String fieldsSeprater = ",";
	private static final String nameValueSperate = ":";
	/**
	 * @category 把对象集合转换成json字符串
	 * @warning 当前只支持list ，不支持map
	 * @warning 如果集合内含有集合，则被忽略
	 * */
	public static String  toJsonString(List<?> voList)
	{
		if(voList == null)
		{
			return null;
		}
		String json ="";
		String jsonStart="[";
		String jsonEnd = "]";
		String jsonSeprate=",";
		for(Object obj:voList)
		{
			Class<? extends Object> c = obj.getClass();
			Field[] fields = c.getDeclaredFields();
			String voJson = toVOJson(obj,fields);
			json+=voJson+jsonSeprate;
		}
		json = removeLastChara(json);
		if(StringUtils.isEmpty(json))
		{
			return null;
		}
		return jsonStart+json+jsonEnd;
	}
	/**
	 * @category 把JSON串转换成对象集合
	 * @warning 当前只支持LIST ，不支持MAP
	 * @warning 如果集合内含有集合，则被忽略
	 */
	@SuppressWarnings("unchecked")
	public static Collection<?> toOjbectList(String jsonStr,Class c)
	{
		List<String> voStrList = seprateList(jsonStr);
		List<?> objList = constructVOList(voStrList,c);
		if(objList ==null)
		{
			return null;
		}
		if(objList.size()==0)
		{
			return null;
		}
		return objList;
	};
	
	private static List<?> constructVOList(List<String> voStrList, Class<?> c) 
	{
		if(voStrList == null)
		{
			return null;
		}
		if(voStrList.size() ==0)
		{
			return null;
		}
		List<Object> objList = new ArrayList<Object>();
		for(String str:voStrList)
		{
			Object obj = constructVO(str,c);
			if(obj == null)
			{
				continue;
			}
			objList.add(obj);
		}
		return objList;
	}
	private static Object constructVO(String str, Class<?> c) 
	{
		if(str == null)
		{
			return null;
		}
		Object obj = null;
		try 
		{
			obj = c.newInstance();
		} catch (InstantiationException e) 
		{
			return null;
		} catch (IllegalAccessException e) 
		{
			return null;
		}
		String[] nameAndValueArray = str.split(fieldsSeprater);
		if(nameAndValueArray == null)
		{
			return null;
		}
		if(nameAndValueArray.length ==0)
		{
			return null;
		}
		for(int i=0;i<nameAndValueArray.length;i++)
		{
			String nameAndValueStr = nameAndValueArray[i];
			if(nameAndValueStr == null)
			{
				continue;
			}
			String[] tempVOArray = nameAndValueStr.split(nameValueSperate);
			if(tempVOArray == null)
			{
				continue;
			}
			if(tempVOArray.length !=2)
			{
				continue;
			}
			String name = tempVOArray[0];
			String value = tempVOArray[1];
			if(name == null || value==null)
			{
				continue;
			}
			try 
			{
				Field tempField = c.getDeclaredField(name);
				tempField.setAccessible(true);
				tempField.set(obj, value);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (SecurityException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			} catch (NoSuchFieldException e) {
				continue;
			}
		}
		return obj;
	}
	private static List<String> seprateList(String jsonStr)
	{
		if(StringUtils.isEmpty(jsonStr))
		{
			return null;
		}
		List<String> strVoList = getVOStrList(jsonStr);
		if(strVoList == null)
		{
			return null;
		}
		if(strVoList.size() ==0)
		{
			return null;
		}
		return strVoList;
	}
	
	private static List<String> getVOStrList(String jsonStr)
	{
		List<Integer> startList = getSeprateList("\\"+voJsonStart,jsonStr);
		List<Integer> endList = getSeprateList("\\"+voJsonEnd,jsonStr);
		if(startList == null || endList==null)
		{
			return null;
		}
		if(startList.size() != endList.size())
		{
			return null;
		}
		List<String> strList = new ArrayList<String>();
		for(int i=0;i<startList.size();i++)
		{
			int startIndex = startList.get(i);
			int endIndex = endList.get(i);
			String str = null;
			try
			{
				str = jsonStr.substring(startIndex+1, endIndex-1);
			}catch(StringIndexOutOfBoundsException ex)
			{
				continue;
			}
			if(str != null)
			{
				strList.add(str);
			}	
		}
		return strList;
	}
	private static List<Integer> getSeprateList(String patterner,String content) 
	{
		List<Integer>list = new ArrayList<Integer>();
		Pattern pat = java.util.regex.Pattern.compile(patterner);
		Matcher  m= pat.matcher(content);
		while(m.find())
		{
			int start = m.start();
			list.add(start);
		}
		return list;
	}
	/**
	 * @param voJson
	 * @return
	 */
	private static String removeLastChara(String voJson)
	{
		if(voJson.length()>1)
		{
			voJson = voJson.substring(0, voJson.length());
		}
		return voJson;
	}
	private static String toVOJson(Object vo,Field[] fields)
	{
		if(fields == null)
		{
			return null;
		}
		String voJson = "";
		for(Field tempFiled:fields)
		{
			tempFiled.setAccessible(true);
			String name = tempFiled.getName();
			String value = null;
			try 
			{
				value = (String) tempFiled.get(vo);
			} catch (IllegalArgumentException e) 
			{
				//e.printStackTrace();
				continue;
			} catch (IllegalAccessException e)
			{
				//e.printStackTrace();
				continue;
			}catch(ClassCastException e)
			{
				//e.printStackTrace();
				continue;
			}
			if(name ==null || value == null)
			{
				continue;
			}
			String fieldJson =name+nameValueSperate+value;
			voJson+=fieldJson+fieldsSeprater;
		}
		voJson = removeLastChara(voJson);
		return voJsonStart+voJson+voJsonEnd;
	}
	
}
