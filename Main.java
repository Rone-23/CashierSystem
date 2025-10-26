import controllers.MainController;
import viewsRework.panels.DuringArticles;
import viewsRework.panels.DuringRegister;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args) throws SQLException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        SwingUtilities.invokeLater(()-> {
            JFrame mainFrame = new JFrame("Cashier system");
            mainFrame.setSize(screenSize.width,screenSize.height);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            mainFrame.setResizable(false);

            //Logo
            Path billaLogoPath = Paths.get("Images","Billa_logo.png");
            ImageIcon billaLogo = new ImageIcon(String.valueOf(billaLogoPath));
            mainFrame.setIconImage(billaLogo.getImage());
            DuringRegister duringRegister = new DuringRegister();
            DuringArticles duringArticles = new DuringArticles();

            mainFrame.add(duringRegister);
            mainFrame.setVisible(true);

            new MainController(duringRegister, duringArticles);
        });
    }
}
