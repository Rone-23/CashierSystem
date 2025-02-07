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


            JFrame main_frame = new JFrame("Billa_system");
            main_frame.setSize((int)width,(int)height);
            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Logo
            Path billaLogoPath = Paths.get("Images","Billa_logo.png");
            ImageIcon billaLogo = new ImageIcon(String.valueOf(billaLogoPath));
            main_frame.setIconImage(billaLogo.getImage());

            //initialize database\
            //TODO: Make intializer class containing window creation sql etc..
            SQL_Connect sql = new SQL_Connect();
            try {
                System.out.println(sql.getPrice(1));
                for(String s : sql.getTypes())
                    System.out.println(s);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            MainPanel mainPanel = new MainPanel();
            ArticlesPanel secondPanel = null;
            try {
                secondPanel = new ArticlesPanel(sql.getNames("BEZNE-PECIVO"), sql.getTypes());
//                secondPanel = new ArticlesPanel(sql.getSubTypes("PECIVO"), sql.getTypes());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

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
