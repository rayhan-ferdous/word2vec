                for (int i = 0; i < header.samples; ++i) {

                    A[i] = f.readUnsignedShort();

                    if (A[i] > max) max = A[i];

                }

                for (int i = 0; i < header.samples; ++i) {

                    C[i] = f.readUnsignedShort();

                    if (C[i] > max) max = C[i];

                }

                for (int i = 0; i < header.samples; ++i) {

                    G[i] = f.readUnsignedShort();

                    if (G[i] > max) max = G[i];

                }

                for (int i = 0; i < header.samples; ++i) {
