        jointBox.add(this.inferButton);

        detailsBox.add(jointBox);

        jointBox = Box.createHorizontalBox();

        label = new JLabel("Record Delimiter:");

        this.recordDelimiter = new JTextField("\\n");

        this.recordDelimiter.addActionListener(new ActionListener() {



            public void actionPerformed(ActionEvent ev) {

                int selectedRow = fileselector.getSelectedRow();

                if (selectedRow >= 0) {

                    String fn = (String) sourcemodel.getValueAt(selectedRow, 0);

                    if (fn != null) {

                        fn = fn.trim();

                        if ((fn != null) && (fn.length() > 0)) {

                            updateDetailsFor(fn);
