                navigatorView = (View) ((IAdaptable) navigatorGroup.getParent()).getAdapter(View.class);

            }

        }

        if (navigatorView == null) {

            return;

        }

        IEditorInput editorInput = getEditorInput(navigatorView.getDiagram());

        IEditorPart editor = aPage.findEditor(editorInput);

        if (editor == null) {

            return;

        }

        aPage.bringToTop(editor);

        if (editor instanceof DiagramEditor) {

            DiagramEditor diagramEditor = (DiagramEditor) editor;

            ResourceSet diagramEditorResourceSet = diagramEditor.getEditingDomain().getResourceSet();

            EObject selectedView = diagramEditorResourceSet.getEObject(EcoreUtil.getURI(navigatorView), true);

            if (selectedView == null) {

                return;

            }

            GraphicalViewer graphicalViewer = (GraphicalViewer) diagramEditor.getAdapter(GraphicalViewer.class);

            EditPart selectedEditPart = (EditPart) graphicalViewer.getEditPartRegistry().get(selectedView);

            if (selectedEditPart != null) {

                graphicalViewer.select(selectedEditPart);

            }

        }

    }

}
