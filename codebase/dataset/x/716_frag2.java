                    } catch (IOException e) {

                        log.warn("Could not send packet >" + cmd.toString() + "<");

                    }

                }

            }

        }

    }



    private class DomintellReaderThread extends Thread {



        private DatagramSocket socket;



        public DomintellReaderThread(DatagramSocket socket) {

            super();

            this.socket = socket;

        }



        @Override

        public void run() {

            log.info("Reader thread starting");

            synchronized (loginState) {

                loginState.loggedIn = false;

                loginState.needsLogin = true;
