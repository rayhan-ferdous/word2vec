    public void run() {

        while (true) {

            try {

                Thread.sleep(100);

                Display.getDefault().syncExec(new Runnable() {



                    public void run() {

                        refreshVisuals();

                    }

                });

            } catch (InterruptedException ex) {

                System.err.println(ex);

                ex.printStackTrace();

            }

        }

    }
