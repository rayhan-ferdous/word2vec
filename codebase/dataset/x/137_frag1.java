            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getTitle();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("name".equals(pColumn)) {
