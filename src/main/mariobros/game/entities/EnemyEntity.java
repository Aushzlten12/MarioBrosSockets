package src.main.mariobros.game.entities;

import src.main.mariobros.game.ServerGame;
import src.main.mariobros.game.action.Action;

public class EnemyEntity extends ServerGame.Entity implements HorDirectionedEntity, GravitationalEntity {
    private static final long serialVersionUID = 423302831329368937L;
    private HorDirection horDirection;
    private double speed;
    public EnemyEntity(final ServerGame game, final double width, final double height, double x,
                       double y, final HorDirection horDirection,final double speed) {
        super(game, width, height, x, y);
        this.horDirection = horDirection;
        this.speed = speed;
    }

    @Override
    public void tick() {
        switch (horDirection) {
            case LEFT: {
                shiftX(-speed);
                break;
            }
            case RIGHT: {
                shiftX(speed);
                break;
            }
        }
        applyGravity();
        applyVelocity();
    }

    @Override
    public void handleCollision(final ServerGame.Entity otherEntity) {
        if (otherEntity instanceof PlatformEntity) {
            final Vector2D collisionNormal = getCollisionNormal(otherEntity);

            if (collisionNormal.getX() > 0) {
                // set right of this to the left of other
                this.setX(otherEntity.getX() - this.getWidth());
                this.setXVel(0);
            } else if (collisionNormal.getX() < 0) {
                // set left of this to the right of other
                this.setX(otherEntity.getX() + otherEntity.getWidth());
                this.setXVel(0);
            }

            if (collisionNormal.getY() > 0) {
                // set top of this to the bottom of other
                this.setY(otherEntity.getY() - this.getHeight());
                this.setYVel(0);
            } else if (collisionNormal.getY() < 0) {
                // set bottom of this to the top of other
                this.setY(otherEntity.getY() + otherEntity.getHeight());
                this.setYVel(0);
            }
        }
    }

    @Override
    public HorDirection getHorDirection() {
        return horDirection;
    }

    @Override
    public void setHorDirection(HorDirection horDirection) {
        this.horDirection = horDirection;
    }

    @Override
    public void setXVel(double newXVel) {
        speed = newXVel;
    }

    @Override
    public void setYVel(double newYVel) {

    }

    @Override
    public double getXVel() {
        return speed;
    }

    @Override
    public double getYVel() {
        return 0;
    }
}
