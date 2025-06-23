package com.example.invoice_processor.util;

import com.example.invoice_processor.model.Invoice;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Base64;

public class XmlUtil {

    public static Invoice parseInvoiceFromBase64(String base64Xml) throws JAXBException {
        // Base64 kodlanmış XML stringini decode eder.
        byte[] decodedBytes = Base64.getDecoder().decode(base64Xml);
        String xmlContent = new String(decodedBytes);

        // JAXB context oluştur ve Invoice sınıfı ile çalışacak unmarshaller ayarlar.
        JAXBContext jaxbContext = JAXBContext.newInstance(Invoice.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // XML içeriğini StringReader ile oku ve Invoice nesnesine dönüştürür.
        StringReader reader = new StringReader(xmlContent);
        return (Invoice) unmarshaller.unmarshal(reader);
    }
}
