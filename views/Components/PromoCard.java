package views.Components;

import assets.Colors;
import assets.Scaler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class PromoCard extends JPanel {

    public PromoCard(String itemName, int originalPriceCents, int discountPercent, String imagePath, boolean requiresCard) {
        setOpaque(false);
        setLayout(new BorderLayout(0, Scaler.getPadding(0.01)));

        int pad = Scaler.getPadding(0.015);
        setBorder(new EmptyBorder(pad, pad, pad, pad));

        setPreferredSize(Scaler.getDimension(0.18, 0.26));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel badgeLabel = new JLabel("-" + discountPercent + "%");
        badgeLabel.setFont(Scaler.getFont(0.025, Font.BOLD));
        badgeLabel.setForeground(Colors.DANGER_RED.getColor());

        String titleText = requiresCard ? "★ " + itemName : itemName;
        JLabel nameLabel = new JLabel("<html><div style='text-align: center; width: 100%;'>" + titleText + "</div></html>");
        nameLabel.setFont(Scaler.getFont(0.016, Font.BOLD));

        if (requiresCard) {
            nameLabel.setForeground(Colors.YELLOW.getColor());
        } else {
            nameLabel.setForeground(Colors.BLACK_TEXT.getColor());
        }

        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(badgeLabel, BorderLayout.EAST);
        topPanel.add(nameLabel, BorderLayout.CENTER);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        boolean imageLoaded = false;

        if (imagePath != null && !imagePath.trim().isEmpty()) {
            String cleanPath = imagePath.startsWith("/") ? imagePath.substring(1) : imagePath;

            File imgFile = new File(cleanPath);
            if (imgFile.exists()) {
                ImageIcon originalIcon = new ImageIcon(cleanPath);

                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();
                int maxBoxSize = Scaler.getDimension(0.06, 0).width;

                int newWidth, newHeight;
                if (originalWidth > originalHeight) {
                    newWidth = maxBoxSize;
                    newHeight = (int) (((double) maxBoxSize / originalWidth) * originalHeight);
                } else {
                    newHeight = maxBoxSize;
                    newWidth = (int) (((double) maxBoxSize / originalHeight) * originalWidth);
                }

                Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
                imageLoaded = true;
            }
        }

        if (!imageLoaded) {
            imageLabel.setText("(Bez obrázka)");
            imageLabel.setFont(Scaler.getFont(0.015, Font.ITALIC));
            imageLabel.setForeground(Colors.BLACK_TEXT_LIGHT.getColor());
        }

        double original = originalPriceCents / 100.0;
        double discounted = original * (100 - discountPercent) / 100.0;

        JLabel priceLabel = new JLabel(String.format("<html><center><strike>%.2f €</strike><br><b style='font-size: 1.4em; color: green;'>%.2f €</b></center></html>", original, discounted));
        priceLabel.setFont(Scaler.getFont(0.016, Font.PLAIN));
        priceLabel.setForeground(Colors.BLACK_TEXT_LIGHT.getColor());
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(priceLabel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Colors.BACKGROUND_WHITE.getColor());

        int arc = Scaler.getPadding(0.02);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
        g2.dispose();
    }
}