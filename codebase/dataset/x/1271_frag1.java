    protected synchronized void flush() throws IOException {

        flushing = true;

        try {

            if (needsFlush) {

                needsFlush = false;

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                ZipOutputStream zos = new ZipOutputStream(bos);

                if (zipFile != null) {

                    for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {

                        ZipEntry zipEntry = (ZipEntry) (e.nextElement());

                        JarFile jarFile = (JarFile) (jarFileTable.get(zipEntry.getName()));

                        if (jarFile == null) writeZipEntry(zos, zipEntry, zipFile.getInputStream(zipEntry));

                    }

                }

                for (Enumeration e = jarFileTable.keys(); e.hasMoreElements(); ) {

                    String name = (String) (e.nextElement());

                    JarFile jarFile = (JarFile) (jarFileTable.get(name));

                    if (jarFile.exists() && !jarFile.isDirectory()) {

                        jarFile.flush();

                        writeZipEntry(zos, jarFile.getZipEntry(), jarFile.getInputStream());

                    }

                }

                if (zipFile != null) zipFile.close();

                zos.flush();

                zos.close();

                file.delete();

                file.createNewFile();

                FileOutputStream fos = new FileOutputStream(file);

                fos.write(bos.toByteArray());

                fos.flush();

                fos.close();

                zipFile = new ZipFile(file, ZipFile.OPEN_READ);

            }

        } finally {

            flushing = false;

        }

    }
