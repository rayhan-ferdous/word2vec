    private int actionNamePosition(List<String> list, String seekAction) {

        int position = 0;

        for (String action : list) {

            if (action.equals(seekAction)) {

                break;

            } else {

                position++;

            }

        }

        return position;

    }
