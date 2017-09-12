package com.citic.server.cgb.jibx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jibx.runtime.IAliasable;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.MarshallingContext;
import org.jibx.runtime.impl.UnmarshallingContext;

/**
 * JiBX处理HashMap、LinkedHashMap等Map类型
 * 
 * @author Liu Xuanfei
 * @date 2016年11月28日 下午3:11:06
 */
public class HashMapper implements IMarshaller, IUnmarshaller, IAliasable {
	
	private static final String ATTRIBUTE_NAME_SIZE = "size";
	private static final String ENTRY_ELEMENT_NAME = "field";
	private static final String ATTRIBUTE_NAME_KEY = "name";
	
	private String m_uri;
	private int m_index;
	private String m_name;
	
	public HashMapper() {
		this.m_uri = null;
		this.m_index = 0;
		this.m_name = null;
	}
	
	/**
	 * @param uri
	 * @param index namespace URI index number
	 * @param name element name
	 */
	public HashMapper(String uri, int index, String name) {
		this.m_uri = uri;
		this.m_index = index;
		this.m_name = name;
	}
	
	@Override
	public boolean isExtension(String mapname) {
		return false;
	}
	
	@Override
	public void marshal(Object obj, IMarshallingContext ictx) throws JiBXException {
		// Make sure the parameters are as expected...
		if (!(obj instanceof HashMap<?, ?>) || !(ictx instanceof MarshallingContext)) {
			throw new JiBXException("Invalid object type for marshaller.");
		} else {
			MarshallingContext ctx = (MarshallingContext) ictx;
			HashMap<?, ?> hashMap = (HashMap<?, ?>) obj;
			
			// Start by generating start tag for container...
			if (m_name != null) {
				ctx.startTagAttributes(m_index, m_name).attribute(0, ATTRIBUTE_NAME_SIZE, hashMap.size()).closeStartContent(); //
			}
			
			// Loop through all entries in map...
			Iterator<?> it = hashMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) it.next();
				ctx.startTagAttributes(m_index, ENTRY_ELEMENT_NAME); // 
				if (entry.getKey() != null) {
					ctx.attribute(0, ATTRIBUTE_NAME_KEY, entry.getKey().toString()); // index = 0 不添加命名空间
				}
				ctx.closeStartContent();
				if (entry.getValue() != null) {
					ctx.content(entry.getValue().toString()); // 
				}
				ctx.endTag(m_index, ENTRY_ELEMENT_NAME); //
			}
			
			// Finish with end tag for container element...
			if (m_name != null) {
				ctx.endTag(m_index, m_name);
			}
		}
	}
	
	@Override
	public boolean isPresent(IUnmarshallingContext ctx) throws JiBXException {
		return ctx.isAt(m_uri, m_name);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object unmarshal(Object obj, IUnmarshallingContext ictx) throws JiBXException {
		UnmarshallingContext ctx = (UnmarshallingContext) ictx;
		// Make sure we're at the appropriate start tag...
		if (!ctx.isAt(m_uri, m_name)) {
			ctx.throwStartTagNameError(m_uri, m_name);
		}
		
		// Create new map if needed...
		HashMap<String, String> hashMap = (HashMap<String, String>) obj;
		if (hashMap == null) {
			int size = ctx.attributeInt(m_uri, ATTRIBUTE_NAME_SIZE, 10); // 默认大小10
			hashMap = new HashMap<String, String>(size);
		}
		
		// Process all entries present in document...
		ctx.parsePastStartTag(m_uri, m_name);
		while (ctx.isAt(m_uri, ENTRY_ELEMENT_NAME)) {
			String key = ctx.attributeText(null, ATTRIBUTE_NAME_KEY, null);
			String value = ctx.parseElementText(m_uri, ENTRY_ELEMENT_NAME);
			hashMap.put(key, value);
		}
		ctx.parsePastEndTag(m_uri, m_name);
		return hashMap;
	}
}
