    protected int readBlock() {

        blockSize = read();

        int n = 0;

        if (blockSize > 0) {

            try {

                int count = 0;

                while (n < blockSize) {

                    count = in.read(block, n, blockSize - n);

                    if (count == -1) break;

                    n += count;

                }

            } catch (IOException e) {

            }

            if (n < blockSize) {

                status = STATUS_FORMAT_ERROR;

            }

        }

        return n;

    }
