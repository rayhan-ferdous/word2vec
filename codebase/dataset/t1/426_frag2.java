        for (Enumeration it = cfg.keys(); it.hasMoreElements(); ) {

            String key = (String) it.nextElement();

            if (key.startsWith("key.")) {

                try {

                    keys.putXX(Tags.forName(key.substring(4)), cfg.getProperty(key));

                } catch (Exception e) {

                    throw new IllegalArgumentException("Illegal key - " + key + "=" + cfg.getProperty(key));
