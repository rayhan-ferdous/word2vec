        String style = (String) selector.getStyle();

        if (style != null) {

            rw.writeAttribute("style", style, "style");

        }

        String tabindex = (String) selector.getTabindex();

        if (tabindex != null) {

            rw.writeAttribute("tabindex", tabindex, "tabindex");

        }

        String title = (String) selector.getTitle();
