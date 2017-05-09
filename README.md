# ChatSystem

This project is a P2P Chat System for communicating in a local network. It enables you to see every other persons connected to the Chat System and communicate with them.
The project is implemented in Java 8.

# Requirements 

Java 8 is required to compile this project.

In order to use the Chat System you can either :
- Compile manually all the sources, contained in the "src" folder. The "lib" folder contains every library used and needed to compile. The "resources" folder contains all the files needed to compile. You then need to execute the "Launch" class.
- Download and execute ChatSystem.jar.

# Functionalities 

When executing the Chat System, the Login view is opened. It allows you to :

- Connect with your credentials. If it is your first time using the app you will need to "Sign up" using the button.
Once you are signed up, you can "Sign in" to connect to the Chat System.

- You can optionally choose a port, this port will be used by other users to communication with you.

Once connected to the Chat System, you see the Communication View. You can now : 
- Modify your "status", it is a complement of information about yourself, that other connected persons can see. 
- Select a user in the "Connected users" list. 
- Communicate with this selected user by sending him messages.
- Deconnect from the Chat System by clicking on "Disconnect" button.
- Change between different users. Your discussions with each users are saved and you can simply change between user by just clicking on them.


# Network informations

The application uses Multicast messages in order to know every connected users. 

Each Chat System instance :
- Send information about the user (Login, IP Adress, Port to communicate, Status, State) every 3 seconds to a Multicast channel. 
- Listen to a Multicast channel and retrieve user informations in order to add them to the "Connected users" list.
- Send a disconnect message to the Multicast channel each time you click on "Disconnect" button.

# Tests

