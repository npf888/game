package com.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 数组逻辑
 * @author Thinker
 *
 */
public class ArrayUtils
{
	
	/**
	 * 随机
	 */
	public static List<Integer> randomIndexByWeight(int[] weightTable,int num,boolean repeat)
	{
		int[] tempWeightTable = ArrayUtils.copyIntArr(weightTable);
		int weightSize = tempWeightTable.length;
		if(repeat)
			Assert.isTrue(weightSize>=num);
		
		int total = 0;
		for(int i=0;i<weightSize;i++)
		{
			total += tempWeightTable[i];
		}
		List<Integer> returnlist = new ArrayList<Integer>();
		for(int i=0;i<num;i++)
		{
			Assert.isTrue(total>0);
			int randomNum = (int)MathUtils.random(0.f,(float)total);
			for(int j=0;j<weightSize;j++)
			{
				int temp = tempWeightTable[j];
				if(randomNum<temp)
				{
					returnlist.add(j);
					if(!repeat)
					{
						total-=temp;
						tempWeightTable[j]=0;
					}
					break;
				}
				randomNum-=temp;
			}
		}
		return returnlist;
	}
	
	/**
	 * 随机数目
	 * @param <T>
	 * @param arr
	 * @param num
	 * @return
	 */
	public static <T> List<T> randomFromArray(List<T> arr,int num,boolean repeat)
	{
		int[] temp = new int[arr.size()];
		for(int i =0;i<temp.length;i++)
			temp[i]=1;
		return randomFromArray(arr,temp,num,repeat);
	}
	/**
	 * 随机数目
	 * @param <T>
	 * @param arr
	 * @param num
	 * @return
	 */
	public static <T> List<T> randomFromArray(List<T> arr,int[] weightArr,int num,boolean repeat)
	{
		if(repeat)
			Assert.isTrue(arr.size()>=num);
		List<T> temp = new ArrayList<T>(num);
		System.out.println(weightArr);
		List<Integer> indexList = randomIndexByWeight(weightArr,num,repeat);
		for(int i=0;i<indexList.size();i++)
		{
			temp.add(arr.get(indexList.get(i)));
		}
		return temp;
	}
	
	public static int[] intList2Array(List<Integer> list)
	{
		if (list != null) 
		{
			int[] ary = new int[list.size()];
			for (int i = 0; i < list.size(); i++)
			{
				ary[i] = list.get(i);
			}
			return ary;
		}
		return null;
	}

	public static int[] intSet2Array(Set<Integer> set)
	{
		if (set != null)
		{
			int i = 0;
			int[] ary = new int[set.size()];
			for (Integer integer : set) 
			{
				ary[i] = integer;
				i++;
			}
			return ary;
		}
		return null;
	}

	/**
	 * 创建一个二维数组
	 * 
	 * @param <T>
	 *            数组单元的类型
	 * @param rows
	 *            数组的第一维长度
	 * @param cols
	 *            数组的第二位长度
	 * @param clazz
	 *            数组单元的类型
	 * @return 如果类型实例化失败,则数组里的单元为null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[][] createTwoDimensionArray(int rows, int cols,Class<T> clazz)
	{
		T[][] arr = (T[][]) Array.newInstance(clazz, rows, cols);
		for (int y = 0; y < rows; y++)
		{
			for (int x = 0; x < cols; x++)
			{
				try
				{
					arr[y][x] = clazz.newInstance();
				} catch (Exception e)
				{
					arr[y][x] = null;
				}
			}
		}
		return arr;
	}

	/**
	 * 进行数组比较,每次比较会将结果回调给不同的函数.如果数组中存在null的元素, 则跳过此元素进行下一个的比较,且不会调用
	 * <code>compCallback</code>
	 * 
	 * @param <T>
	 *            数组的类型,必须实现<code>Comparable</code>接口
	 * @param arr1
	 *            第一个数组
	 * @param arr2
	 *            第二个数组
	 * @param compCallback
	 *            比较后的回调函数,第一个参数是比较值comp,见<code>Compareble.compareTo</code>方法.
	 *            第二个参数根据comp的值不同,当comp小于0时为较小的值,当comp等于0时为相同的值,当comp大于0时为较大的值
	 */
	public static <T extends Comparable<T>> void compare(T[] arr1, T[] arr2,Functions.Function2<Integer, T> compCallback) 
	{
		int index1 = 0;
		int index2 = 0;
		while (index1 < arr1.length && index2 < arr2.length)
		{
			T obj1 = arr1[index1];
			T obj2 = arr2[index2];
			if (obj1 == null)
			{
				index1++;
				continue;
			}
			if (obj2 == null) 
			{
				index2++;
				continue;
			}
			int comp = obj1.compareTo(obj2);
			if (comp < 0)
			{
				compCallback.apply(comp, obj1);
				index1++;
			} else if (comp == 0)
			{
				compCallback.apply(comp, obj1);
				index1++;
				index2++;
			} else
			{
				compCallback.apply(comp, obj2);
				index2++;
			}
		}
		// 修正从单元格0（第一行第一列）移动到单元格9（第二行第一列）时比较结果不准确的问题
		while (index1 < arr1.length)
		{
			if (arr1[index1] != null)
				compCallback.apply(-1, arr1[index1]);
			index1++;
		}
		while (index2 < arr2.length)
		{
			if (arr2[index2] != null)
				compCallback.apply(1, arr2[index2]);
			index2++;
		}
	}
	
	/**
	 * 把INT数组转化为集合
	 * @param <T>
	 * @param intArray
	 * @return list*/
	public static <T> List<T> toList(T[] intArray)
	{
		if(intArray ==null)
		{
			return null;
		}
		if(intArray.length==0)
		{
			return null;
		}
		List <T>list = new ArrayList<T>();
		for(T tempIntVal:intArray)
		{
			list.add(tempIntVal);
		}
		return list;
	}

	/**
	 * 将 int 数组转换为 short 数组
	 * 
	 * @param intArr
	 * @return 
	 * 
	 */
	public static short[] fromIntToShort(int[] intArr)
	{
		if (intArr == null || intArr.length <= 0) 
		{
			return new short[0];
		}
		// 创建结果数组
		short[] result = new short[intArr.length];

		for (int i = 0; i < intArr.length; i++)
		{
			// 将 int 转成 short
			result[i] = (short)intArr[i];
		}
		return result;
	}
	
	public static int[] copyIntArr(int[] arr){
		int[] t = new int[arr.length];
		for(int i=0;i<arr.length;i++){
			t[i]=arr[i];
		}
		return t;
	}
}
