    private static void cleanUpdater() {

        final File tmpDir = new File("./UPDATER_TMP");

        final File updaterFile = new File("./CoopnetUpdater.jar");

        if (tmpDir.exists() || updaterFile.exists()) {

            Logger.log(LogTypes.LOG, "Updater files queued for deletion ...");

            new Thread() {



                private static final int UPDATER_CLOSING_SLEEP = 1000;



                @Override

                public void run() {

                    try {

                        sleep(UPDATER_CLOSING_SLEEP);

                    } catch (InterruptedException ex) {

                        Logger.log(ex);

                    }

                    try {

                        if (tmpDir.exists()) {

                            Logger.log(LogTypes.LOG, "Deleting ./UPDATER_TMP recursively");

                            deleteFile(tmpDir);

                        }

                    } catch (IOException e) {

                        Logger.log(e);

                    }

                    try {

                        if (updaterFile.exists()) {

                            Logger.log(LogTypes.LOG, "Deleting ./CoopnetUpdater.jar");

                            deleteFile(updaterFile);

                        }

                    } catch (IOException e) {

                        Logger.log(e);

                    }

                }

            }.start();

        }

    }
