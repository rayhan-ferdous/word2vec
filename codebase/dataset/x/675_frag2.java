    public void readProxy() {

        if (proxyUse()) {

            HttpStream.proxyAddress = proxyAddress();

            HttpStream.proxyPort = proxyPort();

            ;

        } else {

            HttpStream.proxyAddress = null;

            HttpStream.proxyPort = -1;

        }

        this.defaultBauds = defaultBauds();

    }
