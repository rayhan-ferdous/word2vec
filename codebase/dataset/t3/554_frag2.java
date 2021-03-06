    void qSortImpl_nokeys(String src[], int fromIndex, int toIndex, Comparator c) {

        int low = fromIndex;

        int high = toIndex;

        String middle = "";

        if (high > low) {

            middle = src[(low + high) / 2];

            while (low <= high) {

                while ((low < toIndex) && (compare(src[low], middle, c) < 0)) {

                    ++low;

                }

                while ((high > fromIndex) && (compare(src[high], middle, c) > 0)) {

                    --high;

                }

                if (low <= high) {

                    String swap = src[low];

                    src[low] = src[high];

                    src[high] = swap;

                    ++low;

                    --high;

                }

            }

            if (fromIndex < high) {

                qSortImpl_nokeys(src, fromIndex, high, c);

            }

            if (low < toIndex) {

                qSortImpl_nokeys(src, low, toIndex, c);

            }

        }

    }
