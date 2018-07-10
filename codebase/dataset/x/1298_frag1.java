    public static double[][] toDoubleArray(int[][] intArray) {

        int m = intArray.length;

        int n = intArray[0].length;

        double[][] doubleArray = new double[m][n];

        for (int y = 0; y < m; y++) {

            for (int x = 0; x < n; x++) {

                doubleArray[y][x] = intArray[y][x];

            }

        }

        return doubleArray;

    }
