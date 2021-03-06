    protected IDocumentProvider getDocumentProvider(IEditorInput input) {

        if (input instanceof IFileEditorInput || input instanceof URIEditorInput) {

            return ParallelJDiagramEditorPlugin.getInstance().getDocumentProvider();

        }

        return super.getDocumentProvider(input);

    }
