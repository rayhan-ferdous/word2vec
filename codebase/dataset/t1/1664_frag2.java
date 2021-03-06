    private void list(String prefix, DirRecord first, SAXTransformerFactory tf, Templates xslt) throws IOException, TransformerConfigurationException {

        int count = 1;

        for (DirRecord rec = first; rec != null; rec = rec.getNextSibling(onlyInUse)) {

            Dataset ds = rec.getDataset();

            String prompt = POS_FORMAT.format(ds.getItemOffset()) + prefix + count + " [" + rec.getType() + "]";

            ds.writeDataset2(getTransformerHandler(tf, xslt, prompt), dict, null, 128, null);

            list(prefix + count + '.', rec.getFirstChild(onlyInUse), tf, xslt);

            ++count;

        }

    }
