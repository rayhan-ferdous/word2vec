    public void run() {

        try {

            task.collect(source);

        } catch (Throwable e) {

            cancel();

        }

    }
