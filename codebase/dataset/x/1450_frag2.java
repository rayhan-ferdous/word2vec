            url = new URL(urls);

        } catch (MalformedURLException e) {

            finished(Navigator.i18n.getString("ManualWMSConfigurationPanel_INVALID_URL"));

        }

        try {

            wms = new WebMapServer(url);

        } catch (Exception e) {

            finished(Navigator.i18n.getString("ManualWMSConfigurationPanel_INVALID_WMS"));
