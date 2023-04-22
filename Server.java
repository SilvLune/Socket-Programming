import java.io.*; 
import java.net.*; 
class Server {
   public static void main(String argv[]) throws Exception 
   {
      ServerSocket welcomeSocket = new ServerSocket(8000); 
      System.out.println("SERVER is running ... ");

      while(true) {
         Socket connectionSocket = welcomeSocket.accept();
         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
         DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
         String option;
         
         while (true) {
            System.out.println("waiting...");
            option = inFromClient.readLine();
            switch (option) {
               case "0":
                  System.out.println("The option is " + option);
                  String username = inFromClient.readLine();
                  System.out.println("The username is " + username);
                  String password = inFromClient.readLine();
                  System.out.println("The password is " + password);

                  outToClient.writeBytes("Access Granted\n");
                  break;
               case "1":
                  System.out.println("The option is " + option);
                  // get the user name from hashmap
                  outToClient.writeBytes("Alice Bob\n");
                  System.out.println("Alice Bob");      
                  break;
               case "2":
                  System.out.println("The option is " + option);
                  String receiver = inFromClient.readLine();
                  System.out.println("The receiver is " + receiver);
                  String message = inFromClient.readLine();
                  System.out.println("The message is " + message);     
                  // store the receiver and message to the hashmap
                  break;
               case "3":
                  System.out.println("The option is " + option);
                  // check if the username is in the hashmap
                  // inti String message
                  // if so then get the value(message) of the key(username) set this to message
                  // if not sent "No message\n" set this to message
                  // send the message to the client
                  outToClient.writeBytes("No message\n");      
                  break;
               case "4":
                  connectionSocket.close();
                  break;
            }
            if (option.equals("4")) {
               break;
            }
         }
      }
   }
}
