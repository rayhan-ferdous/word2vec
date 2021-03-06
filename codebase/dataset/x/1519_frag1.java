    public List<String> listDatabases() {

        Response resp = null;

        try {

            resp = get("/_all_dbs");

            if (!resp.isOk()) {

                throw new CouchDBException("Error listing databases: " + resp);

            }

            return resp.getContentAsList();

        } finally {

            if (resp != null) {

                resp.destroy();

            }

        }

    }
