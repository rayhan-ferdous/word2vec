    private synchronized void logout() {

        if (!logged_in) {

            return;

        }

        if (!quit_sent) {

            String p[] = { quit_message };

            sendMessage("quit", p);

        }

        this.logged_in = false;

    }
