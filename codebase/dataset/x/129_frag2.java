    public static boolean decodeToFile(String dataToDecode, String filename) {

        boolean success = false;

        Base64.OutputStream bos = null;

        try {

            bos = new Base64.OutputStream(new java.io.FileOutputStream(filename), Base64.DECODE);

            bos.write(dataToDecode.getBytes(PREFERRED_ENCODING));

            success = true;

        } catch (java.io.IOException e) {

            success = false;

        } finally {

            try {

                bos.close();

            } catch (Exception e) {

            }

        }

        return success;

    }
