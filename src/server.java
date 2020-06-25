import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class server {
    //initializes ArrayList of groups
    public static ArrayList<Group> groups = new ArrayList<>();
    public static ServerSocket serSock = null;


    public static void main(String[] args) {

        //initializes the variables
        boolean portSet = false;
        int port = 8545;

        //asks for what port the server would run on
        System.out.println("On what port should the server run on?");
        System.out.println("Press enter for default! (Port 8545)");
        while (!portSet) { //loop that runs until the port is set and accepted

            //String portString = new Scanner(System.in).nextLine();
            String portString = "";

            if (!portString.equals("")) {
                port = Integer.parseInt(portString);
            }

            try {
                //opens the port for access to server.
                serSock = new ServerSocket(port);
                portSet = true;
            } catch (Exception e) {
                System.out.println("That port isn't allowed, try again!");
            }
        }
        System.out.println("Server running on port " + port);

        /*
         * loop that accepts new users and inserts them in groups
         * Creates the user, sets the username and also prints the logged in users in the group.
         */

        while (true) {
            ServerSocket finalSerSock = server.serSock;
                Socket sock; //initializes the variable sock
                try {
                    sock = finalSerSock.accept(); //accepts connections (clients)
                } catch (IOException e) {
                    System.out.println("Some stupid person killed it");
                    break;
                }
                Thread t = new Thread(() -> {
                    User tempUser;
                    try {
                        tempUser = new User(sock); //creates tempUser for use while creation of Group
                    } catch (IOException e) {
                        System.out.println("Some stupid person killed it");
                        return;
                    }
                    tempUser.out.println("connectGroup?"); //asks what group you want to connect to
                    tempUser.out.flush();
                    String receiveRead; //initializes the variable
                    try {
                        receiveRead = tempUser.in.readLine(); //reads the answer from client
                    } catch (IOException e) {
                        System.out.println("Some stupid person killed it");
                        return;
                    }
                    if (!server.CheckGroups(receiveRead)) { //checks if group exist, else creates it

                        groups.add(new Group(receiveRead)); //creates the group and inserts the groupname
                    }

                    int groupNumber = server.CheckGroupNumber(receiveRead);
                    Group tmpGroup = groups.get(groupNumber);
                    if (server.CheckPlayersConnected() >= 2) {
                        tempUser.out.println("This Group is full, connect to another!");
                        tempUser.out.flush();
                    } else {
                        int userNumber = tmpGroup.addUser(tempUser);
                        tmpGroup.users.get(userNumber).CreateUser(sock, groupNumber);
                    }

                });
                t.start();
            }


        }

        public static int CheckPlayersConnected () {
            return 0;
        }

        /**
         * checks if group exist
         * @param receiveMessage input
         * @return bool
         */
        public static boolean CheckGroups (String receiveMessage){
            for (Group group : groups) {
                if (group.name.equals(receiveMessage)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * check what group number a group have
         * @param input input
         * @return int
         */
        public static int CheckGroupNumber (String input){
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).name.equals(input)) {
                    return i;
                }
            }
            return 0;
        }

    }




