    public static void matmat_f77(double a[][], double b[][], double c[][], int n, int p, int r) {

        double vdot;

        int i, j, k, m;

        for (i = 1; i <= n; i++) {

            for (j = 1; j <= r; j++) {

                vdot = 0.0;

                m = p % 5;

                for (k = 1; k <= m; k++) {

                    vdot += a[i][k] * b[k][j];

                }

                for (k = m + 1; k <= p; k += 5) {

                    vdot += a[i][k] * b[k][j] + a[i][k + 1] * b[k + 1][j] + a[i][k + 2] * b[k + 2][j] + a[i][k + 3] * b[k + 3][j] + a[i][k + 4] * b[k + 4][j];

                }

                c[i][j] = vdot;

            }

        }

    }
