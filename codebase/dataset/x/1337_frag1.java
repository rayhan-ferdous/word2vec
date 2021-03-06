    public void saveOrUpdate(Object persistentObject) throws Exception {

        Transaction tx = null;

        try {

            tx = getSession().beginTransaction();

            getSession().saveOrUpdate(persistentObject);

            tx.commit();

        } catch (Exception e) {

            tx.rollback();

            e.printStackTrace();

            throw e;

        }

    }
