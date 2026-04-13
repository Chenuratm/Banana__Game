package com.banana.game.session;
public class SessionManager {

    // Stores current logged-in username
    private static String currentUser;

    /*
     * Login method
     * Sets current user
     */
    public static void login(String username) {
        currentUser = username;
    }

    /*
     * Get current logged-in user
     */
    public static String getCurrentUser() {
        return currentUser;
    }

    /*
     * Logout method
     * Clears current user
     */
    public static void logout() {
        currentUser = null;
    }

    /*
     * Check if user is logged in
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}