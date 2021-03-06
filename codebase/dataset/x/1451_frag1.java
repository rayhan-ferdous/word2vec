    public static Resource createDiagram(URI diagramURI, final OwlsInitializerFacade facade, IProgressMonitor progressMonitor) {

        TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE.createEditingDomain();

        progressMonitor.beginTask(Messages.OwlsDiagramEditorUtil_CreateDiagramProgressTask, 3);

        final Resource diagramResource = editingDomain.getResourceSet().createResource(diagramURI);

        final String diagramName = diagramURI.lastSegment();

        AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, Messages.OwlsDiagramEditorUtil_CreateDiagramCommandLabel, Collections.EMPTY_LIST) {



            protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

                OwlsCompositeProcess model = createInitialModel(facade);

                attachModelToResource(model, diagramResource);

                Diagram diagram = ViewService.createDiagram(model, OwlsCompositeProcessEditPart.MODEL_ID, OwlsDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

                if (diagram != null) {

                    diagramResource.getContents().add(diagram);

                    diagram.setName(diagramName);

                    diagram.setElement(model);

                }

                try {

                    diagramResource.save(owls.diagram.part.OwlsDiagramEditorUtil.getSaveOptions());

                } catch (IOException e) {

                    OwlsDiagramEditorPlugin.getInstance().logError("Unable to store model and diagram resources", e);

                }

                return CommandResult.newOKCommandResult();

            }

        };

        try {

            OperationHistoryFactory.getOperationHistory().execute(command, new SubProgressMonitor(progressMonitor, 1), null);

        } catch (ExecutionException e) {

            OwlsDiagramEditorPlugin.getInstance().logError("Unable to create model and diagram", e);

        }

        setCharset(WorkspaceSynchronizer.getFile(diagramResource));

        return diagramResource;

    }
