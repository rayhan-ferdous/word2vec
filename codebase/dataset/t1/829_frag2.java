    public static boolean[] readBooleanArray(DataInput in) throws IOException {

        int len = in.readInt();

        if (len <= 0) {

            return null;

        }

        boolean[] ret = new boolean[len];

        for (int i = 0; i < len; i++) {

            ret[i] = in.readBoolean();

        }

        return ret;

    }
