package src.main.mariobros.gui.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.JPanel;

import src.main.mariobros.game.ClientGame;
import src.main.mariobros.game.ServerGame.Entity;
import src.main.mariobros.game.entities.CoinEntity;
import src.main.mariobros.game.entities.EnemyEntity;
import src.main.mariobros.game.entities.PlatformEntity;
import src.main.mariobros.game.entities.PlayerEntity;
import src.main.mariobros.gui.client.gfx.Texture;

public class ClientGamePanel extends JPanel {
    private static final Logger logger = Logger.getLogger(ClientGamePanel.class.getName());

    private final double[][] gameViewRanges = new double[][] { { 0,  36}, { 0, 40 } }; // {xRange, yRange}
    private final ClientGame game;
    private final Sprites sprites = new Sprites();


    private static class Sprites {
        private final Texture tex = new Texture();
        private BufferedImage[] spriteL = tex.getMarioL();
        private BufferedImage[] spriteS = tex.getMarioS();
        private BufferedImage[] spriteBlocks = tex.getPipe1();

        private final BufferedImage marioL,marioS,marioInvL, marioInvS, blocks,coin,enemy;
        private Sprites() {
            marioL = spriteL[0];
            marioInvL = getReflectedImage(marioL);

            marioS = spriteS[0];
            marioInvS = getReflectedImage(marioS);

            blocks = spriteBlocks[0];
            coin = tex.getCoin_sheet();
            enemy = tex.getEnemy_sheet();
        }

        private static BufferedImage getReflectedImage(final BufferedImage originalImage) {
            // flip right player sprite to get left player sprite
            final AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
            transform.translate(-originalImage.getWidth(), 0);
            final AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            return op.filter(originalImage, null);
        }
    }

    public ClientGamePanel(final ClientGame game) {
        this.game = game;

        addKeyListener(new ClientInputHandler(game));

        setFocusable(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(720, 800);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public void paint(final Graphics g) {
        // System.out.println("Starting to paint.");
        final Graphics2D graphics2d = (Graphics2D) g;
        
        for (final Entity entity : game.getEntities().values()) {
            // System.out.println("Entity " + entity.getId() +
            // ": [x=" + entity.getX() + ", y=" + entity.getY() + ", w="
            // + entity.getWidth() + ", h=" + entity.getHeight() + "]");

            final int x1 = remapXCoords(entity.getX()), y1 = remapYCoords(entity.getY()),
                    x2 = x1 + rescaleWidth(entity.getWidth()), y2 = y1 + rescaleHeight(entity.getHeight());

            final int minX = Math.min(x1, x2), minY = Math.min(y1, y2), maxX = Math.max(x1, x2),
                    maxY = Math.max(y1, y2);
            final int width = maxX - minX, height = maxY - minY;

            BufferedImage sprite;

            if (entity instanceof final PlayerEntity playerEntity) {
                    if(playerEntity.getHearts()==2){
                        sprite = switch (playerEntity.getHorDirection()) {
                            case LEFT -> sprites.marioInvL;
                            case RIGHT -> sprites.marioL;
                        };
                    } else {
                        sprite = switch (playerEntity.getHorDirection()) {
                            case LEFT -> sprites.marioInvS;
                            case RIGHT -> sprites.marioS;
                        };
                    }
            } else if (entity instanceof final PlatformEntity platformEntity) {
                sprite = sprites.blocks;
            } else if (entity instanceof final CoinEntity coinEntity) {
                sprite = sprites.coin;
            } else if (entity instanceof final EnemyEntity enemyEntity) {
                sprite = sprites.enemy;
            } else {
                // create a purple square
                sprite = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
                final Graphics2D spriteGraphics2d = sprite.createGraphics();
                spriteGraphics2d.setColor(Color.MAGENTA);
                spriteGraphics2d.drawRect(0, 0, 1, 1);
                spriteGraphics2d.dispose();

                logger.warning("Entity of unknown type");
            }
            graphics2d.drawImage(sprite, minX, minY, width, height, null);
        }
    }


    private int remapXCoords(final double gameX) {
        return remap(gameX, gameViewRanges[0][0], gameViewRanges[0][1], 0, getWidth());
    }

    private int remapYCoords(final double gameY) {
        return remap(gameY, gameViewRanges[1][0], gameViewRanges[1][1], getHeight(), 0);
    }

    private int remap(final double initialPoint, final double initialBottom, final double initialTop,
            final double newBottom, final double newTop) {

        // ratio of [length between point and bottom]:[total initial length]
        final double ratio = (initialPoint - initialBottom) / (initialTop - initialBottom);

        // distance between the new point and new bottom
        final double newDist = (newTop - newBottom) * ratio;

        final double newPoint = newBottom + newDist;
        return (int) Math.round(newPoint); // round because if cast to int across 0, it is not good
    }

    private int rescaleWidth(final double gameWidth) {
        return rescale(gameWidth, gameViewRanges[0][1] - gameViewRanges[0][0], getWidth());
    }

    private int rescaleHeight(final double gameHeight) {
        return rescale(gameHeight, gameViewRanges[1][1] - gameViewRanges[1][0], -getHeight());
    }

    private int rescale(final double initialLength, final double initialRange, final double newRange) {

        // ratio of [initial length]:[initial range]
        final double ratio = initialLength / initialRange;

        final double newLength = newRange * ratio;
        return (int) Math.round(newLength);
    }
}