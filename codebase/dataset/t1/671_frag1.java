    public static List childElementList(Element element, Set childElementNames) {

        if (element == null) return null;

        List elements = FastList.newInstance();

        if (childElementNames == null) return elements;

        Node node = element.getFirstChild();

        if (node != null) {

            do {

                if (node.getNodeType() == Node.ELEMENT_NODE && childElementNames.contains(node.getNodeName())) {

                    Element childElement = (Element) node;

                    elements.add(childElement);

                }

            } while ((node = node.getNextSibling()) != null);

        }

        return elements;

    }
