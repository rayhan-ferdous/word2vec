        if (sizeout < blocksize) {

            closeFile();

        }

        return result;

    }



    @Override

    public ChannelBuffer getBlockChannelBuffer() throws ArUnvalidIndexException, ArFileException, ArEndTransferException {

        int sizeout = getBlockByteBuffer();

        ChannelBuffer buffer = ChannelBuffers.copiedBuffer(bbyteBuffer);

        bbyteBuffer.clear();

        if (sizeout < blocksize) {

            closeFile();

        }

        return buffer;
