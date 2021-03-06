    public static void runTagDocTopicWizard(Shell shell, Wizard wizard, String settingsKey, ShapeNodeEditPart selectedElement, List<ShapeNodeEditPart> listSelected, List<String> listDocs, IWorkbenchPart targetPart) {

        IDialogSettings pluginDialogSettings = PlwebDiagramEditorPlugin.getInstance().getDialogSettings();

        IDialogSettings wizardDialogSettings = pluginDialogSettings.getSection(settingsKey);

        if (wizardDialogSettings == null) {

            wizardDialogSettings = pluginDialogSettings.addNewSection(settingsKey);

        }

        wizard.setDialogSettings(wizardDialogSettings);

        TagDocTopicWizardDialog dialog = new TagDocTopicWizardDialog(shell, wizard, selectedElement, listSelected, listDocs, targetPart);

        dialog.create();

        dialog.getShell().setSize(Math.max(500, dialog.getShell().getSize().x), 500);

        dialog.open();

    }
