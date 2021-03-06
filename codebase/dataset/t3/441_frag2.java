    public java.util.List<fr.inria.uml4tst.papyrus.ocl4tst.ocl4tst.resource.ocl4tst.mopp.Ocl4tstExpectedTerminal> parseToExpectedElements(org.eclipse.emf.ecore.EClass type, fr.inria.uml4tst.papyrus.ocl4tst.ocl4tst.resource.ocl4tst.IOcl4tstTextResource dummyResource, int cursorOffset) {

        this.rememberExpectedElements = true;

        this.parseToIndexTypeObject = type;

        this.cursorOffset = cursorOffset;

        this.lastStartIncludingHidden = -1;

        final org.antlr.runtime3_3_0.CommonTokenStream tokenStream = (org.antlr.runtime3_3_0.CommonTokenStream) getTokenStream();

        fr.inria.uml4tst.papyrus.ocl4tst.ocl4tst.resource.ocl4tst.IOcl4tstParseResult result = parse();

        for (org.eclipse.emf.ecore.EObject incompleteObject : incompleteObjects) {

            org.antlr.runtime3_3_0.Lexer lexer = (org.antlr.runtime3_3_0.Lexer) tokenStream.getTokenSource();

            int endChar = lexer.getCharIndex();

            int endLine = lexer.getLine();

            setLocalizationEnd(result.getPostParseCommands(), incompleteObject, endChar, endLine);

        }

        if (result != null) {

            org.eclipse.emf.ecore.EObject root = result.getRoot();

            if (root != null) {

                dummyResource.getContentsInternal().add(root);

            }

            for (fr.inria.uml4tst.papyrus.ocl4tst.ocl4tst.resource.ocl4tst.IOcl4tstCommand<fr.inria.uml4tst.papyrus.ocl4tst.ocl4tst.resource.ocl4tst.IOcl4tstTextResource> command : result.getPostParseCommands()) {

                command.execute(dummyResource);

            }

        }

        expectedElements = expectedElements.subList(0, expectedElementsIndexOfLastCompleteElement + 1);

        int lastFollowSetID = expectedElements.get(expectedElementsIndexOfLastCompleteElement).getFollowSetID();

        java.util.Set<fr.inria.uml4tst.papyrus.ocl4tst.ocl4tst.resource.ocl4tst.mopp.Ocl4tstExpectedTerminal> currentFollowSet = new java.util.LinkedHashSet<fr.inria.uml4tst.papyrus.ocl4tst.ocl4tst.resource.ocl4tst.mopp.Ocl4tstExpectedTerminal>();
