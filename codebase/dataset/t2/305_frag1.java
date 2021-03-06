    public List getPathsAsList() throws DatabaseException {

        if (pathsList == null) {

            pathsList = new ArrayList();

            StoredMap m = getPaths();

            Iterator i = m.values().iterator();

            while (i.hasNext()) {

                pathsList.add(i.next());

            }

            StoredIterator.close(i);

        }

        return pathsList;

    }
