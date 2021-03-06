    public void test48() throws Exception {

        byte[] buffer;

        String test = "M�nchen";

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(stream, Charset.forName("UTF-8")));

        writer.write(test);

        writer.close();

        buffer = stream.toByteArray();

        stream.close();

        CsvReader reader = new CsvReader(new InputStreamReader(new ByteArrayInputStream(buffer), Charset.forName("UTF-8")));

        Assert.assertTrue(reader.readRecord());

        Assert.assertEquals(test, reader.get(0));

        reader.close();

    }
