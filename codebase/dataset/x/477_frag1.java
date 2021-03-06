    public boolean updateThreads(Threads thread) {

        Transaction tr = null;

        try {

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();

            tr = session.beginTransaction();

            session.update(thread);

            tr.commit();

            return true;

        } catch (HibernateException e) {

            if (tr != null) {

                tr.rollback();

            }

            e.printStackTrace();

        }

        return false;

    }
