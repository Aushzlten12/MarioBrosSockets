package src.main.mariobros.game.entities;

import src.main.mariobros.game.ServerGame;
import src.main.mariobros.game.ServerGame.Entity;

public class PlatformEntity extends Entity {
    private static final long serialVersionUID = 423302831329368937L;

    public PlatformEntity(final ServerGame game, final double width, final double height, final double x,
            final double y) {
        super(game, width, height, x, y);
    }

    @Override
    public void tick() {
    }

    @Override
    public void handleCollision(final Entity otherEntity) {
    }
}
