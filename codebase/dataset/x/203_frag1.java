            java.math.BigDecimal[] temp = new java.math.BigDecimal[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getAmount();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("referenceFi".equals(pColumn)) {

            fComparator = new StringComparator();

            String[] temp = new String[fTableData.length];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = fTableData[i].getReferenceFi();

            }

            sort(temp, 0, temp.length - 1, up);

        }

        if ("orderDate".equals(pColumn)) {