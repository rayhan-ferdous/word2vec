                toRemove.add(rec);

            } else {

                matchAll = false;

            }

        }

        if (matchAll) {

            return true;

        }

        for (Iterator it = toRemove.iterator(); it.hasNext(); ) {

            counter[1] += w.remove((DirRecord) it.next());

        }

        return false;

    }



    private boolean doRemoveInstances(DirWriter w, DirRecord parent, int[] counter, boolean delFiles) throws IOException {
