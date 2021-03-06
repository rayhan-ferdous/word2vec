    public static String propsToString(Properties p, String header) throws Exception {

        ByteArrayOutputStream logstw = null;

        String logmsg = null;

        if (p != null) {

            try {

                logstw = new ByteArrayOutputStream();

                p.store(logstw, header);

                logmsg = logstw.toString();

            } finally {

                CloseUtils.close(logstw);

            }

        }

        return logmsg;

    }
