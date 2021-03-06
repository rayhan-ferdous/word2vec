    public Document build(InputStream istream) throws JPListException {

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new InputStreamReader(istream));

            return AsciiPListParser.parse(reader);

        } catch (ParseException e) {

            throw new JPListException("Unrecognized format", e);

        } finally {

            if (reader != null) {

                try {

                    reader.close();

                } catch (Exception e) {

                }

            }

        }

    }
