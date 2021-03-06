    public int truncateSection(Message in, int maxLength, int length, int section) {

        int removed = 0;

        Record[] records = in.getSectionArray(section);

        for (int i = records.length - 1; i >= 0; i--) {

            Record r = records[i];

            removed += r.getWireLength();

            length -= r.getWireLength();

            in.removeRecord(section, r);

            if (length > maxLength) continue; else {

                for (int j = i - 1; j >= 0; j--) {

                    Record r2 = records[j];

                    if (!r.getName().equals(r2.getName()) || r.getType() != r2.getType() || r.getDClass() != r2.getDClass()) break;

                    removed += r2.getWireLength();

                    length -= r2.getWireLength();

                    in.removeRecord(section, r2);

                }

                return removed;

            }

        }

        return removed;

    }
