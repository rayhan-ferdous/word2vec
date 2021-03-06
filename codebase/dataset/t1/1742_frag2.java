    public int compare(int row1, int row2) {

        _compares++;

        for (int level = 0; level < _sortingColumns.size(); level++) {

            Integer column = _sortingColumns.elementAt(level);

            int result = compareRowsByColumn(row1, row2, column.intValue());

            if (result != 0) {

                return _isAscending ? result : (-result);

            }

        }

        return 0;

    }
