    public static Map<String, Object> getMetadata(String surl, ProgressMonitor mon) throws Exception {

        logger.log(Level.FINE, "getMetadata({0})", surl);

        if (surl.equals(metadataSurl)) {

            return metadata;

        } else {

            URI url = DataSetURI.getURIValid(surl);

            DataSourceFactory factory = DataSetURI.getDataSourceFactory(url, new NullProgressMonitor());

            DataSource result = factory.getDataSource(url);

            if (mon == null) {

                mon = new NullProgressMonitor();

            }

            return result.getMetadata(mon);

        }

    }
