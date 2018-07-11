        Assert.assertEquals("double\"\"double quotes", reader.get(0));

        Assert.assertEquals(0L, reader.getCurrentRecord());

        Assert.assertEquals(1, reader.getColumnCount());

        Assert.assertEquals("\"double\\\"\\\"double quotes\"", reader.getRawRecord());

        Assert.assertFalse(reader.readRecord());

        reader.close();

    }