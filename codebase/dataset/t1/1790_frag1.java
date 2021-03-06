    private String resolveMetaReferences(String name) {

        int beg, end;

        String metaName;

        while ((beg = name.indexOf('{')) != -1) {

            end = name.indexOf('}', beg);

            metaName = name.substring(beg + 1, end);

            PathVariable pv = (PathVariable) metaPathVariables.get(metaName);

            if (pv == null) pv = new PathVariable(metaName, metaName, null, "");

            if (pv.isUnknown()) {

                metaPathVariables.put(metaName, pv);

                metaPathVariableNames.add(metaName);

            }

            name = name.substring(0, beg) + pv.getValue() + name.substring(end + 1);

        }

        name = name.replace('\t', ' ').replace('\n', ' ').replace('\r', ' ').trim();

        while (name.indexOf("  ") != -1) name = StringUtils.findAndReplace(name, "  ", " ");

        return name;

    }
