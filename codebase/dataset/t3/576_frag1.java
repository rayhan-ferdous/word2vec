    public void writeFile(String name, String mimeType, String fileName, byte[] data) throws java.io.IOException {

        if (data == null) {

            throw new IllegalArgumentException("Data cannot be null.");

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

        out.write(data, 0, data.length);

        out.writeBytes(NEWLINE);

        out.flush();

    }
