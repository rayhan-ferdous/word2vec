        if ("ta".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getTa();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("origin".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getOrigin();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("deliveryKind".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getDeliveryKind();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("customerNr".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getCustomerNr();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("sortKey".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getSortKey();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("currencyCode".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getCurrencyCode();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("amount".equals(pColumn)) {
