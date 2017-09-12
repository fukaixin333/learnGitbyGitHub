/**
 * SyyhConvertDataServiceStub.java
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.5 Built on : May 06, 2017 (03:45:26 BST)
 */
package com.citic.server.gf.webservice;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.OutInAxisOperation;

/*
 * SyyhConvertDataServiceStub java implementation
 */
public class SyyhConvertDataServiceStub extends org.apache.axis2.client.Stub {
	private static int counter = 0;
	protected AxisOperation[] _operations;
	
	/**
	 * Constructor that takes in a configContext
	 */
	public SyyhConvertDataServiceStub(ConfigurationContext configurationContext, String targetEndpoint) throws AxisFault {
		this(configurationContext, targetEndpoint, false);
	}
	
	/**
	 * Constructor that takes in a configContext and useseperate listner
	 */
	public SyyhConvertDataServiceStub(ConfigurationContext configurationContext, String targetEndpoint, boolean useSeparateListener) throws AxisFault {
		//To populate AxisService
		populateAxisService();
		populateFaults();
		
		_serviceClient = new ServiceClient(configurationContext, _service);
		
		_serviceClient.getOptions().setTo(new EndpointReference(targetEndpoint));
		_serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
	}
	
	/**
	 * Constructor taking the target endpoint
	 */
	public SyyhConvertDataServiceStub(String targetEndpoint) throws AxisFault {
		this(null, targetEndpoint);
	}
	
	private static synchronized String getUniqueSuffix() {
		// reset the counter if it is greater than 99999
		if (counter > 99999) {
			counter = 0;
		}
		
		counter = counter + 1;
		return Long.toString(System.currentTimeMillis()) + "_" + counter;
	}
	
	private static final QName QNAME_0 = new QName("http://syyh.service.webService.ckw.tdh/", "zjInfo");
	private static final QName QNAME_1 = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzcxInfo");
	private static final QName QNAME_2 = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzkzInfo");
	private static final QName QNAME_3 = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzcxwlxxInfo");
	private static final QName QNAME_4 = new QName("http://syyh.service.webService.ckw.tdh/", "cxwsInfo");
	private static final QName QNAME_5 = new QName("http://syyh.service.webService.ckw.tdh/", "getXzcxList");
	private static final QName QNAME_6 = new QName("http://syyh.service.webService.ckw.tdh/", "kzwsInfo");
	private static final QName QNAME_7 = new QName("http://syyh.service.webService.ckw.tdh/", "getXzkzList");
	private static final QName QNAME_8 = new QName("http://syyh.service.webService.ckw.tdh/", "htInfo");
	
	private void populateAxisService() throws AxisFault {
		//creating the Service with a unique name
		_service = new AxisService("SyyhConvertDataService" + getUniqueSuffix());
		addAnonymousOperations();
		
		//creating the operations
		_operations = new AxisOperation[9];
		_operations[0] = new OutInAxisOperation(QNAME_0);
		_service.addOperation(_operations[0]);
		
		_operations[1] = new OutInAxisOperation(QNAME_1);
		_service.addOperation(_operations[1]);
		
		_operations[2] = new OutInAxisOperation(QNAME_2);
		_service.addOperation(_operations[2]);
		
		_operations[3] = new OutInAxisOperation(QNAME_3);
		_service.addOperation(_operations[3]);
		
		_operations[4] = new OutInAxisOperation(QNAME_4);
		_service.addOperation(_operations[4]);
		
		_operations[5] = new OutInAxisOperation(QNAME_5);
		_service.addOperation(_operations[5]);
		
		_operations[6] = new OutInAxisOperation(QNAME_6);
		_service.addOperation(_operations[6]);
		
		_operations[7] = new OutInAxisOperation(QNAME_7);
		_service.addOperation(_operations[7]);
		
		_operations[8] = new OutInAxisOperation(QNAME_8);
		_service.addOperation(_operations[8]);
	}
	
	//populates the faults
	private void populateFaults() {
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfoResponse zjInfo(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfo zjInfo0)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/zjInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), zjInfo0, new QName("http://syyh.service.webService.ckw.tdh/", "zjInfo"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfoResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfoResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfoResponse shfeedXzcxInfo(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfo shfeedXzcxInfo2)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/shfeedXzcxInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), shfeedXzcxInfo2, new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzcxInfo"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfoResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfoResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfoResponse shfeedXzkzInfo(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfo shfeedXzkzInfo4)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/shfeedXzkzInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), shfeedXzkzInfo4, new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzkzInfo"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfoResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfoResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfoResponse shfeedXzcxwlxxInfo(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfo shfeedXzcxwlxxInfo6)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/shfeedXzcxwlxxInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), shfeedXzcxwlxxInfo6, new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzcxwlxxInfo"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfoResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfoResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfoResponse cxwsInfo(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfo cxwsInfo8)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/cxwsInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), cxwsInfo8, new QName("http://syyh.service.webService.ckw.tdh/", "cxwsInfo"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfoResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfoResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxListResponse getXzcxList(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxList getXzcxList10)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/getXzcxList");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getXzcxList10, new QName("http://syyh.service.webService.ckw.tdh/", "getXzcxList"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxListResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxListResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfoResponse kzwsInfo(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfo kzwsInfo12)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/kzwsInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), kzwsInfo12, new QName("http://syyh.service.webService.ckw.tdh/", "kzwsInfo"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfoResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfoResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzListResponse getXzkzList(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzList getXzkzList14)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[7].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/getXzkzList");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getXzkzList14, new QName("http://syyh.service.webService.ckw.tdh/", "getXzkzList"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzListResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzListResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	public com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfoResponse htInfo(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfo htInfo16)
		throws java.rmi.RemoteException {
		MessageContext _messageContext = null;
		
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[8].getName());
			_operationClient.getOptions().setAction("http://syyh.service.webService.ckw.tdh/SyyhConvertDataHandle/htInfo");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			
			addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");
			
			// create a message context
			_messageContext = new MessageContext();
			
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;
			
			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), htInfo16, new QName("http://syyh.service.webService.ckw.tdh/", "htInfo"));
			
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			
			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);
			
			//execute the operation client
			_operationClient.execute(true);
			
			MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
			
			Object object = fromOM(_returnEnv.getBody().getFirstElement(), com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfoResponse.class);
			
			return (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfoResponse) object;
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender().cleanup(_messageContext);
			}
		}
	}
	
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfo param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfo.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfo param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfo.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfo param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfo.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfo param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfo.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfo param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfo.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxList param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxList.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfo param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfo.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzList param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzList.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfo param, QName elementQName)
		throws AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfo.MY_QNAME, factory));
			
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw AxisFault.makeFault(e);
		}
	}
	
	/* methods to provide back word compatibility */
	
	private Object fromOM(org.apache.axiom.om.OMElement param, Class type) throws AxisFault {
		try {
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfo.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfoResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.CxwsInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxList.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxList.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxListResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzcxListResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzList.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzList.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzListResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.GetXzkzListResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfo.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfoResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.HtInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfo.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfoResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.KzwsInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfo.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfoResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfo.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfoResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzcxwlxxInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfo.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfoResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ShfeedXzkzInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfo.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
			
			if (com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfoResponse.class.equals(type)) {
				return com.citic.server.gf.webservice.SyyhConvertDataServiceStub.ZjInfoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
			}
		} catch (Exception e) {
			throw AxisFault.makeFault(e);
		}
		
		return null;
	}
	
	public static class ShfeedXzcxInfo implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = shfeedXzcxInfo
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzcxInfo", "ns1");
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		/**
		 * field for Arg1
		 */
		protected String localArg1;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg1Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		public boolean isArg1Specified() {
			return localArg1Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg1() {
			return localArg1;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg1
		 */
		public void setArg1(String param) {
			localArg1Tracker = param != null;
			
			this.localArg1 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":shfeedXzcxInfo", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "shfeedXzcxInfo", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			if (localArg1Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg1", xmlWriter);
				
				if (localArg1 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg1);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static ShfeedXzcxInfo parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				ShfeedXzcxInfo object = new ShfeedXzcxInfo();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"shfeedXzcxInfo".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (ShfeedXzcxInfo) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg1").equals(reader.getName())) || new QName("", "arg1").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg1" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class ZjInfoResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = zjInfoResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "zjInfoResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":zjInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "zjInfoResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static ZjInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				ZjInfoResponse object = new ZjInfoResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"zjInfoResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (ZjInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class GetXzcxList implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = getXzcxList
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "getXzcxList", "ns1");
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":getXzcxList", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "getXzcxList", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static GetXzcxList parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				GetXzcxList object = new GetXzcxList();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"getXzcxList".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (GetXzcxList) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class ShfeedXzkzInfoResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = shfeedXzkzInfoResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzkzInfoResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":shfeedXzkzInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "shfeedXzkzInfoResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static ShfeedXzkzInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				ShfeedXzkzInfoResponse object = new ShfeedXzkzInfoResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"shfeedXzkzInfoResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (ShfeedXzkzInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class CxwsInfo implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = cxwsInfo
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "cxwsInfo", "ns1");
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		/**
		 * field for Arg1
		 */
		protected String localArg1;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg1Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		public boolean isArg1Specified() {
			return localArg1Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg1() {
			return localArg1;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg1
		 */
		public void setArg1(String param) {
			localArg1Tracker = param != null;
			
			this.localArg1 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":cxwsInfo", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "cxwsInfo", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			if (localArg1Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg1", xmlWriter);
				
				if (localArg1 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg1);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static CxwsInfo parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				CxwsInfo object = new CxwsInfo();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"cxwsInfo".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (CxwsInfo) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg1").equals(reader.getName())) || new QName("", "arg1").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg1" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class KzwsInfoResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = kzwsInfoResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "kzwsInfoResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":kzwsInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "kzwsInfoResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static KzwsInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				KzwsInfoResponse object = new KzwsInfoResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"kzwsInfoResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (KzwsInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class HtInfoResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = htInfoResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "htInfoResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":htInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "htInfoResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static HtInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				HtInfoResponse object = new HtInfoResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"htInfoResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (HtInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class GetXzcxListResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = getXzcxListResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "getXzcxListResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":getXzcxListResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "getXzcxListResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static GetXzcxListResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				GetXzcxListResponse object = new GetXzcxListResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"getXzcxListResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (GetXzcxListResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class CxwsInfoResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = cxwsInfoResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "cxwsInfoResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":cxwsInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "cxwsInfoResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static CxwsInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				CxwsInfoResponse object = new CxwsInfoResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"cxwsInfoResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (CxwsInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class KzwsInfo implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = kzwsInfo
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "kzwsInfo", "ns1");
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		/**
		 * field for Arg1
		 */
		protected String localArg1;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg1Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		public boolean isArg1Specified() {
			return localArg1Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg1() {
			return localArg1;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg1
		 */
		public void setArg1(String param) {
			localArg1Tracker = param != null;
			
			this.localArg1 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":kzwsInfo", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "kzwsInfo", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			if (localArg1Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg1", xmlWriter);
				
				if (localArg1 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg1);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static KzwsInfo parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				KzwsInfo object = new KzwsInfo();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"kzwsInfo".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (KzwsInfo) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg1").equals(reader.getName())) || new QName("", "arg1").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg1" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class ExtensionMapper {
		public static Object getTypeObject(String namespaceURI, String typeName, javax.xml.stream.XMLStreamReader reader) throws Exception {
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "shfeedXzcxInfo".equals(typeName)) {
				return ShfeedXzcxInfo.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "zjInfoResponse".equals(typeName)) {
				return ZjInfoResponse.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "shfeedXzkzInfoResponse".equals(typeName)) {
				return ShfeedXzkzInfoResponse.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "kzwsInfo".equals(typeName)) {
				return KzwsInfo.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "shfeedXzcxwlxxInfo".equals(typeName)) {
				return ShfeedXzcxwlxxInfo.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "zjInfo".equals(typeName)) {
				return ZjInfo.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "cxwsInfoResponse".equals(typeName)) {
				return CxwsInfoResponse.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "kzwsInfoResponse".equals(typeName)) {
				return KzwsInfoResponse.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "getXzkzList".equals(typeName)) {
				return GetXzkzList.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "shfeedXzkzInfo".equals(typeName)) {
				return ShfeedXzkzInfo.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "getXzcxListResponse".equals(typeName)) {
				return GetXzcxListResponse.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "htInfo".equals(typeName)) {
				return HtInfo.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "getXzcxList".equals(typeName)) {
				return GetXzcxList.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "shfeedXzcxInfoResponse".equals(typeName)) {
				return ShfeedXzcxInfoResponse.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "cxwsInfo".equals(typeName)) {
				return CxwsInfo.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "getXzkzListResponse".equals(typeName)) {
				return GetXzkzListResponse.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "htInfoResponse".equals(typeName)) {
				return HtInfoResponse.Factory.parse(reader);
			}
			
			if ("http://syyh.service.webService.ckw.tdh/".equals(namespaceURI) && "shfeedXzcxwlxxInfoResponse".equals(typeName)) {
				return ShfeedXzcxwlxxInfoResponse.Factory.parse(reader);
			}
			
			throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
		}
	}
	
	public static class GetXzkzList implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = getXzkzList
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "getXzkzList", "ns1");
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":getXzkzList", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "getXzkzList", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static GetXzkzList parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				GetXzkzList object = new GetXzkzList();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"getXzkzList".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (GetXzkzList) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class GetXzkzListResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = getXzkzListResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "getXzkzListResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":getXzkzListResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "getXzkzListResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static GetXzkzListResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				GetXzkzListResponse object = new GetXzkzListResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"getXzkzListResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (GetXzkzListResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class ZjInfo implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = zjInfo
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "zjInfo", "ns1");
		
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		/**
		 * field for Arg1
		 */
		protected String localArg1;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg1Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		public boolean isArg1Specified() {
			return localArg1Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg1() {
			return localArg1;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg1
		 */
		public void setArg1(String param) {
			localArg1Tracker = param != null;
			
			this.localArg1 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":zjInfo", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "zjInfo", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			if (localArg1Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg1", xmlWriter);
				
				if (localArg1 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg1);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static ZjInfo parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				ZjInfo object = new ZjInfo();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"zjInfo".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (ZjInfo) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg1").equals(reader.getName())) || new QName("", "arg1").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg1" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class ShfeedXzcxInfoResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = shfeedXzcxInfoResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzcxInfoResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":shfeedXzcxInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "shfeedXzcxInfoResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static ShfeedXzcxInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				ShfeedXzcxInfoResponse object = new ShfeedXzcxInfoResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"shfeedXzcxInfoResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (ShfeedXzcxInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class ShfeedXzkzInfo implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = shfeedXzkzInfo
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzkzInfo", "ns1");
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		/**
		 * field for Arg1
		 */
		protected String localArg1;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg1Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		public boolean isArg1Specified() {
			return localArg1Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg1() {
			return localArg1;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg1
		 */
		public void setArg1(String param) {
			localArg1Tracker = param != null;
			
			this.localArg1 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":shfeedXzkzInfo", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "shfeedXzkzInfo", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			if (localArg1Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg1", xmlWriter);
				
				if (localArg1 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg1);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static ShfeedXzkzInfo parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				ShfeedXzkzInfo object = new ShfeedXzkzInfo();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"shfeedXzkzInfo".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (ShfeedXzkzInfo) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg1").equals(reader.getName())) || new QName("", "arg1").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg1" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class ShfeedXzcxwlxxInfo implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = shfeedXzcxwlxxInfo
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzcxwlxxInfo", "ns1");
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		/**
		 * field for Arg1
		 */
		protected String localArg1;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg1Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		public boolean isArg1Specified() {
			return localArg1Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg1() {
			return localArg1;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg1
		 */
		public void setArg1(String param) {
			localArg1Tracker = param != null;
			
			this.localArg1 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":shfeedXzcxwlxxInfo", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "shfeedXzcxwlxxInfo", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			if (localArg1Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg1", xmlWriter);
				
				if (localArg1 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg1);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static ShfeedXzcxwlxxInfo parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				ShfeedXzcxwlxxInfo object = new ShfeedXzcxwlxxInfo();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"shfeedXzcxwlxxInfo".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (ShfeedXzcxwlxxInfo) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg1").equals(reader.getName())) || new QName("", "arg1").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg1" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class ShfeedXzcxwlxxInfoResponse implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = shfeedXzcxwlxxInfoResponse
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "shfeedXzcxwlxxInfoResponse", "ns1");
		/**
		 * field for _return
		 */
		protected String local_return;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean local_returnTracker = false;
		
		public boolean is_returnSpecified() {
			return local_returnTracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String get_return() {
			return local_return;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param _return
		 */
		public void set_return(String param) {
			local_returnTracker = param != null;
			
			this.local_return = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":shfeedXzcxwlxxInfoResponse", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "shfeedXzcxwlxxInfoResponse", xmlWriter);
				}
			}
			
			if (local_returnTracker) {
				namespace = "";
				writeStartElement(null, namespace, "return", xmlWriter);
				
				if (local_return == null) {
					
					throw new org.apache.axis2.databinding.ADBException("return cannot be null!!");
				} else {
					xmlWriter.writeCharacters(local_return);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static ShfeedXzcxwlxxInfoResponse parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				ShfeedXzcxwlxxInfoResponse object = new ShfeedXzcxwlxxInfoResponse();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"shfeedXzcxwlxxInfoResponse".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (ShfeedXzcxwlxxInfoResponse) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "return").equals(reader.getName())) || new QName("", "return").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "return" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
	
	public static class HtInfo implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had
		 * name = htInfo
		 * Namespace URI = http://syyh.service.webService.ckw.tdh/
		 * Namespace Prefix = ns1
		 */
		public static final QName MY_QNAME = new QName("http://syyh.service.webService.ckw.tdh/", "htInfo", "ns1");
		/**
		 * field for Arg0
		 */
		protected String localArg0;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg0Tracker = false;
		
		/**
		 * field for Arg1
		 */
		protected String localArg1;
		
		/*
		 * This tracker boolean wil be used to detect whether the user called the set method
		 * for this attribute. It will be used to determine whether to include this field
		 * in the serialized XML
		 */
		protected boolean localArg1Tracker = false;
		
		public boolean isArg0Specified() {
			return localArg0Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg0() {
			return localArg0;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg0
		 */
		public void setArg0(String param) {
			localArg0Tracker = param != null;
			
			this.localArg0 = param;
		}
		
		public boolean isArg1Specified() {
			return localArg1Tracker;
		}
		
		/**
		 * Auto generated getter method
		 * 
		 * @return String
		 */
		public String getArg1() {
			return localArg1;
		}
		
		/**
		 * Auto generated setter method
		 * 
		 * @param param Arg1
		 */
		public void setArg1(String param) {
			localArg1Tracker = param != null;
			
			this.localArg1 = param;
		}
		
		/**
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final QName parentQName, final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {
			return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}
		
		public void serialize(final QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType) throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
			String prefix = null;
			String namespace = null;
			
			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
			
			if (serializeType) {
				String namespacePrefix = registerPrefix(xmlWriter, "http://syyh.service.webService.ckw.tdh/");
				
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix + ":htInfo", xmlWriter);
				} else {
					writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "htInfo", xmlWriter);
				}
			}
			
			if (localArg0Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg0", xmlWriter);
				
				if (localArg0 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg0 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg0);
				}
				
				xmlWriter.writeEndElement();
			}
			
			if (localArg1Tracker) {
				namespace = "";
				writeStartElement(null, namespace, "arg1", xmlWriter);
				
				if (localArg1 == null) {
					
					throw new org.apache.axis2.databinding.ADBException("arg1 cannot be null!!");
				} else {
					xmlWriter.writeCharacters(localArg1);
				}
				
				xmlWriter.writeEndElement();
			}
			
			xmlWriter.writeEndElement();
		}
		
		private static String generatePrefix(String namespace) {
			if (namespace.equals("http://syyh.service.webService.ckw.tdh/")) {
				return "ns1";
			}
			
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}
		
		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(String prefix, String namespace, String localPart, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}
				
				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}
		
		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(String prefix, String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
			String writerPrefix = xmlWriter.getPrefix(namespace);
			
			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(String namespace, String attName, String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
			}
		}
		
		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(String namespace, String attName, QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String attributeNamespace = qname.getNamespaceURI();
			String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
			
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			
			String attributeValue;
			
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}
			
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
			}
		}
		
		/**
		 * method to handle Qnames
		 */
		private void writeQName(QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			String namespaceURI = qname.getNamespaceURI();
			
			if (namespaceURI != null) {
				String prefix = xmlWriter.getPrefix(namespaceURI);
				
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}
				
				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
			} else {
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}
		
		private void writeQNames(QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not possible to write any
				// namespace data after writing the charactor data
				StringBuffer stringToWrite = new StringBuffer();
				String namespaceURI = null;
				String prefix = null;
				
				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					
					namespaceURI = qnames[i].getNamespaceURI();
					
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}
						
						if (prefix.trim().length() > 0) {
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						} else {
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					} else {
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				
				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}
		
		/**
		 * Register a namespace prefix
		 */
		private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
			String prefix = xmlWriter.getPrefix(namespace);
			
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				
				javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
				
				while (true) {
					String uri = nsContext.getNamespaceURI(prefix);
					
					if ((uri == null) || (uri.length() == 0)) {
						break;
					}
					
					prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}
				
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			
			return prefix;
		}
		
		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);
			
			/**
			 * static method to create the object
			 * Precondition: If this object is an element, the current or next start element starts
			 * this object and any intervening reader events are ignorable
			 * If this object is not an element, it is a complex type and the reader is at the event
			 * just after the outer start element
			 * Postcondition: If this object is an element, the reader is positioned at its end
			 * element
			 * If this object is a complex type, the reader is positioned at the end element of its
			 * outer element
			 */
			public static HtInfo parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
				HtInfo object = new HtInfo();
				
				int event;
				QName currentQName = null;
				String nillableValue = null;
				String prefix = "";
				String namespaceuri = "";
				
				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					currentQName = reader.getName();
					
					if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
						String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
						
						if (fullTypeName != null) {
							String nsPrefix = null;
							
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
							}
							
							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
							
							String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
							
							if (!"htInfo".equals(type)) {
								//find namespace for the prefix
								String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								
								return (HtInfo) ExtensionMapper.getTypeObject(nsUri, type, reader);
							}
						}
					}
					
					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();
					
					reader.next();
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg0").equals(reader.getName())) || new QName("", "arg0").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg0" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if ((reader.isStartElement() && new QName("", "arg1").equals(reader.getName())) || new QName("", "arg1").equals(reader.getName())) {
						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
						
						if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
							throw new org.apache.axis2.databinding.ADBException("The element: " + "arg1" + "  cannot be null");
						}
						
						String content = reader.getElementText();
						
						object.setArg1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
						
						reader.next();
					} // End of if for expected property start element
					
					else {
					}
					
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();
					
					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new Exception(e);
				}
				
				return object;
			}
		} //end of factory class
	}
}
