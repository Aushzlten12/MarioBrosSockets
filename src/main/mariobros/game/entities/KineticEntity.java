package src.main.mariobros.game.entities;

public interface KineticEntity {
    public void setX(double newX);

    public void setY(double newY);

    public void setXVel(double newXVel);

    public void setYVel(double newYVel);

    public double getX();

    public double getXVel();

    public double getY();

    public double getYVel();

    public default double shiftX(final double dX) {
        setX(getX() + dX);
        return getX();
    }

    public default double shiftXVel(final double dXVel) {
        setXVel(getXVel() + dXVel);
        return getXVel();
    }

    public default double shiftY(final double dY) {
        setY(getY() + dY);
        return getY();
    }

    public default double shiftYVel(final double dYVel) {
        setYVel(getYVel() + dYVel);
        return getYVel();
    }

    public default void applyVelocity() {
        shiftX(getXVel());
        shiftY(getYVel());
    }
}
