# Spring Boot Apache CXF test project
This project contains the current Spring Boot (2.1.5) with a test setup for
Apache CXF (3.3.1). While I know Spring has its own support of SOAP web services,
I like the JAX-WS way better. I am more experienced with Java EE and the JAX WS
approach just seems cleaner.

## Running the application
Just run:
```bash
mvn spring-boot:run
```

And make a SOAP call (requires curl and xmllint):
```bash
./testrequest.sh
``` 

## TODO
For my own research, implement the following:
* Authentication with Spring Security.
* Contract first: add a WSDL and the corresponding generating configuration to 
  generate code suitable for implementing in a CXF endpoint.
* A JAX-WS client with CXF.
     