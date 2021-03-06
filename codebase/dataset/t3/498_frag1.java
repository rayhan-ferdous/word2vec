        if ("evalStart".equals(pColumn)) {

            fComparator = new DateComparator();

            java.sql.Date[] temp = new java.sql.Date[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getEvalStart();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("evalDate".equals(pColumn)) {

            fComparator = new DateComparator();

            java.sql.Date[] temp = new java.sql.Date[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getEvalDate();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("ledgerID".equals(pColumn)) {

            Sorter sorter = new Sorter();

            long[] temp = new long[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getLedgerID();

            }

            fSortOrder = sorter.sortLong(temp, fSortOrder, up);

        }

        if ("accountID".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getAccountID();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("ledgerName".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getLedgerName();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("ledgerType".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getLedgerType();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("amountStart".equals(pColumn)) {
