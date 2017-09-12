/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/12 23:25:31$
 */
package com.citic.server.runtime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.util.ClassUtils;

import com.citic.server.utils.StringUtils;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/12 23:25:31$
 */
public class ClasspathResourceLoader {
	
	private ClassLoader classLoader;
	
	private Class<?> clazz;
	
	public ClasspathResourceLoader() {
		this((ClassLoader) null);
	}
	
	public ClasspathResourceLoader(ClassLoader classLoader) {
		//		new org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader().get
		this.classLoader = (classLoader != null ? classLoader : getDefaultClassLoader());
	}
	
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader classLoader = null;
		try {
			classLoader = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (classLoader == null) {
			// No thread context class loader -> use class loader of this class.
			classLoader = ClassUtils.class.getClassLoader();
			if (classLoader == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					classLoader = ClassLoader.getSystemClassLoader();
				} catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can live with null...
				}
			}
		}
		return classLoader;
	}
	
	public InputStream getResourceStream(String path) throws IOException {
		String pathToUse = StringUtils.cleanPath(path);
		InputStream in;
		if (this.clazz != null) {
			in = this.clazz.getResourceAsStream(pathToUse);
		} else if (this.classLoader != null) {
			in = this.classLoader.getResourceAsStream(pathToUse);
		} else {
			in = ClassLoader.getSystemResourceAsStream(pathToUse);
		}
		if (in == null) {
			throw new FileNotFoundException(path + " cannot be opened because it does not exist");
		}
		return in;
	}
	
	public URL getURL(String path) throws IOException {
		String pathToUse = StringUtils.cleanPath(path);
		URL url = resolveURL(pathToUse);
		if (url == null) {
			throw new FileNotFoundException(path + " cannot be resolved to URL because it does not exist");
		}
		return url;
	}
	
	protected URL resolveURL(String path) {
		if (this.clazz != null) {
			return this.clazz.getResource(path);
		} else if (this.classLoader != null) {
			return this.classLoader.getResource(path);
		} else {
			return ClassLoader.getSystemResource(path);
		}
	}
}
