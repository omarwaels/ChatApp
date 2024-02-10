package iti.jets.app.client.controllers;

import iti.jets.app.client.CallBack.ClientImpl;
import iti.jets.app.client.utils.ChatBot;
import iti.jets.app.client.utils.ServerIPAddress;
import iti.jets.app.client.utils.ViewsFactory;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.Interfaces.server.DeleteChatsService;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import iti.jets.app.shared.Interfaces.server.UpdateInfoService;
import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class ChatScreenController implements Initializable {

    @FXML
    public ImageView attachementBtn;
    @FXML
    public ImageView addFriendBtn;
    @FXML
    public ImageView exitBtn;
    @FXML
    public StackPane singleChat;
    @FXML
    public StackPane groupChat;
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
    public TextArea messageTextField;
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
    public ConnectionItemController currentConnection = null;
    public ConnectionGroupItemController currentGroup = null;
    @FXML
    public ComboBox<String> comboBoxStatus;
    @FXML
    public ImageView inviteFriendsBtn;
    @FXML
    public Circle profilePic;
    @FXML
    public ImageView invitaionStarImage;
    @FXML
    public ImageView singleChatStarImage;
    @FXML
    public ImageView groubChatStarImage;
    @FXML
    public Label emptyGroups;
    @FXML
    public Label emptyFriends;
    @FXML
    public ImageView deleteBtn;

    @FXML
    public ImageView leaveGroupBtn;

    private final String appDirectory = "AppDirectory\\userObj";

    public LoginResultDto loginResultDto;
    Integer currentScreenUserId = null;
    Integer currentScreenChatId = null;
    Image currentScreenImage = null;
    HashMap<Integer, Node[]> chatsArr = new HashMap<>();
    ClientImpl client;
    ServerService serverService;

    ChatBot chatBot;
    public ConcurrentHashMap<Integer, ConnectionItemController> onlineUsers = new ConcurrentHashMap<>();
    public HashMap<Integer, ConnectionItemController> offlineUsers = new HashMap<>();
    public HashMap<Integer, ConnectionGroupItemController> groupChats = new HashMap<>();

    public HashMap<Integer, HBox> privateChatHBoxes = new HashMap<>();
    public HashMap<Integer, HBox> groupHBoxes = new HashMap<>();

    private String currentTypeOfActiveScreen = "SINGLECHAT";

    @FXML
    public StackPane invitationsBtn;

    public boolean isSingleChat = true;

    public boolean botOn = false;

    public boolean registered = true;

    void deleteChatsOfChatID(int chatID){
        chatsArr.remove(chatID);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customizeStatusBox();
        createToolTips();
        customizeEditorPane();
        try {
            chatBot = new ChatBot();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        comboBoxStatus.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    updateMode(newValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        chatLayout.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                chatScrollPane.setVvalue((Double) newHeight);
            }
        });
        groubChatContainer.setVisible(false);
    }

    public void setCurrentScreenImage(Image currentScreenImage) {
        this.currentScreenImage = currentScreenImage;
    }

    public void setChatScreenDto(LoginResultDto loginResultDto) throws IOException, NotBoundException {
        this.loginResultDto = loginResultDto;
        customInit();
        chatBorderPane.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    temporaryScreen.setVisible(true);
                    chatArea.setVisible(false);
                }
            }
        });
        new Thread(() -> {
            try {
                client = new ClientImpl(this, loginResultDto.getUserDto().getId());
                serverService = getServerService();
                serverService.register(client);
                registered = true;
                informFriends(true);
            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }).start();
        Platform.runLater(() -> {
            try {
                getServerService().receiveAllOfflineMessages(loginResultDto.getUserDto().getId());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
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
        ObservableList<Node> sortedChildren = FXCollections.observableArrayList(connectionLayout.getChildren());
        sortedChildren.sort(Comparator.comparingInt(ChatScreenController::getOrderOfNodeByStatus));
        connectionLayout.getChildren().setAll(sortedChildren);
        connectionLayout.setVisible(true);
    }

    private void sortSingleContactListOnTimeStamp() {
        connectionLayout.setVisible(false);
        ObservableList<Node> sortedChildren = FXCollections.observableArrayList(connectionLayout.getChildren());
        sortedChildren.sort(Comparator.comparing(ChatScreenController::getOrderOfNodeByTimeStamp, Comparator.nullsLast(Comparator.reverseOrder())));
        connectionLayout.getChildren().setAll(sortedChildren);
        connectionLayout.setVisible(true);
    }

    private void sortGroupContactListOnTimeStamp() {
        connectionGroupsLayout.setVisible(false);
        ObservableList<Node> sortedChildren = FXCollections.observableArrayList(connectionGroupsLayout.getChildren());
        sortedChildren.sort(Comparator.comparing(ChatScreenController::getOrderOfNodeByTimeStamp, Comparator.nullsLast(Comparator.reverseOrder())));
        // Replace the unsorted children with the sorted ones
        connectionGroupsLayout.getChildren().setAll(sortedChildren);
        // Set the visibility to true after sorting
        connectionGroupsLayout.setVisible(true);
    }

    ServerService getServerService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(), ServerIPAddress.getPort());
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getServerService();
    }


    public void customInit() throws IOException {
        List<FriendInfoDto> contactListArray = getContactListArray();
        List<ChatDto> groubsListArray = getGroupListArray();
        showGroupList(groubsListArray);
        showContactList(contactListArray);

        Collection<Node> nodesToScaleTransition = new ArrayList<>();
        nodesToScaleTransition.add(singleChat);
        nodesToScaleTransition.add(groupChat);
        nodesToScaleTransition.add(chatSettingImg);
        nodesToScaleTransition.add(exitBtn);
        nodesToScaleTransition.add(createGroupBtn);
        nodesToScaleTransition.add(addFriendBtn);
        nodesToScaleTransition.add(invitationsBtn);
        scaleTransitionIn(nodesToScaleTransition);
        profilePic.setFill(new ImagePattern(new Image(new ByteArrayInputStream(loginResultDto.getUserDto().getPicture()))));

        Stage currentStage = (Stage) chatSettingImg.getScene().getWindow();
        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (registered) {
                    try {
                        performActionsBeforeClosing();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (NotBoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.exit(0);
            }
        });
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
        emptyFriends.setVisible(false);
        this.currentTypeOfActiveScreen = "GROUPCHAT";
        groubChatStarImage.setVisible(false);
        if (connectionGroupsLayout.getChildren().isEmpty())
            emptyGroups.setVisible(true);
        else
            emptyGroups.setVisible(false);
    }

    public void showSingleChat() {
        groubChatContainer.setVisible(false);
        singleChatContainer.setVisible(true);
        emptyGroups.setVisible(false);
        this.currentTypeOfActiveScreen = "SINGLECHAT";
        singleChatStarImage.setVisible(false);
        if (connectionLayout.getChildren().isEmpty())
            emptyFriends.setVisible(true);
        else
            emptyFriends.setVisible(false);

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
                showReceivedMessageAnnouncement("You have received a new private message, go and check it.");
                sortSingleContactListOnTimeStamp();
                showStar("SINGLECHAT");
            } else {
                showReceivedMessageAnnouncement("You have received a new group message, go and check it.");
                sortGroupContactListOnTimeStamp();
                showStar("GROUPCHAT");
            }
            FXMLLoader fxmlLoader;
            if (message.isContainsFile()) {
                fxmlLoader = ViewsFactory.getViewsFactory().getFileRecieveController();
            } else {
                fxmlLoader = ViewsFactory.getViewsFactory().getMessageReceivedLoader();
            }
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (message.isContainsFile()) {
                FileReceiveController msc = fxmlLoader.getController();
                Path path = Paths.get(message.getMessageContent());
                message.setMessageContent(path.getFileName().toString());
                createRecievedFile(message, msc, hbox);
            } else {
                MessageReceiveController msc = fxmlLoader.getController();
                createRecievedMessage(message, msc, hbox);
            }
            if (message.isSingleChat() && botOn && !message.getMessageContent().isEmpty()) {
                try {
                    String response = chatBot.getResponse(message.getMessageContent());
                    FXMLLoader fxmlLoaderBot = ViewsFactory.getViewsFactory().getMessageSentLoader();
                    HBox hboxBot = fxmlLoaderBot.load();
                    MessageDto newMessage = createMessageDto(response);
                    newMessage.setReceiverId(new ArrayList<>(Arrays.asList(message.getSenderId())));
                    newMessage.setChatId(message.getChatId());
                    newMessage.setSingleChat(true);
                    MessageSentController mscBot = fxmlLoaderBot.getController();
                    Image userImg = new Image(new ByteArrayInputStream(loginResultDto.getUserDto().getPicture()));
                    mscBot.setData(newMessage, userImg);
                    createSentMessage(newMessage, mscBot, hboxBot);
                    new Thread(() -> {
                        try {
                            serverService.sendMessage(newMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createRecievedFile(MessageDto message, FileReceiveController msc, HBox hbox) {
        Image messageImage = new Image(new ByteArrayInputStream(message.getSenderImage()));
        msc.setData(message, messageImage);

        if (currentScreenChatId != null && currentScreenChatId.equals(message.getChatId())) {
            chatLayout.setAlignment(Pos.TOP_LEFT);
            chatLayout.getChildren().add(hbox);
        } else {
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
    }

    private void createRecievedMessage(MessageDto message, MessageReceiveController msc, HBox hbox) {
        Image messageImage = new Image(new ByteArrayInputStream(message.getSenderImage()));
        msc.setData(message, messageImage);
        if (currentScreenChatId != null && currentScreenChatId.equals(message.getChatId())) {
            chatLayout.setAlignment(Pos.TOP_LEFT);
            chatLayout.getChildren().add(hbox);
        } else {
            if (chatsArr.containsKey(message.getChatId())) {
                Node[] nodes = chatsArr.get(message.getChatId());
                ArrayList<Node> nodeList = new ArrayList<>(Arrays.asList(nodes));
                nodeList.add(hbox);
                Node[] updatedNodesArray = nodeList.toArray(new Node[0]);
                chatsArr.put(message.getChatId(), updatedNodesArray);
            } else {
                ArrayList<Node> nodeList = new ArrayList<>();
                nodeList.add(hbox);
                Node[] nodesArray = nodeList.toArray(new Node[0]);
                chatsArr.put(message.getChatId(), nodesArray);
            }
        }
    }

    private void createSentMessage(MessageDto message, MessageSentController msc, HBox hbox) {
        Image messageImage = new Image(new ByteArrayInputStream(message.getSenderImage()));
        msc.setData(message, messageImage);
        if (currentScreenChatId != null && currentScreenChatId.equals(message.getChatId())) {
            chatLayout.setAlignment(Pos.TOP_LEFT);
            chatLayout.getChildren().add(hbox);
        } else {
            if (chatsArr.containsKey(message.getChatId())) {
                Node[] nodes = chatsArr.get(message.getChatId());
                ArrayList<Node> nodeList = new ArrayList<>(Arrays.asList(nodes));
                nodeList.add(hbox);
                Node[] updatedNodesArray = nodeList.toArray(new Node[0]);
                chatsArr.put(message.getChatId(), updatedNodesArray);
            } else {
                ArrayList<Node> nodeList = new ArrayList<>();
                nodeList.add(hbox);
                Node[] nodesArray = nodeList.toArray(new Node[0]);
                chatsArr.put(message.getChatId(), nodesArray);
            }
        }
    }

    private void updateCounters(MessageDto message) {
        if (!message.getChatId().equals(currentScreenChatId)) {
            if (message.isSingleChat()) {
                ConnectionItemController connectionItemController = onlineUsers.get(message.getSenderId());
                if (connectionItemController == null)
                    connectionItemController = offlineUsers.get(message.getSenderId());
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
            if (connectionItemController == null)
                connectionItemController = offlineUsers.get(message.getSenderId());
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
        if (connections.isEmpty())
            emptyFriends.setVisible(true);
        for (FriendInfoDto connection : connections) {
            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getConnectionLoader();
            HBox hbox = fxmlLoader.load();
            int chatId = loginResultDto.getUserFriendsAndChatDto().get(connection).getChatId();
            privateChatHBoxes.put(chatId, hbox);
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
            privateChatHBoxes.put(friendChat.getChatId(), hbox);
            ConnectionItemController connectionItemController = fxmlLoader.getController();
            emptyFriends.setVisible(false);
            connectionItemController.setData(friend, this, friendChat);
            connectionItemController.setLastTimeStamp(new Timestamp(System.currentTimeMillis()));
            if (friend.getUserFriendStatus() == StatusEnum.ONLINE) {
                onlineUsers.put(friend.getUserFriendID(), connectionItemController);
            } else {
                offlineUsers.put(friend.getUserFriendID(), connectionItemController);
            }
            connectionLayout.getChildren().add(hbox);

            //update Hashmap
            HashMap<FriendInfoDto, ChatDto> friendInfoDtoChatDtoHashMap = loginResultDto.getUserFriendsAndChatDto();
            friendInfoDtoChatDtoHashMap.put(friend, friendChat);

            sortSingleContactListOnTimeStamp();
            sortSingleChatContactListOnstatus();
            showAddedToGroupAnnouncement("You and " + friend.getUserFriendName() + " are now friends, go and chat with him.");
        });
    }

    private void showGroupList(List<ChatDto> connections) throws IOException {
        for (ChatDto connection : connections) {
            FXMLLoader fxmlLoaders = ViewsFactory.getViewsFactory().getConnectionGroupItemController();
            HBox hbox = fxmlLoaders.load();
            ConnectionGroupItemController connectionGroupItemController = fxmlLoaders.getController();
            groupHBoxes.put(connection.getChatId(), hbox);
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
            groupHBoxes.put(groupChat.getChatId(), hbox);
            emptyGroups.setVisible(false);
            ConnectionGroupItemController connectionGroupItemController = fxmlLoaders.getController();
            //Update groupchat Hashmap
            connectionGroupItemController.setLastTimeStamp(new Timestamp(System.currentTimeMillis()));
            groupChats.put(groupChat.getChatId(), connectionGroupItemController);
            connectionGroupItemController.setData(groupChat, this);
            connectionGroupsLayout.getChildren().add(hbox);
            updateLoginResultDtoForNewGroup(groupChat, groupUsers);
            sortGroupContactListOnTimeStamp();
            showAddedToGroupAnnouncement("You have been added to " + groupChat.getChatName() + " group, go and check it.");
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
            double finalScaleX = 1.2;
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
                    connectionItemController.user.setUserFriendMode(ModeEnum.AVAILABLE);
                    showFriendChangeStatusAnnouncement("Your friend " + connectionItemController.user.getUserFriendName() + " is online now, let's chat with him.");
                    onlineUsers.put(friendId, connectionItemController);
                    connectionItemController.connectionStatus.setFill(javafx.scene.paint.Color.GREEN);
                    connectionItemController.userModeLabel.setText(ModeEnum.AVAILABLE.getMode());
                    connectionItemController.userModeLabel.setStyle("-fx-text-fill: green;");
                }
            } else {
                connectionItemController = onlineUsers.get(friendId);
                if (connectionItemController != null) {
                    onlineUsers.remove(friendId);
                    connectionItemController.user.setUserFriendStatus(StatusEnum.OFFLINE);
                    connectionItemController.user.setUserFriendMode(ModeEnum.AWAY);
                    showFriendChangeStatusAnnouncement("Your friend " + connectionItemController.user.getUserFriendName() + " is offline now.");
                    offlineUsers.put(friendId, connectionItemController);
                    connectionItemController.connectionStatus.setFill(Color.RED);
                    connectionItemController.userModeLabel.setText("");
                }
            }
            if (connectionItemController == currentConnection && isSingleChat) {
                try {
                    currentConnection.friendClicked();
                } catch (RemoteException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            }
            sortSingleChatContactListOnstatus();
        });
    }

    public void updateFriendMode(int friendId, String mode) {
        Platform.runLater(() -> {
            ConnectionItemController connectionItemController = onlineUsers.get(friendId);
            if (connectionItemController != null) {
                connectionItemController.user.setUserFriendMode(ModeEnum.valueOf(mode.toUpperCase()));
                connectionItemController.userModeLabel.setText(mode);
                switch (mode) {
                    case "Available":
                        connectionItemController.userModeLabel.setStyle("-fx-text-fill: green;");
                        break;
                    case "Busy":
                        connectionItemController.userModeLabel.setStyle("-fx-text-fill: orange;");
                        break;
                    case "Away":
                        connectionItemController.userModeLabel.setStyle("-fx-text-fill: yellow;");
                        ;
                        break;
                }
            }
        });
    }

    public void updateFriendPhoto(int friendId, byte[] photo) {
        Platform.runLater(() -> {
            ConnectionItemController connectionItemController = onlineUsers.get(friendId);
            if (connectionItemController != null) {
                connectionItemController.connectionPic.setFill(new ImagePattern(new Image(new ByteArrayInputStream(photo))));
                //connectionItemController.connectionPic.setImage(new Image(new ByteArrayInputStream(photo)));
            }
        });
    }

    public void updateFriendName(int friendId, String newName) {
        Platform.runLater(() -> {
            ConnectionItemController connectionItemController = onlineUsers.get(friendId);
            if (connectionItemController != null) {
                connectionItemController.user.setUserFriendName(newName);
                connectionItemController.connectionName.setText(newName);
                if (currentScreenUserId == friendId) {
                    updateConnectionName(newName);
                }
            }
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

    public void performActionsBeforeClosing() throws RemoteException, NotBoundException {
        informFriends(false);
        serverService.unregister(client);
        Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(), ServerIPAddress.getPort());
        ServiceFactory serviceFactory = (ServiceFactory) registry.lookup("ServiceFactory");
        serviceFactory.getLoginService().logOut(loginResultDto.getUserDto().getPhoneNumber());
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
            dialogStage.initOwner(invitationsBtn.getScene().getWindow());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.DECORATED);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Invitations");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
            invitaionStarImage.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chooseFile() throws IOException, InterruptedException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null && showConfirmationDialog(selectedFile.toPath())) {
            // User confirmed, proceed with sending the file

            try {
                FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getFileSentController();
                HBox hbox = fxmlLoader.load();

                MessageDto newMessage = createMessageDto(selectedFile.getAbsolutePath());

                newMessage.setContainsFile(true);
                FileSentController msc = fxmlLoader.getController();
                Image userImg = new Image(new ByteArrayInputStream(loginResultDto.getUserDto().getPicture()));
                msc.setData(newMessage, userImg, selectedFile);
                msc.setSendingStatus("Sending . . .");
                chatLayout.setAlignment(Pos.TOP_RIGHT);
                chatLayout.getChildren().add(hbox);
                sendFile(selectedFile.getAbsolutePath(), newMessage, msc);

            } catch (IOException e) {
            }
        }
    }

    private void sendFile(String filePath, MessageDto messageDto, FileSentController msc) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(filePath))) {
            int bufferSize = 1_000_000_000;
            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            int count;
            do {
                count = fileChannel.read(byteBuffer);
                if (count != -1) {
                    ByteBuffer sendBuffer = ByteBuffer.allocate(count);
                    byteBuffer.flip();
                    byteBuffer.limit(count);
                    sendBuffer.put(byteBuffer);
                    sendBuffer.flip();
                    new Thread(() -> {
                        try {
                            serverService.sendFile(messageDto, sendBuffer.array());
                            Platform.runLater(() -> {
                                msc.setSendingStatus("Done");
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                            Platform.runLater(() -> {
                                msc.setSendingStatus("Failure");
                            });
                        }
                    }).start();
                    byteBuffer.clear();
                }
            } while (count != -1);
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

    public void customizeStatusBox() {
        ObservableList<String> statusList = FXCollections.observableArrayList(ModeEnum.AVAILABLE.getMode(), ModeEnum.BUSY.getMode(), ModeEnum.AWAY.getMode());
        comboBoxStatus.setItems(statusList);
        comboBoxStatus.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            HBox hBox = new HBox(5);
                            Circle circle = new Circle(5);
                            Label label = new Label(item);
                            hBox.getChildren().addAll(circle, label);
                            setGraphic(hBox);

                            // Set the color of the circle based on the status
                            if (item.equals("Available")) {
                                circle.setFill(Color.GREEN);
                            } else if (item.equals("Busy")) {
                                circle.setFill(Color.ORANGE);
                            } else if (item.equals("Away")) {
                                circle.setFill(Color.YELLOW);
                            }
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        comboBoxStatus.setValue(statusList.get(0));
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
            addConnectionController.setData(loginResultDto.getUserDto(), this);
            Stage dialogStage = new Stage();
            dialogStage.initOwner(invitationsBtn.getScene().getWindow());
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

    public void createToolTips() {
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
        Tooltip.install(exitBtn, tooltipExit);
    }

    public void updateMode(String newMode) throws Exception {
        // Update the new mode in the DB
        int userID = loginResultDto.getUserDto().getId();
        UpdateInfoDto newUpdateInfo = new UpdateInfoDto("mode", newMode, userID);
        Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(), ServerIPAddress.getPort());
        UpdateInfoService updateInfoService = ((ServiceFactory) registry.lookup("ServiceFactory")).getUpdateInfoService();
        updateInfoService.updateField(newUpdateInfo);

        //Notify all friends with the new changed mode.
        getServerService().updateMode(new ArrayList<>(onlineUsers.keySet()), userID, newMode);

    }

    private boolean showConfirmationDialog(Path filePath) throws IOException {
        long fileSizeInBytes = Files.size(filePath);
        long oneGiga = (1000 * 1000 * 1000);

        if (fileSizeInBytes >= oneGiga) {
            Alert sizeExceededAlert = new Alert(Alert.AlertType.ERROR);
            sizeExceededAlert.setTitle("File Size Exceeded");
            sizeExceededAlert.setHeaderText(null);
            sizeExceededAlert.setContentText("File size exceeds 1 gigabyte. Please choose a smaller file.");
            sizeExceededAlert.showAndWait();
            return false;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm File Sending");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Do you want to send the file?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void showServerAnnouncement(String subject, String message) {
        Platform.runLater(() -> {
                    Image image = new Image(getClass().getResourceAsStream("/img/megaphone.png"));
                    Notifications notifications = Notifications.create()
                            .title(subject)
                            .text("     " + message)
                            .graphic(new ImageView(image))
                            .hideAfter(Duration.seconds(7))
                            .position(Pos.BOTTOM_RIGHT)
                            .owner(chatBorderPane.getScene().getWindow());
                    notifications.show();
                }
        );
    }

    public void showInvitationAnnouncement(String message) {
        showStar("INVITATION");
        Platform.runLater(() -> {
                    Image image = new Image(getClass().getResourceAsStream("/img/friend-request.png"));
                    Notifications notifications = Notifications.create()
                            .title("Friend Request")
                            .text("     " + message)
                            .graphic(new ImageView(image))
                            .hideAfter(Duration.seconds(7))
                            .position(Pos.BOTTOM_RIGHT)
                            .owner(chatBorderPane.getScene().getWindow());
                    notifications.show();
                }
        );
    }

    public void showFriendChangeStatusAnnouncement(String message) {
        Platform.runLater(() -> {
                    Image image = new Image(getClass().getResourceAsStream("/img/change.png"));
                    Notifications notifications = Notifications.create()
                            .title("Friend Status Change")
                            .text("     " + message)
                            .graphic(new ImageView(image))
                            .hideAfter(Duration.seconds(7))
                            .position(Pos.BOTTOM_RIGHT)
                            .owner(chatBorderPane.getScene().getWindow());
                    notifications.show();
                }
        );
    }

    public void showReceivedMessageAnnouncement(String message) {
        Platform.runLater(() -> {
                    Image image = new Image(getClass().getResourceAsStream("/img/communication.png"));
                    Notifications notifications = Notifications.create()
                            .title("New Message")
                            .text("     " + message)
                            .graphic(new ImageView(image))
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BOTTOM_RIGHT);
                    notifications.show();
                }
        );
    }

    public void showAddedToGroupAnnouncement(String message) {
        Platform.runLater(() -> {
                    Image image = new Image(getClass().getResourceAsStream("/img/groupAnn.png"));
                    Notifications notifications = Notifications.create()
                            .title("Group Invitation")
                            .text("     " + message)
                            .graphic(new ImageView(image))
                            .hideAfter(Duration.seconds(7))
                            .position(Pos.BOTTOM_RIGHT)
                            .owner(chatBorderPane.getScene().getWindow());
                    notifications.show();
                }
        );
    }

    private void signOutActions() {
        Platform.runLater(() -> {
            try {
                String UserPhoneNumber = loginResultDto.getUserDto().getPhoneNumber();
                performActionsBeforeClosing();
                FXMLLoader loader = ViewsFactory.getViewsFactory().getLoginLoader();
                Stage currentStage = (Stage) chatSettingImg.getScene().getWindow();
                Parent root = loader.load();
                SignInController signInController = loader.getController();
                signInController.setUserNameInScreen(UserPhoneNumber);
                currentStage.setScene(new Scene(root));

            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void onSignOutClicked() {
        deleteFileIfExists(appDirectory);
        signOutActions();
    }

    private void deleteFileIfExists(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() {
        onSignOutClicked();
        registered = false;
        showServerDownAlert();
    }

    public void showServerDownAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Down");
            alert.setHeaderText(null);
            alert.setContentText("Server is down, please try again later.");
            // Check if this is correct
            alert.initOwner(chatBorderPane.getScene().getWindow());
            alert.showAndWait();
        });
    }

    void showStar(String screenToStar) {
        if (this.currentTypeOfActiveScreen.equals(screenToStar)) {
            return;
        }
        if (screenToStar.equals("SINGLECHAT")) {
            singleChatStarImage.setVisible(true);
        } else if (screenToStar.equals("GROUPCHAT")) {
            groubChatStarImage.setVisible(true);
        } else {
            invitaionStarImage.setVisible(true);
        }

    }


    public void leaveGroup(int chatId) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(), ServerIPAddress.getPort());
        DeleteChatsService deleteChatsService = ((ServiceFactory) registry.lookup("ServiceFactory")).getDeleteChatsService();
        int ret = deleteChatsService.leaveGroupChat(loginResultDto.getUserDto().getId(), chatId);
        if (ret != 0) {
            connectionGroupsLayout.getChildren().remove(groupHBoxes.get(chatId));
            groupHBoxes.remove(chatId);
            HashMap<ChatDto, ArrayList<FriendInfoDto>> groups = loginResultDto.getGroupParticipants();
            for (ChatDto chat : groups.keySet()) {
                if (chat.getChatId() == chatId) {
                    groups.remove(chat);
                    break;
                }
            }
            if (connectionGroupsLayout.getChildren().isEmpty())
                emptyGroups.setVisible(true);
            temporaryScreen.setVisible(true);
            chatArea.setVisible(false);
        } else {
            showServerDownAlert();
        }
    }

    public void deleteFriend(int friendId, int chatId) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(), ServerIPAddress.getPort());
        ServiceFactory serviceFactory = (ServiceFactory) registry.lookup("ServiceFactory");
        DeleteChatsService deleteChatsService = serviceFactory.getDeleteChatsService();
        int ret = deleteChatsService.deletePrivateChat(loginResultDto.getUserDto().getId(), friendId, chatId);
        if (ret != 0) {
            onlineUsers.remove(friendId);
            offlineUsers.remove(friendId);
            HashMap<FriendInfoDto, ChatDto> map = loginResultDto.getUserFriendsAndChatDto();
            for (FriendInfoDto friend : map.keySet()) {
                if (friendId == friend.getUserFriendID()) {
                    map.remove(friend);
                    break;
                }
            }
            Platform.runLater(() -> {
                try {
                    ServerService serverService = serviceFactory.getServerService();
                    serverService.deleteFriend(chatId, friendId, loginResultDto.getUserDto().getId());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                connectionLayout.getChildren().remove(privateChatHBoxes.get(chatId));
                privateChatHBoxes.remove(chatId);
                if (connectionLayout.getChildren().isEmpty())
                    emptyFriends.setVisible(true);
                chatArea.setVisible(false);
                temporaryScreen.setVisible(true);
            });
        } else {
            showServerDownAlert();
        }
    }

    public void getDeleted(int chatId, int friendId) {
        Platform.runLater(() -> {
            onlineUsers.remove(friendId);
            offlineUsers.remove(friendId);
            connectionLayout.getChildren().remove(privateChatHBoxes.get(chatId));
            if (connectionLayout.getChildren().isEmpty())
                emptyFriends.setVisible(true);
            privateChatHBoxes.remove(chatId);
            if (currentScreenChatId != null && currentScreenChatId.equals(chatId)) {
                chatArea.setVisible(false);
                temporaryScreen.setVisible(true);
            }
            HashMap<FriendInfoDto, ChatDto> map = loginResultDto.getUserFriendsAndChatDto();
            for (FriendInfoDto friend : map.keySet()) {
                if (friendId == friend.getUserFriendID()) {
                    map.remove(friend);
                    break;
                }
            }
        });
    }

    @FXML
    public void onClickDeleteFriend() {
        if (currentConnection != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to remove this friend?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    try {
                        currentConnection.onDeleteFriend();
                    } catch (NotBoundException e) {
                        throw new RuntimeException(e);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    @FXML
    public void onClickLeaveGroup() {
        if (currentGroup != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to leave this group?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    try {
                        currentGroup.onClickLeaveGroup();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (NotBoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
    void getStoredMessage(ArrayList<MessageDto> messages) throws IOException {
        chatLayout.getChildren().clear();
        Platform.runLater(()->{
            try{
                // Delete message

                for (MessageDto message : messages) {
                    if (message.getSenderId().equals(loginResultDto.getUserDto().getId())) {
                        if (message.isContainsFile()) {
                            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getFileSentController();
                            HBox hbox = fxmlLoader.load();
                            FileSentController msc = fxmlLoader.getController();
                            Image userImg = new Image(new ByteArrayInputStream(loginResultDto.getUserDto().getPicture()));
                            File file = new File(message.getMessageContent());
                            message.setMessageContent(file.getName());
                            msc.setData(message, userImg, file);
                            msc.setSendingStatus("");
                            chatLayout.setAlignment(Pos.TOP_RIGHT);
                            chatLayout.getChildren().add(hbox);
                        } else {
                            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getMessageSentLoader();
                            HBox hbox = fxmlLoader.load();
                            MessageSentController msc = fxmlLoader.getController();
                            Image userImg = new Image(new ByteArrayInputStream(loginResultDto.getUserDto().getPicture()));
                            msc.setData(message, userImg);
                            chatLayout.setAlignment(Pos.TOP_RIGHT);
                            chatLayout.getChildren().add(hbox);
                        }

                    } else {
                        if (message.isContainsFile()) {
                            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getFileRecieveController();
                            HBox hbox = fxmlLoader.load();
                            FileReceiveController msc = fxmlLoader.getController();
                            Path path = Paths.get(message.getMessageContent());
                            message.setMessageContent(path.getFileName().toString());

                            createRecievedFile(message, msc, hbox);
                        } else {
                            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getMessageReceivedLoader();
                            HBox hbox = fxmlLoader.load();
                            MessageReceiveController msc = fxmlLoader.getController();
                            createRecievedMessage(message, msc, hbox);
                        }

                    }
                }
            }catch (IOException exception){

            }

        });



    }

    @FXML
    public void toggleBotAction() {
        botOn = !botOn;
    }
}
