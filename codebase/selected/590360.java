package org.apache.axiom.om.ds;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.axiom.om.OMDataSourceExt;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMOutputFormat;
import org.apache.axiom.om.impl.MTOMXMLStreamWriter;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * OMDataSourceExtBase is a convenient base class that can be extended
 * by other OMDataSourceExt implementations.
 */
public abstract class OMDataSourceExtBase implements OMDataSourceExt {

    private static final Log log = LogFactory.getLog(OMDataSourceExtBase.class);

    private static boolean DEBUG_ENABLED = log.isDebugEnabled();

    HashMap map = null;

    public Object getProperty(String key) {
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    public Object setProperty(String key, Object value) {
        if (map == null) {
            map = new HashMap();
        }
        return map.put(key, value);
    }

    public boolean hasProperty(String key) {
        if (map == null) {
            return false;
        }
        return map.containsKey(key);
    }

    public InputStream getXMLInputStream(String encoding) throws UnsupportedEncodingException {
        if (DEBUG_ENABLED) {
            log.debug("getXMLInputStream encoding=" + encoding);
        }
        return new ByteArrayInputStream(getXMLBytes(encoding));
    }

    public void serialize(OutputStream output, OMOutputFormat format) throws XMLStreamException {
        if (DEBUG_ENABLED) {
            log.debug("serialize output=" + output + " format=" + format);
        }
        try {
            output.write(getXMLBytes(format.getCharSetEncoding()));
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }

    public void serialize(Writer writer, OMOutputFormat format) throws XMLStreamException {
        if (DEBUG_ENABLED) {
            log.debug("serialize writer=" + writer + " format=" + format);
        }
        try {
            String text = new String(getXMLBytes(format.getCharSetEncoding()));
            writer.write(text);
        } catch (UnsupportedEncodingException e) {
            throw new XMLStreamException(e);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }

    public void serialize(XMLStreamWriter xmlWriter) throws XMLStreamException {
        if (DEBUG_ENABLED) {
            log.debug("serialize xmlWriter=" + xmlWriter);
        }
        OutputStream os = getOutputStream(xmlWriter);
        if (os != null) {
            if (DEBUG_ENABLED) {
                log.debug("serialize OutputStream optimisation: true");
            }
            String encoding = getCharacterEncoding(xmlWriter);
            OMOutputFormat format = new OMOutputFormat();
            format.setCharSetEncoding(encoding);
            serialize(os, format);
        } else {
            if (DEBUG_ENABLED) {
                log.debug("serialize OutputStream optimisation: false");
            }
            XMLStreamReader xmlReader = getReader();
            reader2writer(xmlReader, xmlWriter);
        }
    }

    /**
     * Simple utility that takes an XMLStreamReader and writes it
     * to an XMLStreamWriter
     * @param reader
     * @param writer
     * @throws XMLStreamException
     */
    private static void reader2writer(XMLStreamReader reader, XMLStreamWriter writer) throws XMLStreamException {
        StAXOMBuilder builder = new StAXOMBuilder(reader);
        builder.releaseParserOnClose(true);
        try {
            OMDocument omDocument = builder.getDocument();
            Iterator it = omDocument.getChildren();
            while (it.hasNext()) {
                OMNode omNode = (OMNode) it.next();
                omNode.serializeAndConsume(writer);
            }
        } finally {
            builder.close();
        }
    }

    /**
     * Some XMLStreamWriters expose an OutputStream that can be
     * accessed directly.
     * @return OutputStream or null
     */
    private static OutputStream getOutputStream(XMLStreamWriter writer) throws XMLStreamException {
        if (writer instanceof MTOMXMLStreamWriter) {
            return ((MTOMXMLStreamWriter) writer).getOutputStream();
        }
        return null;
    }

    /**
     * Get the character set encoding of the XMLStreamWriter
     * @return String or null
     */
    private static String getCharacterEncoding(XMLStreamWriter writer) {
        if (writer instanceof MTOMXMLStreamWriter) {
            return ((MTOMXMLStreamWriter) writer).getCharSetEncoding();
        }
        return null;
    }
}
