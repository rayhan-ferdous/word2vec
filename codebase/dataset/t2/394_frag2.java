    public static FloatMatrix constructWithCopy(float[][] A) {

        int m = A.length;

        int n = A[0].length;

        FloatMatrix X = new FloatMatrix(m, n);

        float[][] C = X.getArray();

        for (int i = 0; i < m; i++) {

            if (A[i].length != n) {

                throw new IllegalArgumentException("All rows must have the same length.");

            }

            for (int j = 0; j < n; j++) {

                C[i][j] = A[i][j];

            }

        }

        return X;

    }
