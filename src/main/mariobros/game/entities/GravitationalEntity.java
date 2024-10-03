package src.main.mariobros.game.entities;

import src.main.mariobros.game.ServerGame.GameSettings;

public interface GravitationalEntity extends KineticEntity {

    public default double getGravitationalForce() {
        return GameSettings.GLOBAL_GRAVITY;
    }

    public default void applyGravity() {
        shiftYVel(getGravitationalForce());
    }
}
