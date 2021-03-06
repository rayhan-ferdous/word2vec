    public static CVSRoot getSelectedGlobalCVSRoot(GruntspudContext context) {

        StringTokenizer s = new StringTokenizer(context.getHost().getProperty(Constants.OPTIONS_CONNECTION_ROOT_LIST, "|"), "");

        while (s.hasMoreTokens()) {

            boolean sel = false;

            String t = s.nextToken();

            if (t.startsWith("&")) {

                t = t.substring(1);

                sel = true;

            }

            if (sel) {

                try {

                    return new CVSRoot(t);

                } catch (IllegalArgumentException iae) {

                }

            }

        }

        return null;

    }
