    private void init(String filePath, FileConfig config) {

        file = new File(filePath);

        boolean exist = file.exists();

        try {

            int keyTreeMin = 0;

            if (!exist) {

                fileHeader = createEmptyFile(file, config);

            } else {

                fileHeader = readHeader(file, config);

            }

            long tailPos = fileHeader.getFileTail();

            long rootNodePos = -1;

            if (tailPos != -1) {

                fileTail = dropTransfer.readTail(tailPos);

                rootNodePos = fileTail.getRootNode();

            }

            keyTreeMin = fileHeader.getKeyTreeMin();

            initTree(keyTreeMin, rootNodePos);

            closed = false;

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }
