    protected IDocumentProvider getDocumentProvider(IEditorInput input) {

        if (input instanceof IFileEditorInput || input instanceof URIEditorInput) {

            return FilesystemDiagramEditorPlugin.getInstance().getDocumentProvider();

        }

        return super.getDocumentProvider(input);

    }
