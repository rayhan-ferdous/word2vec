    private byte[] addAgentToThemeMap(HTTPurl urlData) throws Exception {

        String agent = urlData.getParameter("agent");

        String theme = urlData.getParameter("theme");

        store.addAgentToThemeMap(agent, theme);

        StringBuffer buff = new StringBuffer();

        buff.append("HTTP/1.0 302 Moved Temporarily\n");

        buff.append("Location: /servlet/SystemDataRes?action=19\n\n");

        return buff.toString().getBytes();

    }
