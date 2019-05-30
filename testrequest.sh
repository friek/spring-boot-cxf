#!/usr/bin/env bash

curl -s -H 'Content-Type:' -XPOST http://localhost:8082/services/Hello --data "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.ws.sample/\">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:sayHello>
         <myname>world</myname>
      </ser:sayHello>
   </soapenv:Body>
</soapenv:Envelope>" | xmllint --format -
