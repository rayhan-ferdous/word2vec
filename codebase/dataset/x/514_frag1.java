        System.out.println("writing " + outFile + "...");

        PixelDataWriter pdWriter = pdFact.newWriter(pdReader.getPixelDataArray(), false, ds, out, dcmParser.getDcmDecodeParam().byteOrder, dcmParser.getReadVR());

        pdWriter.writePixelData();

        out.flush();
