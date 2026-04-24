package utility.tutorial;

import assets.Colors;
import assets.Scaler;
import assets.ThemeManager;
import assets.ThemeObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;
import java.util.List;

public class TutorialOverlay extends JPanel implements ThemeObserver {
    private final JPanel dialogPanel;

    private final JLabel titleLabel;
    private final JLabel textLabel;

    private final JButton nextButton;
    private final JButton skipButton;

    private Color backgroundColor;
    private Color borderColor;
    private Color forgroundColor;

    private BufferedImage blurredBackground;
    private TutorialStep currentStep;
    private List<JComponent> targetComponents;

    public TutorialOverlay() {
        onThemeChange();
        ThemeManager.getInstance().addObserver(this);

        setOpaque(false);
        setLayout(null);

        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseAdapter() {});

        int gap = Scaler.getPadding(0.01);

        dialogPanel = new JPanel(new BorderLayout(gap, gap)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(backgroundColor);

                int arc = Scaler.getPadding(0.03);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(borderColor);

                int strokeThickness = Math.max(2, Scaler.getPadding(0.005));
                g2.setStroke(new BasicStroke(strokeThickness));

                int arc = Scaler.getPadding(0.03);
                int offset = strokeThickness / 2;
                g2.drawRoundRect(offset, offset, getWidth() - strokeThickness, getHeight() - strokeThickness, arc, arc);

                g2.dispose();
            }
        };
        dialogPanel.setOpaque(false);

        int vPad = Scaler.getPadding(0.02);
        int hPad = Scaler.getPadding(0.04);
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(vPad, hPad, vPad, hPad));

        titleLabel = new JLabel();
        titleLabel.setForeground(forgroundColor);
        titleLabel.setFont(Scaler.getFont(0.04, Font.BOLD));

        textLabel = new JLabel();
        textLabel.setForeground(forgroundColor);
        textLabel.setFont(Scaler.getFont(0.03, Font.PLAIN));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        skipButton = new JButton("Ukončiť");
        nextButton = new JButton("Ďalej");

        skipButton.setFont(Scaler.getFont(0.025, Font.BOLD));
        nextButton.setFont(Scaler.getFont(0.025, Font.BOLD));

        buttonPanel.add(skipButton);
        buttonPanel.add(nextButton);

        dialogPanel.add(titleLabel, BorderLayout.NORTH);
        dialogPanel.add(textLabel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(dialogPanel);
    }

    private void updateBackgroundBlur() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame) {
            Container contentPane = ((JFrame) window).getContentPane();
            if (contentPane.getWidth() <= 0 || contentPane.getHeight() <= 0) return;

            int scale = 2;
            int w = contentPane.getWidth() / scale;
            int h = contentPane.getHeight() / scale;

            if (w == 0 || h == 0) return;

            BufferedImage capture = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = capture.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.scale(1.0 / scale, 1.0 / scale);
            contentPane.paint(g2);
            g2.dispose();

            int radius = 3;
            int size = radius * 2 + 1;
            float weight = 1.0f / (size * size);
            float[] data = new float[size * size];
            Arrays.fill(data, weight);

            Kernel kernel = new Kernel(size, size, data);
            ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

            blurredBackground = op.filter(capture, null);
        }
    }

    public void displayStep(TutorialStep step, Runnable onNext, Runnable onSkip) {
        this.currentStep = step;
        this.targetComponents = step.getTargetComponents();

        titleLabel.setText(step.getTitle());

        int htmlWidth = Scaler.getDimension(0.25, 0).width;
        textLabel.setText("<html><div style='width: " + htmlWidth + "px; margin-top: 5px;'>" + step.getMessage() + "</div></html>");

        for (java.awt.event.ActionListener al : nextButton.getActionListeners()) {
            nextButton.removeActionListener(al);
        }
        for (java.awt.event.ActionListener al : skipButton.getActionListeners()) {
            skipButton.removeActionListener(al);
        }

        nextButton.addActionListener(e -> onNext.run());
        skipButton.addActionListener(e -> onSkip.run());

        updateBackgroundBlur();

        positionDialog();
        repaint();
    }

    private void positionDialog() {
        dialogPanel.setSize(dialogPanel.getPreferredSize());

        int margin = Scaler.getPadding(0.06);
        int targetOffset = Scaler.getPadding(0.02);

        if (currentStep.getPosition() != TutorialStep.DialogPosition.AUTO) {
            switch (currentStep.getPosition()) {
                case TOP_LEFT: dialogPanel.setLocation(margin, margin); break;
                case TOP_RIGHT: dialogPanel.setLocation(getWidth() - dialogPanel.getWidth() - margin, margin); break;
                case BOTTOM_LEFT: dialogPanel.setLocation(margin, getHeight() - dialogPanel.getHeight() - margin); break;
                case BOTTOM_RIGHT: dialogPanel.setLocation(getWidth() - dialogPanel.getWidth() - margin, getHeight() - dialogPanel.getHeight() - margin); break;
                case CENTER: dialogPanel.setLocation((getWidth() - dialogPanel.getWidth()) / 2, (getHeight() - dialogPanel.getHeight()) / 2); break;
            }
        }
        else if (targetComponents != null && !targetComponents.isEmpty() && targetComponents.getFirst() != null && targetComponents.getFirst().isShowing()) {
            JComponent primaryTarget = targetComponents.getFirst();
            Point p = SwingUtilities.convertPoint(primaryTarget.getParent(), primaryTarget.getLocation(), this);

            int x = Math.min(p.x, getWidth() - dialogPanel.getWidth() - targetOffset);
            int y = p.y + primaryTarget.getHeight() + targetOffset;

            if (y + dialogPanel.getHeight() > getHeight()) y = p.y - dialogPanel.getHeight() - targetOffset;
            dialogPanel.setLocation(x, Math.max(targetOffset, y));
        }
        else {
            dialogPanel.setLocation((getWidth() - dialogPanel.getWidth()) / 2, (getHeight() - dialogPanel.getHeight()) / 2);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Area dimArea = new Area(new Rectangle(0, 0, getWidth(), getHeight()));

        if (targetComponents != null) {
            for (JComponent target : targetComponents) {
                if (target != null && target.isShowing()) {
                    Point p = SwingUtilities.convertPoint(target.getParent(), target.getLocation(), this);

                    int padding = Scaler.getPadding(0.03);
                    int cutoutArc = Scaler.getPadding(0.05);

                    RoundRectangle2D cutout = new RoundRectangle2D.Double(
                            p.x - padding, p.y - padding,
                            target.getWidth() + (padding * 2),
                            target.getHeight() + (padding * 2),
                            cutoutArc, cutoutArc);

                    dimArea.subtract(new Area(cutout));
                }
            }
        }

        g2.setClip(dimArea);

        if (blurredBackground != null) {
            g2.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), null);
        }

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fill(dimArea);

        g2.dispose();
    }

    @Override
    public void onThemeChange() {
        backgroundColor = Colors.BACKGROUND_WHITE.getColor();
        borderColor = Colors.BUTTON_LIGHT_BLUE.getColor();
        forgroundColor = Colors.BLACK_TEXT.getColor();

        if (titleLabel != null) {
            titleLabel.setForeground(forgroundColor);
        }
        if (textLabel != null) {
            textLabel.setForeground(forgroundColor);
        }

        if (dialogPanel != null) {
            dialogPanel.repaint();
        }

        if (isVisible()) {
            updateBackgroundBlur();
            repaint();
        }
    }
}