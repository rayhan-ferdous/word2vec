    public void test118() throws Exception {

        byte[] buffer;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        CsvWriter writer = new CsvWriter(stream, '\t', Charset.forName("ISO-8859-1"));

        Assert.assertEquals('\"', writer.getTextQualifier());

        writer.setTextQualifier('\'');

        Assert.assertEquals('\'', writer.getTextQualifier());

        writer.write("1,2");

        writer.write("3");

        writer.write("blah \"some stuff in quotes\"");

        writer.write("blah \'some stuff in quotes\'");

        writer.endRecord();

        writer.close();

        buffer = stream.toByteArray();

        stream.close();

        String data = Charset.forName("ISO-8859-1").decode(ByteBuffer.wrap(buffer)).toString();

        Assert.assertEquals("1,2\t3\tblah \"some stuff in quotes\"\t\'blah \'\'some stuff in quotes\'\'\'\r\n", data);

    }
