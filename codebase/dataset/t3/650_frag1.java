    @Test

    public void test52() throws Exception {

        String data = "\\xfa\\u0afa\\xFA\\u0AFA";

        CsvReader reader = CsvReader.parse(data);

        reader.setUseTextQualifier(false);

        reader.setEscapeMode(CsvReader.ESCAPE_MODE_BACKSLASH);

        Assert.assertTrue(reader.readRecord());

        Assert.assertEquals("úૺúૺ", reader.get(0));

        Assert.assertEquals("\\xfa\\u0afa\\xFA\\u0AFA", reader.getRawRecord());

        Assert.assertEquals(0L, reader.getCurrentRecord());

        Assert.assertEquals(1, reader.getColumnCount());

        Assert.assertFalse(reader.readRecord());

        reader.close();

    }
