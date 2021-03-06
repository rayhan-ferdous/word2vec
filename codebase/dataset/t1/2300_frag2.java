    private static void write(File pdfFile, File dcmFile, Dataset ds) throws IOException {

        InputStream in = new BufferedInputStream(new FileInputStream(pdfFile), BUFFER_SIZE);

        try {

            OutputStream out = new BufferedOutputStream(new FileOutputStream(dcmFile));

            try {

                ds.writeFile(out, null);

                final int doclen = (int) pdfFile.length();

                ds.writeHeader(out, DcmEncodeParam.EVR_LE, Tags.EncapsulatedDocument, VRs.OB, (doclen + 1) & ~1);

                copy(in, out);

                if ((doclen & 1) != 0) out.write(0);

            } finally {

                out.close();

            }

        } finally {

            in.close();

        }

    }
