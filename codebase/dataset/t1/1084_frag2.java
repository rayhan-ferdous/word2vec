                Header header = response.getHeader();

                header.setFlag(Flags.QR);

                header.setFlag(Flags.AA);

                addRRset(rrset.getName(), response, rrset, Section.ANSWER, FLAG_DNSSECOK);

                if (tsig != null) {

                    tsig.applyStream(response, qtsig, first);

                    qtsig = response.getTSIG();

                }

                first = false;

                byte[] out = response.toWire();

                dataOut.writeShort(out.length);

                dataOut.write(out);

            }

        } catch (IOException ex) {

            System.out.println("AXFR failed");

        }

        try {

            s.close();

        } catch (IOException ex) {

        }

        return null;

    }
