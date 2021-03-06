    public static void extractZipEntry(final ZipFile zipFile, final ZipEntry target, final File toDirectory) throws IOException {

        final File fileToExtract = new File(toDirectory, target.getName());

        InputStream is = null;

        BufferedInputStream bis = null;

        FileOutputStream fos = null;

        BufferedOutputStream bos = null;

        try {

            is = zipFile.getInputStream(target);

            bis = new BufferedInputStream(is);

            new File(fileToExtract.getParent()).mkdirs();

            fos = new FileOutputStream(fileToExtract);

            bos = new BufferedOutputStream(fos);

            for (int c; (c = bis.read()) != -1; ) {

                bos.write((byte) c);

            }

            bos.flush();

            bos.close();

        } catch (final IOException e) {

            throw e;

        } finally {

            StreamUtils.closeInputStream(bis);

            StreamUtils.closeInputStream(is);

            StreamUtils.closeOutputStream(fos);

            StreamUtils.closeOutputStream(bos);

        }

    }
