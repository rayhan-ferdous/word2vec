        if ("id".equals(pColumn)) {

            Sorter sorter = new Sorter();

            long[] temp = new long[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getId();

            }

            fSortOrder = sorter.sortLong(temp, fSortOrder, up);

        }

        if ("productCategoryTreeID".equals(pColumn)) {

            Sorter sorter = new Sorter();

            long[] temp = new long[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getProductCategoryTreeID();

            }

            fSortOrder = sorter.sortLong(temp, fSortOrder, up);

        }

        if ("itemID".equals(pColumn)) {

            Sorter sorter = new Sorter();

            long[] temp = new long[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getItemID();

            }

            fSortOrder = sorter.sortLong(temp, fSortOrder, up);
