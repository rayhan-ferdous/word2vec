                    mutatedSeq = seqRead.substring(0, changePos) + mMutation + seqRead.substring(changePos + 1, totalReadLength);

                } else if (mType.equals("i")) {

                    totalReadLength += mMutation.length() - 1;

                    seqRead = fastaSequence.subStr(readOffset + 1, readOffset + totalReadLength);

                    seqRead = seqRead.toUpperCase();
