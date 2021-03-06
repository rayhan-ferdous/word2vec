    public ERDesignerEditor() {

        editorSaving = false;

        resourceListener = new ResourceTracker();

        DefaultEditDomain defaultEditDomain = new MyDefaultEditDomain(this);

        defaultEditDomain.setActiveTool(new PanningSelectionTool());

        setEditDomain(defaultEditDomain);

        preferenceStore = DBDesignerPlugin.getDefault().getPreferenceStore();

        preferenceListener = new ERDPreferenceChangeListener(this, preferenceStore);

        preferenceStore.addPropertyChangeListener(preferenceListener);

    }
