    public Cache getCache(int dclass) {

        Cache c = (Cache) caches.get(new Integer(dclass));

        if (c == null) {

            c = new Cache(dclass);

            caches.put(new Integer(dclass), c);

        }

        return c;

    }
