        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        CsvWriter writer = new CsvWriter(stream, ',', Charset.forName("ISO-8859-1"));

        writer.setForceQualifier(true);

        writer.write(" data ");

        writer.endRecord();

        writer.close();

        buffer = stream.toByteArray();

        stream.close();

        String data = Charset.forName("ISO-8859-1").decode(ByteBuffer.wrap(buffer)).toString();

        Assert.assertEquals("\"data\"\r\n", data);

    }


