        assocRQ.setMaxPDULength(Integer.parseInt(cfg.getProperty("max-pdu-len", "16352")));

        assocRQ.setAsyncOpsWindow(aFact.newAsyncOpsWindow(Integer.parseInt(cfg.getProperty("max-op-invoked", "0")), 1));

        if (echo) {

            assocRQ.addPresContext(aFact.newPresContext(PCID_ECHO, UIDs.Verification, DEF_TS));

            return;

        }

        for (Enumeration it = cfg.keys(); it.hasMoreElements(); ) {

            String key = (String) it.nextElement();

            if (key.startsWith("pc.")) {

                initPresContext(Integer.parseInt(key.substring(3)), cfg.tokenize(cfg.getProperty(key), new LinkedList()));

            }

        }

    }



    private final void initPresContext(int pcid, List val) {

        Iterator it = val.iterator();

        String as = UIDs.forName((String) it.next());

        String[] tsUIDs = new String[val.size() - 1];

        for (int i = 0; i < tsUIDs.length; ++i) {

            tsUIDs[i] = UIDs.forName((String) it.next());

        }

        assocRQ.addPresContext(aFact.newPresContext(pcid, as, tsUIDs));

    }



    private void initOverwrite(Configuration cfg) {

        for (Enumeration it = cfg.keys(); it.hasMoreElements(); ) {

            String key = (String) it.nextElement();

            if (key.startsWith("set.")) {

                try {

                    overwrite.putXX(Tags.forName(key.substring(4)), cfg.getProperty(key));

                } catch (Exception e) {

                    throw new IllegalArgumentException("Illegal entry in dcmsnd.cfg - " + key + "=" + cfg.getProperty(key));

                }

            }

        }

    }



    private boolean initPollDirSrv(Configuration cfg) {
