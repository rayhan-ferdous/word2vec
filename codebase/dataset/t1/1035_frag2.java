            if (!dir.canWrite()) throw new IOException("FileCopy: " + "destination directory is unwriteable: " + parent);

        }

        FileInputStream from = null;

        FileOutputStream to = null;

        try {

            from = new FileInputStream(fromFile);

            to = new FileOutputStream(toFile);

            byte[] buffer = new byte[4096];

            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1) to.write(buffer, 0, bytesRead);

        } finally {

            if (from != null) try {

                from.close();

            } catch (IOException e) {

                ;

            }

            if (to != null) try {

                to.close();

            } catch (IOException e) {

                ;

            }

        }

    }
