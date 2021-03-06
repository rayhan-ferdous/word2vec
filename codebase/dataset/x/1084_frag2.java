    @Override

    protected Enumeration<URL> findResources(String path) throws IOException {

        if (accepts(path) == false) {

            return EMPTY;

        }

        List<URL> results = new LinkedList<URL>();

        for (ClassLoaderDelegate delegate : delegates) {

            Iterable<URL> found = delegate.findAllResources(path);

            if (found != null) {

                for (URL url : found) {

                    results.add(url);

                }

            }

        }

        return new IteratorEnumeration<URL>(results.iterator());

    }
