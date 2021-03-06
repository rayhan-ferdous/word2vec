    public static boolean zipContainsEntry(final String zipFilename, final String locationWithinZip) {

        ZipFile zipFile = null;

        try {

            zipFile = new ZipFile(zipFilename);

            getZipEntry(zipFile, locationWithinZip);

            return true;

        } catch (IOException e) {

            return false;

        } finally {

            try {

                if (zipFile != null) {

                    zipFile.close();

                }

            } catch (IOException e) {

            }

        }

    }
