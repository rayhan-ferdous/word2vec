    public static boolean resourceExists(String resourcePath) {

        InputStream stream = null;

        boolean exists = false;

        try {

            stream = getInputStreamForPath(resourcePath);

            exists = true;

        } catch (IOException e) {

            stream = null;

        } finally {

            if (stream != null) {

                try {

                    stream.close();

                } catch (IOException ignored) {

                }

            }

        }

        return exists;

    }
