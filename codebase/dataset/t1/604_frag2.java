                Message m = null;

                try {

                    m = folder.getMessage(msgnum);

                    dumpPart(m);

                } catch (IndexOutOfBoundsException iex) {

                    System.out.println("Message number out of range");
