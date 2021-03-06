    }



    static {

        try {

            DataInputStream s = new DataInputStream(new BufferedInputStream(Lexer.class.getResourceAsStream("lexer.dat")));

            int length = s.readInt();

            gotoTable = new int[length][][][];

            for (int i = 0; i < gotoTable.length; i++) {

                length = s.readInt();

                gotoTable[i] = new int[length][][];

                for (int j = 0; j < gotoTable[i].length; j++) {

                    length = s.readInt();

                    gotoTable[i][j] = new int[length][3];

                    for (int k = 0; k < gotoTable[i][j].length; k++) {

                        for (int l = 0; l < 3; l++) {

                            gotoTable[i][j][k][l] = s.readInt();

                        }

                    }

                }

            }

            length = s.readInt();

            accept = new int[length][];

            for (int i = 0; i < accept.length; i++) {

                length = s.readInt();

                accept[i] = new int[length];

                for (int j = 0; j < accept[i].length; j++) {

                    accept[i][j] = s.readInt();

                }

            }

            s.close();

        } catch (Exception e) {

            throw new RuntimeException("The file \"lexer.dat\" is either missing or corrupted.");

        }

    }

}
