    public void writeFile(String name, String mimeType, String fileName, InputStream is) throws java.io.IOException {

        if (is == null) {

            throw new IllegalArgumentException("Input stream cannot be null.");

        }

        if (fileName == null || fileName.length() == 0) {

            throw new IllegalArgumentException("File name cannot be null or empty.");

        }

        out.writeBytes(PREFIX);

        out.writeBytes(boundary);

        out.writeBytes(NEWLINE);

        out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"");

        out.writeBytes(NEWLINE);

        if (mimeType != null) {

            out.writeBytes("Content-Type: " + mimeType);

            out.writeBytes(NEWLINE);

        }

        out.writeBytes(NEWLINE);

        byte[] data = new byte[1024];

        int r = 0;

        while ((r = is.read(data, 0, data.length)) != -1) {

            out.write(data, 0, r);

        }

        try {

            is.close();

        } catch (Exception e) {

        }

        out.writeBytes(NEWLINE);

        out.flush();

    }
