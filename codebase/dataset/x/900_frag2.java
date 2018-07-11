        public byte[] get() {

            try {

                InputStream in = open();

                try {

                    return IOUtils.load(in);

                } finally {

                    in.close();

                }

            } catch (IOException ex) {

                throw new RuntimeException(ex);

            }

        }
