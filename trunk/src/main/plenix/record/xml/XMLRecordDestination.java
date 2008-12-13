package plenix.record.xml;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamSource;

import org.apache.xml.serializer.Method;
import org.apache.xml.serializer.OutputPropertiesFactory;
import org.apache.xml.serializer.Serializer;
import org.apache.xml.serializer.SerializerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import plenix.record.Record;
import plenix.record.util.FieldSpec;
import plenix.record.util.OutputStreamRecordDestination;
import plenix.util.io.InputStreamSource;

public class XMLRecordDestination extends OutputStreamRecordDestination {
    private String outputMethod = Method.XML;
    private InputStreamSource stylesheetInputStreamSource;
    
    private String uri = "";
    private String rootElementName;
    private String recordElementName;
    
    private int indentAmount = 4;

    private TransformerHandler handler;
    
    private static final Attributes attrs = new AttributesImpl();

    @Override
    public void open() throws Exception {
        java.util.Properties props = OutputPropertiesFactory.getDefaultMethodProperties(outputMethod);
        if (indentAmount > 0) {
            props.put("indent", "yes");
            props.put(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "" + indentAmount);
        }
        Serializer serializer = SerializerFactory.getSerializer(props);
        serializer.setOutputStream(getOutputStreamSource().getOutputStream());

        SAXTransformerFactory transformerFactory = (SAXTransformerFactory) TransformerFactory.newInstance();
        Source stylesheetSource = new StreamSource(stylesheetInputStreamSource.getInputStream());
        handler = transformerFactory.newTransformerHandler(stylesheetSource);
        handler.setResult(new SAXResult(serializer.asContentHandler()));
        
        handler.startDocument();
        handler.startPrefixMapping("", uri);
        handler.startElement(uri, rootElementName, rootElementName, attrs);
    }

    @Override
    public void put(Record record) throws Exception {
        handler.startElement(uri, recordElementName, recordElementName, attrs);

        for (int i = 0; i < getFields().size(); i++) {
            FieldSpec fieldSpec = getFields().get(i);
            String fieldName = fieldSpec.getName();
            Object fieldValue = record.getField(fieldName);
            String fieldString = fieldSpec.format(fieldValue);
            if (fieldString != null) {
                handler.startElement(uri, fieldName, fieldName, attrs);
                handler.characters(fieldString.toCharArray(), 0, fieldString.length());
                handler.endElement(uri, fieldName, fieldName);
            }
        }
        
        handler.endElement(uri, recordElementName, recordElementName);
    }

    @Override
    public void close() throws Exception {
        handler.endElement(rootElementName, uri, rootElementName);
        handler.endPrefixMapping("");
        handler.endDocument();
    }

    public String getOutputMethod() {
        return outputMethod;
    }

    public void setOutputMethod(String outputMethod) {
        this.outputMethod = outputMethod;
    }

    public InputStreamSource getStylesheetInputStreamSource() {
        return stylesheetInputStreamSource;
    }

    public void setStylesheetInputStreamSource(InputStreamSource stylesheetInputStreamSource) {
        this.stylesheetInputStreamSource = stylesheetInputStreamSource;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRootElementName() {
        return rootElementName;
    }

    public void setRootElementName(String rootElementName) {
        this.rootElementName = rootElementName;
    }

    public String getRecordElementName() {
        return recordElementName;
    }

    public void setRecordElementName(String recordElementName) {
        this.recordElementName = recordElementName;
    }

    public int getIndentAmount() {
        return indentAmount;
    }

    public void setIndentAmount(int indentAmount) {
        this.indentAmount = indentAmount;
    }
}
