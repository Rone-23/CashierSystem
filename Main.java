import assets.Assets;
import assets.Colors;
import controllers.MainController;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;
import views.ArticlesPanel;
import views.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args) throws SQLException {
        Assets as = new Assets();

        SwingUtilities.invokeLater(()-> {


            JFrame main_frame = new JFrame("Billa_system");
            main_frame.setSize((int)as.width,(int)as.height);
            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Logo
            Path billaLogoPath = Paths.get("Images","Billa_logo.png");
            ImageIcon billaLogo = new ImageIcon(String.valueOf(billaLogoPath));
            main_frame.setIconImage(billaLogo.getImage());

            MainPanel mainPanel = new MainPanel();
            ArticlesPanel secondPanel = new ArticlesPanel();

            JLayeredPane childPanel = mainPanel.getMainPanel();
            main_frame.add(childPanel);

            GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
            childPanel.add(secondPanel.getMainPanel(), gbc, JLayeredPane.MODAL_LAYER);
            childPanel.setBorder(null);
            main_frame.setVisible(true);

            new MainController(mainPanel, secondPanel);
        });

    }
}
