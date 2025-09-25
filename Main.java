import controllers.MainController;
import services.SQL_Connect;
import utility.GridBagConstraintsBuilder;
import views.ArticlesPanel;
import views.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args) throws SQLException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = (screenSize.height*4/3)-(screenSize.height*4/300);
        double height = (screenSize.height)-(screenSize.height*4/300);

        SwingUtilities.invokeLater(()-> {
            JFrame mainFrame = new JFrame("Cashier system");
            mainFrame.setSize((int)width,(int)height);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setResizable(false);
            //Logo
            Path billaLogoPath = Paths.get("Images","Billa_logo.png");
            ImageIcon billaLogo = new ImageIcon(String.valueOf(billaLogoPath));
            mainFrame.setIconImage(billaLogo.getImage());
            MainPanel mainPanel = new MainPanel();
            ArticlesPanel secondPanel = null;
            try {
                secondPanel = new ArticlesPanel(SQL_Connect.getInstance().getNames("PECIVO"), SQL_Connect.getInstance().getTypes());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            JLayeredPane childPanel = mainPanel.getMainPanel();
            mainFrame.add(childPanel);

            GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
            childPanel.add(secondPanel.getMainPanel(), gbc, JLayeredPane.MODAL_LAYER);
            childPanel.setBorder(null);
            mainFrame.setVisible(true);

            new MainController(mainPanel, secondPanel);
        });
    }
}
