    private IntegerSquareMatrix rawAdd(final IntegerMatrix m) {

        if (numRows == m.numRows && numCols == m.numCols) {

            final int array[][] = new int[numRows][numCols];

            for (int j, i = 0; i < numRows; i++) {

                array[i][0] = matrix[i][0] + m.matrix[i][0];

                for (j = 1; j < numCols; j++) array[i][j] = matrix[i][j] + m.matrix[i][j];

            }

            return new IntegerSquareMatrix(array);

        } else throw new MatrixDimensionException("Matrices are different sizes.");

    }
