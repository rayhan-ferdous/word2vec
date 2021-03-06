    public void close() throws IOException {

        log.info("stopping EPOS server");

        eposServerProcess.destroy();

        try {

            eposServerProcess.waitFor();

            log.info("EPOS server stopped");

        } catch (InterruptedException e) {

            log.error("failed to wait for EPOS termination");

        }

    }
