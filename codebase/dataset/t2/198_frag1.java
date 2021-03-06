    void fireChannelModeReceived(ChannelModeEvent event) {

        synchronized (connectionListeners) {

            for (final ConnectionListener connectionListener : connectionListeners) {

                try {

                    connectionListener.channelModeReceived(event);

                } catch (Exception exc) {

                    handleException(exc);

                }

            }

        }

    }
