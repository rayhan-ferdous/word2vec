    public File EncodePiggybackData(PiggybackDataLayer layer, File data) throws EncodeFailedException {

        File outfile = RawEncode(data, layer.EncodeKey);

        File newout = null;

        try {

            newout = TmpDir.createNewFile("tmp", "tmp");

            ChannelWriter cw = new ChannelWriter(newout);

            layer.Header.clear();

            cw.putByteBuffer(layer.Header);

            cw.putFile(outfile);

            cw.close();

        } catch (Exception e) {

            e.printStackTrace();

            throw new EncodeFailedException(e.getMessage());

        }

        return newout;

    }
