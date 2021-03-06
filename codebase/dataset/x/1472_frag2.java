    public static void listFilesRecursive(File path, ArrayList<File> filesArray) {

        if (path.exists()) {

            File[] files = path.listFiles();

            for (int i = 0; i < files.length; i++) {

                if (files[i].isDirectory()) {

                    FileTools.listFilesRecursive(files[i], filesArray);

                } else {

                    filesArray.add(files[i]);

                }

            }

        }

    }



    public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {

        byte[] buffer = new byte[1024];

        int len;

        while ((len = in.read(buffer)) >= 0) out.write(buffer, 0, len);

        in.close();

        out.close();

    }

}
