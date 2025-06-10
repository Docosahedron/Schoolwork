package FRONT.Controller;

import BACK.Entity.*;
import BACK.Service.SerImpl.GameSerImpl;
import BACK.Service.SerImpl.ReviewSerImpl;
import BACK.Service.SerImpl.UserSerImpl;
import BACK.Service.SerImpl.WishlistSerImpl;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

import static FRONT.View.GameView.showAlert;

public class gameController {
    private Game game;
    private User user;
    UserSerImpl us = new UserSerImpl();
    GameSerImpl gd = new GameSerImpl();
    @FXML
    private TableColumn<?, ?> authortable;

    @FXML
    private Button buyButton;

    @FXML
    private TableColumn<Review, String> conteenttable;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label genreLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private TableView<Review> reviewtable;

    @FXML
    private TableColumn<?, ?> timetable;

    @FXML
    private Button wishlistButton;

    WishlistSerImpl wd = new WishlistSerImpl();
    @FXML
    public void wishAddAction(){
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return !wd.queryAdded(user, game) && wd.addWishlist(user, game);
            }
        };

        task.setOnSucceeded(e -> {
            boolean success = task.getValue();
            if (success) {
                showAlert(null, "添加成功");
            } else {
                showAlert(null, "添加失败");
            }
        });

        task.setOnFailed(e -> {
            showAlert(null, "添加失败: " + task.getException().getMessage());
        });

        new Thread(task).start();
    }

    @FXML
    public void addAction() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("确认购买");
        confirm.setHeaderText(null);
        confirm.setContentText("确定要购买该游戏吗？");

        // 显示确认框并等待用户操作
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // 用户确认后再执行耗时操作
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        us.buy(user, game);
                        return null;
                    }
                };

                task.setOnSucceeded(e -> {
                    showAlert(null, "购买成功！");
                });

                task.setOnFailed(e -> {
                    showAlert(null, "购买失败：" + task.getException().getMessage());
                });

                new Thread(task).start();
            }
        });
    }


    ReviewSerImpl rw = new ReviewSerImpl();


    public void setGame(Game game) {
        this.game = game;
        loadGameData();
    }

    public void setUser(User user) {
        this.user = user;
    }
    private void loadGameData() {
        if (game == null) return;

        genreLabel.setText(game.getType());
        ratingLabel.setText(String.format("%d", game.getScore()));
        priceLabel.setText("￥" + String.format("%.2f", game.getPrice()));
        descriptionArea.setText(game.getOverview());

        conteenttable.prefWidthProperty().bind(Bindings.multiply(reviewtable.widthProperty(), 0.65));
        authortable.prefWidthProperty().bind(Bindings.multiply(reviewtable.widthProperty(), 0.15));
        timetable.prefWidthProperty().bind(Bindings.multiply(reviewtable.widthProperty(), 0.20));

        // 设置列与属性的绑定
        conteenttable.setCellValueFactory(new PropertyValueFactory<>("content"));

        conteenttable.setCellFactory(column -> new TableCell<>() {
            private final Text text = new Text();

            {
                // 绑定列宽，使文字能自动换行
                text.wrappingWidthProperty().bind(conteenttable.widthProperty().subtract(10));
                setPrefHeight(Control.USE_COMPUTED_SIZE); // 关键：撑高行高
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        });
        authortable.setCellValueFactory(new PropertyValueFactory<>("author"));
        timetable.setCellValueFactory(new PropertyValueFactory<>("time"));
        // 加载评论
        loadReviews();
        // 模拟评论数据
    }
    private void loadReviews() {

        List<Review> reviews = rw.getReviews(game);
        ObservableList<Review> observableReviews = FXCollections.observableArrayList(reviews);
        reviewtable.setItems(observableReviews);
    }

}
