    protected Parameter getParameter(Element parameterElem) {

        if (parameterElem != null) {

            String Name = parameterElem.getAttributeValue("Name");

            String Value = parameterElem.getAttributeValue("Value");

            String Comment = parameterElem.getAttributeValue("Comment");

            Parameter parameter = new Parameter(Name, Value, Comment);

            return parameter;

        } else {

            System.err.println("Invalid element input in getParameter()");

            if (this.console != null) {

                console.setText("Invalid element input in getParameter()");

            }

            return null;

        }

    }
