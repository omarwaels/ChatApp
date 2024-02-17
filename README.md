# Void Chat App

Welcome to Void Chat App, a Java-based chat application that allows users to register, create accounts, and communicate with friends via private and group chats. This document provides an overview of the features, technologies used, and instructions for getting started with the application.

## Features

- **User Registration and Authentication**: Users can register and create accounts using their phone numbers. They can then log in using their registered phone number.
- **Add Friends**: Users can add friends via their phone numbers, and invitations will be sent through the app and email.
- **Remove Friends**: Users can remove friends from their contact list.
- **Status and Mode**: Users can set their status and mode (available, busy, away), which reflects on their profile for their friends.
- **Notifications**: Users receive notifications for friend's online/offline status changes, friend requests, group invitations, and server announcements.
- **Group Chat**: Users can create groups consisting of their friends and engage in group chats.
- **Leave Group**: Users can leave groups that they have joined.
- **Customizable Message Style**: Users can customize their message style, including font size, font color, and message appearance.
- **Offline Messaging**: Users receive all messages sent to them while they were offline and can view old chats with friends.
- **File Transfer**: Ability to transfer files (text, sound, short videos, long movies, etc.) to other clients from the contact list.
- **Secure Authentication**: Users can sign out from the application, and the application forgets the password for the user while retaining the user ID for password-only sign-in.
- **Persistent User Sessions**: Users can exit the application without being logged out, allowing them to open the application directly without signing in again.

## Technologies Used

- **Java**: Core programming language.
- **JavaFX**: For building the graphical user interface.
- **RMI (Remote Method Invocation)**: For communication between the client and server.
- **MVC (Model-View-Controller)**: For the application's architecture.
- **Maven**: Dependency management.
- **JDBC (Java Database Connectivity)**: For connecting to the MySQL database.
- **MySQL**: A database management system for storing user data and messages.

## Getting Started

1. **Clone the Repository**: Clone the Void Chat App repository from [GitHub](https://github.com/Mohamed-Adel2/VoidChatApp).
   
    ```
    git clone https://github.com/Mohamed-Adel2/VoidChatApp.git
    ```

2. **Set Up MySQL Database**: Set up a MySQL database using the DB_Design.sql file and configure the JDBC connection in the application accordingly.

3. **Build and Run**: Use Maven to build the application and download the dependency, then run the server and the client.

4. **Register and Log In**: Register with your phone number and log in to use the application.

5. **Explore Features**: Explore the various features of the application, such as adding friends, removing friends, creating groups, leaving groups, sending messages, and customizing message styles.

6. **Enjoy Communication**: Enjoy seamless communication with your friends using the Void Chat App!

## Contributors

- [Mohamed Adel](https://github.com/Mohamed-Adel2)
- [Omar Wael](https://github.com/omarwaels)
- [Omar Ashraf](https://github.com/Omar-Ashraf9)


## Contributing

Contributions to the Void Chat App are welcome! Feel free to submit a pull request or open an issue on GitHub if you have any ideas for improvements or new features.

---

Feel free to reach out if you have any questions or need further assistance!
