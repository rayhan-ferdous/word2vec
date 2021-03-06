    private static int getTestType(String makeFileName) {

        int type = DEVICE_SIDE_ONLY;

        try {

            BufferedReader reader = new BufferedReader(new FileReader(makeFileName));

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith(TEST_TYPE)) {

                    type = HOST_SIDE_ONLY;

                } else if (line.startsWith(JAR_PATH)) {

                    jarPath = line.substring(JAR_PATH.length(), line.length()).trim();

                }

            }

            reader.close();

        } catch (IOException e) {

        }

        return type;

    }
