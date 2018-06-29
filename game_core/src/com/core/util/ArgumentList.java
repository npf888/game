package com.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Thinker    
 */
public final class ArgumentList
{
   public ArgumentList()
   {
   }

   public String get(String strArgName)
   {
      return get(strArgName, String.class);
   }

   @SuppressWarnings("unchecked")
   public <T> T get(String strArgName, Class<T> type)
   {
      Object oValue = m_hashValues.get(strArgName);
      if (oValue == null)
      {
    	  return null;
      }
      return (T)oValue;
   }

   public ArgumentList set(String strArgName, Object  objArgValue)
   {
      Object oValue = m_hashValues.get(strArgName);
      m_hashValues.put(strArgName, objArgValue);
      if (oValue == null)
      {     
         m_arrOrderedNames.add(strArgName);
      }
      return this;
   }

   public void replace(String strArgName, Object strArgValue)
   {
      remove(strArgName);
      set(strArgName, strArgValue);
   }

   public void remove(String strArgName)
   {
      m_hashValues.remove(strArgName);
      m_arrOrderedNames.remove(strArgName);
   }

   public Iterator<String> nameIterator()
   {
      return m_arrOrderedNames.iterator();
   } 

   public boolean isEmpty()
   {
      return m_arrOrderedNames.isEmpty();
   }

   public String toString()
   {
      StringBuffer strBuf = new StringBuffer(256);

      strBuf.append('{');

      boolean bFirst = true;
      Iterator<String> iterName = nameIterator();
      while ( iterName.hasNext() )
      {
         String strName = iterName.next();
         Object objValue = get(strName,Object.class);

         if ( bFirst == false )
         {
            strBuf.append(',');
         }
         strBuf.append(strName);
         strBuf.append('=');
         strBuf.append(objValue.toString());
         bFirst = false;
      }

      strBuf.append('}');

      return strBuf.toString();
   }
  
   private HashMap<String,Object> m_hashValues = new HashMap<String, Object>(5,1.0f);
   private ArrayList<String> m_arrOrderedNames = new ArrayList<String>(5);
   
}