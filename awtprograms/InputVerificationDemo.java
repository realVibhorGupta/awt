/*
 * InputVerificationDemo.java is a 1.4 example that
 * requires no other files.
 */

import java.util.Set;
import java.util.HashSet;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.text.*;

/**
 * InputVerificationDemo.java is a 1.4 example that
 * requires no other files.
 *
 * Yet another mortgage calculator.
 * However, instead of using a formatted text field,
 * as shown in FormattedTextFieldDemo, this example
 * uses input verification to validate user input.
 */
public class InputVerificationDemo extends JPanel {
    //Default values
    private static double DEFAULT_AMOUNT = 100000;
    private static double DEFAULT_RATE = 7.5; //7.5%
    private static int DEFAULT_PERIOD = 30;

    //Labels to identify the text fields
    private JLabel amountLabel;
    private JLabel rateLabel;
    private JLabel numPeriodsLabel;
    private JLabel paymentLabel;

    //Strings for the labels
    private static String amountString = "Loan Amount (10,000-10,000,000): ";
    private static String rateString = "APR (>= 0%): ";
    private static String numPeriodsString = "Years (1-40): ";
    private static String paymentString = "Monthly Payment: ";

    //Text fields for data entry
    private JTextField amountField;
    private JTextField rateField;
    private JTextField numPeriodsField;
    private JTextField paymentField;

    //Formats to format and parse numbers
    private NumberFormat moneyFormat;
    private NumberFormat percentFormat;
    private DecimalFormat decimalFormat;
    private DecimalFormat paymentFormat;
    private MyVerifier verifier = new MyVerifier();

    public InputVerificationDemo() {
        super(new BorderLayout());
        setUpFormats();
        double payment = computePayment(DEFAULT_AMOUNT,
                                        DEFAULT_RATE,
                                        DEFAULT_PERIOD);

        //Create the labels.
        amountLabel = new JLabel(amountString);
        rateLabel = new JLabel(rateString);
        numPeriodsLabel = new JLabel(numPeriodsString);
        paymentLabel = new JLabel(paymentString);

        //Create the text fields and set them up.
        amountField = new JTextField(moneyFormat.format(DEFAULT_AMOUNT), 10);
        amountField.setInputVerifier(verifier);

        rateField = new JTextField(percentFormat.format(DEFAULT_RATE), 10);
        rateField.setInputVerifier(verifier);

        numPeriodsField = new JTextField(decimalFormat.format(DEFAULT_PERIOD), 10);
        numPeriodsField.setInputVerifier(verifier);

        paymentField = new JTextField(paymentFormat.format(payment), 10);
        paymentField.setInputVerifier(verifier);
        paymentField.setEditable(false);
        //Remove this component from the focus cycle.
        paymentField.setFocusable(false);
        paymentField.setForeground(Color.red);

        //Register an action listener to handle Return.
        amountField.addActionListener(verifier);
        rateField.addActionListener(verifier);
        numPeriodsField.addActionListener(verifier);

        //Tell accessibility tools about label/textfield pairs.
        amountLabel.setLabelFor(amountField);
        rateLabel.setLabelFor(rateField);
        numPeriodsLabel.setLabelFor(numPeriodsField);
        paymentLabel.setLabelFor(paymentField);

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(amountLabel);
        labelPane.add(rateLabel);
        labelPane.add(numPeriodsLabel);
        labelPane.add(paymentLabel);

        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(amountField);
        fieldPane.add(rateField);
        fieldPane.add(numPeriodsField);
        fieldPane.add(paymentField);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }

    class MyVerifier extends InputVerifier
                     implements ActionListener {
        double MIN_AMOUNT = 10000.0;
        double MAX_AMOUNT = 10000000.0;
        double MIN_RATE = 0.0;
        int MIN_PERIOD = 1;
        int MAX_PERIOD = 40;

       public boolean shouldYieldFocus(JComponent input) {
            boolean inputOK = verify(input);
            makeItPretty(input);
            updatePayment();

            if (inputOK) {
                return true;
            } else {
                Toolkit.getDefaultToolkit().beep();
                return false;
            }
        }

        protected void updatePayment() {
            double amount = DEFAULT_AMOUNT;
            double rate = DEFAULT_RATE;
            int numPeriods = DEFAULT_PERIOD;
            double payment = 0.0;

            //Parse the values.
            try {
                amount = moneyFormat.parse(amountField.getText()).
                                  doubleValue();
            } catch (ParseException pe) {}
            try {
                rate = percentFormat.parse(rateField.getText()).
                                     doubleValue();
            } catch (ParseException pe) {}
            try {
                numPeriods = decimalFormat.parse(numPeriodsField.getText()).
                                  intValue();
            } catch (ParseException pe) {}

            //Calculate the result and update the GUI.
            payment = computePayment(amount, rate, numPeriods);
            paymentField.setText(paymentFormat.format(payment));
        }

        //This method checks input, but should cause no side effects.
        public boolean verify(JComponent input) {
            return checkField(input, false);
        }

        protected void makeItPretty(JComponent input) {
            checkField(input, true);
        }

        protected boolean checkField(JComponent input, boolean changeIt) {
            if (input == amountField) {
                return checkAmountField(changeIt);
            } else if (input == rateField) {
                return checkRateField(changeIt);
            } else if (input == numPeriodsField) {
                return checkNumPeriodsField(changeIt);
            } else {
                return true; //shouldn't happen
            }
        }

        //Checks that the amount field is valid.  If it is valid,
        //it returns true; otherwise, returns false.  If the
        //change argument is true, this method reigns in the
        //value if necessary and (even if not) sets it to the
        //parsed number so that it looks good -- no letters,
        //for example.
        protected boolean checkAmountField(boolean change) {
            boolean wasValid = true;
            double amount = DEFAULT_AMOUNT;

            //Parse the value.
            try {
                amount = moneyFormat.parse(amountField.getText()).
                                  doubleValue();
            } catch (ParseException pe) {
                wasValid = false;
            }

            //Value was invalid.
            if ((amount < MIN_AMOUNT) || (amount > MAX_AMOUNT)) {
                wasValid = false;
                if (change) {
                    if (amount < MIN_AMOUNT) {
                        amount = MIN_AMOUNT;
                    } else { // amount is greater than MAX_AMOUNT
                        amount = MAX_AMOUNT;
                    }
                }
            }

            //Whether value was valid or not, format it nicely.
            if (change) {
                amountField.setText(moneyFormat.format(amount));
                amountField.selectAll();
            }

            return wasValid;
        }

        //Checks that the rate field is valid.  If it is valid,
        //it returns true; otherwise, returns false.  If the
        //change argument is true, this method reigns in the
        //value if necessary and (even if not) sets it to the
        //parsed number so that it looks good -- no letters,
        //for example.
        protected boolean checkRateField(boolean change) {
            boolean wasValid = true;
            double rate = DEFAULT_RATE;

            //Parse the value.
            try {
                rate = percentFormat.parse(rateField.getText()).
                                     doubleValue();
            } catch (ParseException pe) {
                wasValid = false;
            }

            //Value was invalid.
            if (rate < MIN_RATE) {
                wasValid = false;
                if (change) {
                    rate = MIN_RATE;
                }
            }

            //Whether value was valid or not, format it nicely.
            if (change) {
                rateField.setText(percentFormat.format(rate));
                rateField.selectAll();
            }

            return wasValid;
        }

        //Checks that the numPeriods field is valid.  If it is valid,
        //it returns true; otherwise, returns false.  If the
        //change argument is true, this method reigns in the
        //value if necessary and (even if not) sets it to the
        //parsed number so that it looks good -- no letters,
        //for example.
        protected boolean checkNumPeriodsField(boolean change) {
            boolean wasValid = true;
            int numPeriods = DEFAULT_PERIOD;

            //Parse the value.
            try {
                numPeriods = decimalFormat.parse(numPeriodsField.getText()).
                                  intValue();
            } catch (ParseException pe) {
                wasValid = false;
            }

            //Value was invalid.
            if ((numPeriods < MIN_PERIOD) || (numPeriods > MAX_PERIOD)) {
                wasValid = false;
                if (change) {
                    if (numPeriods < MIN_PERIOD) {
                        numPeriods = MIN_PERIOD;
                    } else { // numPeriods is greater than MAX_PERIOD
                        numPeriods = MAX_PERIOD;
                    }
                }
            }

            //Whether value was valid or not, format it nicely.
            if (change) {
                numPeriodsField.setText(decimalFormat.format(numPeriods));
                numPeriodsField.selectAll();
            }

            return wasValid;
        }

        public void actionPerformed(ActionEvent e) {
            JTextField source = (JTextField)e.getSource();
            shouldYieldFocus(source); //ignore return value
            source.selectAll();
        }
    }

    public static void main(String[] args) {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("InputVerificationDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent newContentPane = new InputVerificationDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //Compute the monthly payment based on the loan amount,
    //APR, and length of loan.
    double computePayment(double loanAmt, double rate, int numPeriods) {
        double I, partial1, denominator, answer;

        numPeriods *= 12;        //get number of months
        if (rate > 0.01) {
            I = rate / 100.0 / 12.0;         //get monthly rate from annual
            partial1 = Math.pow((1 + I), (0.0 - numPeriods));
            denominator = (1 - partial1) / I;
        } else { //rate ~= 0
            denominator = numPeriods;
        }

        answer = (-1 * loanAmt) / denominator;
        return answer;
    }

    //Create and set up number formats. These objects are used
    //for both parsing input and formatting output.
    private void setUpFormats() {
        moneyFormat = (NumberFormat)NumberFormat.getNumberInstance();

        percentFormat = NumberFormat.getNumberInstance();
        percentFormat.setMinimumFractionDigits(3);

        decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance();
        decimalFormat.setParseIntegerOnly(true);

        paymentFormat = (DecimalFormat)NumberFormat.getNumberInstance();
        paymentFormat.setMaximumFractionDigits(2);
        paymentFormat.setNegativePrefix("(");
        paymentFormat.setNegativeSuffix(")");
    }
}
