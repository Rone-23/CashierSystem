import Assets.Assets;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args) throws SQLException {
        Assets as = new Assets();
        SwingUtilities.invokeLater(()-> {

            JFrame main_frame = new JFrame("Billa_system");
            //Logo
            Path billaLogoPath = Paths.get("Images","Billa_logo.png");
            ImageIcon billaLogo = new ImageIcon(String.valueOf(billaLogoPath));
            main_frame.setIconImage(billaLogo.getImage());

            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main_frame.setContentPane(new Main_Window().getLayeredPane());
            main_frame.setSize(as.width,as.height);
            main_frame.setVisible(true);
        });

    }
}
