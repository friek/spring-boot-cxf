package nl.mumasoft.soaptest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

@WebService(serviceName = "HelloService", portName = "HelloPort",
		targetNamespace = "http://service.ws.sample/",
		endpointInterface = "nl.mumasoft.soaptest.service.Hello")
@HandlerChain(file = "/handlers.xml")
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class HelloPortImpl implements Hello
{
	private HttpServletRequest request;
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloPortImpl.class);

	// Use an atomic integer to be thread safe. Although this is a test service, I'd like to maintain best practices.
	private static final AtomicInteger REQUEST_COUNTER = new AtomicInteger(0);

	public HelloPortImpl()
	{
		// For every request, this should get logged as it should be request scoped
		LOGGER.info("Service initialized");
	}

	@Autowired
	void setHttpServletRequest(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
									   HttpServletRequest request)
	{
		this.request = request;
	}

	public String sayHello(String myname)
	{
		LOGGER.info("Executing operation sayHello{} from {}", myname, request.getRemoteAddr());

		// Throw an exception every 3rd request. This should result in a SOAP fault.
		int currentRequestNum = REQUEST_COUNTER.incrementAndGet();
		if (currentRequestNum % 3 == 0)
		{
			throw new CustomException("Exception at request " + currentRequestNum);
		}

		return "Hello, Welcome to CXF Spring boot " + myname + "!!!";
	}

	private static class CustomException extends RuntimeException
	{
		CustomException(String message)
		{
			super(message);
		}
	}
}