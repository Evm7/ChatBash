package ChatBash;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatClient {

    private static String host;
    private static int portNumber = 5000;
    private String username;
    private String state;
    private List<String> users;

    public ChatClient() {
        this.state = "ACCEPT";
        this.users = new ArrayList<>();
    }

    private void startClient() {
        try {
            Scanner scan = new Scanner(System.in);
            ClientThread clientThread = new ClientThread(this);
            Thread serverAccessThread = new Thread(clientThread);
            serverAccessThread.start();
            System.out.print(Key.CLEAR_KEY);
            while (serverAccessThread.isAlive()) {
                switch (state) {
                    case "ACCEPT":
                        clientThread.addNextMessage(this.getName());
                        this.state = "JOIN";
                        break;
                    case "JOIN":
                        break;
                    case "MESSAGE":
                        if (scan.hasNextLine()) {
                            clientThread.addNextMessage(scan.nextLine());
                        }
                        break;
                }

            }

        } catch (Exception ex) {
            System.out.println("Error while starting client: " + ex);
        }
    }

    public void updateUsers(String users) {
        System.out.println("Users connected: " + users);
        users = users.substring(1, users.length()-1);
        for (String name : users.split(", ")) {
            if (!this.users.contains(name)) {
                this.users.add(name);
            }
        }
    }

    public String getColour(String name) {
        if(name.equals("Admin")){
            return(Key.WHITE);
        }
        return (Key.COLOURS[this.users.indexOf(name)% (Key.COLOURS.length-1)]);
    }

    private String getName() {
        String readName = "";
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input username: ");
        while (readName == null || readName.trim().equals("")) {
            // null, empty, whitespace(s) not allowed.
            readName = scan.nextLine();
            if (readName.trim().equals("") || this.users.contains(readName)) {
                System.out.println("Invalid. Please enter username again:");
                readName="";
            }
        }
        return readName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getHost() {
        return this.host;
    }

    public int getPortNumber() {
        return this.portNumber;
    }

    public String getState() {
        return this.state;
    }

    public static void main(String[] args) {
        host = "localhost";
        portNumber = 5000;
        if (args.length <= 2 && args.length > 0) {
            if (!args[0].isEmpty()) {
                host = args[0];
                if (!args[1].isEmpty()) {
                    portNumber = Integer.parseInt(args[1]);
                }
            }
        }

        ChatClient client = new ChatClient();
        client.startClient();
    }
}
