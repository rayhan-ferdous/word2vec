    private void initTLS(Configuration cfg) {

        try {

            cipherSuites = url.getCipherSuites();

            if (cipherSuites == null) {

                return;

            }

            tls = SSLContextAdapter.getInstance();

            char[] keypasswd = cfg.getProperty("tls-key-passwd", "secret").toCharArray();

            tls.setKey(tls.loadKeyStore(DcmGen.class.getResource(cfg.getProperty("tls-key", "identity.p12")), keypasswd), keypasswd);

            tls.setTrust(tls.loadKeyStore(DcmGen.class.getResource(cfg.getProperty("tls-cacerts", "cacerts.jks")), cfg.getProperty("tls-cacerts-passwd", "secret").toCharArray()));

            tls.init();

        } catch (Exception ex) {

            throw new RuntimeException("Could not initalize TLS configuration: ", ex);

        }

    }
