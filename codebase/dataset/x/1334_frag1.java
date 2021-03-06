    @Override

    public Collection<ValueType> getLoadables(String filter) throws LoaderException {

        Collection<Object> collection;

        List<ValueType> results = new ArrayList<ValueType>();

        try {

            collection = elements.get(elements.createFilter(filter));

        } catch (Exception e) {

            throw new LoaderException(e);

        }

        for (Iterator<Object> iterator = collection.iterator(); iterator.hasNext(); ) {

            Object next = iterator.next();

            if (next instanceof LoaderBase.LoadProxy) {

                LoadProxy proxy = (LoadProxy) next;

                results.add(proxy.getValue());

            } else {

                results.add((ValueType) next);

            }

        }

        return results;

    }
