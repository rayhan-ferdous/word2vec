    public SyncML getXmlResponse(String data) {

        SyncML sml = null;

        String xsd = JaxbHelper.getSyncMLSchema();

        StringReader xmlReader = new StringReader(data);

        StringReader xsdReader = new StringReader(xsd);

        try {

            sml = SyncMLHandler.CreateSymcMLObject(xmlReader, xsdReader);

        } catch (Exception e) {

            e.printStackTrace();

            Log4j.logger.error(e.toString());

        } finally {

            if (xmlReader != null) {

                xmlReader.close();

                xmlReader = null;

            }

            if (xsdReader != null) {

                xsdReader.close();

                xsdReader = null;

            }

        }

        return sml;

    }
