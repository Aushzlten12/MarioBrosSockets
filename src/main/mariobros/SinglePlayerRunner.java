package src.main.mariobros;

import src.main.mariobros.gui.client.ClientMainFrame;
import src.main.mariobros.net.Server;

public class SinglePlayerRunner {
    public static void main(final String[] args) {
        Server.main(args);
        ClientMainFrame.main(args);
    }
}
