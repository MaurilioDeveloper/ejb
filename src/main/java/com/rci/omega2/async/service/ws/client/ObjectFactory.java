
package com.rci.omega2.async.service.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rci.omega2.async.service.ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendMessageSalesman_QNAME = new QName("http://ws.service.async.omega2.rci.com/", "sendMessageSalesman");
    private final static QName _SendMessageSalesmanResponse_QNAME = new QName("http://ws.service.async.omega2.rci.com/", "sendMessageSalesmanResponse");
    private final static QName _AsyncUnexpectedException_QNAME = new QName("http://ws.service.async.omega2.rci.com/", "AsyncUnexpectedException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rci.omega2.async.service.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendMessageSalesman }
     * 
     */
    public SendMessageSalesman createSendMessageSalesman() {
        return new SendMessageSalesman();
    }

    /**
     * Create an instance of {@link SendMessageSalesmanResponse }
     * 
     */
    public SendMessageSalesmanResponse createSendMessageSalesmanResponse() {
        return new SendMessageSalesmanResponse();
    }

    /**
     * Create an instance of {@link AsyncUnexpectedException }
     * 
     */
    public AsyncUnexpectedException createAsyncUnexpectedException() {
        return new AsyncUnexpectedException();
    }

    /**
     * Create an instance of {@link SalesmanCommunicationRequest }
     * 
     */
    public SalesmanCommunicationRequest createSalesmanCommunicationRequest() {
        return new SalesmanCommunicationRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageSalesman }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.async.omega2.rci.com/", name = "sendMessageSalesman")
    public JAXBElement<SendMessageSalesman> createSendMessageSalesman(SendMessageSalesman value) {
        return new JAXBElement<SendMessageSalesman>(_SendMessageSalesman_QNAME, SendMessageSalesman.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageSalesmanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.async.omega2.rci.com/", name = "sendMessageSalesmanResponse")
    public JAXBElement<SendMessageSalesmanResponse> createSendMessageSalesmanResponse(SendMessageSalesmanResponse value) {
        return new JAXBElement<SendMessageSalesmanResponse>(_SendMessageSalesmanResponse_QNAME, SendMessageSalesmanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AsyncUnexpectedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.async.omega2.rci.com/", name = "AsyncUnexpectedException")
    public JAXBElement<AsyncUnexpectedException> createAsyncUnexpectedException(AsyncUnexpectedException value) {
        return new JAXBElement<AsyncUnexpectedException>(_AsyncUnexpectedException_QNAME, AsyncUnexpectedException.class, null, value);
    }

}
