    public static void TelemetryFileClose() {

        try {

            telemetryStream.close();

        } catch (IOException ioe) {

            System.err.println("Error during file close: " + ioe.toString());

        }

    }
