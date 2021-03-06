    private static String readURLConnection(URLConnection uc) throws Exception {

        StringBuffer buffer = new StringBuffer();

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));

            int letter = 0;

            while ((letter = reader.read()) != -1) buffer.append((char) letter);

        } catch (Exception e) {

            System.out.println("Cannot read from URL" + e.toString());

            throw e;

        } finally {

            try {

                reader.close();

            } catch (java.io.IOException io) {

                System.out.println("Error closing URLReader!");

                throw io;

            }

        }

        return buffer.toString();

    }
