package Project1.Icon;

import javax.swing.JOptionPane;

public class UserSession {

    public static boolean isLoggedIn = false;
    public static String currentUserID = null;
    public static String currentUserName = null;


    public static void logout() {
        isLoggedIn = false;
        currentUserID = null;
        currentUserName = null;
        System.out.println("User logged out successfully.");
    }


    public static void main(String[] args) {

        System.out.println("UserSession class is ready.");

        if (!isLoggedIn) {
            System.out.println("Status: No user is currently logged in.");
            JOptionPane.showMessageDialog(null, "UserSession is active but no one is logged in.\n" +
                    "Please run AuthenticationLogin.java to start the system.");
        }
    }
}