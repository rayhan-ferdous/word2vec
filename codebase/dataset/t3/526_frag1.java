    public void test73() throws Exception {

        byte[] buffer;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        CsvWriter writer = new CsvWriter(stream, ',', Charset.forName("ISO-8859-1"));

        Assert.assertEquals(CsvWriter.ESCAPE_MODE_DOUBLED, writer.getEscapeMode());

        writer.setEscapeMode(CsvWriter.ESCAPE_MODE_BACKSLASH);

        Assert.assertEquals(CsvWriter.ESCAPE_MODE_BACKSLASH, writer.getEscapeMode());

        writer.write("1,2");

        writer.write("3");

        writer.write("blah \"some stuff in quotes\"");

        writer.endRecord();

        writer.setForceQualifier(true);

        writer.write("1,2");

        writer.write("3");

        writer.write("blah \"some stuff in quotes\"");

        writer.close();

        buffer = stream.toByteArray();

        stream.close();

        String data = Charset.forName("ISO-8859-1").decode(ByteBuffer.wrap(buffer)).toString();

        Assert.assertEquals("\"1,2\",3,\"blah \\\"some stuff in quotes\\\"\"\r\n\"1,2\",\"3\",\"blah \\\"some stuff in quotes\\\"\"", data);

    }
