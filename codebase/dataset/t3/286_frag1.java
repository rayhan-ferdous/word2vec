    public static Field[] getStaticFields(Class cl) {

        Field[] members = cl.getFields();

        if (members.length == 0) {

            return null;

        }

        Vector vec = new Vector();

        int n = members.length;

        for (int i = 0; i < n; i++) {

            Field memb = members[i];

            if (isStatic(memb)) {

                vec.add(memb);

            }

        }

        if (vec.size() == 0) {

            return null;

        }

        Field[] out = new Field[vec.size()];

        vec.toArray(out);

        return out;

    }
