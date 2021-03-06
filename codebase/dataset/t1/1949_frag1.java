    protected Document getDocument(InputStream xml, ITaskMonitor monitor) {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setIgnoringComments(true);

            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            xml.reset();

            Document doc = builder.parse(new InputSource(xml));

            return doc;

        } catch (ParserConfigurationException e) {

            monitor.setResult("Failed to create XML document builder");

        } catch (SAXException e) {

            monitor.setResult("Failed to parse XML document");

        } catch (IOException e) {

            monitor.setResult("Failed to read XML document");

        }

        return null;

    }
