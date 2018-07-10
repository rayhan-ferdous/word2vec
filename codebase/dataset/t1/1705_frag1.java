    public static void main(String argv[]) {

        try {

            initSystemProperties();

            initThreads();

            for (int count = 0; count < NUM_THREADS; count++) {

                INSTANCES[count].m_thread.start();

            }

        } catch (Throwable e) {

            e.printStackTrace();

            System.exit(1);

        }

    }
