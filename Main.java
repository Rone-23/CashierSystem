import controllers.MainController;
import controllers.panels.ViewManager;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args) throws SQLException {
        SwingUtilities.invokeLater(()-> {

            ViewManager.getInstance().getMainFrame().setTitle("Cashier system");

            //Logo
            Path billaLogoPath = Paths.get("Images","Icon.png");
            ImageIcon billaLogo = new ImageIcon(String.valueOf(billaLogoPath));
            ViewManager.getInstance().getMainFrame().setIconImage(billaLogo.getImage());

            try {
                new MainController();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
