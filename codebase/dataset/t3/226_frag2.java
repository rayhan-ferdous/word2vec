    private static void update(EntityManagerFactory entityManagerFactory) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT o FROM Order o");

            Collection<Order> collection = (Collection<Order>) query.getResultList();

            for (Order order : collection) {

                for (OrderItem item : order.getItems()) {

                    item.setPrice(item.getPrice() * 2);

                }

            }

            entityManager.getTransaction().commit();

        } finally {

            if (entityManager.getTransaction().isActive()) {

                entityManager.getTransaction().rollback();

            }

            entityManager.close();

        }

    }
