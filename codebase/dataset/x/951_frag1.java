        public synchronized Entry getPrecedingEntry(Object key) {

            Entry p = getRoot();

            if (p == null) return null;

            while (true) {

                int cmp = compare(key, p.getKey0());

                if (cmp > 0) {

                    if (p.getRight() != null) p = p.getRight(); else return p;

                } else {

                    if (p.getLeft() != null) {

                        p = p.getLeft();

                    } else {

                        Entry parent = p.getParent();

                        Entry ch = p;

                        while (parent != null && ch == parent.getLeft()) {

                            ch = parent;

                            parent = parent.getParent();

                        }

                        return parent;

                    }

                }

            }

        }
