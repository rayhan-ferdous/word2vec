    protected void merge(int begin, int middle, int end) {

        int firstHalf, secondHalf, count;

        firstHalf = count = begin;

        secondHalf = middle + 1;

        while ((firstHalf <= middle) && (secondHalf <= end)) {

            if (this.compareElementsAt(secondHalf, firstHalf) < 0) swapSpace[count++] = toSort[secondHalf++]; else swapSpace[count++] = toSort[firstHalf++];

        }

        if (firstHalf <= middle) {

            while (firstHalf <= middle) swapSpace[count++] = toSort[firstHalf++];

        } else {

            while (secondHalf <= end) swapSpace[count++] = toSort[secondHalf++];

        }

        for (count = begin; count <= end; count++) toSort[count] = swapSpace[count];

    }
