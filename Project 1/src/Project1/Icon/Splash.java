package Project1.Icon;

import javax.swing.*;

public class Splash extends JFrame {


    Splash() {
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("Project1/Icon/Oh My God Wow GIF.gif"));
        JLabel label = new JLabel(imageIcon);
        label.setBounds(-200,-200,858,680);
        add(label);

        setLayout(null);
        setLocation(300,80);
        setSize(480, 347);
        setVisible(true);
        try {
            Thread.sleep(5000);
            new Project1.Icon.login().setVisible(true);
            setVisible(false);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Splash();
    }

}
