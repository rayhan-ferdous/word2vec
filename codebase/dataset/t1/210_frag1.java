    private void doRemove(DirWriter w, int[] counter, boolean delFiles) throws IOException {

        for (DirRecord rec = w.getFirstRecord(true); rec != null; rec = rec.getNextSibling(true)) {

            if (patientIDs.contains(rec.getDataset().getString(Tags.PatientID))) {

                if (delFiles) {

                    deleteRefFiles(w, rec, counter);

                }

                counter[1] += w.remove(rec);

            } else if (doRemoveStudy(w, rec, counter, delFiles)) {

                counter[1] += w.remove(rec);

            }

        }

    }
