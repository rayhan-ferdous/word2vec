    public void insert(E obj) {

        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            obj = em.merge(obj);

            tx.commit();

        } finally {

            if (tx.isActive()) {

                tx.rollback();

            }

            em.close();

        }

    }
