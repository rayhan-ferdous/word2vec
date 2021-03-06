    public static Font[] loadFont(File file, boolean fingerprint) throws IOException {

        FontFactory fontFactory = FontFactory.getInstance();

        fontFactory.fingerprintFont(fingerprint);

        FileInputStream is = null;

        try {

            is = new FileInputStream(file);

            return fontFactory.loadFonts(is);

        } finally {

            if (is != null) {

                is.close();

            }

        }

    }
