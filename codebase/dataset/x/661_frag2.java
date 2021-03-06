    public boolean isParent(long txId) throws Exception {

        Session s = null;

        try {

            s = HibernateUtils.getSessionFactory().getCurrentSession();

            s.beginTransaction();

            String query = "select count(R) from TT R where R.parentTxId=?";

            Query q = s.createQuery(query);

            q.setLong(0, txId);

            List l = q.list();

            return l.size() != 0;

        } catch (Exception e) {

            throw e;

        } finally {

            if (s != null) HibernateUtils.closeSession();

        }

    }
