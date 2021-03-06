    public static void deleteDirectoryContents(File directory) {

        File[] contents = directory.listFiles();

        for (int index = 0; index < contents.length; index++) {

            File file = contents[index];

            if (file.isDirectory()) {

                deleteDirectoryContents(file);

                file.delete();

            } else {

                file.delete();

            }

        }

    }
