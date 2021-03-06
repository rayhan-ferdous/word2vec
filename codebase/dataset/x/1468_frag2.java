    @Override

    public String getOuput() {

        String[] args = input.split(" ");

        if (args.length == 2) {

            File file1 = new File(args[0]);

            File file2 = new File(args[1]);

            boolean ok = false;

            if (file1.exists()) {

                if (file2.isDirectory()) {

                    file2 = new File(file2.getAbsolutePath(), file1.getName());

                }

                ok = copyFile(file1, file2);

            }

            if (!ok) {

                return "Copy failed";

            }

        }

        return "";

    }
