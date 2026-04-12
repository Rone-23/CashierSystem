package utility.tutorial;

import assets.Colors;

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

        JPanel mainCard = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 30, 30, 220));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        mainCard.setOpaque(false);
        mainCard.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Tréningový Nákup", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        titleLabel.setForeground(Colors.BUTTON_LIGHT_BLUE.getColor());
        mainCard.add(titleLabel, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        for (TrainingObjective obj : targets) {
            JPanel row = createObjectiveRow(obj);
            objectivePanels.put(obj.getItemName().toLowerCase(), row);
            listPanel.add(row);
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        mainCard.add(listPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Nablokuj všetky položky", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        statusLabel.setForeground(Colors.MILD_YELLOW.getColor());
        mainCard.add(statusLabel, BorderLayout.SOUTH);

        add(mainCard);
        pack();

        int x = parentFrame.getX() + parentFrame.getWidth() - this.getWidth() - 30;
        int y = parentFrame.getY() + 30;
        setLocation(x, y);
    }

    private JPanel createObjectiveRow(TrainingObjective obj) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        row.setMaximumSize(new Dimension(350, 60));

        JLabel nameLabel = new JLabel(obj.getItemName());
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        row.add(nameLabel, BorderLayout.CENTER);

        JLabel progressLabel = new JLabel("0 / " + obj.getTargetAmount() + " ks");
        progressLabel.setFont(new Font("Roboto", Font.BOLD, 16));
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