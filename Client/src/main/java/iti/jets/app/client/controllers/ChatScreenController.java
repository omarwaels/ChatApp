package iti.jets.app.client.controllers;

import iti.jets.app.client.CallBack.ClientImpl;
import iti.jets.app.client.utils.ViewsFactory;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.util.*;


public class ChatScreenController implements Initializable {

    @FXML
    public ImageView attachementBtn;
    @FXML
    public ImageView addFriendBtn;
    @FXML
    public ImageView exitImg;
    @FXML
    public ImageView singleChat;
    @FXML
    public ImageView groupChat;
    @FXML
    public ImageView chatSettingImg;

    @FXML
    public BorderPane chatBorderPane;
    @FXML
    public VBox chatArea;

    @FXML
    public HBox chatHeader;

    @FXML
    public VBox chatLayout;

    @FXML
    public VBox connectionLayout;
    @FXML
    public VBox connectionGroupsLayout;

    @FXML
    public Label connectionName;

    @FXML
    public ScrollPane singleChatContainer;
    @FXML
    public ScrollPane groubChatContainer;

    @FXML
    public ImageView emojiBtn;

    @FXML
    public TextField messageTextField;

    @FXML
    public ImageView sendBtn;

    @FXML
    public Circle currentScreenStatusColor;


    @FXML
    public Label currentScreenStatusWord;


    @FXML
    public ImageView threeDotsBtn;

    @FXML
    public ImageView videoCallBtn;

    @FXML
    public ImageView voiceCallBtn;

    @FXML
    public ScrollPane chatScrollPane;
    @FXML
    public HBox chatFooter;
    @FXML
    public VBox temporaryScreen;
    @FXML
    public ImageView createGroupBtn;
    @FXML
    public ToggleButton boldToggleBtn;
    @FXML
    public ToggleButton italicTogglebtn;
    @FXML
    public ToggleButton lineToggleBtn;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public FlowPane editPane;
    @FXML
    public ComboBox<String> fontComboBox;
    @FXML
    public ComboBox<String> fontSizeComboBox;

    @FXML
    public ImageView inviteFriendsBtn;
    public ConnectionItemController currentConnection;

    public LoginResultDto loginResultDto;
    Integer currentScreenUserId = null;
    Integer currentScreenChatId = null;
    Image currentScreenImage = null;

    HashMap<Integer, Node[]> chatsArr = new HashMap<>();


    ClientImpl client;
    ServerService serverService;

    private HashMap<Integer, ConnectionItemController> onlineUsers = new HashMap<>();

    private HashMap<Integer, ConnectionItemController> offlineUsers = new HashMap<>();
    private HashMap<Integer, ConnectionGroupItemController> groupChats = new HashMap<>();

    @FXML
    public ImageView invitationsBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createToolTips();
        customizeEditorPane();
        groubChatContainer.setVisible(false);
    }

    public void setCurrentScreenImage(Image currentScreenImage) {
        this.currentScreenImage = currentScreenImage;
    }

    public void setChatScreenDto(LoginResultDto loginResultDto) throws IOException, NotBoundException {
        this.loginResultDto = loginResultDto;
        customInit();
        new Thread(() -> {
            try {
                client = new ClientImpl(this, loginResultDto.getUserDto().getId());
                serverService = getServerService();
                serverService.register(client);
                informFriends(true);
            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private static int getOrderOfNodeByStatus(Node node) {
        if (node instanceof HBox) {
            Node node2 = ((HBox) node).getChildren().get(2);
            if (node2 instanceof Pane) {
                Node node3 = ((Pane) node2).getChildren().get(0);
                if (node3 instanceof Circle) {
                    Color color = (Color) ((Circle) node3).getFill();
                    if (color.toString().equals("0x008000ff")) return 0;

                }
            }
        }
        return 1;
    }

    private static Timestamp getOrderOfNodeByTimeStamp(Node node) {
        if (node instanceof HBox) {
            Node node2 = ((HBox) node).getChildren().get(1);
            if (node2 instanceof VBox) {
                Node node3 = ((VBox) node2).getChildren().get(0);
                if (node3 instanceof Label) {
                    String timeOfNode = ((Label) node3).getText();
                    return Timestamp.valueOf(timeOfNode);

                }
            }
        }
        return null;
    }

    private void sortSingleChatContactListOnstatus() {
        connectionLayout.setVisible(false);
        // Create a new sorted list of children
        ObservableList<Node> sortedChildren = FXCollections.observableArrayList(connectionLayout.getChildren());
        sortedChildren.sort(Comparator.comparingInt(ChatScreenController::getOrderOfNodeByStatus));
        // Replace the unsorted children with the sorted ones
        connectionLayout.getChildren().setAll(sortedChildren);
        // Set the visibility to true after sorting
        connectionLayout.setVisible(true);
    }

    private void sortSingleContactListOnTimeStamp() {
        connectionLayout.setVisible(false);
        // Create a new sorted list of children
        ObservableList<Node> sortedChildren = FXCollections.observableArrayList(connectionLayout.getChildren());
        // Sorting based on Timestamp in descending order
        sortedChildren.sort(Comparator.comparing(ChatScreenController::getOrderOfNodeByTimeStamp, Comparator.nullsLast(Comparator.reverseOrder())));
        // Replace the unsorted children with the sorted ones
        connectionLayout.getChildren().setAll(sortedChildren);
        // Set the visibility to true after sorting
        connectionLayout.setVisible(true);
    }

    private void sortGroupContactListOnTimeStamp() {
        connectionGroupsLayout.setVisible(false);
        // Create a new sorted list of children
        ObservableList<Node> sortedChildren = FXCollections.observableArrayList(connectionGroupsLayout.getChildren());
        // Sorting based on Timestamp in descending order
        sortedChildren.sort(Comparator.comparing(ChatScreenController::getOrderOfNodeByTimeStamp, Comparator.nullsLast(Comparator.reverseOrder())));
        // Replace the unsorted children with the sorted ones
        connectionGroupsLayout.getChildren().setAll(sortedChildren);
        // Set the visibility to true after sorting
        connectionGroupsLayout.setVisible(true);
    }

    ServerService getServerService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getServerService();
    }

    public void customInit() throws IOException {
        List<FriendInfoDto> contactListArray = getContactListArray();
        List<ChatDto> groubsListArray = getGroupListArray();
        showGroupList(groubsListArray);
        this.showContactList(contactListArray);

        Collection<Node> nodesToScaleTransition = new ArrayList<>();
        nodesToScaleTransition.add(singleChat);
        nodesToScaleTransition.add(groupChat);
        nodesToScaleTransition.add(chatSettingImg);
        nodesToScaleTransition.add(exitImg);
        nodesToScaleTransition.add(createGroupBtn);
        scaleTransitionIn(nodesToScaleTransition);

    }


    void updateCurrentScreenStatusWord(StatusEnum statusWord) {
        currentScreenStatusWord.setText(statusWord.getStatus());
        String statusColor = getOnlineAndOfflineColor(statusWord);
        currentScreenStatusColor.setFill(Color.web(statusColor));
    }

    void updateCurrentScreenStatusWordForGroups(String statusWord) {
        currentScreenStatusWord.setText(statusWord);
        currentScreenStatusColor.setFill(Color.rgb(0, 0, 0, 0.0));
    }

    private String getOnlineAndOfflineColor(StatusEnum statusWord) {
        switch (statusWord) {
            case ONLINE:
                return "#50c984";
            default:
                return "#ff0000";
        }
    }

    public void showGroupChat() {
        singleChatContainer.setVisible(false);
        groubChatContainer.setVisible(true);
    }

    public void showSingleChat() {
        groubChatContainer.setVisible(false);
        singleChatContainer.setVisible(true);
    }

    public void updateConnectionName(String name) {
        connectionName.setText(name);
    }

    public void sendMessageByKeyboard(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)
            sendMessage();
    }

    public void sendMessageByIcon() throws IOException {
        sendMessage();
    }

    public void sendMessage() throws IOException {
        String text = messageTextField.getText().trim();

        messageTextField.setText("");
        if (!text.isEmpty()) {

            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getMessageSentLoader();
            HBox hbox = fxmlLoader.load();
            MessageDto newMessage = createMessageDto(text);
            MessageSentController msc = fxmlLoader.getController();
            Image userImg = new Image(new ByteArrayInputStream(loginResultDto.getUserDto().getPicture()));
            msc.setData(newMessage, userImg);
            chatLayout.setAlignment(Pos.TOP_RIGHT);
            chatLayout.getChildren().add(hbox);
            new Thread(() -> {
                try {
                    serverService.sendMessage(newMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private MessageDto createMessageDto(String text) {

        String color = toRGBCode(colorPicker.getValue());
        String weight = (boldToggleBtn.isSelected()) ? "Bold" : "normal";
        String size = fontSizeComboBox.getSelectionModel().getSelectedItem();
        String style = (italicTogglebtn.isSelected()) ? "italic" : "normal";
        String font = fontComboBox.getSelectionModel().getSelectedItem();
        Boolean underline = lineToggleBtn.isSelected();

        ArrayList<Integer> IdsOfRecivers = new ArrayList<>();
        boolean isSingleChat = true;
        if (currentScreenUserId == null) {
            isSingleChat = false;
            IdsOfRecivers = getUserFriendsIdsInSameGroup(loginResultDto, currentScreenChatId);
        } else {

            IdsOfRecivers.add(currentScreenUserId);
        }

        MessageDto msgDto = new MessageDto(loginResultDto.getUserDto().getId(), IdsOfRecivers, currentScreenChatId,
                false, text, new Timestamp(System.currentTimeMillis()), loginResultDto.getUserDto().getPicture(), isSingleChat);

        msgDto.setFontSize(Integer.parseInt(size));
        msgDto.setFontColor(color);
        msgDto.setFontWeight(weight);
        msgDto.setFontFamily(font);
        msgDto.setFontStyle(style);
        msgDto.setUnderline(underline);

        return msgDto;
    }

    private ArrayList<Integer> getUserFriendsIdsInSameGroup(LoginResultDto loginResultDto, Integer currentChatId) {
        ArrayList<Integer> userFriendsIdsInSameGroup = new ArrayList<>();
        HashMap<ChatDto, ArrayList<FriendInfoDto>> groubsAndMembers = loginResultDto.getGroupParticipants();
        for (ChatDto chatDto : groubsAndMembers.keySet()) {
            if (currentChatId.equals(chatDto.getChatId())) {
                ArrayList<FriendInfoDto> membersOfGroup = groubsAndMembers.get(chatDto);
                for (FriendInfoDto memberGroup : membersOfGroup) {
                    userFriendsIdsInSameGroup.add(memberGroup.getUserFriendID());
                }
                return userFriendsIdsInSameGroup;

            }
        }
        return userFriendsIdsInSameGroup;
    }

    public void receiveMessage(MessageDto message) {

        Platform.runLater(() -> {
            updateCounters(message);
            updateTimeStamps(message);
            if (message.isSingleChat()) {
                sortSingleContactListOnTimeStamp();
            } else {
                sortGroupContactListOnTimeStamp();
            }

            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getMessageReceivedLoader();
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MessageReceiveController msc = fxmlLoader.getController();
            Image messageImage = new Image(new ByteArrayInputStream(message.getSenderImage()));
            msc.setData(message, messageImage);
            //update Current Screen

            if (currentScreenChatId != null && currentScreenChatId.equals(message.getChatId())) {
                chatLayout.setAlignment(Pos.TOP_LEFT);
                chatLayout.getChildren().add(hbox);
            } else {
                //Add message between nodes
                //If chat screen array includes the chat
                if (chatsArr.containsKey(message.getChatId())) {
                    Node[] nodes = chatsArr.get(message.getChatId());
                    ArrayList<Node> nodeList = new ArrayList<>(Arrays.asList(nodes));
                    nodeList.add(hbox);
                    Node[] updatedNodesArray = nodeList.toArray(new Node[0]);
                    chatsArr.put(message.getChatId(), updatedNodesArray);
                } else {
                    //If the chat didn't start
                    ArrayList<Node> nodeList = new ArrayList<>();
                    nodeList.add(hbox);
                    Node[] nodesArray = nodeList.toArray(new Node[0]);
                    chatsArr.put(message.getChatId(), nodesArray);
                }
            }


        });


    }

    private void updateCounters(MessageDto message) {
        if (!message.getChatId().equals(currentScreenChatId)) {
            if (message.isSingleChat()) {
                ConnectionItemController connectionItemController = onlineUsers.get(message.getSenderId());
                connectionItemController.updateCounter();
            } else {
                ConnectionGroupItemController connectionGroupItemController = groupChats.get(message.getChatId());
                connectionGroupItemController.updateCounter();

            }

        }
    }

    private void updateTimeStamps(MessageDto message) {

        if (message.isSingleChat()) {
            ConnectionItemController connectionItemController = onlineUsers.get(message.getSenderId());
            connectionItemController.setLastTimeStamp(message.getSentAt());
        } else {
            ConnectionGroupItemController connectionGroupItemController = groupChats.get(message.getChatId());
            connectionGroupItemController.setLastTimeStamp(message.getSentAt());

        }


    }


    public void updateChatLayout(Integer newUserIdScreen, Integer newChatIdScreen) {

        if (this.currentScreenChatId != null) {
            Node[] currentChildren = chatLayout.getChildren().toArray(new Node[0]);
            chatsArr.put(this.currentScreenChatId, currentChildren);
        }
        chatLayout.getChildren().clear();
        if (chatsArr.containsKey(newChatIdScreen)) {

            chatLayout.getChildren().addAll(chatsArr.get(newChatIdScreen));

        }
        //Just update the current Screen user Id & current Screen chat Id
        this.currentScreenUserId = newUserIdScreen;
        this.currentScreenChatId = newChatIdScreen;
    }

    private void showContactList(List<FriendInfoDto> connections) throws IOException {
        for (FriendInfoDto connection : connections) {
            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getConnectionLoader();
            HBox hbox = fxmlLoader.load();
            ConnectionItemController connectionItemController = fxmlLoader.getController();
            ChatDto associateChatDto = loginResultDto.getUserFriendsAndChatDto().get(connection);
            connectionItemController.setData(connection, this, associateChatDto);
            if (connection.getUserFriendStatus() == StatusEnum.ONLINE) {
                onlineUsers.put(connection.getUserFriendID(), connectionItemController);
            } else {
                offlineUsers.put(connection.getUserFriendID(), connectionItemController);
            }
            connectionLayout.getChildren().add(hbox);
        }
    }


    public void addNewFriendInContactList(FriendInfoDto friend, ChatDto friendChat) throws IOException {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getConnectionLoader();
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ConnectionItemController connectionItemController = fxmlLoader.getController();

            connectionItemController.setData(friend, this, friendChat);
            connectionItemController.setLastTimeStamp(new Timestamp(System.currentTimeMillis()));
            if (friend.getUserFriendStatus() == StatusEnum.ONLINE) {
                onlineUsers.put(friend.getUserFriendID(), connectionItemController);
            } else {
                offlineUsers.put(friend.getUserFriendID(), connectionItemController);
            }
            connectionLayout.getChildren().add(hbox);
            sortSingleContactListOnTimeStamp();
            sortSingleChatContactListOnstatus();
        });
    }

    private void showGroupList(List<ChatDto> connections) throws IOException {
        for (ChatDto connection : connections) {
            FXMLLoader fxmlLoaders = ViewsFactory.getViewsFactory().getConnectionGroupItemController();
            HBox hbox = fxmlLoaders.load();
            ConnectionGroupItemController connectionGroupItemController = fxmlLoaders.getController();
            //Update groupchat Hashmap
            groupChats.put(connection.getChatId(), connectionGroupItemController);
            connectionGroupItemController.setData(connection, this);
            connectionGroupsLayout.getChildren().add(hbox);

        }
    }

    public void addNewGroupInContactList(ChatDto groupChat, List<Integer> groupUsers) throws IOException {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoaders = ViewsFactory.getViewsFactory().getConnectionGroupItemController();
            HBox hbox = null;
            try {
                hbox = fxmlLoaders.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ConnectionGroupItemController connectionGroupItemController = fxmlLoaders.getController();
            //Update groupchat Hashmap
            connectionGroupItemController.setLastTimeStamp(new Timestamp(System.currentTimeMillis()));
            groupChats.put(groupChat.getChatId(), connectionGroupItemController);
            connectionGroupItemController.setData(groupChat, this);
            connectionGroupsLayout.getChildren().add(hbox);
            updateLoginResultDtoForNewGroup(groupChat, groupUsers);
            sortGroupContactListOnTimeStamp();
        });
    }

    private void updateLoginResultDtoForNewGroup(ChatDto chatDto, List<Integer> groupUsers) {
        HashMap<ChatDto, ArrayList<FriendInfoDto>> groups = loginResultDto.getGroupParticipants();
        ArrayList<FriendInfoDto> friends = new ArrayList<>();
        for (Integer userId : groupUsers) {
            if (userId.equals(loginResultDto.getUserDto().getId())) continue;
            FriendInfoDto friend = new FriendInfoDto();
            friend.setUserFriendID(userId);
            friends.add(friend);
        }
        groups.put(chatDto, friends);
    }

    private List<FriendInfoDto> getContactListArray() {
        HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto = loginResultDto.getUserFriendsAndChatDto();
        List<FriendInfoDto> ls = new ArrayList<>(userFriendsAndChatDto.keySet());
        ls.sort(Comparator.comparing(FriendInfoDto::getUserFriendStatus));
        return ls;
    }

    private List<ChatDto> getGroupListArray() {
        HashMap<ChatDto, ArrayList<FriendInfoDto>> userFriendsAndChatDto = loginResultDto.getGroupParticipants();
        return new ArrayList<>(userFriendsAndChatDto.keySet());
    }

    public void scaleTransitionIn(Collection<Node> nodes) {
        for (Node node : nodes) {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), node);

            // Set the initial and final scales
            double initialScaleX = node.getScaleX();
            double initialScaleY = node.getScaleY();
            double finalScaleX = 1.2; // You can adjust the scale factor as needed
            double finalScaleY = 1.2;

            // Set the scales for the transition
            scaleTransition.setFromX(initialScaleX);
            scaleTransition.setFromY(initialScaleY);
            scaleTransition.setToX(finalScaleX);
            scaleTransition.setToY(finalScaleY);

            // Play the transition on mouse enter
            node.setOnMouseEntered(event -> {
                scaleTransition.play();
            });

            // Reverse the transition on mouse exit
            node.setOnMouseExited(event -> {
                scaleTransition.setRate(-1);
                scaleTransition.play();
            });
        }
    }

    public void updateFriendStatus(int friendId, boolean online) {
        Platform.runLater(() -> {
            ConnectionItemController connectionItemController = null;
            if (online) {
                connectionItemController = offlineUsers.get(friendId);
                if (connectionItemController != null) {
                    offlineUsers.remove(friendId);
                    connectionItemController.user.setUserFriendStatus(StatusEnum.ONLINE);
                    onlineUsers.put(friendId, connectionItemController);
                    connectionItemController.connectionStatus.setFill(javafx.scene.paint.Color.GREEN);

                }
            } else {
                connectionItemController = onlineUsers.get(friendId);
                if (connectionItemController != null) {
                    onlineUsers.remove(friendId);
                    connectionItemController.user.setUserFriendStatus(StatusEnum.OFFLINE);
                    offlineUsers.put(friendId, connectionItemController);
                    connectionItemController.connectionStatus.setFill(Color.RED);
                }
            }
            if (connectionItemController == currentConnection)
                currentConnection.friendClicked();
            sortSingleChatContactListOnstatus();
        });
    }


    public void informFriends(boolean online) {
        if (onlineUsers.keySet().isEmpty())
            return;
        new Thread(() -> {
            try {
                serverService.updateStatus(new ArrayList<>(onlineUsers.keySet()), loginResultDto.getUserDto().getId(), online);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void performActionsBeforeClosing() throws RemoteException {
        informFriends(false);
        serverService.unregister(client);
    }

    public void onSettingClicked() throws IOException {
        redirectToUserSettings();
    }

    public void redirectToUserSettings() throws IOException {
        FXMLLoader loader = ViewsFactory.getViewsFactory().getUserSettingsLoader();
        Stage currentStage = (Stage) chatSettingImg.getScene().getWindow();
        ViewsFactory.getViewsFactory().getUserSettingsRoot();
        UserSettingsController userSettingsController = loader.getController();
        userSettingsController.setChatScreenController(this);
        userSettingsController.setUser(loginResultDto.getUserDto());
        currentStage.setScene(ViewsFactory.getViewsFactory().getUserSettingsScene());
    }


    @FXML
    public void onCreateGroup() {
        try {
            FXMLLoader loader = ViewsFactory.getViewsFactory().getCreateGroupLoader();
            Stage currentStage = (Stage) chatSettingImg.getScene().getWindow();
            Parent root = loader.load();
            CreateGroupController createGroupController = loader.getController();
            createGroupController.setData(getContactListArray(), loginResultDto.getUserDto().getId());
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.DECORATED);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Create Group");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickInvitations() {
        try {
            FXMLLoader loader = ViewsFactory.getViewsFactory().getInvitationsLoader();
            Parent root = loader.load();
            InvitationRequestController invitationRequestController = loader.getController();
            invitationRequestController.setData(loginResultDto.getUserDto().getId(), this);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.DECORATED);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Invitations");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void customizeEditorPane() {
        ObservableList<String> limitedFonts = FXCollections.observableArrayList("Arial", "Times", "Courier New", "Comic Sans MS");
        fontComboBox.setItems(limitedFonts);
        fontComboBox.getSelectionModel().selectFirst();

        ObservableList<String> fontSizes = FXCollections.observableArrayList("8", "10", "12", "14", "18", "24");
        fontSizeComboBox.setItems(fontSizes);
        fontSizeComboBox.getSelectionModel().select(2);

        colorPicker.setValue(Color.BLACK);
    }

    public String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @FXML
    public void onClickInviteFriends() {
        FXMLLoader loader = ViewsFactory.getViewsFactory().getAddConnectionLoader();
        try {
            Parent root = loader.load();
            AddConnectionController addConnectionController = loader.getController();
            addConnectionController.setData(loginResultDto.getUserDto());
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.DECORATED);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Add Connection");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }
    }
    public void createToolTips()
    {
        final Tooltip tooltipSingleChat = new Tooltip();
        tooltipSingleChat.setText("Find your private chats here");
        Tooltip.install(singleChat, tooltipSingleChat);

        final Tooltip tooltipAddFriend = new Tooltip();
        tooltipAddFriend.setText("Create new connection");
        Tooltip.install(addFriendBtn, tooltipAddFriend);

        final Tooltip tooltipInvitation = new Tooltip();
        tooltipInvitation.setText("New connection requests");
        Tooltip.install(invitationsBtn, tooltipInvitation);

        final Tooltip tooltipCreateGroup = new Tooltip();
        tooltipCreateGroup.setText("Create New Group");
        Tooltip.install(createGroupBtn, tooltipCreateGroup);

        final Tooltip tooltipGroupChat = new Tooltip();
        tooltipGroupChat.setText("Find your group chats here");
        Tooltip.install(groupChat, tooltipGroupChat);

        final Tooltip tooltipUserSettings = new Tooltip();
        tooltipUserSettings.setText("Edit profile");
        Tooltip.install(chatSettingImg, tooltipUserSettings);

        final Tooltip tooltipExit = new Tooltip();
        tooltipExit.setText("Logout");
        Tooltip.install(exitImg, tooltipExit);
    }
}
