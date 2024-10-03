package src.main.mariobros.game;

import java.util.TreeMap;

import src.main.mariobros.game.ServerGame.Entity;
import src.main.mariobros.game.action.ActionSet;
import src.main.mariobros.game.entities.PlayerEntity;
import src.main.mariobros.net.Client;

public class ClientGame {
    private final int playerId;
    private final Client client;

    private final ActionSet actionSet;

    private TreeMap<Integer, Entity> entities;

    public ClientGame(final Client client, final int clientId) {
        this.client = client;
        playerId = clientId;
        actionSet = new ActionSet();
    }

    public ActionSet getActionSet() {
        return actionSet;
    }

    @Deprecated
    public PlayerEntity getPlayerEntity() {
        return (PlayerEntity) entities.get(playerId);
    }

    public void addEntity(final Entity entity) {
        entities.put(entity.getId(), entity);
    }

    public TreeMap<Integer, Entity> getEntities() {
        return entities;
    }

    public void processEntityList(final TreeMap<Integer, Entity> incomingEntityList) {
        entities = incomingEntityList;
    }

    public void tick() {
        if (!getEntities().containsKey(playerId)) {
            client.disconnect();
        }
    }
}
