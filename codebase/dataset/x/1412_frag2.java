    public void connect(Connection connection) {

        jdbcConnection = connection;

        try {

            handler = SessionHandler.getInstance();

            handler.setConnection(jdbcConnection);

        } catch (HibernateException e) {

            handler = null;

            e.printStackTrace();

        }

    }
