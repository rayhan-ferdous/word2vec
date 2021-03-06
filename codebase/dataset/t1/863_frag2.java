        Object o1 = data.getValueAt(row1, column);

        Object o2 = data.getValueAt(row2, column);

        if (o1 == null && o2 == null) {

            return 0;

        } else if (o1 == null) {

            return -1;

        } else if (o2 == null) {

            return 1;

        }

        if (type.getSuperclass() == java.lang.Number.class) {

            Number n1 = (Number) data.getValueAt(row1, column);

            double d1 = n1.doubleValue();

            Number n2 = (Number) data.getValueAt(row2, column);

            double d2 = n2.doubleValue();

            if (d1 < d2) return -1; else if (d1 > d2) return 1; else return 0;

        } else if (type == java.util.Date.class) {

            Date d1 = (Date) data.getValueAt(row1, column);

            long n1 = d1.getTime();

            Date d2 = (Date) data.getValueAt(row2, column);

            long n2 = d2.getTime();

            if (n1 < n2) return -1; else if (n1 > n2) return 1; else return 0;

        } else if (type == String.class) {

            String s1 = (String) data.getValueAt(row1, column);

            String s2 = (String) data.getValueAt(row2, column);

            int result = s1.compareTo(s2);

            if (result < 0) return -1; else if (result > 0) return 1; else return 0;

        } else if (type == Boolean.class) {

            Boolean bool1 = (Boolean) data.getValueAt(row1, column);

            boolean b1 = bool1.booleanValue();

            Boolean bool2 = (Boolean) data.getValueAt(row2, column);

            boolean b2 = bool2.booleanValue();

            if (b1 == b2) return 0; else if (b1) return 1; else return -1;

        } else {
