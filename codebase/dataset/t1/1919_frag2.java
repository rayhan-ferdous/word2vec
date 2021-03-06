    @Test

    public void test173() throws Exception {

        FailingReader fail = new FailingReader();

        CsvReader reader = new CsvReader(fail);

        boolean exceptionThrown = false;

        Assert.assertFalse(fail.DisposeCalled);

        try {

            reader.readRecord();

        } catch (IOException ex) {

            Assert.assertTrue(fail.DisposeCalled);

            exceptionThrown = true;

            Assert.assertEquals("Read failed.", ex.getMessage());

        } finally {

            reader.close();

        }

        Assert.assertTrue(exceptionThrown);

        try {

            reader.getHeaders();

        } catch (Exception ex) {

            assertException(new IOException("This instance of the CsvReader class has already been closed."), ex);

        }

    }
