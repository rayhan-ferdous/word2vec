            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getCurrencyDebit();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("ledgerCredit".equals(pColumn)) {

            Sorter sorter = new Sorter();

            long[] temp = new long[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getLedgerCredit();

            }

            fSortOrder = sorter.sortLong(temp, fSortOrder, up);

        }

        if ("amountCredit".equals(pColumn)) {

            fComparator = new BigDecimalComparator();

            java.math.BigDecimal[] temp = new java.math.BigDecimal[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getAmountCredit();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("currencyCredit".equals(pColumn)) {
