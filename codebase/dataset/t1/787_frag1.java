        public int read() throws java.io.IOException {

            if (position < 0) {

                if (encode) {

                    byte[] b3 = new byte[3];

                    int numBinaryBytes = 0;

                    for (int i = 0; i < 3; i++) {

                        try {

                            int b = in.read();

                            if (b >= 0) {

                                b3[i] = (byte) b;

                                numBinaryBytes++;

                            }

                        } catch (java.io.IOException e) {

                            if (i == 0) throw e;

                        }

                    }

                    if (numBinaryBytes > 0) {

                        encode3to4(b3, 0, numBinaryBytes, buffer, 0, options);

                        position = 0;

                        numSigBytes = 4;

                    } else {

                        return -1;

                    }

                } else {

                    byte[] b4 = new byte[4];

                    int i = 0;

                    for (i = 0; i < 4; i++) {

                        int b = 0;

                        do {

                            b = in.read();

                        } while (b >= 0 && decodabet[b & 0x7f] <= WHITE_SPACE_ENC);

                        if (b < 0) break;

                        b4[i] = (byte) b;

                    }

                    if (i == 4) {

                        numSigBytes = decode4to3(b4, 0, buffer, 0, options);

                        position = 0;

                    } else if (i == 0) {

                        return -1;

                    } else {

                        throw new java.io.IOException("Improperly padded Base64 input.");
