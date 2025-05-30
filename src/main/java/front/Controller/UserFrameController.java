package front.Controller;

import back.ENTITY.Game;
import back.ENTITY.User;
import back.SERVICE.SerImpl.GameSerImpl;
import front.GUI.WalletFrame;
import front.GUI.GameDetailsFrame;
import front.GUI.wishlistFrame;
import front.Views.UserFrameApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
public class UserFrameController {

    @FXML private MenuItem homeMenuItem, featuresMenuItem, discoveryMenuItem, wishListMenuItem;
    @FXML private MenuItem collectionMenuItem, downloadMenuItem;
    @FXML private MenuItem walletMenuItem, lockMenuItem, exitMenuItem;
    @FXML private TextField typeField, minPriceField, maxPriceField;
    @FXML private Button searchButton, refreshButton;
    @FXML private TableView<Game> gameTableView;
    @FXML private TableColumn<Game, String> nameColumn;
    @FXML private TableColumn<Game, String> typeColumn;
    @FXML private TableColumn<Game, Integer> scoreColumn;
    @FXML private TableColumn<Game, Double> priceColumn;

    private User curUser;
    private final GameSerImpl gs = new GameSerImpl();
    private final ObservableList<Game> gameList = FXCollections.observableArrayList();

    public void initialize() {


// 假设 getName() 是 String，getScore() 是 double 等
        nameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        typeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getType()));
        scoreColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<Integer>(data.getValue().getScore()));
        priceColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPrice()));

        gameTableView.setItems(gameList);

        searchButton.setOnAction(e -> onSearch());
        refreshButton.setOnAction(e -> refresh("", 0, 1e9));
        featuresMenuItem.setOnAction(e -> showFeatures(80));
        walletMenuItem.setOnAction(e -> new WalletFrame(curUser));
        lockMenuItem.setOnAction(e -> {
            ((Stage) gameTableView.getScene().getWindow()).close();
            new UserFrameApp();
        });
        exitMenuItem.setOnAction(e -> System.exit(0));
        wishListMenuItem.setOnAction(e -> new wishlistFrame(curUser));

        gameTableView.setRowFactory(tv -> {
            TableRow<Game> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Game game = row.getItem();
                    new GameDetailsFrame(curUser, gs.getWholeInfo(game.getName().toString()));
                }
            });
            return row;
        });

        refresh("", 0, 1e9);
    }

    private void onSearch() {
        try {
            String type = typeField.getText();
            double min = minPriceField.getText().isEmpty() ? 0 : Double.parseDouble(minPriceField.getText());
            double max = maxPriceField.getText().isEmpty() ? 1e9 : Double.parseDouble(maxPriceField.getText());
            refresh(type, min, max);
        } catch (NumberFormatException e) {
            showAlert("价格格式错误", "请输入合法的数字");
        }
    }

    private void refresh(String type, double min, double max) {
        gameList.setAll(gs.getGameBySearch(type, min, max));
    }

    private void showFeatures(int score) {
        List<Game> featured = gs.getGameByScore(score);
        gameList.setAll(featured);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setCurUser(User user) {
        this.curUser = user;
    }
}
