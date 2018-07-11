    public static boolean arePermissionsValid(final String permissions) {

        if (permissions != null && permissions.length() == 3) {

            try {

                Integer.parseInt(permissions);

                return true;

            } catch (NumberFormatException e) {

                return false;

            }

        } else return false;

    }
