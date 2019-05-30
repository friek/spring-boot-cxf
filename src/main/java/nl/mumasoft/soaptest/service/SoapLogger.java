package nl.mumasoft.soaptest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * This class gets called with every incoming and outgoing SOAP message.
 * <p>It is initialized in src/main/resources/handlers.xml, which in turn is configured in the @HandlerChain
 * annotation on HelloPortImpl.</p>
 */
public class SoapLogger implements SOAPHandler<SOAPMessageContext>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SoapLogger.class);

	@Override
	public boolean handleMessage(SOAPMessageContext soapMessageContext)
	{
		try
		{
			LOGGER.info(getSoapXml(soapMessageContext));
		}
		catch (IOException | SOAPException e)
		{
			LOGGER.error("Exception caught", e);
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context)
	{
		return false;
	}

	@Override
	public void close(MessageContext messageContext)
	{
		// Not implemented.
	}

	@Override
	public Set<QName> getHeaders()
	{
		return Collections.emptySet();
	}

	/**
	 * Convert a SOAPMessage to a String containing the XML from the SOAP message.
	 * @param message    The SOAP message to convert
	 * @return The string representation.
	 * @throws java.io.IOException Will likely never occur (thrown by ByteArrayOutputStream)
	 * @throws javax.xml.soap.SOAPException Thrown if a SOAP exception occurs somehow.
	 */
	private String getSoapXml(SOAPMessage message) throws IOException, SOAPException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		message.writeTo(out);
		return new String(out.toByteArray());
	}

	/**
	 * Convert the SOAPMessage from a SOAPMessageContext to an XML String.
	 * @param context     The context to get the SOAPMessage from.
	 * @return The string representation.
	 * @throws java.io.IOException Will likely never occur (thrown by ByteArrayOutputStream)
	 * @throws javax.xml.soap.SOAPException Thrown if a SOAP exception occurs somehow.
	 */
	private String getSoapXml(SOAPMessageContext context) throws IOException, SOAPException
	{
		return getSoapXml(context.getMessage());
	}
}

