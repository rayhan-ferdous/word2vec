    public void test123() throws Exception {

        byte[] buffer;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        CsvWriter writer = new CsvWriter(stream, ',', Charset.forName("ISO-8859-1"));

        writer.write("#123");

        writer.endRecord();

        writer.setEscapeMode(CsvWriter.ESCAPE_MODE_BACKSLASH);

        writer.setUseTextQualifier(false);

        writer.write("#123");

        writer.endRecord();

        writer.write("#");

        writer.endRecord();

        writer.close();

        buffer = stream.toByteArray();

        stream.close();

        String data = Charset.forName("ISO-8859-1").decode(ByteBuffer.wrap(buffer)).toString();

        Assert.assertEquals("\"#123\"\r\n\\#123\r\n\\#\r\n", data);

    }
