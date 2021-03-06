    public boolean modifyMember(Members member) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction tr = null;

        try {

            tr = session.beginTransaction();

            session.update(member);

            session.flush();

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
