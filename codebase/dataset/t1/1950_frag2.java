    private static void insert(EntityManagerFactory entityManagerFactory) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            Person person = new Person();

            person.setFirstName("Jesse");

            person.setLastName("James");

            person.setImage(new byte[] { 0x12, 0x23, 0x34, 0x45, 0x56, 0x67, 0x78, (byte) 0xff });

            person.setProfile(generateLongText());

            entityManager.persist(person);

            entityManager.getTransaction().commit();

        } finally {

            if (entityManager.getTransaction().isActive()) {

                entityManager.getTransaction().rollback();

            }

            entityManager.close();

        }

    }
