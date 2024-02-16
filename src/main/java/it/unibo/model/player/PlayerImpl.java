package it.unibo.model.player;


/**
 * Implementation of Player.
 */
public final class PlayerImpl implements Player {
    private int victoryPoints;
    private final String name;

    /**
     * Creates a Player.
     * 
     * @param name the name of the player
     */
    public PlayerImpl(final String name) {
        this.name = name;
        victoryPoints = 0;
    }

    @Override
    public void incrementVictoryPoints(final int amount) {
        checkPointsAmount(amount);
        sumPoints(amount);
    }

    @Override
    public void decrementVictoryPoints(final int amount) {
        checkPointsAmount(amount);
        sumPoints(-amount);
    }

    @Override
    public int getVictoryPoints() {
        return this.victoryPoints;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        return this.name.equals(((Player) obj).getName());
    }

    private void sumPoints(final int amount) {
        victoryPoints += amount;
    }

    private void checkPointsAmount(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non negative");
        }
    }
}
