    private byte[] deleteNamePattern(HTTPurl urlData) throws Exception {

        int index = Integer.parseInt(urlData.getParameter("id"));

        store.deleteNamePattern(index);

        StringBuffer buff = new StringBuffer();

        buff.append("HTTP/1.0 302 Moved Temporarily\n");

        buff.append("Location: /servlet/SystemDataRes?action=27\n\n");

        return buff.toString().getBytes();

    }
