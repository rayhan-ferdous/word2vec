    public static int readIntWithVaryingBytesBE(InputStream in, int numBytes) throws IOException {

        int val = 0;

        for (int i = 0; i < numBytes; i++) {

            int curr = in.read();

            if (curr < 0) throw new EOFException();

            val <<= 8;

            val |= (curr & 0xFF);

        }

        return val;

    }
