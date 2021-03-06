    public void setupTranslationEuler(double[] trans, double[] euler) {

        double[] xyz = new double[12];

        int i;

        for (i = 0; i < 12; i++) {

            xyz[i] = 0.0f;

        }

        xyz[(3 * 1) + 0] = 1.0f;

        xyz[(3 * 2) + 1] = 1.0f;

        xyz[(3 * 3) + 2] = 1.0f;

        applyTranslation(trans, xyz, 4);

        applyEuler(euler, xyz, 4);

        setup(xyz);

    }
