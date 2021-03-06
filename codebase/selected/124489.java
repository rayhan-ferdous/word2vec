package jmri.jmrit.symbolicprog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Represent an entire speed table as a single Variable.
 * <P>
 * This presents as a set of vertically oriented sliders, with numeric values above them.
 * That it turn is done using VarSlider and DecVariableValue objects respectively.
 * VarSlider is an interior class to color a JSlider by state. The respective VarSlider
 * and DecVariableValue communicate through their underlying CV objects. Changes to
 * CV Values are listened to by this class, which updates the model objects for the
 * VarSliders; the DecVariableValues listen directly.
 *<P>
 * Color (hence state) of individual sliders (hence CVs) are directly coupled to the
 * state of those CVs.
 *<P> The state of the entire variable has to be a composite of all the sliders, hence CVs.
 * The mapping is (in order):
 *<UL>
 *<LI>If any CVs are UNKNOWN, its UNKNOWN..
 *<LI>If not, and any are EDITED, its EDITED.
 *<LI>If not, and any are FROMFILE, its FROMFILE.
 *<LI>If not, and any are READ, its READ.
 *<LI>If not, and any are STORED, its STORED.
 *<LI>And if we get to here, something awful has happened.
 *</UL><P>
 * A similar pattern is used for a read or write request.  Write writes them all;
 * Read reads any that aren't READ or WRITTEN.
 *<P>
 * Speed tables can have different numbers of entries; 28 is the default, and also the maximum.
 * <P>
 * The NMRA specification says that speed table entries cannot be non-monotonic (e.g. cannot
 * decrease when moving from lower to higher CV numbers). In earlier versions of the code,
 * this was enforced any time a value was changed (for any reason).  This caused a problem
 * when CVs were read that were non-monotonic:  That value was read, causing lower CVs to be made
 * consistent, a change in their value which changed their state, so they were read again.  To
 * avoid this, the class now only enforces non-monotonicity when the slider is adjusted.
 *<P>
 * _value is a holdover from the LongAddrVariableValue, which this was copied from; it should
 * be removed.
 *<P>
 * @author	Bob Jacobsen, Alex Shepherd   Copyright (C) 2001, 2004
 * @version	$Revision: 1.37 $
 *
 */
public class SpeedTableVarValue extends VariableValue implements PropertyChangeListener, ChangeListener {

    int nValues;

    BoundedRangeModel[] models;

    int _min;

    int _max;

    int _range;

    List<JCheckBox> stepCheckBoxes;

    /**
     * Create the object with a "standard format ctor".
     */
    public SpeedTableVarValue(String name, String comment, String cvName, boolean readOnly, boolean infoOnly, boolean writeOnly, boolean opsOnly, int cvNum, String mask, int minVal, int maxVal, Vector<CvValue> v, JLabel status, String stdname, int entries) {
        super(name, comment, cvName, readOnly, infoOnly, writeOnly, opsOnly, cvNum, mask, v, status, stdname);
        nValues = entries;
        _min = minVal;
        _max = maxVal;
        _range = maxVal - minVal;
        models = new BoundedRangeModel[nValues];
        for (int i = 0; i < nValues; i++) {
            DefaultBoundedRangeModel j = new DefaultBoundedRangeModel(_range * i / (nValues - 1) + _min, 0, _min, _max);
            models[i] = j;
            CvValue c = _cvVector.elementAt(getCvNum() + i);
            c.setValue(_range * i / (nValues - 1) + _min);
            c.addPropertyChangeListener(this);
            c.setState(CvValue.FROMFILE);
        }
        _defaultColor = (new JSlider()).getBackground();
    }

    /**
     * Create a null object.  Normally only used for tests and to pre-load classes.
     */
    public SpeedTableVarValue() {
    }

    public Object rangeVal() {
        log.warn("rangeVal doesn't make sense for a speed table");
        return "Speed table";
    }

    public CvValue[] usesCVs() {
        CvValue[] retval = new CvValue[nValues];
        int i;
        for (i = 0; i < nValues; i++) retval[i] = _cvVector.elementAt(getCvNum() + i);
        return retval;
    }

    /**
     * Called for new values of a slider.
     * <P>
     * Sets the CV(s) as needed.
     * @param e
     */
    public void stateChanged(ChangeEvent e) {
        JSlider j = (JSlider) e.getSource();
        BoundedRangeModel r = j.getModel();
        for (int i = 0; i < nValues; i++) {
            if (r == models[i]) {
                setModel(i, r.getValue());
                break;
            }
        }
        prop.firePropertyChange("Value", null, j);
    }

    void setModel(int i, int value) {
        if (models[i].getValue() != value) models[i].setValue(value);
        _cvVector.elementAt(getCvNum() + i).setValue(value);
        if (isReading || isWriting) return; else {
            forceMonotonic(i, value);
            matchPoints(i);
        }
    }

    /**
     * Check entries on either side to see if they are set monotonically.
     * If not, adjust.
     *
     * @param i number (index) of the entry
     * @param value  new value
     */
    void forceMonotonic(int modifiedStepIndex, int value) {
        if (modifiedStepIndex > 0) {
            if (models[modifiedStepIndex - 1].getValue() > value) {
                setModel(modifiedStepIndex - 1, value);
            }
        }
        if (modifiedStepIndex < nValues - 1) {
            if (value > models[modifiedStepIndex + 1].getValue()) {
                setModel(modifiedStepIndex + 1, value);
            }
        }
    }

    /**
     * If there are fixed points specified, set linear 
     * step settings to them.
     *
     */
    void matchPoints(int modifiedStepIndex) {
        if (stepCheckBoxes == null) {
            return;
        }
        if (modifiedStepIndex < 0) log.error("matchPoints called with index too small: " + modifiedStepIndex);
        if (modifiedStepIndex >= stepCheckBoxes.size()) log.error("matchPoints called with index too large: " + modifiedStepIndex + " >= " + stepCheckBoxes.size());
        if (stepCheckBoxes.get(modifiedStepIndex) == null) log.error("matchPoints found null checkbox " + modifiedStepIndex);
        if (!stepCheckBoxes.get(modifiedStepIndex).isSelected()) return;
        matchPointsLeft(modifiedStepIndex);
        matchPointsRight(modifiedStepIndex);
    }

    void matchPointsLeft(int modifiedStepIndex) {
        for (int i = modifiedStepIndex - 1; i >= 0; i--) {
            if (stepCheckBoxes.get(i).isSelected()) {
                int leftval = _cvVector.elementAt(getCvNum() + i).getValue();
                int rightval = _cvVector.elementAt(getCvNum() + modifiedStepIndex).getValue();
                int steps = modifiedStepIndex - i;
                log.debug("left found " + leftval + " " + rightval + " " + steps);
                for (int j = i + 1; j < modifiedStepIndex; j++) {
                    int newValue = leftval + (rightval - leftval) * (j - i) / steps;
                    log.debug("left set " + j + " to " + newValue);
                    if (_cvVector.elementAt(getCvNum() + j).getValue() != newValue) _cvVector.elementAt(getCvNum() + j).setValue(newValue);
                }
                return;
            }
        }
        return;
    }

    void matchPointsRight(int modifiedStepIndex) {
        for (int i = modifiedStepIndex + 1; i < nValues; i++) {
            if (stepCheckBoxes.get(i).isSelected()) {
                int rightval = _cvVector.elementAt(getCvNum() + i).getValue();
                int leftval = _cvVector.elementAt(getCvNum() + modifiedStepIndex).getValue();
                int steps = i - modifiedStepIndex;
                log.debug("right found " + leftval + " " + rightval + " " + steps);
                for (int j = modifiedStepIndex + 1; j < i; j++) {
                    int newValue = leftval + (rightval - leftval) * (j - modifiedStepIndex) / steps;
                    log.debug("right set " + j + " to " + newValue);
                    if (_cvVector.elementAt(getCvNum() + j).getValue() != newValue) _cvVector.elementAt(getCvNum() + j).setValue(newValue);
                }
                return;
            }
        }
        return;
    }

    public int getState() {
        int i;
        for (i = 0; i < nValues; i++) if (_cvVector.elementAt(getCvNum() + i).getState() == UNKNOWN) return UNKNOWN;
        for (i = 0; i < nValues; i++) if (_cvVector.elementAt(getCvNum() + i).getState() == EDITED) return EDITED;
        for (i = 0; i < nValues; i++) if (_cvVector.elementAt(getCvNum() + i).getState() == FROMFILE) return FROMFILE;
        for (i = 0; i < nValues; i++) if (_cvVector.elementAt(getCvNum() + i).getState() == READ) return READ;
        for (i = 0; i < nValues; i++) if (_cvVector.elementAt(getCvNum() + i).getState() == STORED) return STORED;
        log.error("getState did not decode a possible state");
        return UNKNOWN;
    }

    public String getValueString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < models.length; i++) {
            if (i != 0) buf.append(",");
            buf.append(Integer.toString(models[i].getValue()));
        }
        return buf.toString();
    }

    public void setIntValue(int i) {
        log.warn("setIntValue doesn't make sense for a speed table: " + i);
    }

    public int getIntValue() {
        log.warn("getValue doesn't make sense for a speed table");
        return 0;
    }

    public Object getValueObject() {
        return null;
    }

    public Component getCommonRep() {
        log.warn("getValue not implemented yet");
        return new JLabel("speed table");
    }

    public void setValue(int value) {
        log.warn("setValue doesn't make sense for a speed table: " + value);
    }

    Color _defaultColor;

    void setColor(Color c) {
    }

    public Component getNewRep(String format) {
        final int GRID_Y_BUTTONS = 3;
        JPanel j = new JPanel();
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints cs = new GridBagConstraints();
        j.setLayout(g);
        initStepCheckBoxes();
        for (int i = 0; i < nValues; i++) {
            cs.gridy = 0;
            cs.gridx = i;
            CvValue cv = _cvVector.elementAt(getCvNum() + i);
            JSlider s = new VarSlider(models[i], cv, i + 1);
            s.setOrientation(JSlider.VERTICAL);
            s.addChangeListener(this);
            int currentState = cv.getState();
            int currentValue = cv.getValue();
            DecVariableValue decVal = new DecVariableValue("val" + i, "", "", false, false, false, false, getCvNum() + i, "VVVVVVVV", _min, _max, _cvVector, _status, "");
            decVal.setValue(currentValue);
            decVal.setState(currentState);
            Component v = decVal.getCommonRep();
            ((JTextField) v).setToolTipText("Step " + (i + 1) + " CV " + (getCvNum() + i));
            ((JComponent) v).setBorder(null);
            g.setConstraints(v, cs);
            if (i == 0 && log.isDebugEnabled()) log.debug("Font size " + v.getFont().getSize());
            float newSize = v.getFont().getSize() * 0.8f;
            v.setFont(jmri.util.FontUtil.deriveFont(v.getFont(), newSize));
            j.add(v);
            cs.gridy++;
            g.setConstraints(s, cs);
            j.add(s);
            cs.gridy++;
            JCheckBox b = stepCheckBoxes.get(i);
            g.setConstraints(b, cs);
            j.add(b, cs);
        }
        JPanel k = new JPanel();
        JButton b;
        k.add(b = new JButton("Force Straight"));
        b.setToolTipText("Insert straight line between min and max");
        b.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                doForceStraight(e);
            }
        });
        k.add(b = new JButton("Match ends"));
        b.setToolTipText("Insert a straight line between existing endpoints");
        b.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                doMatchEnds(e);
            }
        });
        k.add(b = new JButton("Constant ratio curve"));
        b.setToolTipText("Insert a constant ratio curve between existing endpoints");
        b.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                doRatioCurve(e);
            }
        });
        k.add(b = new JButton("Log curve"));
        b.setToolTipText("Insert a logarithmic curve between existing endpoints");
        b.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                doLogCurve(e);
            }
        });
        k.add(b = new JButton("Shift left"));
        b.setToolTipText("Shift the existing curve left one slot");
        b.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                doShiftLeft(e);
            }
        });
        k.add(b = new JButton("Shift right"));
        b.setToolTipText("Shift the existing curve right one slot");
        b.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                doShiftRight(e);
            }
        });
        cs.gridy = GRID_Y_BUTTONS;
        cs.gridx = 0;
        cs.gridwidth = GridBagConstraints.RELATIVE;
        g.setConstraints(k, cs);
        JPanel val = new JPanel();
        val.setLayout(new BorderLayout());
        val.add(j, BorderLayout.CENTER);
        val.add(k, BorderLayout.SOUTH);
        updateRepresentation(val);
        return val;
    }

    void initStepCheckBoxes() {
        stepCheckBoxes = new ArrayList<JCheckBox>();
        for (int i = 0; i < nValues; i++) {
            JCheckBox b = new JCheckBox();
            b.setToolTipText("Check to fix this point when adjusting; uncheck to adjust individually");
            stepCheckBoxes.add(b);
        }
    }

    /**
     * Set the values to a straight line from _min to _max
     */
    void doForceStraight(java.awt.event.ActionEvent e) {
        _cvVector.elementAt(getCvNum() + 0).setValue(_min);
        _cvVector.elementAt(getCvNum() + nValues - 1).setValue(_max);
        doMatchEnds(e);
    }

    /**
     * Set the values to a straight line from existing ends
     */
    void doMatchEnds(java.awt.event.ActionEvent e) {
        int first = _cvVector.elementAt(getCvNum() + 0).getValue();
        int last = _cvVector.elementAt(getCvNum() + nValues - 1).getValue();
        log.debug(" first=" + first + " last=" + last);
        _cvVector.elementAt(getCvNum() + 0).setValue(last);
        for (int i = 0; i < nValues; i++) {
            int value = first + i * (last - first) / (nValues - 1);
            _cvVector.elementAt(getCvNum() + i).setValue(value);
        }
    }

    /**
     * Set a constant ratio curve
     */
    void doRatioCurve(java.awt.event.ActionEvent e) {
        double first = _cvVector.elementAt(getCvNum() + 0).getValue();
        if (first < 1.) first = 1.;
        double last = _cvVector.elementAt(getCvNum() + nValues - 1).getValue();
        if (last < first + 1) last = first + 1.;
        double step = Math.log(last / first) / (nValues - 1);
        log.debug("log ratio step is " + step);
        _cvVector.elementAt(getCvNum() + 0).setValue((int) Math.round(last));
        for (int i = 0; i < nValues; i++) {
            int value = (int) (Math.floor(first * Math.exp(step * i)));
            _cvVector.elementAt(getCvNum() + i).setValue(value);
        }
    }

    /**
     * Set a log curve
     */
    void doLogCurve(java.awt.event.ActionEvent e) {
        double first = _cvVector.elementAt(getCvNum() + 0).getValue();
        double last = _cvVector.elementAt(getCvNum() + nValues - 1).getValue();
        if (last < first + 1.) last = first + 1.;
        double factor = 1. / 10.;
        _cvVector.elementAt(getCvNum() + 1).setValue((int) Math.round(last));
        double previous = first;
        double ratio = Math.pow(1. - factor, nValues - 1.);
        double limit = last + (last - first) * ratio;
        for (int i = 1; i < nValues; i++) {
            previous = limit - (limit - first) * ratio / Math.pow(1. - factor, nValues - 1. - i);
            int value = (int) (Math.floor(previous));
            _cvVector.elementAt(getCvNum() + i).setValue(value);
        }
    }

    /**
     * Shift the curve one CV to left.  The last entry is left unchanged.
     */
    void doShiftLeft(java.awt.event.ActionEvent e) {
        for (int i = 0; i < nValues - 1; i++) {
            int value = _cvVector.elementAt(getCvNum() + i + 1).getValue();
            _cvVector.elementAt(getCvNum() + i).setValue(value);
        }
    }

    /**
     * Shift the curve one CV to right.  The first entry is left unchanged.
     */
    void doShiftRight(java.awt.event.ActionEvent e) {
        for (int i = nValues - 1; i > 0; i--) {
            int value = _cvVector.elementAt(getCvNum() + i - 1).getValue();
            _cvVector.elementAt(getCvNum() + i).setValue(value);
        }
    }

    /**
     * IDLE if a read/write operation is not in progress.  During an operation, it
     * indicates the index of the CV to handle when the current programming operation
     * finishes.
     */
    private int _progState = IDLE;

    private static final int IDLE = -1;

    boolean isReading;

    boolean isWriting;

    /**
     * Count number of retries done
     */
    private int retries = 0;

    /**
     * Define maximum number of retries of read/write operations before moving on
     */
    private static final int RETRY_MAX = 2;

    boolean onlyChanges = false;

    /**
     * Notify the connected CVs of a state change from above
     * @param state
     */
    public void setCvState(int state) {
        _cvVector.elementAt(getCvNum()).setState(state);
    }

    public boolean isChanged() {
        for (int i = 0; i < nValues; i++) {
            if (considerChanged(_cvVector.elementAt(getCvNum() + i))) {
                return true;
            }
        }
        return false;
    }

    public void readChanges() {
        if (log.isDebugEnabled()) log.debug("readChanges() invoked");
        if (!isChanged()) return;
        onlyChanges = true;
        setBusy(true);
        if (_progState != IDLE) log.warn("Programming state " + _progState + ", not IDLE, in read()");
        isReading = true;
        isWriting = false;
        _progState = -1;
        retries = 0;
        if (log.isDebugEnabled()) log.debug("start series of read operations");
        readNext();
    }

    public void writeChanges() {
        if (log.isDebugEnabled()) log.debug("writeChanges() invoked");
        if (!isChanged()) return;
        onlyChanges = true;
        if (getReadOnly()) log.error("unexpected write operation when readOnly is set");
        setBusy(true);
        super.setState(STORED);
        if (_progState != IDLE) log.warn("Programming state " + _progState + ", not IDLE, in write()");
        isReading = false;
        isWriting = true;
        _progState = -1;
        retries = 0;
        if (log.isDebugEnabled()) log.debug("start series of write operations");
        writeNext();
    }

    public void readAll() {
        if (log.isDebugEnabled()) log.debug("readAll() invoked");
        onlyChanges = false;
        setToRead(false);
        setBusy(true);
        if (_progState != IDLE) log.warn("Programming state " + _progState + ", not IDLE, in read()");
        isReading = true;
        isWriting = false;
        _progState = -1;
        retries = 0;
        if (log.isDebugEnabled()) log.debug("start series of read operations");
        readNext();
    }

    public void writeAll() {
        if (log.isDebugEnabled()) log.debug("writeAll() invoked");
        onlyChanges = false;
        if (getReadOnly()) log.error("unexpected write operation when readOnly is set");
        setToWrite(false);
        setBusy(true);
        super.setState(STORED);
        if (_progState != IDLE) log.warn("Programming state " + _progState + ", not IDLE, in write()");
        isReading = false;
        isWriting = true;
        _progState = -1;
        retries = 0;
        if (log.isDebugEnabled()) log.debug("start series of write operations");
        writeNext();
    }

    void readNext() {
        if ((_progState >= 0) && (retries < RETRY_MAX) && (_cvVector.elementAt(getCvNum() + _progState).getState() != CvValue.READ)) {
            retries++;
        } else {
            retries = 0;
            _progState++;
        }
        if (_progState >= nValues) {
            _progState = IDLE;
            isReading = false;
            isWriting = false;
            setBusy(false);
            return;
        }
        CvValue cv = _cvVector.elementAt(getCvNum() + _progState);
        int state = cv.getState();
        if (log.isDebugEnabled()) log.debug("invoke CV read index " + _progState + " cv state " + state);
        if (!onlyChanges || considerChanged(cv)) cv.read(_status); else readNext();
    }

    void writeNext() {
        if ((_progState >= 0) && (retries < RETRY_MAX) && (_cvVector.elementAt(getCvNum() + _progState).getState() != CvValue.STORED)) {
            retries++;
        } else {
            retries = 0;
            _progState++;
        }
        if (_progState >= nValues) {
            _progState = IDLE;
            isReading = false;
            isWriting = false;
            setBusy(false);
            return;
        }
        CvValue cv = _cvVector.elementAt(getCvNum() + _progState);
        int state = cv.getState();
        if (log.isDebugEnabled()) log.debug("invoke CV write index " + _progState + " cv state " + state);
        if (!onlyChanges || considerChanged(cv)) cv.write(_status); else writeNext();
    }

    public void propertyChange(java.beans.PropertyChangeEvent e) {
        if (log.isDebugEnabled()) log.debug("property changed event - name: " + e.getPropertyName());
        if (e.getPropertyName().equals("Busy") && ((Boolean) e.getNewValue()).equals(Boolean.FALSE)) {
            if (isReading) readNext(); else if (isWriting) writeNext(); else return;
        } else if (e.getPropertyName().equals("State")) {
            CvValue cv = _cvVector.elementAt(getCvNum());
            if (log.isDebugEnabled()) log.debug("CV State changed to " + cv.getState());
            setState(cv.getState());
        } else if (e.getPropertyName().equals("Value")) {
            CvValue cv = (CvValue) e.getSource();
            int value = cv.getValue();
            for (int i = 0; i < nValues; i++) {
                if (_cvVector.elementAt(getCvNum() + i) == cv) {
                    setModel(i, value);
                    break;
                }
            }
        }
    }

    public class VarSlider extends JSlider {

        VarSlider(BoundedRangeModel m, CvValue var, int step) {
            super(m);
            _var = var;
            setBackground(_var.getColor());
            setToolTipText("Step " + step + " CV " + var.number());
            _var.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    originalPropertyChanged(e);
                }
            });
        }

        CvValue _var;

        void originalPropertyChanged(java.beans.PropertyChangeEvent e) {
            if (log.isDebugEnabled()) log.debug("VarSlider saw property change: " + e);
            if (e.getPropertyName().equals("State")) {
                setBackground(_var.getColor());
            }
        }
    }

    public void dispose() {
        if (log.isDebugEnabled()) log.debug("dispose");
        for (int i = 0; i < nValues; i++) {
            _cvVector.elementAt(getCvNum() + i).removePropertyChangeListener(this);
        }
    }

    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SpeedTableVarValue.class.getName());
}
