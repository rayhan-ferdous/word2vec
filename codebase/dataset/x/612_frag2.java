    public String put(final File file) throws IOException {

        HttpURLConnection http = null;

        try {

            final URL url = new URL(UPLOAD_URL);

            final Map<String, Object> nameValuePairs = new HashMap<String, Object>();

            nameValuePairs.put("upfile", file);

            http = NetUtils.doPostMultipart(url, nameValuePairs);

            return new String(StreamReader.read(http.getInputStream()));

        } finally {

            if (http != null) {

                http.disconnect();

            }

        }

    }
