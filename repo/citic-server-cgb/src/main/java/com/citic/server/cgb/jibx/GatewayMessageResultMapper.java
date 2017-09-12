package com.citic.server.cgb.jibx;

import org.jibx.runtime.IAliasable;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.UnmarshallingContext;

import com.citic.server.cgb.domain.response.GatewayMessageResult;

public abstract class GatewayMessageResultMapper implements IMarshaller, IUnmarshaller, IAliasable {
	
	private static final String ENTRY_ELEMENT_NAME = "field";
	private static final String ATTRIBUTE_NAME_KEY = "name";
	
	private String namespace;
	private String name;
	protected int index;
	
	public GatewayMessageResultMapper() {
		this.namespace = null;
		this.name = null;
	}
	
	public GatewayMessageResultMapper(String ns, int index, String name) {
		this.namespace = ns;
		this.index = index;
		this.name = name;
	}
	
	public abstract GatewayMessageResult createGatewayMessageResult();
	
	@Override
	public Object unmarshal(Object obj, IUnmarshallingContext ictx) throws JiBXException {
		UnmarshallingContext ctx = (UnmarshallingContext) ictx;
		// Make sure we're at the appropriate start tag...
		if (!ctx.isAt(namespace, name)) {
			ctx.throwStartTagNameError(namespace, name);
		}
		
		GatewayMessageResult body = (GatewayMessageResult) obj;
		if (body == null) {
			body = createGatewayMessageResult();
		}
		
		ctx.parsePastStartTag(namespace, name);
		while (ctx.isAt(namespace, ENTRY_ELEMENT_NAME)) {
			String key = ctx.attributeText(null, ATTRIBUTE_NAME_KEY, null);
			String value = ctx.parseElementText(namespace, ENTRY_ELEMENT_NAME);
			body.putEntry(key, value);
		}
		ctx.parsePastEndTag(namespace, name);
		return body;
		
	}
	
	@Override
	public boolean isPresent(IUnmarshallingContext ctx) throws JiBXException {
		return ctx.isAt(namespace, name);
	}
	
	@Override
	public boolean isExtension(String mapname) {
		return false;
	}
	
	@Override
	public void marshal(Object obj, IMarshallingContext ctx) throws JiBXException {
		// Do nothing
	}
}
