        if ("count".equals(pColumn)) {

            Sorter sorter = new Sorter();

            long[] temp = new long[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getCount();

            }

            fSortOrder = sorter.sortLong(temp, fSortOrder, up);

        }

        if ("description".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getDescription();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("remarks".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getRemarks();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("price".equals(pColumn)) {

            fComparator = new BigDecimalComparator();

            java.math.BigDecimal[] temp = new java.math.BigDecimal[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getPrice();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("discount".equals(pColumn)) {

            fComparator = new BigDecimalComparator();

            java.math.BigDecimal[] temp = new java.math.BigDecimal[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getDiscount();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("discountPercent".equals(pColumn)) {
