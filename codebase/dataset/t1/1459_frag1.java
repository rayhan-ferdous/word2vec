            if (excludePrivate) ds.excludePrivate().writeDataset(out, netParam); else ds.writeDataset(out, netParam);

            if (parser.getReadTag() == Tags.PixelData) {

                DcmDecodeParam fileParam = parser.getDcmDecodeParam();

                ds.writeHeader(out, netParam, parser.getReadTag(), parser.getReadVR(), parser.getReadLength());

                if (netParam.encapsulated) {

                    parser.parseHeader();

                    while (parser.getReadTag() == Tags.Item) {

                        ds.writeHeader(out, netParam, parser.getReadTag(), parser.getReadVR(), parser.getReadLength());

                        writeValueTo(out, false);

                        parser.parseHeader();

                    }

                    if (parser.getReadTag() != Tags.SeqDelimitationItem) {

                        throw new DcmParseException("Unexpected Tag:" + Tags.toString(parser.getReadTag()));

                    }

                    if (parser.getReadLength() != 0) {

                        throw new DcmParseException("(fffe,e0dd), Length:" + parser.getReadLength());

                    }

                    ds.writeHeader(out, netParam, Tags.SeqDelimitationItem, VRs.NONE, 0);

                } else {

                    boolean swap = fileParam.byteOrder != netParam.byteOrder && parser.getReadVR() == VRs.OW;

                    writeValueTo(out, swap);

                }

                if (truncPostPixelData) {

                    return;

                }

                ds.clear();

                try {

                    parser.parseDataset(fileParam, -1);

                } catch (IOException e) {

                    log.warn("Error reading post-pixeldata attributes:", e);

                }
