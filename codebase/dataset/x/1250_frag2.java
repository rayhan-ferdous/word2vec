    public static List<List<String>> readListFromFile(final File file, final String split) throws IOException {

        final List<String> lines = readListFromFile(file);

        final List<List<String>> datasList = new ArrayList<List<String>>();

        for (final String line : lines) {

            final List<String> dataList = new ArrayList<String>();

            dataList.addAll(Arrays.asList(line.split(split)));

            datasList.add(dataList);

        }

        return datasList;

    }
