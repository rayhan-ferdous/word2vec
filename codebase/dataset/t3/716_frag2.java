    protected int getSortingColumn() {

        int answer = -1;

        if (!_sortingColumns.isEmpty()) {

            Integer columnNum = _sortingColumns.elementAt(0);

            if (columnNum != null) {

                answer = columnNum.intValue();

            }

        }

        return answer;

    }