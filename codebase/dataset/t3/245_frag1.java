    public TaskDataExportWizard() {

        IDialogSettings masterSettings = TasksUiPlugin.getDefault().getDialogSettings();

        setDialogSettings(getSettingsSection(masterSettings));

        setNeedsProgressMonitor(true);

        setWindowTitle(WINDOW_TITLE);

    }
