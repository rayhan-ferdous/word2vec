        xmlStreamWriter.writeStartElement("xsl:template");

        xmlStreamWriter.writeAttribute("name", "addListeners");

        xmlStreamWriter.writeCharacters("\n");

        List<Extension> listeners = new ArrayList<Extension>();

        listeners.addAll(getExtensions(pd, "listener"));

        ExtensionPoint exnPt = pd.getExtensionPoint(EXNPT_CTX_LISTENER_ADPTR);

        if (exnPt != null) {

            listeners.addAll(exnPt.getConnectedExtensions());

        }

        Comparator<Object> comparator = new Comparator<Object>() {



            public int compare(Object obj1, Object obj2) {

                Extension e1 = (Extension) obj1;

                Extension e2 = (Extension) obj2;

                return e1.getParameter("listener-class").valueAsString().compareTo(e2.getParameter("listener-class").valueAsString());

            }



            ;

        };

        Collections.sort(listeners, comparator);

        for (Extension exn : listeners) {
