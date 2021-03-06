    private static String getDetails(InputSource source) {

        StringBuilder sb = new StringBuilder();

        if (source.getPublicId() != null) {

            sb.append("PublicId='");

            sb.append(source.getPublicId());

            sb.append("'  ");

        }

        if (source.getSystemId() != null) {

            sb.append("SystemId='");

            sb.append(source.getSystemId());

            sb.append("'  ");

        }

        if (source.getEncoding() != null) {

            sb.append("Encoding='");

            sb.append(source.getEncoding());

            sb.append("'  ");

        }

        return sb.toString();

    }
