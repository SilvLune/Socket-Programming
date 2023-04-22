import java.io.*; 
import java.net.*; 

class Client {
   public static void main(String argv[]) throws Exception 
   {
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      Socket clientSocket = new Socket("127.0.0.1", 8000); 
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
      

      while (true) {
         System.out.println("\nYou are connected to the Server.");
         System.out.println("\n0. Log in to the server\n1. Get the user list\n2. Send a message\n3. Get my messages\n4. Exit");
         System.out.print("\nEnter your option: ");
         String option = inFromUser.readLine();
         outToServer.writeBytes(option + "\n");
         

         switch (option) {
               case "0":
                  System.out.print("Enter your username: ");
                  String username = inFromUser.readLine();
                  System.out.print("Enter your password: ");
                  String password = inFromUser.readLine();

                  outToServer.writeBytes(username + "\n");
                  outToServer.writeBytes(password + "\n");
                  String response = inFromServer.readLine();
                  System.out.println(response);
                  break;
               case "1":
                  System.out.println("User List:");
                  String user = inFromServer.readLine();
                  
                  System.out.println(user);
                  break;
               case "2":
                  System.out.print("Enter the receiver's username: ");
                  String receiver = inFromUser.readLine();
                  System.out.print("Enter your message: ");
                  String message = inFromUser.readLine();

                  outToServer.writeBytes(receiver + "\n");
                  outToServer.writeBytes(message + "\n");
                  break;
               case "3":
                  System.out.println("Your messages:");
                  // ask user to input thier user name
                  // send the user name to the server
                  String answer = inFromServer.readLine();
                  System.out.println(answer);
                  break;
               case "4":
                  System.out.print("log out");
                  clientSocket.close();
         }
         if (option.equals("4")) {
            break;
         }
      }
   } 
}
