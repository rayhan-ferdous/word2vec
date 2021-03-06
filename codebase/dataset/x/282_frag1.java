    public static String toHexString(byte[] b) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < b.length; i++) {

            if (b[i] < 0) sb.append(Integer.toHexString(256 + b[i])); else {

                if (b[i] < 16) sb.append("0");

                sb.append(Integer.toHexString(b[i]));

            }

        }

        return sb.toString();

    }
