package nl.mumasoft.soaptest;

import nl.mumasoft.soaptest.service.Hello;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig
{
	private final Bus bus;

	@Autowired
	public WebServiceConfig(Bus bus)
	{
		this.bus = bus;
	}

	@Bean
	public Endpoint endpoint(@Autowired Hello hello)
	{
		EndpointImpl endpoint = new EndpointImpl(bus, hello);
		// The default CXF config registers endpoints under /services/*. This is configurable in the CXF config,
		// but I haven't added one yet so far.
		endpoint.publish("/Hello");
		
		return endpoint;
	}
}