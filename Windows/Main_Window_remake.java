package Windows;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import assets.Assets;


import java.awt.*;

public class Main_Window_remake {
    Assets as = new Assets();
    private static Double amount = 0.0;
//    final String[] keypadNames = {" 7 ", " 8 ", " 9 ", "4", "5", "6", "1", "2", "3", "0", "00", ","};
    final String[] keypadNames = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "00", ","};


    private final JPanel mainPanel = new JPanel();
    private final JTextArea displayCount = new JTextArea();

    public Main_Window_remake(){
        mainPanel.setBounds(0, 0, (int)as.width, (int)as.height);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#E5ECF5"));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.decode("#E5ECF5"));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Color.decode("#E5ECF5"));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.setBackground(Color.decode("#E5ECF5"));
        JPanel leftPanelKeyboardPart = new JPanel();
        leftPanelKeyboardPart.setLayout(new GridBagLayout());
        leftPanelKeyboardPart.setBackground(Color.decode("#E5ECF5"));
        JPanel leftPanelKeyboardRightButtons = new JPanel();
        leftPanelKeyboardRightButtons.setLayout(new GridBagLayout());
        leftPanelKeyboardRightButtons.setBackground(Color.decode("#E5ECF5"));
        JPanel leftPanelKeyboardDials = new JPanel();
        leftPanelKeyboardDials.setLayout(new GridBagLayout());
        leftPanelKeyboardDials.setBackground(Color.decode("#E5ECF5"));
        JPanel rightPanelBottomPart = new JPanel();
        rightPanelBottomPart.setLayout(new GridBagLayout());
        rightPanelBottomPart.setBackground(Color.decode("#E5ECF5"));
        JPanel rightPanelTopPart = new JPanel();
        rightPanelTopPart.setLayout(new GridBagLayout());
        rightPanelTopPart.setBackground(Color.decode("#E5ECF5"));

        GridBagConstraints mainGBC = new GridBagConstraints();
        System.out.println("w: " + (int) (as.width) + " h: "+(int) (as.height));

        //left panel
        mainGBC.gridx =0;
        mainGBC.gridy =0;
        mainGBC.insets=new Insets((int)as.mainInsets,(int)as.mainInsets,0,(int)(as.mainInsets));
        leftPanel.setPreferredSize(new Dimension((int) (as.width*as.leftPanelFactor),(int) (as.height*as.topPanelFactor)));
        System.out.println("leftPanel w: " + (int) (as.width*as.leftPanelFactor) + " h: "+(int) (as.height*as.topPanelFactor));
        mainGBC.fill=GridBagConstraints.BOTH;
        mainGBC.weightx = as.leftPanelFactor;
        mainGBC.weighty = as.topPanelFactor;
        mainPanel.add(leftPanel, mainGBC);

        //right panel
        mainGBC.gridx =1;
        mainGBC.gridy =0;
        mainGBC.insets=new Insets((int)as.mainInsets,0,0,(int)as.mainInsets);
        rightPanel.setPreferredSize(new Dimension ((int) (as.width*as.rightPanelFactor),(int) (as.height*as.topPanelFactor)));
        System.out.println("rightPanel w: " + (int) (as.width*as.rightPanelFactor) + " h: "+(int) (as.height*as.topPanelFactor));
        mainGBC.fill=GridBagConstraints.BOTH;
        mainGBC.weightx = as.rightPanelFactor;
        mainGBC.weighty = as.topPanelFactor;
        rightPanel.setBackground(Color.gray);
        mainPanel.add(rightPanel,mainGBC);

        //bottom Panel
        mainGBC.gridy =1;
        mainGBC.gridx =0;
        mainGBC.insets=new Insets(0,(int)as.mainInsets,(int)as.mainInsets,(int)as.mainInsets);
        bottomPanel.setPreferredSize(new Dimension ((int) (as.width),(int) (as.height*as.bottomPanelFactor)));
        System.out.println("bottomPanel w: " + (int) (as.width) + " h: "+(int) (as.height*as.bottomPanelFactor));
        mainGBC.gridwidth =2;
        mainGBC.fill=GridBagConstraints.BOTH;
        mainGBC.weighty = as.bottomPanelFactor;
        bottomPanel.setBackground(Color.decode("#736E7E"));
        mainPanel.add(bottomPanel,mainGBC);

        //display part on left side
        GridBagConstraints leftContentsGBC = new GridBagConstraints();
        leftContentsGBC.gridy = 0;
        leftContentsGBC.weighty = as.topPanelFactor*as.leftPanelDisplayPartFactor;
        leftContentsGBC.weightx = as.leftPanelFactor;
        JPanel leftPanelDisplayPart = new JPanel();
        leftPanelDisplayPart.setBorder(null);
        leftPanelDisplayPart.setLayout(new GridBagLayout());
        leftPanelDisplayPart.setPreferredSize(new Dimension((int) (as.width*as.leftPanelFactor),(int) (as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor)));
        System.out.println("leftPanelDisplayPart w: " + (int) (as.width*as.leftPanelFactor) + " h: "+(int) (as.height*as.leftPanelDisplayPartFactor)+ " fax: " +as.leftPanelFactor+ " fay: "+as.topPanelFactor*as.leftPanelDisplayPartFactor);
        leftContentsGBC.fill=GridBagConstraints.BOTH;
        leftPanel.add(leftPanelDisplayPart,leftContentsGBC);

        //keyboard part on left side
        leftContentsGBC.gridy = 1;
        leftContentsGBC.weighty = as.topPanelFactor*as.leftPanelKeyboardPartFactor;
        leftContentsGBC.weightx = as.leftPanelFactor;
        leftPanelKeyboardPart.setPreferredSize(new Dimension((int) (as.width*as.leftPanelFactor),(int) (as.height*as.topPanelFactor*as.leftPanelKeyboardPartFactor)));
        System.out.println("leftPanelKeyboardPart w: " + (int) (as.width*as.leftPanelFactor) + " h: "+(int) (as.height*as.leftPanelKeyboardPartFactor)+" fax: "+as.leftPanelFactor+" fay: "+as.topPanelFactor*as.leftPanelKeyboardPartFactor);
        leftContentsGBC.fill=GridBagConstraints.BOTH;
        leftPanel.add(leftPanelKeyboardPart,leftContentsGBC);

        GridBagConstraints keypadGBC = new GridBagConstraints();
        keypadGBC.fill = GridBagConstraints.BOTH;
        keypadGBC.insets = new Insets((int)(as.mainInsets),0,0,0);

        //left side of dial pad (numbers)
        keypadGBC.gridx = 0;
        keypadGBC.gridy = 0;
        keypadGBC.weighty = as.topPanelFactor*as.leftPanelKeyboardPartFactor;
        keypadGBC.weightx = as.leftPanelFactor*as.keypadPanelFactor;
        leftPanelKeyboardDials.setPreferredSize(new Dimension((int) (as.width*as.leftPanelFactor*as.keypadPanelFactor),(int) (as.height*as.topPanelFactor*as.leftPanelKeyboardPartFactor)));
        System.out.println("leftPanelKeyboardDials w: " + (int) (as.width*as.leftPanelFactor*as.keypadPanelFactor) + " h: "+(int) (as.height*as.leftPanelKeyboardPartFactor)+" fax: "+ as.leftPanelFactor*as.keypadPanelFactor+" fay: "+as.topPanelFactor*as.leftPanelKeyboardPartFactor);
        leftPanelKeyboardPart.add(leftPanelKeyboardDials,keypadGBC);

        //right side of dial pad (utilities)
        keypadGBC.gridx = 1;
        keypadGBC.weighty = as.topPanelFactor*as.leftPanelKeyboardPartFactor;
        keypadGBC.weightx = as.leftPanelFactor*as.topPanelFactor*as.keypadUtilityButtonsFactor;
        leftPanelKeyboardRightButtons.setPreferredSize(new Dimension((int) (as.width*as.leftPanelFactor*as.keypadUtilityButtonsFactor),(int) (as.height*as.topPanelFactor*as.leftPanelKeyboardPartFactor)));
        leftPanelKeyboardRightButtons.setBorder(null);
        System.out.println("leftPanelKeyboardRightButtons w: " + (int) (as.width*as.leftPanelFactor*as.keypadUtilityButtonsFactor) + " h: "+(int) (as.height*as.topPanelFactor*as.leftPanelKeyboardPartFactor)+" fax: "+as.leftPanelFactor*as.keypadUtilityButtonsFactor+" fay: "+as.topPanelFactor*as.leftPanelKeyboardPartFactor);
        leftPanelKeyboardPart.add(leftPanelKeyboardRightButtons,keypadGBC);

        GridBagConstraints leftPanelKeyboardDialsGBC = new GridBagConstraints();
        leftPanelKeyboardDialsGBC.fill=GridBagConstraints.BOTH;
        leftPanelKeyboardDialsGBC.insets = new Insets(0, 0, 0, 0);

        //display for count show
        displayCount.setFont(new Font("Arial", Font.PLAIN,40));
        displayCount.setPreferredSize(new Dimension((int) (as.width*as.leftPanelFactor*as.keypadPanelFactor),(int) (as.height*as.topPanelFactor*as.leftPanelKeyboardPartFactor*0.11)));
        displayCount.setBackground(Color.decode("#ECD996"));
        displayCount.setBorder(null);
        System.out.println("displayCount w: " + (int) (as.width*as.leftPanelFactor*as.keypadPanelFactor) + " h: "+(int) (as.height*as.topPanelFactor*as.leftPanelKeyboardPartFactor*0.11) +" fax: "+ as.leftPanelFactor*as.keypadPanelFactor+" fay: "+as.topPanelFactor*as.leftPanelKeyboardPartFactor*0.11);
        leftPanelKeyboardDialsGBC.weightx = as.leftPanelFactor*as.keypadPanelFactor;
        leftPanelKeyboardDialsGBC.weighty = as.topPanelFactor*as.leftPanelKeyboardPartFactor*0.11;
        leftPanelKeyboardDialsGBC.gridx = 0;
        leftPanelKeyboardDialsGBC.gridy = 0;
        leftPanelKeyboardDialsGBC.gridwidth = 3;
        leftPanelKeyboardDials.add(displayCount,leftPanelKeyboardDialsGBC);

        //make keypad buttons
        JButton[] keypadButtons = new JButton[12];
        leftPanelKeyboardDialsGBC.gridwidth = 1;
        leftPanelKeyboardDialsGBC.weightx = as.keyButtonFactor;
        leftPanelKeyboardDialsGBC.weighty = as.keyButtonFactor*0.77;
        System.out.println("keypadButtons w: " + (int) (as.keypadButtonWidth) + " h: "+(int) (as.keypadButtonHeight)+" fax: " +as.keyButtonFactor+" fay: "+as.keyButtonFactor*0.77);

        leftPanelKeyboardDialsGBC.gridy = 1;

        for (int numberIndex = 0; numberIndex < 12; numberIndex++) {
            keypadButtons[numberIndex] = new JButton(keypadNames[numberIndex]){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(3,1,4));
                    g.fillRoundRect(0,0,(int)as.keypadButtonWidth,(int)as.keypadButtonWidth,16,16);
                    g.setColor(new Color(52,44,53));
                    g.fillRoundRect((int)(as.keypadButtonWidth*0.105/2),(int)(as.keypadButtonHeight*0.105/2),(int)(as.keypadButtonWidth*0.97),(int)(as.keypadButtonHeight*0.82),13,13);
                    g.setColor(new Color(255, 255, 255, 80));
                    g.fillRoundRect(5,5,(int)(as.keypadButtonWidth),(int)(as.keypadButtonHeight*0.12),16,16);
                    g.setColor(new Color(255,255,255));
                    g.setFont(new Font("Arial",Font.BOLD,40));
                    g.drawString(super.getText(),(int)(as.keypadButtonWidth/2),(int)(as.keypadButtonHeight/2));
                }
            };
            keypadButtons[numberIndex].setPreferredSize(new Dimension((int) as.keypadButtonWidth, (int) as.keypadButtonHeight));
            keypadButtons[numberIndex].setBackground(Color.decode("#362B36"));
            keypadButtons[numberIndex].setForeground(Color.white);
            keypadButtons[numberIndex].setBorder(new BevelBorder(BevelBorder.RAISED));
            keypadButtons[numberIndex].setFont(new Font("Arial", Font.BOLD,22));
            leftPanelKeyboardDials.add(keypadButtons[numberIndex],leftPanelKeyboardDialsGBC);

            int finalNumberIndex = numberIndex;
            keypadButtons[numberIndex].addActionListener(e -> keypadButtonsFunction(finalNumberIndex));
            leftPanelKeyboardDialsGBC.gridx++;
            if ((numberIndex + 1) % 3 == 0) {
                leftPanelKeyboardDialsGBC.gridx=0;
                leftPanelKeyboardDialsGBC.gridy++;
            }
        }

        //adding utility buttons
        GridBagConstraints leftPanelKeyboardUtilitiesGBC = new GridBagConstraints();
        leftPanelKeyboardUtilitiesGBC.insets =new Insets(0,0,0,0);
        leftPanelKeyboardUtilitiesGBC.weighty=as.leftPanelFactor*as.keypadUtilityButtonsFactor;
        leftPanelKeyboardUtilitiesGBC.weightx=as.leftPanelFactor*as.keypadUtilityButtonsFactor;
        System.out.println("Utility buttons w: " + (int) (as.utilityDimension.width )+ " h: "+(int) (as.utilityDimension.height)+ " fax: "+as.leftPanelFactor*as.keypadUtilityButtonsFactor+" fay: "+as.leftPanelFactor*as.keypadUtilityButtonsFactor);
        leftPanelKeyboardUtilitiesGBC.gridx=0;

        //button for backspace
        leftPanelKeyboardUtilitiesGBC.gridy=0;
        JButton backspace = new JButton("<-BKSP");
        backspace.setPreferredSize(as.utilityDimension);
        backspace.setBackground(Color.decode("#C8D3E0"));
        backspace.setMargin(new Insets(0,0,0,0));
        backspace.setBorder(null);
        leftPanelKeyboardRightButtons.add(backspace,leftPanelKeyboardUtilitiesGBC);

        //button for zmazat
        leftPanelKeyboardUtilitiesGBC.gridy=1;
        JButton zmazat = new JButton("C/ \n Zmaza");
        zmazat.setBackground(Color.decode("#F03320"));
        zmazat.setForeground(Color.decode("#E7C328"));
        zmazat.setMargin(new Insets(0,0,0,0));
        zmazat.setPreferredSize(as.utilityDimension);
        zmazat.setBorder(new BevelBorder(BevelBorder.RAISED));
        leftPanelKeyboardRightButtons.add(zmazat,leftPanelKeyboardUtilitiesGBC);

        //button for mnozstvo
        leftPanelKeyboardUtilitiesGBC.gridy=2;
        JButton mnozstvo = new JButton("Množstvo");
        mnozstvo.setBackground(Color.decode("#E7C328"));
        mnozstvo.setPreferredSize(as.utilityDimension);
        mnozstvo.setMargin(new Insets(0,0,0,0));
        mnozstvo.setBorder(null);
        leftPanelKeyboardRightButtons.add(mnozstvo,leftPanelKeyboardUtilitiesGBC);

        //button for tlac dane
        leftPanelKeyboardUtilitiesGBC.gridy=3;
        JButton tlacDane = new JButton("Tlač \n Dane");
        tlacDane.setPreferredSize(as.utilityDimension);
        tlacDane.setBackground(Color.decode("#C8D3E0"));
        tlacDane.setMargin(new Insets(0,0,0,0));
        tlacDane.setBorder(null);
        leftPanelKeyboardRightButtons.add(tlacDane,leftPanelKeyboardUtilitiesGBC);

        GridBagConstraints leftPanelDisplayPartGBC = new GridBagConstraints();
        //adding display
        leftPanelDisplayPartGBC.weightx=as.leftPanelFactor;
        leftPanelDisplayPartGBC.weighty=as.topPanelFactor*as.leftPanelDisplayPartFactor*0.0724;
        leftPanelDisplayPartGBC.gridy=0;
        leftPanelDisplayPartGBC.gridx=0;
        leftPanelDisplayPartGBC.gridwidth=5;
        leftPanelDisplayPartGBC.fill=GridBagConstraints.BOTH;
        leftPanelDisplayPartGBC.insets=new Insets(0,0,0,0);

        JTextArea topPanel = new JTextArea();
        topPanel.setEditable(false);
        topPanel.setVisible(true);
        topPanel.setBackground(Color.decode("#ECD996"));
        System.out.println("topPanel w: " + (int)(as.width*as.leftPanelFactor)+ " h: "+(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*0.0724)+ " fax: "+as.leftPanelFactor + " fay: "+as.topPanelFactor*as.leftPanelDisplayPartFactor*0.0724);
        topPanel.setPreferredSize(new Dimension((int)(as.width*as.leftPanelFactor),(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*0.0724)));
        leftPanelDisplayPart.add(topPanel,leftPanelDisplayPartGBC);


        leftPanelDisplayPartGBC.weighty=as.topPanelFactor*as.leftPanelDisplayPartFactor*as.mainDisplayFactor;
        leftPanelDisplayPartGBC.gridy=1;
        System.out.println("jScrollPaneForMainDisplay w: " + (int)(as.width*as.leftPanelFactor)+ " h: "+(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*as.mainDisplayFactor)+ " fax: "+as.leftPanelFactor + " fay: "+as.topPanelFactor*as.leftPanelDisplayPartFactor*as.mainDisplayFactor);
        JTextArea mainDisplay = new JTextArea();
        mainDisplay.setEditable(false);
        mainDisplay.setVisible(true);
        mainDisplay.setPreferredSize(new Dimension((int)(as.width*as.leftPanelFactor),(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*as.mainDisplayFactor)));
        mainDisplay.setBackground(Color.decode("#BDCEDE"));
        mainDisplay.setBorder(null);
//        mainDisplay.setLineWrap(true);

        JScrollPane jScrollPaneForMainDisplay = new JScrollPane(mainDisplay);
        jScrollPaneForMainDisplay.setBorder(null);
        jScrollPaneForMainDisplay.setPreferredSize(new Dimension((int)(as.width*as.leftPanelFactor), (int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*as.mainDisplayFactor)));
        jScrollPaneForMainDisplay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanelDisplayPart.add(jScrollPaneForMainDisplay,leftPanelDisplayPartGBC);

        leftPanelDisplayPartGBC.weighty=as.topPanelFactor*as.leftPanelDisplayPartFactor*0.148;
        leftPanelDisplayPartGBC.weightx=as.leftPanelFactor*0.15;
        leftPanelDisplayPartGBC.gridwidth=1;
        leftPanelDisplayPartGBC.gridy=2;
        leftPanelDisplayPartGBC.gridx=0;
        System.out.println("arrowUp w: " + (int)(as.width*as.leftPanelFactor*0.15)+ " h: "+(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*0.148)+ " fax: "+as.leftPanelFactor*0.15 + " fay: "+as.topPanelFactor*as.leftPanelDisplayPartFactor*as.mainDisplayFactor*0.148);

        JButton arrowUp = new JButton("^");
        arrowUp.setPreferredSize(new Dimension((int)(as.width*as.leftPanelFactor*0.15),(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*0.148)));
        arrowUp.setBackground(Color.decode("#C8D3E0"));
        arrowUp.setBorder(null);
        leftPanelDisplayPart.add(arrowUp,leftPanelDisplayPartGBC);

        leftPanelDisplayPartGBC.gridx=1;
        JButton arrowDown = new JButton("d");
        arrowDown.setPreferredSize(new Dimension((int)(as.width*as.leftPanelFactor*0.15),(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*0.148)));
        arrowDown.setBackground(Color.decode("#C8D3E0"));
        arrowDown.setBorder(null);
        leftPanelDisplayPart.add(arrowDown,leftPanelDisplayPartGBC);

        leftPanelDisplayPartGBC.gridx=2;
        JButton minimize = new JButton("s");
        minimize.setPreferredSize(new Dimension((int)(as.width*as.leftPanelFactor*0.15),(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*0.148)));
        minimize.setBackground(Color.decode("#C8D3E0"));
        minimize.setBorder(null);
        leftPanelDisplayPart.add(minimize,leftPanelDisplayPartGBC);

        leftPanelDisplayPartGBC.gridx=3;
        leftPanelDisplayPartGBC.gridwidth=2;
        leftPanelDisplayPartGBC.weighty=as.topPanelFactor*as.leftPanelDisplayPartFactor*0.148;
        leftPanelDisplayPartGBC.weightx=as.leftPanelFactor*0.54;
        System.out.println("priceDisplay w: " + (int)(as.width*as.leftPanelFactor*0.54)+ " h: "+(int)(as.width*as.leftPanelFactor*0.54*0.22)+ " fax: "+as.leftPanelFactor*0.54 + " fay: "+as.topPanelFactor*as.leftPanelDisplayPartFactor*0.148);
        JTextArea priceDisplay = new JTextArea();
        priceDisplay.setBorder(null);
        priceDisplay.setEditable(false);
        priceDisplay.setBackground(Color.decode("#ECD996"));
        priceDisplay.setPreferredSize(new Dimension((int)(as.width*as.leftPanelFactor*0.54),(int)(as.height*as.topPanelFactor*as.leftPanelDisplayPartFactor*0.148)));
        leftPanelDisplayPart.add(priceDisplay,leftPanelDisplayPartGBC);

        GridBagConstraints rightPanelGBC = new GridBagConstraints();
        rightPanelGBC.gridy=0;
        rightPanelGBC.gridx=0;
        rightPanelGBC.weighty=1-(as.topPanelFactor*0.563);
        rightPanelGBC.weightx=as.rightPanelFactor;
        rightPanelGBC.fill=GridBagConstraints.BOTH;
        rightPanelTopPart.setPreferredSize(new Dimension((int)(as.width*as.rightPanelFactor),(int)(as.height*(1 - as.topPanelFactor*0.563))));
        rightPanelTopPart.setBorder(null);
//        rightPanelTopPart.setBackground(Color.BLACK);
        rightPanel.add(rightPanelTopPart,rightPanelGBC);

        rightPanelGBC.gridy=1;
        rightPanelGBC.weighty=as.topPanelFactor*0.563;
        rightPanelBottomPart.setPreferredSize(new Dimension((int)(as.width*as.rightPanelFactor),(int)(as.height*as.topPanelFactor*0.563)));
        rightPanelBottomPart.setBorder(null);
        rightPanel.add(rightPanelBottomPart,rightPanelGBC);
        GridBagConstraints operationButtonsGBC = new GridBagConstraints();
        operationButtonsGBC.weighty=as.keypadButtonWidth*0.929*0.728;
        operationButtonsGBC.weightx=as.keypadButtonWidth*0.929;
//        operationButtonsGBC.fill=GridBagConstraints.BOTH;

        System.out.println("rightPanelBottomPart w: " + (int)(as.width*as.rightPanelFactor)+ " h: "+(int)(as.height*as.topPanelFactor*0.563)+ " fax: "+as.rightPanelFactor + " fay: "+as.topPanelFactor*0.563);

        //adding 25 buttons on right
        JButton[] operationsButtons = new JButton[25];
        for (int numberOfButton = 0; numberOfButton < 25; numberOfButton++) {

            operationsButtons[numberOfButton] = new JButton();
            operationsButtons[numberOfButton].setPreferredSize(new Dimension((int)(as.keypadButtonWidth*0.929), (int)(as.keypadButtonWidth*0.929*0.728)));
            operationsButtons[numberOfButton].setBackground(Color.decode("#C8D3E0"));
            operationsButtons[numberOfButton].setBorder(null);

            rightPanelBottomPart.add(operationsButtons[numberOfButton], operationButtonsGBC);

            operationButtonsGBC.gridx++;
            if ((numberOfButton + 1) % 5 == 0) {
                operationButtonsGBC.gridx = 0;
                operationButtonsGBC.gridy++;
            }
        }
    }
    private void keypadButtonsFunction(int finalNumberIndex) {
        amount = (Double.parseDouble((Double.toString(amount).replaceFirst(".0", "")
                + keypadNames[finalNumberIndex])));


        displayCount.append(keypadNames[finalNumberIndex]);
        System.out.println(amount);
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

}
