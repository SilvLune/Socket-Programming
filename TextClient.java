import java.io.*;
import java.net.*;

class TextClient {
    public static void main(String argv[]) throws Exception {
    String input;
    String response;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    Socket clientSocket = new Socket("127.0.0.1", 6789);

    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    boolean isLoggedIn = false; 

    while (true) {
        System.out.println("\n0. Connect to the server");
        System.out.println("1. Get the user list");
        System.out.println("2. Send a message");
        System.out.println("3. Get my messages");
        System.out.println("4. Exit");
        System.out.println("Enter a number:");

        input = inFromUser.readLine();

        switch (input) { // check user input option
            case "0": // connect to server
                System.out.print("Enter username: ");
                String username = inFromUser.readLine();
                System.out.print("Enter password: ");
                String password = inFromUser.readLine();
            
            // send credentials to server
            outToServer.writeBytes("0 " + username + " " + password + '\n');
            outToServer.flush();

            response = inFromServer.readLine();
            // server verification
            if (response.equals("Access Granted")) {
                System.out.println(response);
                isLoggedIn = true;
            } else {
                System.out.println(response);
            }
            break;

        case "1": // get user list
            if (isLoggedIn) {
                outToServer.writeBytes("1 \n");
                
                // //get response from server
                // response = null;
                // while (response == null || response.isEmpty()){
                //     response = inFromServer.readLine();
                // }

                System.out.println("User list: " + response);
            } else {
                System.out.println("Please connect to the server.");
            }
            break;

        case "2": // send a message
            if (isLoggedIn) {
                System.out.print("Enter recipient username: ");
                String recipient = inFromUser.readLine();
                System.out.print("Enter message: ");
                String message = inFromUser.readLine();

                outToServer.writeBytes("2 " + recipient + " " + message + '\n');

                response = null;
                while (response == null || response.isEmpty()){
                    response = inFromServer.readLine();
                }

                System.out.println(response);
            } else {
                System.out.println("Please connect to the server.");
            }
            break;

        case "3": // get my messages
            if (isLoggedIn) {
                System.out.print("Enter username: ");
                String username2 = inFromUser.readLine();
                outToServer.writeBytes("3 " + username2 + '\n');

                response = null;
                while (response == null || response.isEmpty()){
                    response = inFromServer.readLine();
                }

                System.out.println("My messages: " + response);
            } else {
                System.out.println("Please connect to the server.");
            }
            break;

        case "4": // exit
            if (isLoggedIn) {
                isLoggedIn = false;
                outToServer.writeBytes("4\n");
                System.out.println("Exited");
            }
            clientSocket.close();
            break;

        default:
            System.out.println("Invalid input.");
            break;
        }
        if(input.equals("4") )
            break;
        }
    }
}
