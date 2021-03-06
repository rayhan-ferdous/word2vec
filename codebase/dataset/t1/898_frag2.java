    private static void encapsulate(Configuration cfg, File pdfFile, File dcmFile) throws IOException {

        final DcmObjectFactory df = DcmObjectFactory.getInstance();

        Dataset ds = df.newDataset();

        for (Iterator it = cfg.entrySet().iterator(); it.hasNext(); ) {

            Map.Entry e = (Map.Entry) it.next();

            put(ds, toTags(StringUtils.split((String) e.getKey(), '/')), (String) e.getValue());

        }

        addUIDs(ds);

        addContentDateTime(ds);

        ds.setFileMetaInfo(df.newFileMetaInfo(ds, UIDs.ExplicitVRLittleEndian));

        write(pdfFile, dcmFile, ds);

        System.out.println("Encapsulate " + pdfFile + " into " + dcmFile);

    }
