    public void test27() throws Exception {

        String data = "\"1\",Bruce\r\n\"2\n\",Toni\r\n\"3\",Brian\r\n";

        CsvReader reader = CsvReader.parse(data);

        Assert.assertTrue(reader.readRecord());

        Assert.assertEquals("1", reader.get(0));

        Assert.assertEquals("Bruce", reader.get(1));

        Assert.assertEquals(0L, reader.getCurrentRecord());

        Assert.assertEquals(2, reader.getColumnCount());

        Assert.assertEquals("\"1\",Bruce", reader.getRawRecord());

        Assert.assertTrue(reader.skipRecord());

        Assert.assertEquals("\"2\n\",Toni", reader.getRawRecord());

        Assert.assertTrue(reader.readRecord());

        Assert.assertEquals("3", reader.get(0));

        Assert.assertEquals("Brian", reader.get(1));

        Assert.assertEquals(1L, reader.getCurrentRecord());

        Assert.assertEquals(2, reader.getColumnCount());

        Assert.assertEquals("\"3\",Brian", reader.getRawRecord());

        Assert.assertFalse(reader.readRecord());

        reader.close();

    }
