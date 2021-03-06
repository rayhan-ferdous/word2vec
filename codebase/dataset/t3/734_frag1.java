    public IntegerMatrix subtract(final IntegerMatrix m) {

        switch(m.storageFormat) {

            case ARRAY_2D:

                return rawSubtract(m);

            default:

                if (numRows == m.rows() && numCols == m.columns()) {

                    final int array[][] = new int[numRows][numCols];

                    for (int j, i = 0; i < numRows; i++) {

                        array[i][0] = matrix[i][0] - m.getElement(i, 0);

                        for (j = 1; j < numCols; j++) array[i][j] = matrix[i][j] - m.getElement(i, j);

                    }

                    return new IntegerMatrix(array);

                } else throw new MatrixDimensionException("Matrices are different sizes.");

        }

    }
