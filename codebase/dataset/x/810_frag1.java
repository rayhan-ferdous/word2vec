    public void sleep(long millis) {

        try {

            Thread.sleep(millis);

        } catch (InterruptedException e) {

            logger.error("delay method was interrupted !");

        }

    }
