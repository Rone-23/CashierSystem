import assets.Assets;
import services.CashOut;
import services.SQL_Connect;

import javax.swing.*;
import java.awt.*;

public class Main_Window {
    private JPanel mainPanel;
    private JTextArea displayMain;
    private JTextArea displayCount;
    private JButton backspace;
    private JButton zmazat;
    private JButton mnozstvo;
    private JButton tlacdane;
    private JButton articles;
    private JPanel operationsPanel;
    private JPanel keypad;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel countPanel;
    private JPanel downleftPanel;
    private JPanel displayKeypadPanel;
    private JPanel informationPanel;
    private JButton button1;
    private JButton button3;
    private JButton button4;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JPanel bottomPanel;
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final CashOut cashOut = new CashOut();

    final String[] keypadNames = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "00", ","};
    private static StringBuilder amountBuilder = new StringBuilder();
    private static Double amount = 0.0;

    private static final Assets as = new Assets();
    private static final SQL_Connect conn = new SQL_Connect();

    Choice_Panel choicePanel = new Choice_Panel(this);

    public Main_Window() {
        mainPanel.setBounds(0, 0, (int)as.width,(int) as.height);
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(choicePanel.getMainPanel(), JLayeredPane.PALETTE_LAYER);

        GridBagConstraints mainGBC = new GridBagConstraints();

        mainGBC.gridx =0;
        mainGBC.gridy =0;
        mainGBC.weightx = as.leftPanelFactor;
        mainGBC.weighty = as.topPanelFactor;
        leftPanel.setBackground(Color.black);
        mainPanel.add(leftPanel, mainGBC);
        mainGBC.gridx =1;
        mainGBC.gridy =0;
        mainGBC.weightx = as.rightPanelFactor;
        mainGBC.weighty = as.topPanelFactor;
        rightPanel.setBackground(Color.blue);
        mainPanel.add(rightPanel,mainGBC);
        mainGBC.gridy =1;
        mainGBC.gridwidth =2;
        mainGBC.weighty = as.bottomPanelFactor;
        bottomPanel.setBackground(Color.red);
        mainPanel.add(bottomPanel,mainGBC);



        articles.addActionListener(e -> openArticles());
        mnozstvo.addActionListener(e -> setMnozstvo());

        keypad.setLayout(new GridBagLayout());
        GridBagConstraints keypadGBC = new GridBagConstraints();
        keypadGBC.gridx = 0;
        keypadGBC.gridy = 0;
        keypadGBC.fill = GridBagConstraints.BOTH;
        keypadGBC.weightx = 1.0;
        keypadGBC.weighty = 1.0;

        operationsPanel.setLayout(new GridBagLayout());
        GridBagConstraints operationsGBC = new GridBagConstraints();
        operationsGBC.gridx = 0;
        operationsGBC.gridy = 0;
        operationsGBC.fill = GridBagConstraints.BOTH;
        operationsGBC.weightx = 1.0;
        operationsGBC.weighty = 1.0;
        keypadGBC.insets = new Insets(1, 1, 1, 1);

        //make keypad buttons
        JButton[] keypadButtons = new JButton[12];
        for (int numberIndex = 0; numberIndex < 12; numberIndex++) {


            keypadButtons[numberIndex] = new JButton(keypadNames[numberIndex]);
            keypadButtons[numberIndex].setPreferredSize(new Dimension(171, 132));
            keypadButtons[numberIndex].setBackground(Color.black);
            keypadButtons[numberIndex].setForeground(Color.white);

            keypad.add(keypadButtons[numberIndex], keypadGBC);
            int finalNumberIndex = numberIndex;
            keypadButtons[numberIndex].addActionListener(e -> keypadButtonsFunction(finalNumberIndex));
            keypadGBC.gridx++;
            if ((numberIndex + 1) % 3 == 0) {
                keypadGBC.gridx = 0;
                keypadGBC.gridy++;
            }
        }

        JButton[] operationsButtons = new JButton[25];
        for (int numberOfButton = 0; numberOfButton < 25; numberOfButton++) {

            operationsButtons[numberOfButton] = new JButton();
            operationsButtons[numberOfButton].setPreferredSize(new Dimension(186, 134));
            operationsButtons[numberOfButton].setBackground(Color.decode("#C8D3E0"));

            operationsPanel.add(operationsButtons[numberOfButton], operationsGBC);

            operationsGBC.gridx++;
            if ((numberOfButton + 1) % 5 == 0) {
                operationsGBC.gridx = 0;
                operationsGBC.gridy++;
            }
        }
        //cleaning mainDisplay
        JButton vklad = operationsButtons[20];
        vklad.setText("Vklad");
        vklad.setBackground(Color.decode("#E7C328"));
        vklad.addActionListener(e -> vklad());
    }

    private void vklad() {
        if (!choicePanel.getListOfItems().isEmpty()) {
            displayMain.setText("");
            conn.logToDB(cashOut.makeJSON(choicePanel.getListOfItems()), 0);

            System.out.println(cashOut.makeJSON(choicePanel.getListOfItems()));

            cashOut.makeReceipt(cashOut.makeJSON(choicePanel.getListOfItems()));
            choicePanel.getListOfItems().clear();
        }
    }

    private void keypadButtonsFunction(int finalNumberIndex) {
        amount = (Double.parseDouble((Double.toString(amount).replaceFirst(".0", "")
                + keypadNames[finalNumberIndex])));


        displayCount.append(keypadNames[finalNumberIndex]);
        System.out.println(amount);
    }

    private void setMnozstvo() {
        if (amount != 0.0) {
            choicePanel.setAmountToRemove(amount);
            amount = 0.0;
            displayCount.setText("");
        }
    }

    private void openArticles() {
        displayCount.setText("");
        amount = 0.0;
        choicePanel.getMainPanel().setVisible(true);
    }

    public JTextArea getDisplayMain() {
        return displayMain;
    }

    public static SQL_Connect getConn() {
        return conn;
    }

    public static Assets getAs() {
        return as;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }
}
