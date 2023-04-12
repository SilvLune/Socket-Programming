import java.io.*; 
import java.net.*; 
import java.util.HashMap;

class TextServer {
    public static void main(String argv[]) throws Exception {
    String clientSentence;
    String[] clientInput;
    String respon;
    ServerSocket welcomeSocket = new ServerSocket(6789); 
    System.out.println("SERVER is running ... ");

    // to store user account
    HashMap<String, String> account = new HashMap<String, String>(); 
    // to store messages
    HashMap<String, String> message = new HashMap<String, String>();

    // add user credentials to the account
    account.put("Alice", "1234");
    account.put("Bob", "5678");

    while (true) {
        Socket connectionSocket = welcomeSocket.accept();
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        clientSentence = inFromClient.readLine();
        // split client input by sapce
        /*
         * clientSentence: arg1 arg2 arg3
         * clientInput 1: arg1
         * clientInput 2: arg2
         * clientInput 3: arg3
         */
        clientInput = clientSentence.split(" ");
        System.out.println("FROM CLIENT: " + clientSentence);

        // check client input option
        switch (clientInput[0]) {
            case "0": // login
                if (account.containsKey(clientInput[1]) && account.get(clientInput[1]).equals(clientInput[2])) {
                    respon = "Access Granted\n\n";
                    outToClient.writeBytes(respon);
                } else {
                    respon = "Access Denied - Username/Password Incorrect\n\n";
                    outToClient.writeBytes(respon);
                }
                break;
            case "1": // get user list
                respon = account.keySet().toString() + "\n\n";
                outToClient.writeBytes(respon);
                break;
            case "2": // send message
                // store message
                message.put(clientInput[1], clientInput[2]);
                respon = "Message sent\n\n";
                outToClient.writeBytes(respon);
                break;
            case "3": // get messages
                respon = message.getOrDefault(clientInput[1], "No messages\n\n") + "\n\n";
                outToClient.writeBytes(respon);
                break;
            case "4": // exit
                connectionSocket.close();
                return;
            default:
                respon = "Invalid option\n\n";
                outToClient.writeBytes(respon);
        }
        }
    }
}