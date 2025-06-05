package front.Controller;

import back.DAO.DaoImpl.GameDaoImpl;
import back.DAO.GameDao;
import back.ENTITY.Game;
import front.MainApp;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class userController {
    private MainApp mainApp;  // 保存主应用引用

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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
    @FXML private Slider priceRange;
    @FXML private Button reset;
    @FXML private Button searching;
    @FXML private MenuButton shop;
    @FXML private TableView<Game> shoptable;
    @FXML private TitledPane tabbar;
    @FXML private MenuItem walllet;
    @FXML private MenuItem wishList;

    private final GameDao gameDao = new GameDaoImpl();

    @FXML
    public void initialize() {
        pictureTable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.10));
        nameTable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.40));
        blanceTable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.15));
        priceTable.prefWidthProperty().bind(Bindings.multiply(shoptable.widthProperty(), 0.35));

        // 设置列与属性的绑定
        pictureTable.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));
        nameTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        blanceTable.setCellValueFactory(new PropertyValueFactory<>("score"));
        priceTable.setCellValueFactory(new PropertyValueFactory<>("price"));

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
                imageView.setFitHeight(60);
                imageView.setPreserveRatio(true);
                setGraphic(imageView);
            }
        });

        pictureTable.setResizable(false);
        nameTable.setResizable(false);
        blanceTable.setResizable(false);
        priceTable.setResizable(false);

        shoptable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // 加载游戏数据
        loadGameData();

        // 其他按钮逻辑
        unpurchased.setOnAction(event -> unpurchased.setText(unpurchased.isSelected() ? "已购买" : "未购买"));
        gather.setOnAction(event -> gather.setText(gather.isSelected() ? "交集" : "并集"));
    }

    private void loadGameData() {
        List<Game> games = gameDao.getAll();
        ObservableList<Game> observableGames = FXCollections.observableArrayList(games);
        shoptable.setItems(observableGames);
    }

    // 🆕 生成占位图：黑色背景，白色字（游戏名首字）
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
}

