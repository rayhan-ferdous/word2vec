    public void test89() throws Exception {

        try {

            CsvReader reader = new CsvReader("temp.csv", ',', null);

        } catch (Exception ex) {

            assertException(new IllegalArgumentException("Parameter charset can not be null."), ex);

        }

    }
