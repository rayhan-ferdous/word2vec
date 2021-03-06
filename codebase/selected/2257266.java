package com.NiPlayer.LyricS;

import java.awt.*;
import java.util.Date;
import javax.swing.*;
import java.awt.event.*;

public class Deneme {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transparent Window");
        TransparentBackground bg = new TransparentBackground(frame);
        bg.setLayout(new BorderLayout());
        JButton button = new JButton("This is a button");
        bg.add("North", button);
        JLabel label = new JLabel("This is a label");
        bg.add("South", label);
        frame.getContentPane().add("Center", bg);
        frame.pack();
        frame.setSize(300, 300);
        frame.show();
    }
}

class TransparentBackground extends JComponent implements ComponentListener, WindowFocusListener, Runnable {

    private JFrame frame;

    protected Image background;

    private long lastupdate = 0;

    public boolean refreshRequested = true;

    public TransparentBackground(JFrame frame) {
        this.frame = frame;
        updateBackground();
        frame.addComponentListener(this);
        frame.addWindowFocusListener(this);
        new Thread(this).start();
    }

    public void updateBackground() {
        try {
            Robot rbt = new Robot();
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension dim = tk.getScreenSize();
            background = rbt.createScreenCapture(new Rectangle(0, 0, (int) dim.getWidth(), (int) dim.getHeight()));
        } catch (Exception ex) {
            p(ex.toString());
            ex.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        Point pos = this.getLocationOnScreen();
        Point offset = new Point(-pos.x, -pos.y);
        g.drawImage(background, offset.x, offset.y, null);
    }

    public void componentShown(ComponentEvent evt) {
        repaint();
    }

    public void componentResized(ComponentEvent evt) {
        repaint();
    }

    public void componentMoved(ComponentEvent evt) {
        repaint();
    }

    public void componentHidden(ComponentEvent evt) {
    }

    public void windowGainedFocus(WindowEvent evt) {
        refresh();
    }

    public void windowLostFocus(WindowEvent evt) {
        refresh();
    }

    public void refresh() {
        if (this.isVisible() && frame.isVisible()) {
            repaint();
            refreshRequested = true;
            lastupdate = new Date().getTime();
        }
    }

    @Override
    public void run() {
    }

    public static void p(String str) {
        System.out.println(str);
    }
}
