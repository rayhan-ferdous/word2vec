    protected static SecureRandom getRandom() {

        if (random == null) {

            random = new SecureRandom();

        }

        return random;

    }
