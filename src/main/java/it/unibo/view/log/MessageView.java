package it.unibo.view.log;

/**
 * Log message view.
 * 
 * @param playerName name of the player
 * @param message    message to display
 */
public record MessageView(String playerName, String message) {
}
