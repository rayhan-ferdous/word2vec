    public static ClassLoader addFileToClassPath(ClassLoader root, ClassLoader classLoader, File f) {

        if (f.exists() && (!f.isDirectory()) && f.getName().endsWith(".jar")) {

            try {

                if (classLoader instanceof URLClassLoader) {

                    URL[] old = ((URLClassLoader) classLoader).getURLs();

                    URL[] new_urls = new URL[old.length + 1];

                    System.arraycopy(old, 0, new_urls, 1, old.length);

                    new_urls[0] = f.toURL();

                    classLoader = new URLClassLoader(new_urls, classLoader == root ? classLoader : classLoader.getParent());

                } else {

                    classLoader = new URLClassLoader(new URL[] { f.toURL() }, classLoader);

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        return (classLoader);

    }
