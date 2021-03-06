    @Override

    public void includePackage(final PackageFilter filter) {

        String packageName = filter.getPackage();

        String pattern = ".*" + packageName.replace(".", "/");

        if (filter.deep()) {

            pattern = pattern + "/(?:\\w|/)*([A-Z](?:\\w|\\$)+)\\.class$";

        } else {

            pattern = pattern + "/([A-Z](?:\\w|\\$)+)\\.class$";

        }

        patterns.add(Pattern.compile(pattern));

    }
