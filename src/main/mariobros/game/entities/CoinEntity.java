package src.main.mariobros.game.entities;

import src.main.mariobros.game.ServerGame;

public class CoinEntity extends ServerGame.Entity {
    private static final long serialVersionUID = 423302831329368937L;

    public CoinEntity(final ServerGame game, final double width, final double height, final double x,
                      final double y) {
        super(game,width,height,x,y);
    }

    @Override
    public void tick() {
    }

    @Override
    public void handleCollision(final ServerGame.Entity otherEntity) {
        if(otherEntity instanceof PlatformEntity) {
            delete();
        }
    }

    public void delete() {
        getGame().removeEntity(getId());
    }
}
