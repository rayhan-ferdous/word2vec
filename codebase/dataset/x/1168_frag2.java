    public static String getPostData(HttpServletRequest request) throws IOException {

        LOGGER.log(Level.INFO, "Reading post data");

        InputStream stream = request.getInputStream();

        StringBuilder builder = new StringBuilder();

        int byt = -1;

        while ((byt = stream.read()) != -1) {

            builder.append((char) byt);

        }

        String stringifiedStream = builder.toString();

        stream.close();

        stringifiedStream = new String(stringifiedStream.getBytes(), "UTF-8");

        LOGGER.log(Level.INFO, "Post Data are: " + stringifiedStream);

        return stringifiedStream;

    }
