    private void compileUrlWeb(String query) {

        try {

            q = URLEncoder.encode(query, "UTF-8");

            url = new URL(WEB + "?" + "&v=" + v + "&key=" + KEY + "&userip=" + userip + "&q=" + q + "&rsz=" + rsz + "&hl=" + hl + "&start=" + start);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

    }
