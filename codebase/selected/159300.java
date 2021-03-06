package bias.extension.FilePack;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.Map.Entry;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import bias.Constants;
import bias.core.Attachment;
import bias.core.BackEnd;
import bias.extension.EntryExtension;
import bias.gui.FrontEnd;
import bias.utils.AppManager;
import bias.utils.CommonUtils;
import bias.utils.FSUtils;
import bias.utils.FormatUtils;
import bias.utils.PropertiesUtils;
import bias.utils.Validator;

/**
 * @author kion
 *
 */
public class FilePack extends EntryExtension {

    private static final long serialVersionUID = 1L;

    private static final ImageIcon ICON_ADD = new ImageIcon(CommonUtils.getResourceURL(FilePack.class, "add.png"));

    private static final ImageIcon ICON_DELETE = new ImageIcon(CommonUtils.getResourceURL(FilePack.class, "delete.png"));

    private static final ImageIcon ICON_VIEW = new ImageIcon(CommonUtils.getResourceURL(FilePack.class, "view.png"));

    private static final ImageIcon ICON_APPLY = new ImageIcon(CommonUtils.getResourceURL(FilePack.class, "apply.png"));

    private static final ImageIcon ICON_SAVE = new ImageIcon(CommonUtils.getResourceURL(FilePack.class, "save.png"));

    private static final int MAX_SORT_KEYS_NUMBER = 3;

    private static final String PROPERTY_SORT_BY_COLUMN = "SORT_BY_COLUMN";

    private static final String PROPERTY_SORT_ORDER = "SORT_BY_ORDER";

    private static final String PROPERTY_SELECTED_ROW = "SELECTED_ROW";

    private static final String PROPERTY_COLUMNS_WIDTHS = "COLUMNS_WIDTHS";

    private int[] sortByColumn = new int[MAX_SORT_KEYS_NUMBER];

    private SortOrder[] sortOrder = new SortOrder[MAX_SORT_KEYS_NUMBER];

    private Map<Attachment, String> filePack;

    private Map<String, String> filesChecksums;

    private File lastInputDir = null;

    private File lastOutputDir = null;

    private byte[] settings = null;

    private TableRowSorter<TableModel> sorter;

    private JToolBar jToolBar1;

    private JButton jButton2;

    private JPanel jPanel1;

    private JTable jTable1;

    private JScrollPane jScrollPane1;

    private JButton jButton3;

    private JButton jButton4;

    private JButton jButton5;

    private JButton jButton1;

    public FilePack(UUID id, byte[] data, byte[] settings) throws Throwable {
        super(id, data, settings);
        this.settings = getSettings();
        Properties p = PropertiesUtils.deserializeProperties(data);
        filePack = new LinkedHashMap<Attachment, String>();
        filesChecksums = new HashMap<String, String>();
        try {
            for (Attachment att : BackEnd.getInstance().getAttachments(getId())) {
                String date = p.getProperty(att.getName());
                filePack.put(att, date);
                rememberFileChecksum(att);
            }
        } catch (Throwable t) {
            FrontEnd.displayErrorMessage("Failed to get attachments!", t);
            throw t;
        }
        Properties s = PropertiesUtils.deserializeProperties(this.settings);
        for (int i = 0; i < MAX_SORT_KEYS_NUMBER; i++) {
            int sortByColumn = -1;
            String sortByColumnStr = s.getProperty(PROPERTY_SORT_BY_COLUMN + i);
            if (!Validator.isNullOrBlank(sortByColumnStr)) {
                sortByColumn = Integer.valueOf(sortByColumnStr);
            }
            this.sortByColumn[i] = sortByColumn;
            SortOrder sortOrder = null;
            String sortOrderStr = s.getProperty(PROPERTY_SORT_ORDER + i);
            if (!Validator.isNullOrBlank(sortOrderStr)) {
                sortOrder = SortOrder.valueOf(sortOrderStr);
            }
            this.sortOrder[i] = sortOrder;
        }
        initGUI();
        String selRow = s.getProperty(PROPERTY_SELECTED_ROW);
        if (!Validator.isNullOrBlank(selRow)) {
            jTable1.setRowSelectionInterval(Integer.valueOf(selRow), Integer.valueOf(selRow));
        }
        String colW = s.getProperty(PROPERTY_COLUMNS_WIDTHS);
        if (!Validator.isNullOrBlank(colW)) {
            String[] colsWs = colW.split(":");
            int cc = jTable1.getColumnModel().getColumnCount();
            for (int i = 0; i < cc; i++) {
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(Integer.valueOf(colsWs[i]));
            }
        }
    }

    public byte[] serializeData() throws Throwable {
        Properties p = new Properties();
        for (Entry<Attachment, String> fpEntry : filePack.entrySet()) {
            p.setProperty(fpEntry.getKey().getName(), fpEntry.getValue());
        }
        return PropertiesUtils.serializeProperties(p);
    }

    public byte[] serializeSettings() throws Throwable {
        Properties props = PropertiesUtils.deserializeProperties(settings);
        int idx = jTable1.getSelectedRow();
        if (idx != -1) {
            props.setProperty(PROPERTY_SELECTED_ROW, "" + jTable1.getSelectedRow());
        } else {
            props.remove(PROPERTY_SELECTED_ROW);
        }
        for (int i = 0; i < MAX_SORT_KEYS_NUMBER; i++) {
            if (sortByColumn[i] != -1 && sortOrder[i] != null) {
                props.setProperty(PROPERTY_SORT_BY_COLUMN + i, "" + sortByColumn[i]);
                props.setProperty(PROPERTY_SORT_ORDER + i, sortOrder[i].name());
            } else {
                props.remove(PROPERTY_SORT_BY_COLUMN + i);
                props.remove(PROPERTY_SORT_ORDER + i);
            }
        }
        StringBuffer colW = new StringBuffer();
        int cc = jTable1.getColumnModel().getColumnCount();
        for (int i = 0; i < cc; i++) {
            colW.append(jTable1.getColumnModel().getColumn(i).getWidth());
            if (i < cc - 1) {
                colW.append(":");
            }
        }
        props.setProperty(PROPERTY_COLUMNS_WIDTHS, colW.toString());
        return PropertiesUtils.serializeProperties(props);
    }

    @Override
    public Collection<String> getSearchData() throws Throwable {
        Collection<String> searchData = new ArrayList<String>();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                searchData.add((String) model.getValueAt(i, j));
            }
        }
        return searchData;
    }

    private void initGUI() throws Throwable {
        try {
            {
                BorderLayout thisLayout = new BorderLayout();
                this.setLayout(thisLayout);
                this.setPreferredSize(new java.awt.Dimension(743, 453));
                {
                    jToolBar1 = new JToolBar();
                    jToolBar1.setFloatable(false);
                    this.add(jToolBar1, BorderLayout.SOUTH);
                    jToolBar1.setPreferredSize(new java.awt.Dimension(743, 26));
                    {
                        jButton1 = new JButton();
                        jToolBar1.add(jButton1);
                        jButton1.setIcon(ICON_ADD);
                        jButton1.setToolTipText("add file to pack");
                        jButton1.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent evt) {
                                addFileAction(evt);
                            }
                        });
                    }
                    {
                        jButton2 = new JButton();
                        jToolBar1.add(jButton2);
                        jButton2.setIcon(ICON_DELETE);
                        jButton2.setToolTipText("delete file from pack");
                        jButton2.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent evt) {
                                removeFileAction(evt);
                            }
                        });
                    }
                    {
                        jButton4 = new JButton();
                        jToolBar1.add(jButton4);
                        jButton4.setIcon(ICON_VIEW);
                        jButton4.setToolTipText("view file using default application");
                        jButton4.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent evt) {
                                viewFileAction(evt);
                            }
                        });
                    }
                    {
                        jButton5 = new JButton();
                        jToolBar1.add(jButton5);
                        jButton5.setIcon(ICON_APPLY);
                        jButton5.setToolTipText("apply changes made to file(s)");
                        jButton5.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent evt) {
                                applyChangesAction(evt);
                            }
                        });
                    }
                    {
                        jButton3 = new JButton();
                        jToolBar1.add(jButton3);
                        jButton3.setIcon(ICON_SAVE);
                        jButton3.setToolTipText("save file to external target");
                        jButton3.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent evt) {
                                saveFileAction(evt);
                            }
                        });
                    }
                }
                {
                    jPanel1 = new JPanel();
                    BorderLayout jPanel1Layout = new BorderLayout();
                    jPanel1.setLayout(jPanel1Layout);
                    this.add(jPanel1, BorderLayout.CENTER);
                    {
                        jScrollPane1 = new JScrollPane();
                        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
                        {
                            DefaultTableModel jTable1Model = new DefaultTableModel() {

                                private static final long serialVersionUID = 1L;

                                public boolean isCellEditable(int rowIndex, int mColIndex) {
                                    return false;
                                }
                            };
                            jTable1 = new JTable();
                            jScrollPane1.setViewportView(jTable1);
                            jTable1.setModel(jTable1Model);
                            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                            model.addColumn("File");
                            model.addColumn("Size");
                            model.addColumn("Date");
                            sorter = new TableRowSorter<TableModel>(jTable1Model);
                            sorter.setSortsOnUpdates(true);
                            sorter.setMaxSortKeys(MAX_SORT_KEYS_NUMBER);
                            List<SortKey> sortKeys = new LinkedList<SortKey>();
                            for (int i = 0; i < MAX_SORT_KEYS_NUMBER; i++) {
                                if (sortByColumn[i] != -1 && sortOrder[i] != null) {
                                    SortKey sortKey = new SortKey(sortByColumn[i], sortOrder[i]);
                                    sortKeys.add(sortKey);
                                }
                            }
                            sorter.setSortKeys(sortKeys);
                            sorter.addRowSorterListener(new RowSorterListener() {

                                public void sorterChanged(RowSorterEvent e) {
                                    if (e.getType().equals(RowSorterEvent.Type.SORT_ORDER_CHANGED)) {
                                        List<? extends SortKey> sortKeys = sorter.getSortKeys();
                                        for (int i = 0; i < MAX_SORT_KEYS_NUMBER; i++) {
                                            if (i < sortKeys.size()) {
                                                SortKey sortKey = sortKeys.get(i);
                                                sortByColumn[i] = sortKey.getColumn();
                                                sortOrder[i] = sortKey.getSortOrder();
                                            } else {
                                                sortByColumn[i] = -1;
                                                sortOrder[i] = null;
                                            }
                                        }
                                    }
                                }
                            });
                            jTable1.setRowSorter(sorter);
                            JPanel topPanel = new JPanel(new BorderLayout());
                            topPanel.add(new JLabel("Filter:"), BorderLayout.CENTER);
                            final JTextField filterText = new JTextField();
                            filterText.addCaretListener(new CaretListener() {

                                public void caretUpdate(CaretEvent e) {
                                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filterText.getText()));
                                }
                            });
                            topPanel.add(filterText, BorderLayout.SOUTH);
                            jPanel1.add(topPanel, BorderLayout.NORTH);
                        }
                    }
                }
                for (Entry<Attachment, String> fpEntry : filePack.entrySet()) {
                    addRow(fpEntry.getKey(), fpEntry.getValue());
                }
            }
        } catch (Throwable t) {
            FrontEnd.displayErrorMessage("Failed to initialize GUI!", t);
            throw t;
        }
    }

    private void addFileAction(ActionEvent evt) {
        try {
            JFileChooser jfc;
            if (lastInputDir != null) {
                jfc = new JFileChooser(lastInputDir);
            } else {
                jfc = new JFileChooser();
            }
            jfc.setMultiSelectionEnabled(true);
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File[] files = jfc.getSelectedFiles();
                try {
                    for (File file : files) {
                        addFile(file);
                    }
                    lastInputDir = files[0].getParentFile();
                } catch (Exception e) {
                    FrontEnd.displayErrorMessage("Failed to add file!", e);
                }
            }
        } catch (Exception e) {
            FrontEnd.displayErrorMessage(e);
        }
    }

    private void removeFileAction(ActionEvent evt) {
        try {
            if (jTable1.getSelectedRows().length > 0) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                while (jTable1.getSelectedRow() != -1) {
                    int i = jTable1.getSelectedRow();
                    int idx = sorter.convertRowIndexToModel(i);
                    String fileName = (String) model.getValueAt(idx, 0);
                    removeFile(fileName, i);
                }
            }
        } catch (Exception e) {
            FrontEnd.displayErrorMessage("Failed to remove file!", e);
        }
    }

    private synchronized void viewFileAction(ActionEvent evt) {
        try {
            if (jTable1.getSelectedRows().length > 0) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                for (int i : jTable1.getSelectedRows()) {
                    int idx = sorter.convertRowIndexToModel(i);
                    String fileName = (String) model.getValueAt(idx, 0);
                    final File file = new File(Constants.TMP_DIR, fileName);
                    if (!file.exists()) {
                        byte[] data = getFileData(fileName);
                        FSUtils.writeFile(file, data);
                    }
                    AppManager.getInstance().handleFile(file);
                }
            }
        } catch (Exception e) {
            FrontEnd.displayErrorMessage("Failed to view file!", e);
        }
    }

    private synchronized void applyChangesAction(ActionEvent evt) {
        try {
            Map<String, Integer> toRemove = new HashMap<String, Integer>();
            Collection<File> toAdd = new ArrayList<File>();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String fileName = (String) model.getValueAt(i, 0);
                File file = new File(Constants.TMP_DIR, fileName);
                if (file.exists() && fileCheckSumChanged(file)) {
                    toRemove.put(fileName, i);
                    toAdd.add(file);
                }
            }
            for (Entry<String, Integer> entry : toRemove.entrySet()) {
                removeFile(entry.getKey(), entry.getValue());
            }
            for (File file : toAdd) {
                addFile(file);
            }
        } catch (Exception e) {
            FrontEnd.displayErrorMessage("Failed to apply changes!", e);
        }
    }

    private boolean fileCheckSumChanged(File file) throws Exception {
        MessageDigest md = MessageDigest.getInstance(Constants.DIGEST_ALGORITHM);
        byte[] bytes = FSUtils.readFile(file);
        md.update(bytes);
        String checksum = FormatUtils.formatBytesAsHexString(md.digest());
        return !checksum.equals(filesChecksums.get(file.getName()));
    }

    private void saveFileAction(ActionEvent evt) {
        try {
            if (jTable1.getSelectedRows().length > 0) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                for (int i : jTable1.getSelectedRows()) {
                    JFileChooser jfc;
                    if (lastOutputDir != null) {
                        jfc = new JFileChooser(lastOutputDir);
                    } else {
                        jfc = new JFileChooser();
                    }
                    jfc.setMultiSelectionEnabled(false);
                    int idx = sorter.convertRowIndexToModel(i);
                    String fileName = (String) model.getValueAt(idx, 0);
                    File file = new File(fileName);
                    jfc.setSelectedFile(file);
                    if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                        file = jfc.getSelectedFile();
                        Integer option = null;
                        if (file.exists()) {
                            option = JOptionPane.showConfirmDialog(this, "File already exists, overwrite?", "Overwrite existing file", JOptionPane.YES_NO_OPTION);
                        }
                        if (option == null || option == JOptionPane.YES_OPTION) {
                            byte[] data = getFileData(fileName);
                            FSUtils.writeFile(file, data);
                            lastOutputDir = file.getParentFile();
                        }
                    }
                }
            }
        } catch (Exception e) {
            FrontEnd.displayErrorMessage("Failed to save file!", e);
        }
    }

    private byte[] getFileData(String fileName) throws Exception {
        byte[] data = null;
        for (Attachment att : filePack.keySet()) {
            if (att.getName().equals(fileName)) {
                data = att.getData();
                break;
            }
        }
        return data;
    }

    private void addFile(File file) throws Exception {
        Attachment attachment = new Attachment(file);
        BackEnd.getInstance().addAttachment(getId(), attachment);
        String date = new SimpleDateFormat("yyyy.MM.dd @ HH:mm:ss").format(new Date());
        filePack.put(attachment, date);
        rememberFileChecksum(attachment);
        addRow(attachment, date);
    }

    private void rememberFileChecksum(Attachment att) throws Exception {
        MessageDigest md = MessageDigest.getInstance(Constants.DIGEST_ALGORITHM);
        md.update(att.getData());
        String checksum = FormatUtils.formatBytesAsHexString(md.digest());
        filesChecksums.put(att.getName(), checksum);
    }

    private void addRow(Attachment attachment, String date) {
        long size = attachment.getData().length;
        String sizeStr = FormatUtils.formatByteSize(size);
        ((DefaultTableModel) jTable1.getModel()).addRow(new Object[] { attachment.getName(), sizeStr, date });
    }

    private void removeFile(String fileName, int rowIdx) throws Exception {
        Attachment toRemove = null;
        for (Attachment att : filePack.keySet()) {
            if (att.getName().equals(fileName)) {
                toRemove = att;
                break;
            }
        }
        if (toRemove != null) {
            filePack.remove(toRemove);
            removeRow(rowIdx);
        }
        BackEnd.getInstance().removeAttachment(getId(), fileName);
    }

    private void removeRow(int rowIdx) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.removeRow(rowIdx);
    }
}
