    private Pool createSenonePool(float distFloor, float varianceFloor) {

        Pool pool = new Pool("senones");

        int numMixtureWeights = mixtureWeightsPool.size();

        int numMeans = meansPool.size();

        int numVariances = variancePool.size();

        int numGaussiansPerSenone = mixtureWeightsPool.getFeature(NUM_GAUSSIANS_PER_STATE, 0);

        int numSenones = mixtureWeightsPool.getFeature(NUM_SENONES, 0);

        int whichGaussian = 0;

        logger.fine("NG " + numGaussiansPerSenone);

        logger.fine("NS " + numSenones);

        logger.fine("NMIX " + numMixtureWeights);

        logger.fine("NMNS " + numMeans);

        logger.fine("NMNS " + numVariances);

        assert numGaussiansPerSenone > 0;

        assert numMixtureWeights == numSenones;

        assert numVariances == numSenones * numGaussiansPerSenone;

        assert numMeans == numSenones * numGaussiansPerSenone;

        for (int i = 0; i < numSenones; i++) {

            MixtureComponent[] mixtureComponents = new MixtureComponent[numGaussiansPerSenone];

            int classe = (classeMllr != null) ? classeMllr[i] : 0;

            for (int j = 0; j < numGaussiansPerSenone; j++) {

                mixtureComponents[j] = new MixtureComponent(logMath, (float[]) meansPool.get(whichGaussian), (float[][]) meanTransformationMatrixPool.get(classe), (float[]) meanTransformationVectorPool.get(classe), (float[]) variancePool.get(whichGaussian), (float[][]) varianceTransformationMatrixPool.get(0), (float[]) varianceTransformationVectorPool.get(0), distFloor, varianceFloor);

                whichGaussian++;

            }

            Senone senone = new GaussianMixture(logMath, (float[]) mixtureWeightsPool.get(i), mixtureComponents, i);

            pool.put(i, senone);

        }

        return pool;

    }
