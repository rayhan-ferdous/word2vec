    protected void readHeader() {

        String id = "";

        for (int i = 0; i < 6; i++) id += (char) read();

        if (!id.startsWith("GIF")) {

            status = STATUS_FORMAT_ERROR;

            return;

        }

        readLSD();

        if (gctFlag && !err()) {

            gct = readColorTable(gctSize);

            bgColor = gct[bgIndex];

        }

    }
