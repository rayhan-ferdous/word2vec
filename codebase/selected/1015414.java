package gov.sns.apps.viewers.arraypvviewer;

import java.net.*;
import java.io.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.tree.DefaultTreeModel;
import gov.sns.ca.*;
import gov.sns.tools.plot.*;
import gov.sns.application.*;
import gov.sns.tools.xml.*;
import gov.sns.tools.apputils.*;
import gov.sns.tools.apputils.PVSelection.*;
import gov.sns.apps.viewers.arraypvviewer.utils.*;
import gov.sns.apps.viewers.arraypvviewer.controller.*;

/**
 *  ArrayPVViewerDocument is a custom XalDocument for Array PVs Viewer
 *  application. The document manages the data that is displayed in the window.
 *
 *@author     shishlo
 *@version    1.0
 */
public class ArrayPVViewerDocument extends XalDocument {

    static {
        ChannelFactory.defaultFactory().init();
    }

    private JTextField messageTextLocal = new JTextField();

    private File acceleratorDataFile = null;

    UpdatingController updatingController = new UpdatingController();

    private Action setViewlAction = null;

    private Action setPVsAction = null;

    private Action setAcceleratorAction = null;

    private Action setPredefConfigAction = null;

    private JPanel viewPanel = new JPanel();

    private JLabel viewPanelTitle_Label = new JLabel("============UPDATING MANAGEMENT============", JLabel.CENTER);

    private JRadioButton autoUpdateView_Button = new JRadioButton("Auto Update", true);

    private JSpinner freq_ViewPanel_Spinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));

    private JLabel viewPanelFreq_Label = new JLabel("x0.1 Update f[Hz]", JLabel.LEFT);

    private PVsTreePanel pvsTreePanelView = null;

    private Vector arrayPVs = new Vector();

    private FunctionGraphsJPanel arrayPVGraphs = new FunctionGraphsJPanel();

    private JPanel setPVsPanel = new JPanel();

    private ValuesGraphPanel arrayPVsGraphPanel = null;

    private ArrayPVsTable pvsTable = null;

    private String root_Name = "ROOT";

    private String rootArrayPV_Name = "Array PVs";

    private PVTreeNode root_Node = null;

    private PVTreeNode rootArrayPV_Node = null;

    private PVsSelector pvsSelector = null;

    private ActionListener switchPVTreeListener = null;

    private ActionListener createDeletePVTreeListener = null;

    private ActionListener renamePVTreeListener = null;

    private JPanel preferencesPanel = new JPanel();

    private JButton setFont_PrefPanel_Button = new JButton("Set Font Size");

    private JSpinner fontSize_PrefPanel_Spinner = new JSpinner(new SpinnerNumberModel(7, 7, 26, 1));

    private Font globalFont = new Font("Monospaced", Font.BOLD, 10);

    private PredefinedConfController predefinedConfController = null;

    private JPanel configPanel = null;

    private int ACTIVE_PANEL = 0;

    private int VIEW_PANEL = 0;

    private int SET_PVS_PANEL = 1;

    private int PREFERENCES_PANEL = 2;

    private int PREDEF_CONF_PANEL = 3;

    private static DateAndTimeText dateAndTime = new DateAndTimeText();

    private String dataRootName = "ARRAY_PV_VIEWER";

    /**
	 *  Create a new empty ArrayPVViewerDocument
	 */
    public ArrayPVViewerDocument() {
        ACTIVE_PANEL = VIEW_PANEL;
        double freq = 10.0;
        updatingController.setUpdateFrequency(freq);
        freq_ViewPanel_Spinner.setValue(new Integer((int) freq));
        updatingController.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (ACTIVE_PANEL == VIEW_PANEL) {
                    if (autoUpdateView_Button.isSelected()) {
                        for (int i = 0; i < arrayPVs.size(); i++) {
                            ((ArrayViewerPV) arrayPVs.get(i)).update();
                        }
                        updateGraphPanel();
                    }
                }
            }
        });
        pvsTable = new ArrayPVsTable(arrayPVs);
        pvsTable.addChangeListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                updateGraphPanel();
            }
        });
        makePreferencesPanel();
        makePredefinedConfigurationsPanel();
        makePVsSelectionPanel();
        makeViewPanel();
        AvgAndSigmaCalculator.setValuesGraphPanel(arrayPVsGraphPanel);
    }

    /**
	 *  Create a new document loaded from the URL file
	 *
	 *@param  url  The URL of the file to load into the new document.
	 */
    public ArrayPVViewerDocument(URL url) {
        this();
        if (url == null) {
            return;
        }
        setSource(url);
        readArrayPVViewerDocument(url);
        if (url.getProtocol().equals("jar")) {
            return;
        }
        setHasChanges(true);
    }

    /**
	 *  Make a main window by instantiating the ArrayPVViewerWindow window.
	 */
    @Override
    public void makeMainWindow() {
        mainWindow = new ArrayPVViewerWindow(this);
        getArrayPVViewerWindow().setJComponent(viewPanel);
        messageTextLocal = getArrayPVViewerWindow().getMessageTextField();
        pvsSelector.getMessageJTextField().setDocument(messageTextLocal.getDocument());
        fontSize_PrefPanel_Spinner.setValue(new Integer(globalFont.getSize()));
        setFontForAll(globalFont);
        predefinedConfController.setMessageTextField(getArrayPVViewerWindow().getMessageTextField());
        JToolBar toolbar = getArrayPVViewerWindow().getToolBar();
        JTextField timeTxt_temp = dateAndTime.getNewTimeTextField();
        timeTxt_temp.setHorizontalAlignment(JTextField.CENTER);
        toolbar.add(timeTxt_temp);
        mainWindow.setSize(new Dimension(700, 600));
    }

    /**
	 *  Dispose of ArrayPVViewerDocument resources. This method overrides an empty
	 *  superclass method.
	 */
    @Override
    protected void freeCustomResources() {
        cleanUp();
    }

    /**
	 *  Reads the content of the document from the specified URL.
	 *
	 *@param  url  Description of the Parameter
	 */
    public void readArrayPVViewerDocument(URL url) {
        XmlDataAdaptor readAdp = null;
        readAdp = XmlDataAdaptor.adaptorForUrl(url, false);
        if (readAdp != null) {
            XmlDataAdaptor arrViewerData_Adaptor = readAdp.childAdaptor(dataRootName);
            if (arrViewerData_Adaptor != null) {
                cleanUp();
                setTitle(arrViewerData_Adaptor.stringValue("title"));
                XmlDataAdaptor params_font = arrViewerData_Adaptor.childAdaptor("font");
                int font_size = params_font.intValue("size");
                int style = params_font.intValue("style");
                String font_Family = params_font.stringValue("name");
                globalFont = new Font(font_Family, style, font_size);
                fontSize_PrefPanel_Spinner.setValue(new Integer(font_size));
                setFontForAll(globalFont);
                XmlDataAdaptor params_DA = arrViewerData_Adaptor.childAdaptor("PARAMS");
                boolean autoUpdateOn = params_DA.booleanValue("AutoUpdate");
                int frequency = params_DA.intValue("Frequency");
                freq_ViewPanel_Spinner.setValue(new Integer(frequency));
                autoUpdateView_Button.setSelected(false);
                XmlDataAdaptor xPosPanelDA = arrViewerData_Adaptor.childAdaptor("ARRAY_PVS_PANEL");
                arrayPVsGraphPanel.setConfig(xPosPanelDA);
                XmlDataAdaptor arrayPVsDA = arrViewerData_Adaptor.childAdaptor("ARRAY_PVs");
                java.util.Iterator<XmlDataAdaptor> da_iter = arrayPVsDA.childAdaptorIterator();
                while (da_iter.hasNext()) {
                    XmlDataAdaptor g_DA = da_iter.next();
                    ArrayViewerPV arrPV = new ArrayViewerPV(arrayPVGraphs);
                    arrPV.setConfig(g_DA);
                    arrayPVs.add(arrPV);
                    updatingController.addArrayDataPV(arrPV.getArrayDataPV());
                }
                for (int i = 0, n = arrayPVs.size(); i < n; i++) {
                    ArrayViewerPV arrPV = (ArrayViewerPV) arrayPVs.get(i);
                    PVTreeNode pvNodeNew = new PVTreeNode(arrPV.getChannelName());
                    pvNodeNew.setChannel(arrPV.getChannel());
                    pvNodeNew.setAsPVName(true);
                    pvNodeNew.setCheckBoxVisible(true);
                    rootArrayPV_Node.add(pvNodeNew);
                    pvNodeNew.setSwitchedOn(arrPV.getArrayDataPV().getSwitchOn());
                    pvNodeNew.setSwitchedOnOffListener(switchPVTreeListener);
                    pvNodeNew.setCreateRemoveListener(createDeletePVTreeListener);
                    pvNodeNew.setRenameListener(renamePVTreeListener);
                }
                ((DefaultTreeModel) pvsSelector.getPVsTreePanel().getJTree().getModel()).reload();
                ((DefaultTreeModel) pvsTreePanelView.getJTree().getModel()).reload();
                setColors(rootArrayPV_Node, -1);
                updateGraphPanel();
                autoUpdateView_Button.setSelected(autoUpdateOn);
            }
        }
    }

    /**
	 *  Save the ArrayPVViewerDocument document to the specified URL.
	 *
	 *@param  url  Description of the Parameter
	 */
    @Override
    public void saveDocumentAs(URL url) {
        XmlDataAdaptor da = XmlDataAdaptor.newEmptyDocumentAdaptor();
        XmlDataAdaptor arrViewerData_Adaptor = da.createChild(dataRootName);
        arrViewerData_Adaptor.setValue("title", url.getFile());
        XmlDataAdaptor params_font = arrViewerData_Adaptor.createChild("font");
        params_font.setValue("name", globalFont.getFamily());
        params_font.setValue("style", globalFont.getStyle());
        params_font.setValue("size", globalFont.getSize());
        XmlDataAdaptor params_DA = arrViewerData_Adaptor.createChild("PARAMS");
        params_DA.setValue("AutoUpdate", autoUpdateView_Button.isSelected());
        params_DA.setValue("Frequency", ((Integer) freq_ViewPanel_Spinner.getValue()).intValue());
        XmlDataAdaptor xPosPanelDA = arrViewerData_Adaptor.createChild("ARRAY_PVS_PANEL");
        arrayPVsGraphPanel.dumpConfig(xPosPanelDA);
        XmlDataAdaptor arrayPVsDA = arrViewerData_Adaptor.createChild("ARRAY_PVs");
        for (int i = 0, n = arrayPVs.size(); i < n; i++) {
            ArrayViewerPV arrViewer = (ArrayViewerPV) arrayPVs.get(i);
            arrViewer.dumpConfig(arrayPVsDA);
        }
        try {
            arrViewerData_Adaptor.writeTo(new File(url.getFile()));
            setHasChanges(true);
        } catch (IOException e) {
            System.out.println("IOException e=" + e);
        }
    }

    /**
	 *  Edit preferences for the document.
	 */
    void editPreferences() {
        setActivePanel(PREFERENCES_PANEL);
    }

    /**
	 *  Convenience method for getting the ArrayPVViewerWindow window. It is the
	 *  cast to the proper subclass of XalWindow. This allows me to avoid casting
	 *  the window every time I reference it.
	 *
	 *@return    The main window cast to its dynamic runtime class
	 */
    private ArrayPVViewerWindow getArrayPVViewerWindow() {
        return (ArrayPVViewerWindow) mainWindow;
    }

    /**
	 *  Register actions for the menu items and toolbar.
	 *
	 *@param  commander  Description of the Parameter
	 */
    @Override
    protected void customizeCommands(Commander commander) {
        setViewlAction = new AbstractAction("show-view-panel") {

            public void actionPerformed(ActionEvent event) {
                setActivePanel(VIEW_PANEL);
            }
        };
        commander.registerAction(setViewlAction);
        setPVsAction = new AbstractAction("show-set-pvs-panel") {

            public void actionPerformed(ActionEvent event) {
                setActivePanel(SET_PVS_PANEL);
            }
        };
        commander.registerAction(setPVsAction);
        setAcceleratorAction = new AbstractAction("show-set-accelerator-panel") {

            public void actionPerformed(ActionEvent event) {
                JFileChooser ch = new JFileChooser();
                ch.setDialogTitle("READ ACCELERATOR DATA XML FILE");
                if (acceleratorDataFile != null) {
                    ch.setSelectedFile(acceleratorDataFile);
                }
                int returnVal = ch.showOpenDialog(setPVsPanel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    acceleratorDataFile = ch.getSelectedFile();
                    String path = acceleratorDataFile.getAbsolutePath();
                    pvsSelector.setAcceleratorFileName(path);
                }
            }
        };
        commander.registerAction(setAcceleratorAction);
        setAcceleratorAction.setEnabled(false);
        setPredefConfigAction = new AbstractAction("set-predef-config") {

            public void actionPerformed(ActionEvent event) {
                setActivePanel(PREDEF_CONF_PANEL);
            }
        };
        commander.registerAction(setPredefConfigAction);
    }

    /**
	 *  Description of the Method
	 */
    private void makePreferencesPanel() {
        fontSize_PrefPanel_Spinner.setAlignmentX(JSpinner.CENTER_ALIGNMENT);
        preferencesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
        preferencesPanel.add(fontSize_PrefPanel_Spinner);
        preferencesPanel.add(setFont_PrefPanel_Button);
        setFont_PrefPanel_Button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int fnt_size = ((Integer) fontSize_PrefPanel_Spinner.getValue()).intValue();
                globalFont = new Font(globalFont.getFamily(), globalFont.getStyle(), fnt_size);
                setFontForAll(globalFont);
            }
        });
    }

    /**
	 *  Description of the Method
	 */
    private void makePredefinedConfigurationsPanel() {
        predefinedConfController = new PredefinedConfController(this, "config", "predefinedConfiguration.apv");
        configPanel = predefinedConfController.getJPanel();
        ActionListener selectConfListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                URL url = (URL) e.getSource();
                if (url == null) {
                    Toolkit.getDefaultToolkit().beep();
                    messageTextLocal.setText(null);
                    messageTextLocal.setText("Cannot find an input configuration file!");
                }
                cleanUp();
                readArrayPVViewerDocument(url);
                setHasChanges(false);
                setFontForAll(globalFont);
                setActivePanel(VIEW_PANEL);
            }
        };
        predefinedConfController.setSelectorListener(selectConfListener);
    }

    /**
	 *  Creates the PVs selection panel.
	 */
    private void makePVsSelectionPanel() {
        root_Node = new PVTreeNode(root_Name);
        rootArrayPV_Node = new PVTreeNode(rootArrayPV_Name);
        rootArrayPV_Node.setPVNamesAllowed(true);
        root_Node.add(rootArrayPV_Node);
        pvsSelector = new PVsSelector(root_Node);
        pvsSelector.removeMessageTextField();
        setPVsPanel.setLayout(new BorderLayout());
        setPVsPanel.add(pvsSelector, BorderLayout.CENTER);
        switchPVTreeListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                PVTreeNode pvn = (PVTreeNode) e.getSource();
                boolean switchOnLocal = command.equals(PVTreeNode.SWITCHED_ON_COMMAND);
                PVTreeNode pvn_parent = (PVTreeNode) pvn.getParent();
                int index = -1;
                ArrayViewerPV arrPV = null;
                if (pvn_parent == rootArrayPV_Node) {
                    index = pvn_parent.getIndex(pvn);
                    arrPV = (ArrayViewerPV) arrayPVs.get(index);
                }
                if (index >= 0 && arrPV != null) {
                    arrPV.getArrayDataPV().setSwitchOn(switchOnLocal);
                    updateGraphPanel();
                }
                viewPanel.validate();
                viewPanel.repaint();
            }
        };
        createDeletePVTreeListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PVTreeNode pvn = (PVTreeNode) e.getSource();
                PVTreeNode pvn_parent = (PVTreeNode) pvn.getParent();
                String command = e.getActionCommand();
                boolean bool_removePV = command.equals(PVTreeNode.REMOVE_PV_COMMAND);
                int index = -1;
                ArrayViewerPV pv_tmp = null;
                if (bool_removePV) {
                    if (pvn_parent == rootArrayPV_Node) {
                        index = pvn_parent.getIndex(pvn);
                        pv_tmp = (ArrayViewerPV) arrayPVs.get(index);
                        arrayPVs.remove(pv_tmp);
                    }
                    if (index >= 0) {
                        updatingController.removeArrayDataPV(pv_tmp.getArrayDataPV());
                        setColors(pvn_parent, index);
                        updateGraphPanel();
                    }
                } else {
                    if (pvn_parent == rootArrayPV_Node) {
                        index = pvn_parent.getIndex(pvn);
                        pv_tmp = new ArrayViewerPV(arrayPVGraphs);
                        arrayPVs.add(index, pv_tmp);
                    }
                    if (index >= 0) {
                        pv_tmp.setChannel(pvn.getChannel());
                        updatingController.addArrayDataPV(pv_tmp.getArrayDataPV());
                        setColors(pvn_parent, -1);
                        updateGraphPanel();
                    }
                }
            }
        };
        renamePVTreeListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PVTreeNode pvn = (PVTreeNode) e.getSource();
                PVTreeNode pvn_parent = (PVTreeNode) pvn.getParent();
                int index = -1;
                ArrayViewerPV pv_tmp = null;
                if (pvn_parent == rootArrayPV_Node) {
                    index = pvn_parent.getIndex(pvn);
                    pv_tmp = (ArrayViewerPV) arrayPVs.get(index);
                }
                if (index >= 0) {
                    pv_tmp.setChannel(pvn.getChannel());
                    setColors(pvn_parent, -1);
                    updateGraphPanel();
                }
            }
        };
        rootArrayPV_Node.setSwitchedOnOffListener(switchPVTreeListener);
        rootArrayPV_Node.setCreateRemoveListener(createDeletePVTreeListener);
        rootArrayPV_Node.setRenameListener(renamePVTreeListener);
    }

    /**
	 *  Creates the PV viewer panel with all graphs sub-panels,
	 */
    private void makeViewPanel() {
        arrayPVsGraphPanel = new ValuesGraphPanel("PVs' waveforms", arrayPVs, arrayPVGraphs, this);
        pvsTreePanelView = pvsSelector.getNewPVsTreePanel();
        pvsTreePanelView.getJTree().setBackground(Color.white);
        pvsTreePanelView.setPreferredSize(new Dimension(0, 0));
        pvsSelector.setPreferredSize(new Dimension(0, 0));
        freq_ViewPanel_Spinner.setAlignmentX(JSpinner.CENTER_ALIGNMENT);
        freq_ViewPanel_Spinner.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                int freq = ((Integer) freq_ViewPanel_Spinner.getValue()).intValue();
                updatingController.setUpdateFrequency((freq) * 0.1);
            }
        });
        viewPanel.setLayout(new BorderLayout());
        JPanel tmp_panel_0 = new JPanel();
        tmp_panel_0.setLayout(new BorderLayout());
        tmp_panel_0.add(arrayPVsGraphPanel.getJPanel(), BorderLayout.CENTER);
        tmp_panel_0.add(pvsTable.getPanel(), BorderLayout.SOUTH);
        JPanel tmp_panel_1 = new JPanel();
        tmp_panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
        tmp_panel_1.add(autoUpdateView_Button);
        JPanel tmp_panel_2 = new JPanel();
        tmp_panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
        tmp_panel_2.add(freq_ViewPanel_Spinner);
        tmp_panel_2.add(viewPanelFreq_Label);
        JPanel tmp_panel_3 = new JPanel();
        tmp_panel_3.setLayout(new GridLayout(1, 2, 1, 1));
        tmp_panel_3.add(tmp_panel_1);
        tmp_panel_3.add(tmp_panel_2);
        JPanel tmp_panel_4 = new JPanel();
        tmp_panel_4.setLayout(new VerticalLayout());
        tmp_panel_4.add(viewPanelTitle_Label);
        tmp_panel_4.add(tmp_panel_3);
        JPanel tmp_panel_5 = new JPanel();
        tmp_panel_5.setLayout(new BorderLayout());
        tmp_panel_5.add(tmp_panel_4, BorderLayout.NORTH);
        tmp_panel_5.add(pvsTreePanelView, BorderLayout.CENTER);
        viewPanel.add(tmp_panel_0, BorderLayout.CENTER);
        viewPanel.add(tmp_panel_5, BorderLayout.WEST);
    }

    /**
	 *  Clean up the document content
	 */
    private void cleanUp() {
        cleanMessageTextField();
        for (int i = 0, n = arrayPVs.size(); i < n; i++) {
            ArrayViewerPV pv_tmp = (ArrayViewerPV) arrayPVs.get(i);
            updatingController.removeArrayDataPV(pv_tmp.getArrayDataPV());
        }
        arrayPVs.clear();
        rootArrayPV_Node.removeAllChildren();
        setColors(rootArrayPV_Node, -1);
        ((DefaultTreeModel) pvsSelector.getPVsTreePanel().getJTree().getModel()).reload();
        ((DefaultTreeModel) pvsTreePanelView.getJTree().getModel()).reload();
    }

    /**
	 *  Description of the Method
	 */
    private void cleanMessageTextField() {
        messageTextLocal.setText(null);
        messageTextLocal.setForeground(Color.red);
    }

    /**
	 *  Sets the fontForAll attribute of the ArrayPVViewerDocument object
	 *
	 *@param  fnt  The new fontForAll value
	 */
    private void setFontForAll(Font fnt) {
        pvsSelector.setAllFonts(fnt);
        arrayPVsGraphPanel.setAllFonts(fnt);
        pvsTable.setFont(fnt);
        pvsTreePanelView.setAllFonts(fnt);
        viewPanelTitle_Label.setFont(fnt);
        autoUpdateView_Button.setFont(fnt);
        viewPanelFreq_Label.setFont(fnt);
        freq_ViewPanel_Spinner.setFont(fnt);
        ((JSpinner.DefaultEditor) freq_ViewPanel_Spinner.getEditor()).getTextField().setFont(fnt);
        messageTextLocal.setFont(fnt);
        fontSize_PrefPanel_Spinner.setValue(new Integer(fnt.getSize()));
        predefinedConfController.setFontsForAll(fnt);
        globalFont = fnt;
    }

    /**
	 *  Sets the colors for PVs ( graphs ) and tree nodes. If deleteIndex < 0 then
	 *  nothing to delete.
	 *
	 *@param  deleteIndex  The new colors value
	 *@param  pvNode       The new colors value
	 */
    private void setColors(PVTreeNode pvNode, int deleteIndex) {
        if (pvNode == rootArrayPV_Node) {
            for (int i = 0, n = arrayPVs.size(); i < n; i++) {
                ArrayViewerPV arrPV = (ArrayViewerPV) arrayPVs.get(i);
                arrPV.setColor(IncrementalColors.getColor(i));
            }
        }
        Enumeration enumNodes = pvNode.children();
        int i = 0;
        int count = 0;
        while (enumNodes.hasMoreElements()) {
            PVTreeNode pvn = (PVTreeNode) enumNodes.nextElement();
            if (count != deleteIndex) {
                pvn.setColor(IncrementalColors.getColor(i));
                i++;
            }
            count++;
        }
    }

    /**
	 *  Updates all data on graphs panels
	 */
    public void updateGraphPanel() {
        arrayPVsGraphPanel.update();
        for (int i = 0, n = arrayPVs.size(); i < n; i++) {
            ArrayViewerPV arrPV = (ArrayViewerPV) arrayPVs.get(i);
            arrPV.updateAvgAndSigma();
        }
        pvsTable.doLayout();
    }

    /**
	 *  Sets the activePanel attribute of the ArrayPVViewerDocument object
	 *
	 *@param  newActPanelInd  The new activePanel value
	 */
    private void setActivePanel(int newActPanelInd) {
        int oldActPanelInd = ACTIVE_PANEL;
        if (oldActPanelInd == newActPanelInd) {
            return;
        }
        if (oldActPanelInd == VIEW_PANEL) {
        } else if (oldActPanelInd == SET_PVS_PANEL) {
            setAcceleratorAction.setEnabled(false);
        } else if (oldActPanelInd == PREFERENCES_PANEL) {
        } else if (oldActPanelInd == PREDEF_CONF_PANEL) {
        }
        if (newActPanelInd == VIEW_PANEL) {
            setAcceleratorAction.setEnabled(false);
            getArrayPVViewerWindow().setJComponent(viewPanel);
        } else if (newActPanelInd == SET_PVS_PANEL) {
            setAcceleratorAction.setEnabled(true);
            getArrayPVViewerWindow().setJComponent(setPVsPanel);
        } else if (newActPanelInd == PREFERENCES_PANEL) {
            setAcceleratorAction.setEnabled(false);
            getArrayPVViewerWindow().setJComponent(preferencesPanel);
        } else if (newActPanelInd == PREDEF_CONF_PANEL) {
            setAcceleratorAction.setEnabled(false);
            getArrayPVViewerWindow().setJComponent(configPanel);
        }
        ACTIVE_PANEL = newActPanelInd;
        cleanMessageTextField();
    }
}

/**
 *  Description of the Class
 *
 *@author     shishlo
 *@version
 *@created    July 8, 2004
 */
class DateAndTimeText {

    private SimpleDateFormat dFormat = null;

    private JFormattedTextField dateTimeField = null;

    /**
	 *  Constructor for the DateAndTimeText object
	 */
    public DateAndTimeText() {
        dFormat = new SimpleDateFormat("'Time': MM.dd.yy HH:mm ");
        dateTimeField = new JFormattedTextField(dFormat);
        dateTimeField.setEditable(false);
        Runnable timer = new Runnable() {

            public void run() {
                while (true) {
                    dateTimeField.setValue(new Date());
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        Thread thr = new Thread(timer);
        thr.start();
    }

    /**
	 *  Returns the time attribute of the DateAndTimeText object
	 *
	 *@return    The time value
	 */
    protected String getTime() {
        return dateTimeField.getText();
    }

    /**
	 *  Returns the timeTextField attribute of the DateAndTimeText object
	 *
	 *@return    The timeTextField value
	 */
    protected JFormattedTextField getTimeTextField() {
        return dateTimeField;
    }

    /**
	 *  Returns the newTimeTextField attribute of the DateAndTimeText object
	 *
	 *@return    The newTimeTextField value
	 */
    protected JTextField getNewTimeTextField() {
        JTextField newText = new JTextField();
        newText.setDocument(dateTimeField.getDocument());
        newText.setEditable(false);
        return newText;
    }
}
