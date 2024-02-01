package iti.jets.app.client.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewsFactory {
    private FXMLLoader loginLoader;
    private FXMLLoader signUpLoader;
    private FXMLLoader userSettingsLoader;
    private FXMLLoader chatLoader;
    private FXMLLoader ConnectionGroupItemController;

    private Parent loginRoot;

    private Parent signUpRoot;

    private Parent userSettingsRoot;

    private Parent chatRoot;

    private Scene loginScene;
    private Scene signUpScene;

    private Scene userSettingsScene;

    private Scene chatScene;
    private static ViewsFactory viewsFactory;

    private ViewsFactory() {
        loginLoader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/sign-in.fxml"));
        signUpLoader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/sign-up.fxml"));
        userSettingsLoader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/user-settings.fxml"));
        chatLoader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/chat-screen.fxml"));
        ConnectionGroupItemController = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/connection-Group-item.fxml"));
    }

    public static ViewsFactory getViewsFactory() {
        if (viewsFactory == null)
            viewsFactory = new ViewsFactory();
        return viewsFactory;
    }

    public FXMLLoader getLoginLoader() {
        return loginLoader;
    }

    public FXMLLoader getSignUpLoader() {
        return signUpLoader;
    }

    public FXMLLoader getUserSettingsLoader() {
        return userSettingsLoader;
    }

    public FXMLLoader getChatLoader() {
        return chatLoader;
    }

    public FXMLLoader getMessageSentLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/message-sent.fxml"));
        return fxmlLoader;
    }

    public FXMLLoader getMessageReceivedLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/message-receive.fxml"));
        return fxmlLoader;
    }

    public FXMLLoader getConnectionLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/connection-item.fxml"));
        return fxmlLoader;
    }

    public FXMLLoader getConnectionGroupItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/connection-Group-item.fxml"));
        return fxmlLoader;
    }

    public Parent getLoginRoot() {
        if (loginRoot == null) {
            try {
                loginRoot = loginLoader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loginRoot;
    }

    public Parent getSignUpRoot() {
        if (signUpRoot == null) {
            try {
                signUpRoot = signUpLoader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return signUpRoot;
    }

    public Parent getUserSettingsRoot() {
        if (userSettingsRoot == null) {
            try {
                userSettingsRoot = userSettingsLoader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userSettingsRoot;
    }

    public Parent getChatRoot() {
        if (chatRoot == null) {
            try {
                chatRoot = chatLoader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return chatRoot;
    }

    public Scene getLoginScene() {
        if (loginScene == null) {
            loginScene = new Scene(getLoginRoot());
        }
        return loginScene;
    }

    public Scene getSignUpScene() {
        if (signUpScene == null) {
            signUpScene = new Scene(getSignUpRoot());
        }
        return signUpScene;
    }

    public Scene getUserSettingsScene() {
        if (userSettingsScene == null) {
            userSettingsScene = new Scene(getUserSettingsRoot());
        }
        return userSettingsScene;
    }

    public Scene getChatScene() {
        if (chatScene == null) {
            chatScene = new Scene(getChatRoot());
        }
        return chatScene;
    }
}
