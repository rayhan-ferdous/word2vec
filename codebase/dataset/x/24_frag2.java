        for (int i = 0; i < count; i++) {

            Channel aChan = (Channel) channels[i];

            Object[] items = aChan.getItems().toArray();

            int howManyItems = items.length;

            for (int j = 0; j < howManyItems; j++) {

                Item anItem = (Item) items[j];
