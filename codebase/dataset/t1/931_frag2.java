    public static void main(String[] args) {

        JFrame frame = new JFrame("Transparent Window");

        frame.addComponentListener(new TransparentComponentListener());

        TransparentBackground bg = new TransparentBackground(frame);

        bg.setLayout(new BorderLayout());

        JButton button = new JButton("This is a button");

        bg.add("North", button);

        JLabel label = new JLabel("This is a label");

        bg.add("South", label);

        frame.getContentPane().add("Center", bg);

        frame.pack();

        frame.setSize(200, 200);

        frame.show();

    }
