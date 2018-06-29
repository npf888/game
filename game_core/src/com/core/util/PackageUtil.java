package com.core.util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.base.Predicate;

/**
 * Java包分析类
 * 
 * @author Thinker
 * 
 */
public class PackageUtil 
{
	/**
	 * 读取包内所有的类获取clazz类的子类class对象
	 * 
	 * @param pname
	 * @return
	 */
	public static Set<Class<?>> getSubClass(String packageName, final Class<?> clazz)
	{
		return getPackageClasses(packageName, new Predicate<Class<?>>()
		{

			@Override
			public boolean apply(Class<?> arg)
			{
				return !arg.equals(clazz) && clazz.isAssignableFrom(arg);
			}

		});
	}

	/**
	 * 读取包内所有的类获取class对象
	 * 
	 * @param pname
	 * @return
	 */
	public static Set<Class<?>> getPackageClasses(String pname)
	{
		return getPackageClasses(pname, null);
	}

	/**
	 * 读取包内所有的类获取class对象，并根据指定的条件过滤
	 * 
	 * @param pname
	 * @param predicate
	 * @return
	 */
	public static Set<Class<?>> getPackageClasses(String pname, Predicate<Class<?>> predicate) 
	{
		Set<Class<?>> classes = new HashSet<Class<?>>();
		String packageDirName = pname.replace('.', '/');

		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		try 
		{
			Enumeration<URL> dirs = cl.getResources(packageDirName);

			while (dirs.hasMoreElements())
			{
				URL url = dirs.nextElement();

				String protocol = url.getProtocol();

				if ("file".equals(protocol))
					findByFile(cl, pname, URLDecoder.decode(url.getFile(), "utf-8"), classes, predicate);
				else if ("jar".equals(protocol))
					findInJar(cl, pname, packageDirName, url, classes, predicate);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * 从文件获取java类
	 * 
	 * @param cl
	 * @param packageName
	 * @param filePath
	 * @param classes
	 * @param predicate
	 */
	private static void findByFile(ClassLoader cl, String packageName, String filePath,Set<Class<?>> classes, Predicate<Class<?>> predicate)
	{
		File dir = new File(filePath);
		if (!dir.exists() || !dir.isDirectory())
			return;

		File[] dirFiles = dir.listFiles(new FileFilter() 
		{

			@Override
			public boolean accept(File file)
			{
				return file.isDirectory() || file.getName().endsWith(".class");
			}
		});

		for (File file : dirFiles)
			if (file.isDirectory())
				findByFile(cl, packageName + "." + file.getName(), file.getAbsolutePath(), classes, predicate);
			else
				try {
					Class<?> clazz = cl.loadClass(packageName + "."
							+ file.getName().substring(0, file.getName().length() - 6));
					if (predicate == null || predicate.apply(clazz))
						classes.add(clazz);
				} catch (ExceptionInInitializerError e) {
					// 这个没关系 是无法初始化类
				} catch (NoClassDefFoundError e) {
					// 这个没关系 是无法初始化类
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	}

	/**
	 * 读取jar中的java类
	 * 
	 * @param cl
	 * @param pname
	 * @param packageDirName
	 * @param url
	 * @param classes
	 * @param predicate
	 */
	private static void findInJar(ClassLoader cl, String pname, String packageDirName, URL url,
			Set<Class<?>> classes, Predicate<Class<?>> predicate) {
		try {
			JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();

			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();

				if (entry.isDirectory())
					continue;

				String name = entry.getName();
				if (name.charAt(0) == '/')
					name = name.substring(0);

				if (name.startsWith(packageDirName) && name.contains("/") && name.endsWith(".class")) {
					name = name.substring(0, name.length() - 6).replace('/', '.');
					try {
						Class<?> clazz = cl.loadClass(name);
						if (predicate == null || predicate.apply(clazz))
							classes.add(clazz);
					} catch (Throwable e) {
						System.out.println("无法直接加载的类：" + name);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Set<String> filterNestedPackages(String pname, Predicate<String> predicate) {
		Set<String> packages = new HashSet<String>();
		String packageDirName = pname.replace('.', '/');

		ClassLoader cl = new ClassLoader() {
		};

		try {
			Enumeration<URL> dirs = cl.getResources(packageDirName);

			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();

				String protocol = url.getProtocol();

				if ("file".equals(protocol))
					filterPackageByFile(pname, URLDecoder.decode(url.getFile(), "utf-8"), packages, predicate);
				else if ("jar".equals(protocol))
					filterPackageInJar(pname, packageDirName, url, packages, predicate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return packages;
	}

	/**
	 * 从文件获取java类
	 * 
	 * @param cl
	 * @param packageName
	 * @param filePath
	 * @param classes
	 * @param predicate
	 */
	private static void filterPackageByFile(String packageName, String filePath, Set<String> packages,
			Predicate<String> predicate) {
		File dir = new File(filePath);
		if (!dir.exists() || !dir.isDirectory())
			return;

		File[] dirFiles = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});

		for (File file : dirFiles) {
			String name = packageName + "." + file.getName();
			if (predicate.apply(name))
				packages.add(name);

			filterPackageByFile(name, file.getAbsolutePath(), packages, predicate);
		}
	}

	/**
	 * 读取jar中的java类
	 * 
	 * @param cl
	 * @param pname
	 * @param packageDirName
	 * @param url
	 * @param classes
	 * @param predicate
	 */
	private static void filterPackageInJar(String pname, String packageDirName, URL url,
			Set<String> packages, Predicate<String> predicate) {
		try {
			JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();

			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();

				if (entry.isDirectory()) {
					String name = entry.getName();
					
					if (name.contains("/") && predicate.apply(name))
						packages.add(name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
