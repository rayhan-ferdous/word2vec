    public void deleteByKey(String keys) throws Exception {

        HttpRequest httpReq = new HttpRequest(new URL(remoteIndexUrl));

        CGIParameters params = new CGIParameters();

        params.put("cmd", Constants.CGI_PARAM_CMD_DELETE);

        params.put(Constants.CGI_PARAM_KEYS, keys);

        InputStream is = null;

        is = httpReq.post(params);

        try {

            int read = 0;

            while ((read = is.read()) != -1) {

            }

        } finally {

            if (is != null) is.close();

        }

    }
