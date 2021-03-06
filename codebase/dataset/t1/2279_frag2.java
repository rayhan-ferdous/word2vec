    private void build(DirWriter w, String[] args, int off) throws IOException {

        DirBuilderPref pref = getDirBuilderPref();

        long t1 = System.currentTimeMillis();

        int[] counter = new int[2];

        DirBuilder builder = fact.newDirBuilder(w, pref);

        for (int i = off; i < args.length; ++i) {

            append(builder, new File(args[i]), counter);

        }

        long t2 = System.currentTimeMillis();

        System.out.println(MessageFormat.format(messages.getString("insertDone"), new Object[] { String.valueOf(counter[1]), String.valueOf(counter[0]), String.valueOf((t2 - t1) / 1000f) }));

    }
