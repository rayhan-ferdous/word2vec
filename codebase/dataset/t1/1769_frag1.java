    Vector substituteInRegion(Vector region, String var, String[] fields, String[] fieldData) throws IOException {

        Vector newRegion = new Vector(region.size());

        for (int i = 0; i < region.size(); i++) {

            Object el = region.elementAt(i);

            try {

                String s = (String) el;

                String r = substitute(s, var, fields, fieldData);

                newRegion.addElement(r);

            } catch (ClassCastException e) {

                Vector s = (Vector) el;

                Vector r = substituteInRegion(s, var, fields, fieldData);

                newRegion.addElement(r);

            }

        }

        return newRegion;

    }
