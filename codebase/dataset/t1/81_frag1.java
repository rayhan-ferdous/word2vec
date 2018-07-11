    private void copy(InputStream in, File to) throws IOException {

        OutputStream out = new FileOutputStream(to);

        byte[] buf = new byte[1024];

        int len;

        while ((len = in.read(buf)) > 0) {

            out.write(buf, 0, len);

        }

        in.close();

        out.close();

    }
