        if ("billingInfo".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getBillingInfo();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("referenceNumber".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getReferenceNumber();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("paymentMethod".equals(pColumn)) {
