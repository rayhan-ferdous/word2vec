    public void close(boolean success) {

        if (m_mailTransport != null) {

            try {

                m_mailTransport.close();

            } catch (MessagingException e) {

                lf.debug("Transport Close failed with exception: " + e.getMessage());

            }

        }

        initialized = false;

    }
