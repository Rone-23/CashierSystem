import assets.Assets;
import controllers.MainController;
import utility.GridBagConstraintsBuilder;
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

            JPanel parentPanel = new JPanel(new GridBagLayout());
            parentPanel.setBorder(null);
            MainPanel mainPanel = new MainPanel();
            JPanel childPanel = mainPanel.getMainPanel();
            childPanel.setBorder(null);

            GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
            gbc.weighty=1;
            gbc.weightx=1;

            parentPanel.add(childPanel,gbc);
            main_frame.setContentPane(parentPanel);
            main_frame.setVisible(true);

            //Logo
            Path billaLogoPath = Paths.get("Images","Billa_logo.png");
            ImageIcon billaLogo = new ImageIcon(String.valueOf(billaLogoPath));
            main_frame.setIconImage(billaLogo.getImage());
            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            main_frame.setContentPane(new Main_Window().getLayeredPane());
            new MainController(mainPanel.getLeftPanel());
        });

    }
}
