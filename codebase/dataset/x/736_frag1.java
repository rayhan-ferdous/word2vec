                            for (int i = 0; i < numChannels; i++) {

                                channel[i] = Integer.parseInt(st.nextToken(), 16);

                            }

                            notifyClients();

                            if (DEBUG) System.out.println(getChannel(0));

                        } catch (NumberFormatException ex) {
