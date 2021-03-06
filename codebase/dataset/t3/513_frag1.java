    public boolean validateForOneFileMethodSetFileNamePrefix() throws Exception {

        String testRoot = TEST_ForOneFileMethodSetFileNamePrefix;

        boolean test = true;

        sReferencePS.clear();

        sReferencePS.set(NAME_FileNamePrefix, P);

        sReferencePS.set(NAME_FileNameRoot, testRoot);

        sReferencePS.set(NAME_FileNameSuffix, DOT + TXT);

        sTestPS.load(new File(getTestFolder(), P + testRoot + DOT + TXT));

        test = sTestPS.equals(sReferencePS);

        if (!test) {

            displayPropertySets(sTestPS, sReferencePS);

        }

        return test;

    }
