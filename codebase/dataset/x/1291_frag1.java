    private void entityScan(Element e, Entity entity) throws IOException, XmlException {

        XmlScanner newScanner;

        XmlScanner oldScanner = scanner;

        boolean oldInEntityScan = inEntityScan;

        if (entity.getValue() == null) {

            Reader r = resolver.resolve(entity.getSystemID());

            newScanner = createXmlScanner(r);

        } else {

            newScanner = new XmlScanner(entity.getValue());

        }

        newScanner.setStringPool(oldScanner.getStringPool());

        if (entity.isResolving()) throw new XmlException("Circular entity evaluation for " + entity);

        entity.setResolving(true);

        inEntityScan = true;

        scanner = newScanner;

        content(e);

        if (hasMoreData()) throw new XmlException("Unmatched end tag in entity content " + entity);

        if (!oldInEntityScan) inEntityScan = false;

        scanner = oldScanner;

        entity.setResolving(false);

    }
