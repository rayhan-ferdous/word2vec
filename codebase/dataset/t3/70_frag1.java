        IDiagramDocument document = PetriDiagramDebuggerPlugin.getInstance().getDocumentProvider().getDiagramDocument(anInput);

        if (document == null) {

            return StructuredSelection.EMPTY;

        }

        Diagram diagram = document.getDiagram();

        IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());

        if (file != null) {

            PetriNavigatorItem item = new PetriNavigatorItem(diagram, file, false);

            return new StructuredSelection(item);

        }

        return StructuredSelection.EMPTY;

    }
