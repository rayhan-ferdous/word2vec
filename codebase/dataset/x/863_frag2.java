    private void releaseConnection() {

        try {

            con.close();

        } catch (SQLException e) {

            System.out.println("DirectoryBean: Reaease connect error: " + e.getMessage());

        }

    }
