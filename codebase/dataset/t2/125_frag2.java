        if ("01".equals(urlData.getParameter("action"))) {

            outStream.write(showCalendar(urlData));

            return;

        } else if ("02".equals(urlData.getParameter("action"))) {
