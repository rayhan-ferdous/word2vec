        formparams.add(new BasicNameValuePair("password", "*********************"));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");

        httppost.setEntity(entity);

        HttpResponse httpresponse = httpclient.execute(httppost);

        System.out.println("Getting cookies........");

        System.out.println(EntityUtils.toString(httpresponse.getEntity()));

        Iterator<Cookie> it = httpclient.getCookieStore().getCookies().iterator();

        Cookie escookie = null;

        while (it.hasNext()) {

            escookie = it.next();
