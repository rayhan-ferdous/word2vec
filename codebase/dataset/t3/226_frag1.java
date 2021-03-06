    private static void update(EntityManagerFactory entityManagerFactory) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT p FROM Person p");

            Collection<Person> collection = (Collection<Person>) query.getResultList();

            for (Person person : collection) {

                person.setFirstName(person.getFirstName() + "-1");

            }

            entityManager.getTransaction().commit();

        } finally {

            if (entityManager.getTransaction().isActive()) {

                entityManager.getTransaction().rollback();

            }

            entityManager.close();

        }

    }
