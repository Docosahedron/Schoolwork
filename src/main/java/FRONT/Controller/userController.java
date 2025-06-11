package FRONT.Controller;

import BACK.Entity.*;
import BACK.Service.SerImpl.GameSerImpl;
import BACK.Service.SerImpl.UserSerImpl;
import FRONT.GUI.MarketFrame;
import FRONT.GUI.WalletFrame;
import FRONT.GUI.WareFrame;
import FRONT.GUI.WishlistFrame;
import FRONT.MainApp;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static FRONT.View.UserView.showAlert;

public class userController {

    @FXML public MenuItem song1Button;
     @FXML public MenuItem song1Button2;
    UserSerImpl us = new UserSerImpl();
    GameSerImpl gd = new GameSerImpl();
    private MainApp mainApp;// 保存主应用引用

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML private ToggleButton unpurchased;
    @FXML private ToggleButton gather;
    @FXML private Button DownLoadGo;
    @FXML private Menu MenuName;
    @FXML private MenuButton account;
    @FXML private TableColumn<Game, String> blanceTable;
    @FXML private MenuItem choiceness;
    @FXML private MenuButton community;
    @FXML private MenuItem download;
    @FXML private MenuItem exsit;
    @FXML private MenuItem find;
    @FXML private MenuItem homeButton;
    @FXML private MenuButton homepage;
    @FXML private MenuItem inventory;
    @FXML private MenuItem lock;
    @FXML private MenuItem market;
    @FXML private TableColumn<Game, String> nameTable;
    @FXML private TableColumn<Game, String> pictureTable;
    @FXML private TableColumn<Game, Double> priceTable;
    @FXML private TableColumn<Game, String> typetable;
    @FXML private Slider priceRange;
    @FXML private Button reset;
    @FXML private Button searching;
    @FXML private MenuButton shop;
    @FXML private TableView<Game> shoptable;
    @FXML private TitledPane tabbar;
    @FXML private MenuItem walllet;
    @FXML private MenuItem wishList;



    @FXML
    public void lockaction() throws IOException {
        mainApp.goToLoginStage();
        mainApp.exStage();
    }
    @FXML
    public void exsitaction() throws IOException {
        mainApp.exStage();
    }
    @FXML
    void goToInventory(ActionEvent event) {
        WareFrame wareFrame = new WareFrame(user,us.getBanana(user)); // 你可以自己构造 JavaFX 窗口
        wareFrame.show();
    }

    @FXML
    void goToMarket(ActionEvent event) {
        MarketFrame marketFrame = new MarketFrame(user);
        marketFrame.show();
    }

    @FXML
    void goToWallet(ActionEvent event) {
        WalletFrame walletFrame = new WalletFrame(user);
        walletFrame.show();
    }

    @FXML
    void goToWish(ActionEvent event) {
        WishlistFrame wishlistFrame = new WishlistFrame(user);
        wishlistFrame.show();
    }
    @FXML
    public void initialize() {
        pictureTable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.10));
        nameTable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.25));
        blanceTable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.15));
        priceTable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.20));
        typetable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.30));

        // 设置列与属性的绑定
        nameTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        blanceTable.setCellValueFactory(new PropertyValueFactory<>("score"));
        priceTable.setCellValueFactory(new PropertyValueFactory<>("price"));
        typetable.setCellValueFactory(new PropertyValueFactory<>("type"));

        // 设置表格自定义图片列渲染
        pictureTable.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageUrl, boolean empty) {
                super.updateItem(imageUrl, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Game game = getTableRow().getItem();
                Image image;

                try {
                    if (imageUrl == null || imageUrl.isBlank()) throw new Exception("空图片地址");
                    image = new Image(imageUrl, true);
                } catch (Exception e) {
                    image = generatePlaceholder(game != null ? game.getName() : "?");
                }

                imageView.setImage(image);
                imageView.setFitHeight(120);
                imageView.setPreserveRatio(true);
                setGraphic(imageView);
            }
        });

        pictureTable.setResizable(false);
        nameTable.setResizable(false);
        blanceTable.setResizable(false);
        priceTable.setResizable(false);
        typetable.setResizable(false);

        shoptable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // 加载游戏数据
        loadGameData();

        // 添加双击监听
        shoptable.setRowFactory(tv -> {
            User user = this.user;
            TableRow<Game> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        mainApp.exgame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Game game = row.getItem();
                        mainApp.goToGameStage(us.getUserInfo(user.getName()), game);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        // 其他按钮逻辑
        unpurchased.setOnAction(event -> unpurchased.setText(unpurchased.isSelected() ? "已购买" : "未购买"));
        gather.setOnAction(event -> gather.setText(gather.isSelected() ? "交集" : "并集"));
    }
    private void loadGameData() {
        List<Game> games = gd.getAllGame();
        ObservableList<Game> observableGames = FXCollections.observableArrayList(games);
        shoptable.setItems(observableGames);
    }
    private Image generatePlaceholder(String name) {
        String firstChar = name != null && !name.isBlank() ? name.substring(0, 1).toUpperCase() : "?";
        int width = 60;
        int height = 60;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 黑底
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // 白字（居中）
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 30));

        // 使用 JavaFX 公共 API 测量文本宽度
        Text text = new Text(firstChar);
        text.setFont(gc.getFont());
        double textWidth = text.getLayoutBounds().getWidth();

        gc.fillText(firstChar, (width - textWidth) / 2, height / 2 + 10);

        WritableImage snapshot = new WritableImage(width, height);
        canvas.snapshot(null, snapshot);
        return snapshot;
    }

    // 音乐播放相关变量
    private MediaPlayer mediaPlayer;
    private final String defaultMusicPath = "/resources/music/song1.mp3";
    private String currentMusicPath;
    @FXML public Button chooseMusicButton;
    @FXML public Button playButton;
    @FXML public Button pauseButton;
    @FXML public Button landchoose;
    // 初始化音乐播放器
    private void initMusicPlayer() {
        // 禁用播放按钮直到加载音乐
        playButton.setDisable(true);
        pauseButton.setDisable(true);
    }
    // 加载默认音乐
    private void loadDefaultMusic() {
        try {
            currentMusicPath = defaultMusicPath;
            loadMusic(currentMusicPath);
        } catch (Exception e) {
            showAlert("音乐加载失败", "无法加载默认音乐: " + e.getMessage());
        }
    }
    // 加载音乐文件
    private void loadMusic(String musicPath) {
        try {
            // 停止当前播放
            stopMusic();
            // 获取音乐文件URL
            String musicUrl;
            if (musicPath.startsWith("/")) { // 资源路径
                musicUrl = getClass().getResource(musicPath).toExternalForm();
            } else { // 文件路径
                musicUrl = Paths.get(musicPath).toUri().toString();
            }
            // 创建媒体和播放器
            Media media = new Media(musicUrl);
            mediaPlayer = new MediaPlayer(media);
            // 监听播放状态变化
            mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == MediaPlayer.Status.READY) {
                    // 音乐准备好后启用播放按钮
                    playButton.setDisable(false);
                    pauseButton.setDisable(true);
                } else if (newValue == MediaPlayer.Status.PLAYING) {
                    playButton.setDisable(true);
                    pauseButton.setDisable(false);
                } else if (newValue == MediaPlayer.Status.PAUSED) {
                    playButton.setDisable(false);
                    pauseButton.setDisable(true);
                } else if (newValue == MediaPlayer.Status.STOPPED) {
                    playButton.setDisable(false);
                    pauseButton.setDisable(true);
                }
            });
            // 添加播放结束监听
            mediaPlayer.setOnEndOfMedia(() -> {
                playButton.setDisable(false);
                pauseButton.setDisable(true);
            });
        } catch (Exception e) {
            showAlert("音乐加载失败", "无法加载音乐: " + e.getMessage());
            System.out.println( e.getMessage());
            e.printStackTrace();
        }
    }
    // 停止当前音乐播放
    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }

        playButton.setDisable(true);
        pauseButton.setDisable(true);
    }
    //currentMusicPath = "/music/song1.mp3";
    //loadMusic(currentMusicPath);
    // 播放音乐
    @FXML
    public void playAction(ActionEvent actionEvent) {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }
    // 暂停音乐
    @FXML
    public void pauseAction(ActionEvent actionEvent) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void landAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择音乐文件");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("音频文件", "*.mp3", "*.wav", "*.ogg"),
                new FileChooser.ExtensionFilter("所有文件", "*.*")
        );
        Stage stage = (Stage) landchoose.getScene().getWindow();
        java.io.File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            currentMusicPath = file.getAbsolutePath();
            loadMusic(currentMusicPath);
        }
    }

    public void song1Action(ActionEvent actionEvent) {
        currentMusicPath = "/music/song1.mp3";
        loadMusic(currentMusicPath);
    }

    public void song2Action(ActionEvent actionEvent) {
        currentMusicPath = "/music/song2.mp3";
        loadMusic(currentMusicPath);
    }
}

