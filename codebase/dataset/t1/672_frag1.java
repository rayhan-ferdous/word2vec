    public static String parseResponse(String response, String stringStart, String stringEnd) {

        response = response.substring(response.indexOf(stringStart));

        response = response.replace(stringStart, "");

        response = response.substring(0, response.indexOf(stringEnd));

        return response;

    }
