                lock.readLock().unlock();

            }

        });

        try {

            t1.start();

            t2.start();

            lock.readLock().lock();

            lock.readLock().unlock();

            Thread.sleep(SHORT_DELAY_MS);

            lock.readLock().lock();

            lock.readLock().unlock();

            lock.writeLock().unlock();

            t1.join(MEDIUM_DELAY_MS);

            t2.join(MEDIUM_DELAY_MS);

            assertTrue(!t1.isAlive());

            assertTrue(!t2.isAlive());

        } catch (Exception e) {

            unexpectedException();

        }

    }
