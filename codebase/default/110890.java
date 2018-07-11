import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author  Andrew
 */
public class Demarcations extends javax.swing.JFrame {

    /** Creates new form Demarcations */
    public Demarcations(FredOutVal hClimbResult, BinningAndFred values) {
        narr = new NarrWriter(narrDemarc);
        this.hClimbResult = hClimbResult;
        this.currentValue = hClimbResult;
        this.values = new BinningAndFred(log, narr, values.getSeqVals(), values.getBins());
        initComponents();
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        allGenes = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectedGenes = new javax.swing.JList();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        removeAllButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        runAll = new javax.swing.JButton();
        allSeqLabel = new javax.swing.JLabel();
        seqRunLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        cladeList = new javax.swing.JList();
        cladeLabel = new javax.swing.JLabel();
        defCladeButton = new javax.swing.JButton();
        retCladeButton = new javax.swing.JButton();
        runFullAnalysisButton = new javax.swing.JButton();
        removeCladeButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openItem = new javax.swing.JMenuItem();
        loadTreeItem = new javax.swing.JMenuItem();
        saveFastaItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                if (narr != null) narr.close();
                dispose();
            }
        });
        setTitle("Demarcations");
        allGenes.setModel(new javax.swing.AbstractListModel() {

            String[] strings = {};

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPane1.setViewportView(allGenes);
        selectedGenes.setModel(new javax.swing.AbstractListModel() {

            String[] strings = {};

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPane2.setViewportView(selectedGenes);
        addButton.setText("Add ->");
        addButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        removeAllButton.setText("Remove All");
        removeAllButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllButtonActionPerformed(evt);
            }
        });
        log.setColumns(20);
        log.setEditable(false);
        log.setRows(5);
        log.setDoubleBuffered(true);
        jScrollPane3.setViewportView(log);
        runAll.setText("Run Selected Sequences");
        runAll.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runAllActionPerformed(evt);
            }
        });
        allSeqLabel.setText("All Sequences");
        seqRunLabel.setText("Selected Sequences");
        cladeList.setModel(new javax.swing.AbstractListModel() {

            String[] strings = {};

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        cladeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(cladeList);
        cladeLabel.setText("Clades");
        defCladeButton.setText("Define Clade");
        defCladeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defCladeButtonActionPerformed(evt);
            }
        });
        retCladeButton.setText("Retrieve Clade");
        retCladeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retCladeButtonActionPerformed(evt);
            }
        });
        runFullAnalysisButton.setText("Run Full Analysis");
        runFullAnalysisButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runFullAnalysisButtonActionPerformed(evt);
            }
        });
        removeCladeButton.setText("Remove Clade");
        removeCladeButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCladeButtonActionPerformed(evt);
            }
        });
        fileMenu.setText("File");
        openItem.setText("Open Fasta File");
        openItem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        fileMenu.add(openItem);
        loadTreeItem.setText("Load Newick Tree File");
        loadTreeItem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTreeItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadTreeItem);
        saveFastaItem.setText("Save Sequences as Fasta File");
        saveFastaItem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFastaItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveFastaItem);
        exitItem.setText("Close");
        exitItem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);
        jMenuBar1.add(fileMenu);
        setJMenuBar(jMenuBar1);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(allSeqLabel).addGap(231, 231, 231).addComponent(seqRunLabel).addGap(97, 97, 97).addComponent(cladeLabel)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(45, 45, 45).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(runAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(removeCladeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(retCladeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(runFullAnalysisButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(43, 43, 43).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(removeAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(removeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(50, 50, 50).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(defCladeButton)).addGroup(layout.createSequentialGroup().addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(49, 49, 49).addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))).addGap(35, 35, 35)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(seqRunLabel).addComponent(allSeqLabel).addComponent(cladeLabel)).addGap(16, 16, 16).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(addButton).addGap(20, 20, 20).addComponent(removeButton).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(removeAllButton).addGap(10, 10, 10)))).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(defCladeButton)).addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(retCladeButton).addGap(17, 17, 17).addComponent(removeCladeButton).addGap(27, 27, 27).addComponent(runAll).addGap(22, 22, 22).addComponent(runFullAnalysisButton)).addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));
        pack();
    }

    private void loadTreeItemActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == loadTreeItem) {
            int returnVal = fc.showOpenDialog(Demarcations.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                treeFile = fc.getSelectedFile();
                if (!TreeFinder.verifyTreeFile(treeFile)) {
                    log.append("That is not a valid tree file, please choose " + "a valid newick tree file.\n");
                    return;
                }
            } else {
                log.append("Dialog cancelled by user. \n");
                return;
            }
            int option = JOptionPane.showConfirmDialog(null, "You must now " + "choose the fasta file corresponding to the tree you just " + "loaded, continue?");
            if (!(option == JOptionPane.YES_OPTION)) {
                log.append("Dialog cancelled by user.");
                return;
            }
            returnVal = fc.showOpenDialog(Demarcations.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                inputFile = fc.getSelectedFile();
                if (!BinningFasta.verifyInputFile(inputFile)) {
                    log.append("That is not a valid fasta file, please choose" + " a properly formatted fasta file.\n");
                    return;
                }
                (new Thread() {

                    public void run() {
                        runTree();
                    }
                }).start();
            } else {
                log.append("Dialog Cancelled by hser.\n");
            }
        }
    }

    private void saveFastaItemActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == saveFastaItem) {
            ArrayList data = new ArrayList();
            ListModel model = selectedGenes.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                data.add(model.getElementAt(i));
            }
            if (data.size() == 0) {
                JOptionPane.showMessageDialog(null, "Please add sequences first");
                return;
            }
            int returnVal = fc.showSaveDialog(Demarcations.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File output = fc.getSelectedFile();
                SelectSeqBins fileMaker = new SelectSeqBins(fastaRGCopy, data, output);
                log.append("Saved to file: " + output.getPath() + "\n");
            } else log.append("Dialog cancelled by user.");
        }
    }

    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == openItem) {
            int returnVal = fc.showOpenDialog(Demarcations.this);
            treeFile = null;
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                inputFile = fc.getSelectedFile();
                if (!BinningFasta.verifyInputFile(inputFile)) {
                    log.append("That is not a valid fasta file, please choose" + " a properly formatted fasta file.\n");
                    return;
                }
                (new Thread() {

                    public void run() {
                        runTree();
                    }
                }).start();
            } else {
                log.append("Dialog Cancelled by User.\n");
            }
        }
    }

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == exitItem) {
            dispose();
        }
    }

    private void runAllActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == runAll) {
            (new Thread() {

                public void run() {
                    runDemarc();
                }
            }).start();
        }
    }

    private void retCladeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == retCladeButton) {
            int[] indices = cladeList.getSelectedIndices();
            if (indices.length == 0) {
                JOptionPane.showMessageDialog(null, "Please select a clade first");
                return;
            }
            ListModel model = cladeList.getModel();
            Vector dataTemp = (Vector) (clades.get(model.getElementAt(indices[0])));
            Vector data = new Vector();
            for (int i = 0; i < dataTemp.size(); i++) data.add(dataTemp.get(i));
            try {
                currentValue = (FredOutVal) data.get(0);
                data.remove(0);
                log.append("Now using the following values for " + "demarcations:\n");
                log.append(currentValue.toString() + "\n");
                narr.println("Now using the following values for " + "demarcations:");
                narr.println(currentValue.toString());
            } catch (ClassCastException e) {
            }
            selectedGenes.setListData(data);
            cladeList.clearSelection();
        }
    }

    private void removeCladeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == removeCladeButton) {
            int[] indices = cladeList.getSelectedIndices();
            if (indices.length == 0) {
                JOptionPane.showMessageDialog(null, "Please select a clade first");
                return;
            }
            Vector data = new Vector();
            ListModel model = cladeList.getModel();
            int j;
            for (int i = 0; i < model.getSize(); i++) {
                for (j = 0; j < indices.length; j++) if (indices[j] == i) break;
                if (j == indices.length) data.add(model.getElementAt(i));
            }
            cladeList.setListData(data);
        }
    }

    private void runFullAnalysisButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == runFullAnalysisButton) {
            int[] indices = cladeList.getSelectedIndices();
            if (indices.length == 0) {
                JOptionPane.showMessageDialog(null, "Please select a clade first");
                return;
            }
            (new Thread() {

                public void run() {
                    runHomoGeneity();
                }
            }).start();
        }
    }

    private void defCladeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == defCladeButton) {
            String name = "";
            name = JOptionPane.showInputDialog("Please enter the name of the clade");
            Vector data = new Vector();
            ListModel model = cladeList.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                if (!model.getElementAt(i).equals(name)) data.add(model.getElementAt(i));
            }
            data.add(name);
            cladeList.setListData(data);
            Vector data2 = new Vector();
            ListModel model2 = selectedGenes.getModel();
            for (int j = 0; j < model2.getSize(); j++) {
                data2.add(model2.getElementAt(j));
            }
            if (clades.containsKey(name)) clades.remove(name);
            clades.put(name, data2);
            Vector empty = new Vector();
            selectedGenes.setListData(empty);
        }
    }

    private void removeAllButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == removeAllButton) {
            Vector data = new Vector();
            selectedGenes.setListData(data);
        }
    }

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == removeButton) {
            Vector data = new Vector();
            ListModel model = selectedGenes.getModel();
            int[] indices = selectedGenes.getSelectedIndices();
            int j;
            for (int i = 0; i < model.getSize(); i++) {
                for (j = 0; j < indices.length; j++) if (indices[j] == i) break;
                if (j == indices.length) data.add(model.getElementAt(i));
            }
            selectedGenes.setListData(data);
            allGenes.clearSelection();
        }
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == addButton) {
            Vector data = new Vector();
            ListModel model = selectedGenes.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                data.add(model.getElementAt(i));
            }
            int[] indices = allGenes.getSelectedIndices();
            ListModel model2 = allGenes.getModel();
            for (int i = 0; i < indices.length; i++) {
                data.add(model2.getElementAt(indices[i]));
            }
            selectedGenes.setListData(data);
            allGenes.clearSelection();
        }
    }

    /**
     * Runs hillclimbing
     * @param value the value to run hillclimbing on
     * @return The output value - the optimized set of parameters from
     * hillclimbing
     */
    private FredOutVal hillClimbing(FredOutVal value) throws IOException {
        FredOutVal bestFred = value;
        ArrayList bins = values.getBins();
        int[] sequenceVals = values.getSeqVals();
        HillClimbing hillOne = new HillClimbing(bestFred, narr, bins, sequenceVals, MasterVariables.NUM_SUCCESSES);
        long startTime, stopTime, runTime;
        double hourTime;
        log.append("Running hillclimbing... \n");
        narr.println("Running hillclimbing ");
        startTime = System.currentTimeMillis();
        hillOne.run();
        stopTime = System.currentTimeMillis();
        runTime = stopTime - startTime;
        hourTime = (double) runTime / 3600000;
        FredOutVal hClimbResult = hillOne.getValue();
        log.append("\nThe values from hill climbing: ");
        log.append("\nomega: " + hClimbResult.getOmega());
        log.append("\nsigma: " + hClimbResult.getSigma());
        log.append("\nnpop: " + hClimbResult.getNpop() + "\n");
        narr.println();
        narr.println("The values from hill climbing: ");
        narr.println("omega: " + hClimbResult.getOmega());
        narr.println("sigma: " + hClimbResult.getSigma());
        narr.println("npop: " + hClimbResult.getNpop());
        hClimbResult = values.fullLike(hClimbResult);
        log.append("The full likelihood from hill climbing: \n");
        log.append(hClimbResult.toString() + "\n");
        narr.println("The full likelihood from hill climbing: ");
        narr.println(hClimbResult.toString());
        return hClimbResult;
    }

    /**
     * Reads in the given fasta file, runs binning on it, formats it, then
     * makes a tree out of using dnapars, and then adds all the sequences
     * to the gui
     * @param tree uses the treefile given by the user if it isn't null, 
     * otherwise generates a tree itself
     */
    private void runTree() {
        String type = null;
        if (treeFile == null) {
            String[] choices = new String[2];
            choices[0] = "Neighbor Joining Tree";
            choices[1] = "Parsimony Tree";
            Object returnVal = JOptionPane.showInputDialog(null, "Choose a type of tree:", "Tree Type", JOptionPane.INFORMATION_MESSAGE, null, choices, 0);
            if (returnVal == null) {
                log.append("Dialog cancelled by user.");
                return;
            }
            if (((String) returnVal).equals(choices[0])) type = "nj"; else type = "pars";
        }
        int sortPer = -1;
        BinningAndFred binner = new BinningAndFred(inputFile, log, narr, sortPer);
        binner.runBinningOnly();
        TreeFinder tf;
        if (treeFile == null) tf = new TreeFinder(inputFile, type); else tf = new TreeFinder(inputFile, treeFile);
        Vector data = new Vector();
        ArrayList values = tf.getValues();
        for (int i = values.size() - 1; i > -1; i--) data.add(values.get(i));
        allGenes.setListData(data);
        Vector empty = new Vector();
        selectedGenes.setListData(empty);
        Vector hcv = new Vector();
        hcv.add(hClimbResult);
        Vector cladeData = new Vector();
        String name = "HillClimbing values";
        cladeData.add(name);
        clades.put(name, hcv);
        cladeList.setListData(cladeData);
        copyFasta();
    }

    /**
     * Copies the contents of fasta.dat to the fasta RG copy file, removing
     * the first line in the process
     */
    private void copyFasta() {
        try {
            BufferedReader input = new BufferedReader(new FileReader("fasta.dat"));
            BufferedWriter output = new BufferedWriter(new FileWriter(fastaRGCopy));
            String line = input.readLine();
            line = input.readLine();
            while (line != null) {
                output.write(line);
                output.newLine();
                line = input.readLine();
            }
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the homogeneity test.  It bins the sequences in the given clade,
     * runs the old npop confidence interval on them, finding the best npop
     * and likelihood, and then runs hillclimbing on that value with the omega
     * sigma and drift from the first hillclimbing.  If that comes up with a 
     * better likelihood, it makes note of it.
     */
    private void runHomoGeneity() {
        int[] indices = cladeList.getSelectedIndices();
        ListModel model = cladeList.getModel();
        String name = (String) model.getElementAt(indices[0]);
        if (name.equals("HillClimbing values")) return;
        Vector data = (Vector) (clades.get(model.getElementAt(indices[0])));
        try {
            FredOutVal test = (FredOutVal) data.get(0);
            data.remove(0);
        } catch (ClassCastException e) {
        }
        log.append("Running full analysis on the following sequences: \n");
        for (int i = 0; i < data.size(); i++) {
            log.append((String) data.get(i) + "\n");
        }
        ArrayList sequences = new ArrayList();
        for (int j = 0; j < data.size(); j++) sequences.add(data.get(j));
        SelectSeqBins selector = new SelectSeqBins(fastaRGCopy, sequences, narr);
        ArrayList bins = selector.getBins();
        values.setBins(bins);
        values.setSeqVals(selector.getSeqVals());
        File input = new File("oldNpopIn.dat");
        File output = new File("oldNpopOut.dat");
        String cmd = "oldNpopCI.exe";
        DemarcationConfidence demarcConf = new DemarcationConfidence(hClimbResult, values, narr, log, input, output, cmd, false);
        int[] interval = demarcConf.demarcations();
        double bestLike = demarcConf.getBestLike();
        narr.writeInput(output);
        int sortPer = MasterVariables.getSortPercentage();
        double[] percentages = new double[6];
        percentages[sortPer] = bestLike;
        int npop = interval[0];
        FredOutVal homGen = new FredOutVal(hClimbResult.getOmega(), hClimbResult.getSigma(), npop, hClimbResult.getDrift(), percentages);
        log.append("\nRunning hillclimbing with the following value:\n");
        log.append(homGen.toString() + "\n");
        narr.println();
        narr.println("Running hillclimbing with the following value:");
        narr.println(homGen.toString());
        try {
            homGen = hillClimbing(homGen);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] hClimbPers = hClimbResult.getPercentages();
        double[] homGenPers = homGen.getPercentages();
        if ((homGenPers[sortPer] / hClimbPers[sortPer]) > MasterVariables.HOMGEN_COEFF) {
            data.add(0, homGen);
            clades.put(model.getElementAt(indices[0]), data);
            log.append("The selected clade had a better likelihood than " + "the whole set together.\n");
            narr.println("The selected clade had a better likelihood than " + "the whole set together.");
        } else {
            log.append("The selected clade had a likelihood that was not " + "significantly better than the likelihood for the whole " + "set\n");
            narr.println("The selected clade had a likelihood that was not " + "significantly better than the likelihood for the whole " + "set");
        }
    }

    /**
     * Runs demarctions.  It bins the selected sequences, runs the old npop
     * confidence interval, finds the best value from that, and then finds 
     * the confidence interval, making sure to point out if 1 is in that
     * confidence interval or not
     */
    private void runDemarc() {
        Vector data = new Vector();
        ListModel model = selectedGenes.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            data.add(model.getElementAt(i));
        }
        if (data.size() == 0) {
            JOptionPane.showMessageDialog(null, "Please add sequences first");
            return;
        }
        log.append("Running demarcation on the following sequences: \n");
        for (int i = 0; i < data.size(); i++) {
            log.append((String) data.get(i) + "\n");
        }
        ArrayList sequences = new ArrayList();
        for (int j = 0; j < data.size(); j++) sequences.add(data.get(j));
        SelectSeqBins selector = new SelectSeqBins(fastaRGCopy, sequences, narr);
        ArrayList bins = selector.getBins();
        values.setBins(bins);
        values.setSeqVals(selector.getSeqVals());
        File input = new File("oldNpopIn.dat");
        File output = new File("oldNpopOut.dat");
        String cmd = "oldNpopCI.exe";
        DemarcationConfidence demarcConf = new DemarcationConfidence(currentValue, values, narr, log, input, output, cmd, false);
        int[] interval = demarcConf.demarcations();
        narr.writeInput(output);
        log.append("The optimal value was at " + interval[0] + " and the " + "confidence interval stretched from " + interval[1] + " to " + interval[2] + "\n");
        narr.println("The optimal value was at " + interval[0] + " and the " + "confidence interval stretched from " + interval[1] + " to " + interval[2]);
        if (interval[1] == 1) {
            log.append("The confidence interval included 1\n");
            narr.println("The confidence interval included 1");
        }
    }

    private File treeFile;

    private File fastaRGCopy = new File("fastaRGCopy.dat");

    private BinningAndFred values;

    private FredOutVal currentValue;

    private FredOutVal hClimbResult;

    NarrWriter narr;

    private File narrDemarc = new File("narrDemarc.txt");

    private File inputFile;

    private final JFileChooser fc = new JFileChooser();

    private HashMap clades = new HashMap();

    private javax.swing.JButton addButton;

    private javax.swing.JList allGenes;

    private javax.swing.JLabel allSeqLabel;

    private javax.swing.JLabel cladeLabel;

    private javax.swing.JList cladeList;

    private javax.swing.JButton defCladeButton;

    private javax.swing.JMenuItem exitItem;

    private javax.swing.JMenu fileMenu;

    private javax.swing.JMenuBar jMenuBar1;

    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JScrollPane jScrollPane2;

    private javax.swing.JScrollPane jScrollPane3;

    private javax.swing.JScrollPane jScrollPane4;

    private javax.swing.JMenuItem loadTreeItem;

    private javax.swing.JTextArea log;

    private javax.swing.JMenuItem openItem;

    private javax.swing.JButton removeAllButton;

    private javax.swing.JButton removeButton;

    private javax.swing.JButton removeCladeButton;

    private javax.swing.JButton retCladeButton;

    private javax.swing.JButton runAll;

    private javax.swing.JButton runFullAnalysisButton;

    private javax.swing.JMenuItem saveFastaItem;

    private javax.swing.JList selectedGenes;

    private javax.swing.JLabel seqRunLabel;
}