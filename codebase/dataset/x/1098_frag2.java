    protected void disconnect(Exception e) {

        try {

            if (channel != null) {

                channel.close();

            }

            if (key != null) {

                key.cancel();

            }

            if (session != null) {

                session.remove(this);

            }

            if (daemon.debug) {

                if (Event.LOG) {

                    log("disconnect " + e);

                }

                if (e != null) {

                    e.printStackTrace();

                }

            }

            daemon.error(this, e);

        } catch (Exception de) {

            de.printStackTrace(daemon.out);

        } finally {

            daemon.events.remove(new Integer(index));

        }

    }
