        ensureConnection();

        PreparedStatement preparedStatement = null;

        HistoricalCommand parentCommand = null;

        ResultSet cursor = null;

        try {

            preparedStatement = embeddedConnection.prepareStatement(HISTORY_SELECT_SINGLE_BATCH);

            preparedStatement.setLong(1, reference.getTransactionId());

            boolean hasResults = preparedStatement.execute();

            if (hasResults) {

                cursor = preparedStatement.getResultSet();
