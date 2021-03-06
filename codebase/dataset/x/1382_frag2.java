    public String transform(Source xmlSource, InputStream xsltStream) {

        Source xsltSource = new StreamSource(xsltStream);

        StringWriter writer = new StringWriter();

        Result result = new StreamResult(writer);

        try {

            TransformerFactory transFact = TransformerFactory.newInstance();

            Transformer trans = transFact.newTransformer(xsltSource);

            trans.transform(xmlSource, result);

        } catch (Exception e) {

            freemind.main.Resources.getInstance().logException(e);

            return null;

        }

        return writer.toString();

    }
