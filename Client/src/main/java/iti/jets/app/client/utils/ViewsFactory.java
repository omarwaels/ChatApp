package iti.jets.app.client.utils;

import javafx.fxml.FXMLLoader;

public class ViewsFactory {
    private FXMLLoader loginLoader;
    private FXMLLoader signUpLoader;
    private FXMLLoader userSettingsLoader;
    private FXMLLoader chatLoader;
    private FXMLLoader ConnectionGroupItemController;

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
}
