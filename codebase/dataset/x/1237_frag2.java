    public String loadText(String filePath) {

        if (this.inJar && !isExternal(filePath)) {

            URL uri = getClassResourceUrl(this.getClass(), filePath);

            try {

                if (uri != null) {

                    InputStream is = uri.openStream();

                    String txt = FileTools.loadTextFromStream(is);

                    is.close();

                    return txt;

                }

            } catch (IOException ioe) {

                Debug.signal(Debug.ERROR, this, ioe);

            }

            return "";

        } else {

            return FileTools.loadTextFromFile(filePath);

        }

    }
