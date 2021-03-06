    protected Pool loadTransitionMatricesBinary(String path) throws FileNotFoundException, IOException {

        logger.fine("Loading transition matrices from: " + path);

        int numMatrices;

        int numStates;

        int numRows;

        int numValues;

        Properties props = new Properties();

        DataInputStream dis = readS3BinaryHeader(location, path, props);

        String version = props.getProperty("version");

        boolean doCheckSum;

        if (version == null || !version.equals(TMAT_FILE_VERSION)) {

            throw new IOException("Unsupported version in " + path);

        }

        String checksum = props.getProperty("chksum0");

        doCheckSum = (checksum != null && checksum.equals("yes"));

        Pool pool = new Pool(path);

        numMatrices = readInt(dis);

        numRows = readInt(dis);

        numStates = readInt(dis);

        numValues = readInt(dis);

        assert numValues == numStates * numRows * numMatrices;

        for (int i = 0; i < numMatrices; i++) {

            float[][] tmat = new float[numStates][];

            tmat[numStates - 1] = new float[numStates];

            convertToLogMath(tmat[numStates - 1]);

            for (int j = 0; j < numRows; j++) {

                tmat[j] = readFloatArray(dis, numStates);

                nonZeroFloor(tmat[j], 0.00001f);

                normalize(tmat[j]);

                convertToLogMath(tmat[j]);

            }

            pool.put(i, tmat);

        }

        dis.close();

        return pool;

    }
