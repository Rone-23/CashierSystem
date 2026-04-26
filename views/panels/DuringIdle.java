package views.panels;

import assets.*;
import services.SQL_Connect;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import views.Components.ButtonCluster;
import views.Components.PromoCard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DuringIdle extends JPanel implements ButtonFoundable, ThemeObserver {

    private final ButtonCluster buttonCluster = new ButtonCluster(ButtonSet.IDLE_UTILITY_NAMES.getLabels());
    private final JButton themeButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.THEME_BUTTON.toString(), Colors.BUTTON_LIGHT_BLUE);

    private final JPanel catalogueContentPanel;
    private final JPanel gridPanel;
    private final JLabel catalogueTitle = new JLabel("Zľavy dňa");
    private float fadeAlpha = 1.0f;
    private Timer promoRotationTimer;

    public DuringIdle() {
        setLayout(new GridBagLayout());

        themeButton.setFont(Scaler.getFont(0.03, Font.BOLD));
        themeButton.setPreferredSize(Scaler.getDimension(0.15, 0.13));

        catalogueContentPanel = new JPanel(new BorderLayout()) {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
                super.paint(g2);
                g2.dispose();
            }
        };
        catalogueContentPanel.setOpaque(false);

        int gap = Scaler.getPadding(0.02);
        gridPanel = new JPanel(new GridLayout(2, 3, gap, gap));
        gridPanel.setOpaque(false);
        catalogueContentPanel.add(gridPanel, BorderLayout.CENTER);

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weighty = 1.0;

        gbcMain.weightx = 0.70;
        leftPanel.setPreferredSize(new Dimension(0, 0));
        add(leftPanel, gbcMain);

        gbcMain.gridx++;
        gbcMain.weightx = 0.30;
        rightPanel.setPreferredSize(new Dimension(0, 0));
        add(rightPanel, gbcMain);

        onThemeChange();
        ThemeManager.getInstance().addObserver(this);

        startPromoRotation();
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setName("leftPanel");
        leftPanel.setOpaque(false);

        catalogueTitle.setFont(Scaler.getFont(0.03, Font.BOLD));
        catalogueTitle.setBorder(new EmptyBorder(0, 0, Scaler.getPadding(0.02), 0));

        JPanel nonFadingWrapper = new JPanel(new BorderLayout());
        nonFadingWrapper.setOpaque(false);
        nonFadingWrapper.add(catalogueTitle, BorderLayout.NORTH);
        nonFadingWrapper.add(catalogueContentPanel, BorderLayout.CENTER);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(nonFadingWrapper);
        leftPanel.add(centerWrapper, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(themeButton);
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    private void startPromoRotation() {
        loadAutoCatalogue();
        promoRotationTimer = new Timer(8000, e -> triggerFadeTransition());
        promoRotationTimer.start();
    }

    private void triggerFadeTransition() {
        Timer fadeOut = new Timer(30, null);
        fadeOut.addActionListener(e -> {
            fadeAlpha -= 0.08f;

            if (fadeAlpha <= 0.0f) {
                fadeAlpha = 0.0f;
                fadeOut.stop();
                catalogueContentPanel.repaint();

                Timer pause = new Timer(150, pauseEvent -> {

                    loadAutoCatalogue();

                    Timer fadeIn = new Timer(25, null);
                    fadeIn.addActionListener(e2 -> {
                        fadeAlpha += 0.08f;
                        if (fadeAlpha >= 1.0f) {
                            fadeAlpha = 1.0f;
                            fadeIn.stop();
                        }
                        catalogueContentPanel.repaint();
                    });
                    fadeIn.start();
                });

                pause.setRepeats(false);
                pause.start();

                return;
            }

            catalogueContentPanel.repaint();
        });
        fadeOut.start();
    }

    public void loadAutoCatalogue() {
        gridPanel.removeAll();

        List<String[]> randomDiscounts = SQL_Connect.getInstance().getRandomDiscounts(6);

        if (randomDiscounts.isEmpty()) {
            JLabel emptyLabel = new JLabel("Dnes nie sú aktívne žiadne zľavy.");
            emptyLabel.setFont(Scaler.getFont(0.02, Font.PLAIN));
            emptyLabel.setForeground(Colors.BLACK_TEXT_LIGHT.getColor());
            gridPanel.add(emptyLabel);
        } else {
            for (String[] promo : randomDiscounts) {
                String name = promo[0];
                int originalPriceCents = Integer.parseInt(promo[1]);
                int discountPercent = Integer.parseInt(promo[2]);
                String imagePath = promo[3];
                boolean requiresCard = promo[4].equals("1");

                gridPanel.add(new PromoCard(name, originalPriceCents, discountPercent, imagePath, requiresCard));
            }
        }

        gridPanel.revalidate();
    }

    public JPanel createRightPanel(){
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(buttonCluster, BorderLayout.CENTER);
        return rightPanel;
    }

    @Override
    public JButton getButton(String key) {
        for (Component c : buttonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        if(key.equals(ButtonSet.ButtonLabel.THEME_BUTTON.toString())) return themeButton;
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public JButton[] getButtons(String key) {
        ArrayList<JButton> jButtons = new ArrayList<>();
        for (Component c : buttonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }
        if(key.equals(ButtonSet.ButtonLabel.THEME_BUTTON.toString())) jButtons.add(themeButton);
        if (!jButtons.isEmpty()) return jButtons.toArray(new JButton[0]);
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public void onThemeChange() {
        setBackground(Colors.BACKGROUND_GRAY.getColor());

        catalogueTitle.setForeground(Colors.BLACK_TEXT.getColor());

        loadAutoCatalogue();
        repaint();
    }
}