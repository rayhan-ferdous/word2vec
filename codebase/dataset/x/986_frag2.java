    public static void waitFor() {

        while (playerCount.get() > 0) {

            try {

                synchronized (playerCount) {

                    playerCount.wait();

                }

            } catch (InterruptedException ie) {

                ie.printStackTrace();

            }

        }

    }
