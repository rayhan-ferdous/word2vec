        for (int i = 0; i < clients.size(); i++) {

            if (clients.get(i) != null) {

                ClientThread t = (ClientThread) clients.get(i);

                try {

                    t.closeConnections();

                } catch (IOException e) {
