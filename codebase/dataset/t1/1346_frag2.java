    public static String encodeObject(java.io.Serializable serializableObject, int options) {

        java.io.ByteArrayOutputStream baos = null;

        java.io.OutputStream b64os = null;

        java.io.ObjectOutputStream oos = null;

        java.util.zip.GZIPOutputStream gzos = null;

        int gzip = (options & GZIP);

        int dontBreakLines = (options & DONT_BREAK_LINES);

        try {

            baos = new java.io.ByteArrayOutputStream();

            b64os = new Base64.OutputStream(baos, ENCODE | options);

            if (gzip == GZIP) {

                gzos = new java.util.zip.GZIPOutputStream(b64os);

                oos = new java.io.ObjectOutputStream(gzos);

            } else oos = new java.io.ObjectOutputStream(b64os);

            oos.writeObject(serializableObject);

        } catch (java.io.IOException e) {

            e.printStackTrace();

            return null;

        } finally {

            try {

                oos.close();

            } catch (Exception e) {

            }

            try {

                gzos.close();

            } catch (Exception e) {

            }

            try {

                b64os.close();

            } catch (Exception e) {

            }

            try {

                baos.close();

            } catch (Exception e) {

            }

        }

        try {

            return new String(baos.toByteArray(), PREFERRED_ENCODING);

        } catch (java.io.UnsupportedEncodingException uue) {

            return new String(baos.toByteArray());

        }

    }
