        @Override

        public InetAddress getLocalAddress() {

            try {

                return channel.getLocalAddress();

            } catch (UnknownHostException e) {

                return null;

            }

        }
