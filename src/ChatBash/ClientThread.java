package ChatBash;

import java.util.LinkedList;

public class ClientThread implements Runnable {

    private MySocket mysocket;
    private String user_name;
    private ChatClient c;
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    public ClientThread(ChatClient c) {
        this.c = c;
        this.mysocket = new MySocket(c.getHost(), c.getPortNumber());
        this.messagesToSend = new LinkedList<String>();
    }

    public void addNextMessage(String message) {
        synchronized (messagesToSend) {
            this.hasMessages = true;
            this.messagesToSend.push(message);
        }
    }

    @Override
    public void run() {
        /* TO KNOW WHICH PORT ARE WE CONNECTED TO
         System.out.println("Local Port : " + mysocket.getLocalPort());
         System.out.println("Servidor = " + mysocket.getRemoteSocketAddress() + ":" + mysocket.getPort());
         */
        try {
            mysocket.flush();
            while (mysocket != null && !mysocket.isClosed()) {
                if (mysocket.ready()) {
                    String line = this.mysocket.readLine();
                    if (line != null) {
                        String[] message = line.split(":", 2);

                        switch (message[0]) {
                            case "ACCEPT":
                                c.setState("JOIN");
                                break;
                            case "JOIN":
                                this.user_name = message[1];
                                c.setName(message[1]);
                                c.setState("MESSAGE");
                                break;
                            case "QUIT":
                                mysocket.println("QUIT:" + user_name);
                                System.out.println("GoodBye");
                                return;
                            case "MESSAGE":
                                System.out.print(c.getColour(message[1].split(" ->")[0]));
                                System.out.println(message[1]);
                                System.out.print(Key.WHITE);
                                break;
                            case "USERS":
                                c.updateUsers(message[1]);
                                break;
                        }
                    }
                }
                if (hasMessages) {
                    String nextSend = "";
                    synchronized (messagesToSend) {
                        nextSend = messagesToSend.pop();
                        if (nextSend.startsWith("QUIT:")) {
                            c.setState("QUIT");
                            break;
                        }
                        hasMessages = !messagesToSend.isEmpty();
                    }

                    if (c.getState() == "ACCEPT" || c.getState() == "JOIN") {
                        mysocket.println(nextSend);
                    } else {
                        System.out.print(Key.ERASE_LINE_UP);
                        mysocket.println(this.user_name + " -> " + nextSend);
                    }
                    mysocket.flush();

                }
            }
        } catch (Exception ex) {
            System.out.println("Server not ready. \nMake sure server is listening before start connection!");
            System.exit(0);
        }
    }
}
