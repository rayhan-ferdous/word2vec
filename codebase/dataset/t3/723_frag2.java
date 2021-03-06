    public static void exampleTransformerReuse(String sourceID, String xslID) throws TransformerException, TransformerConfigurationException {

        TransformerFactory tfactory = TransformerFactory.newInstance();

        Transformer transformer = tfactory.newTransformer(new StreamSource(xslID));

        transformer.setParameter("a-param", "hello to you!");

        transformer.transform(new StreamSource(sourceID), new StreamResult(new OutputStreamWriter(System.out)));

        System.out.println("\n=========\n");

        transformer.setParameter("a-param", "hello to me!");

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(new StreamSource(sourceID), new StreamResult(new OutputStreamWriter(System.out)));

    }
