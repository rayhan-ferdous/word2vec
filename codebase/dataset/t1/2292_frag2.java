        try {

            FileReader in = new FileReader(new File(path));

            StringBuffer sb = new StringBuffer();

            for (int c = -1; (c = in.read()) != -1; ) {

                sb.append((char) c);

            }

            in.close();

            return sb.toString();

        } catch (FileNotFoundException e) {
