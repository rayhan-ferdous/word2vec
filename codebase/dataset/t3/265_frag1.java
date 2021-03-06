    public List getAlerts(Session s, long accountId) throws Exception {

        try {

            String query = "select R from Alert R where R.accountId=? order by R.id";

            Query q = s.createQuery(query);

            q.setLong(0, accountId);

            return q.list();

        } catch (Exception e) {

            throw e;

        }

    }
