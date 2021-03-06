    public synchronized void register(RegistryEntry newRE) {

        float priority = newRE.getPriority();

        ListIterator li;

        li = entries.listIterator();

        while (li.hasNext()) {

            RegistryEntry re = (RegistryEntry) li.next();

            if (re.getPriority() > priority) {

                li.previous();

                li.add(newRE);

                return;

            }

        }

        li.add(newRE);

        extensions = null;

        mimeTypes = null;

    }
