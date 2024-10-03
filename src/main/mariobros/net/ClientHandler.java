package src.main.mariobros.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.TreeMap;

import src.main.mariobros.game.ServerGame.Entity;
import src.main.mariobros.net.packets.ClientPacket;

public class ClientHandler implements Runnable {
    private boolean isRunning;
    private final int entityId;
    private final Server server;
    private final Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public int getEntityId() {
        return entityId;
    }

    public ClientHandler(final Server server, final Socket socket, final int id) {
        this.server = server;
        this.socket = socket;
        this.entityId = id;

        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }

        initialClientCommunication();
    }

    private void initialClientCommunication() {
        try {
            outputStream.writeInt(entityId);
            server.sendUpdates(this);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRunning = true;
        startRecieveMessageLoop();
    }

    // client to server
    private void startRecieveMessageLoop() {
        while (isRunning) {
            try {
                final ClientPacket packet = (ClientPacket) inputStream.readObject();
                server.processPacket(this,packet);
            } catch (final SocketException e){
                System.out.println("Connection reset by client...");
                disconnect();
            }catch (final IOException e){
                e.printStackTrace();
                disconnect();
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // server to client
    public void sendUpdate(final TreeMap<Integer, Entity> update) {
        if (!isRunning) {
            return;
        }

        try {
            outputStream.writeObject(update);
            outputStream.reset();
        } catch (final IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public void disconnect() {
        System.out.println("A client has disconnected.");

        isRunning = false;

        // close everything
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
