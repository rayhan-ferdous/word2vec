    public void setFrom(String from) {

        try {

            this.message.setFrom(new InternetAddress(from));

        } catch (MessagingException exception) {

            log.error(exception);

        }

    }
