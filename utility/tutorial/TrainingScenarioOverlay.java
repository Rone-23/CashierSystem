package utility.tutorial;

import assets.Colors;
import assets.Scaler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingScenarioOverlay extends JDialog {

    private final Map<String, JPanel> objectivePanels = new HashMap<>();
    private final Map<String, JLabel> objectiveLabels = new HashMap<>();
    private final JLabel statusLabel;

    public TrainingScenarioOverlay(JFrame parentFrame, List<TrainingObjective> targets) {
        super(parentFrame, false);
        setUndecorated(true);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));

        int gap = Scaler.getPadding(0.01);

        JPanel mainCard = new JPanel(new BorderLayout(gap, gap)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 30, 30, 220));

                int arc = Scaler.getPadding(0.02);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
                g2.dispose();
            }
        };
        mainCard.setOpaque(false);

        int pad = Scaler.getPadding(0.015);
        mainCard.setBorder(new EmptyBorder(pad, pad, pad, pad));

        JLabel titleLabel = new JLabel("Tréningový Nákup", SwingConstants.CENTER);
        titleLabel.setFont(Scaler.getFont(0.02, Font.BOLD));
        titleLabel.setForeground(Color.WHITE);
        mainCard.add(titleLabel, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        int rowGap = Scaler.getPadding(0.01);
        for (TrainingObjective obj : targets) {
            JPanel row = createObjectiveRow(obj);
            objectivePanels.put(obj.getItemName().toLowerCase(), row);
            listPanel.add(row);
            listPanel.add(Box.createRigidArea(new Dimension(0, rowGap)));
        }
        mainCard.add(listPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Nablokuj všetky položky", SwingConstants.CENTER);
        statusLabel.setFont(Scaler.getFont(0.016, Font.BOLD));
        statusLabel.setForeground(Colors.MILD_YELLOW.getColor());
        mainCard.add(statusLabel, BorderLayout.SOUTH);

        add(mainCard);
        pack();

        int margin = Scaler.getPadding(0.03);
        int x = parentFrame.getX() + parentFrame.getWidth() - this.getWidth() - margin;
        int y = parentFrame.getY() + margin;
        setLocation(x, y);
    }

    private JPanel createObjectiveRow(TrainingObjective obj) {
        int hGap = Scaler.getPadding(0.015);
        JPanel row = new JPanel(new BorderLayout(hGap, 0));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        int maxRowWidth = Scaler.getDimension(0.25, 0).width;
        int maxRowHeight = Scaler.getPadding(0.06);
        row.setMaximumSize(new Dimension(maxRowWidth, maxRowHeight));

        JLabel nameLabel = new JLabel(obj.getItemName());
        nameLabel.setFont(Scaler.getFont(0.016, Font.BOLD));
        nameLabel.setForeground(Color.WHITE);
        row.add(nameLabel, BorderLayout.CENTER);

        JLabel progressLabel = new JLabel("0 / " + obj.getTargetAmount() + " ks");
        progressLabel.setFont(Scaler.getFont(0.016, Font.BOLD));
        progressLabel.setForeground(Color.WHITE);
        objectiveLabels.put(obj.getItemName().toLowerCase(), progressLabel);
        row.add(progressLabel, BorderLayout.EAST);

        return row;
    }

    public void updateItemRow(String itemName, int currentAmount, int targetAmount, TrainingObjective.State state) {
        String key = itemName.toLowerCase();
        JLabel label = objectiveLabels.get(key);
        JPanel row = objectivePanels.get(key);

        if (label == null || row == null) return;

        label.setText(currentAmount + " / " + targetAmount + " ks");

        switch (state) {
            case EXACT -> {
                label.setForeground(Colors.SUCCESS_GREEN.getColor());
                row.setBackground(new Color(0, 150, 0, 50));
                row.setOpaque(true);
            }
            case EXCEEDED -> {
                label.setForeground(Colors.DANGER_RED.getColor());
                row.setBackground(new Color(150, 0, 0, 50));
                row.setOpaque(true);
            }
            case PENDING -> {
                label.setForeground(Color.WHITE);
                row.setOpaque(false);
            }
        }
        repaint();
    }

    public void setObjectiveText(String text, Color color) {
        if (text == null || text.isEmpty()) {
            statusLabel.setVisible(false);
        } else {
            statusLabel.setVisible(true);
            statusLabel.setText(text);
            statusLabel.setForeground(color);
        }
    }
}