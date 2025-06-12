package FRONT.Controller;

import BACK.Dao.DaoImpl.WarehouseDaoImpl;
import BACK.Entity.*;
import BACK.Service.SerImpl.*;
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
    WarehouseSerImpl whd =  new WarehouseSerImpl();
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
    public void initialize() {
        // This will be called when the FXML is loaded
    }

    public void setGame(Game game) {
        this.game = game;
        loadGameData();
        updateWishlistButtonState();
        updateBuyButtonState();
    }

    public void setUser(User user) {
        this.user = user;
        updateWishlistButtonState();
        updateBuyButtonState();
    }

    private void updateBuyButtonState() {
        if (user == null || game == null) return;

        Task<Boolean> checkTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return whd.queryBought(user, game);
            }
        };

        checkTask.setOnSucceeded(e -> {
            boolean isPurchased = checkTask.getValue();
            if (isPurchased) {
                buyButton.setText("已购买");
                buyButton.setDisable(true);
                buyButton.setStyle("-fx-background-color: #cccccc;"); // Optional: gray out the button
            } else {
                buyButton.setText("购买");
                buyButton.setDisable(false);
                buyButton.setStyle(""); // Reset to default style
            }
        });

        checkTask.setOnFailed(e -> {
            showAlert(null, "检查购买状态失败: " + checkTask.getException().getMessage());
        });

        new Thread(checkTask).start();
    }

    private void updateWishlistButtonState() {
        if (user == null || game == null) return;

        Task<Boolean> checkTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return wd.queryAdded(user, game);
            }
        };

        checkTask.setOnSucceeded(e -> {
            boolean isInWishlist = checkTask.getValue();
            if (isInWishlist) {
                wishlistButton.setText("移除心愿单");
                wishlistButton.setOnAction(event -> removeFromWishlist());
            } else {
                wishlistButton.setText("添加到心愿单");
                wishlistButton.setOnAction(event -> wishAddAction());
            }
        });

        checkTask.setOnFailed(e -> {
            showAlert(null, "检查心愿单状态失败: " + checkTask.getException().getMessage());
        });

        new Thread(checkTask).start();
    }

    @FXML
    public void wishAddAction() {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return wd.addWishlist(user, game);
            }
        };

        task.setOnSucceeded(e -> {
            boolean success = task.getValue();
            if (success) {
                showAlert(null, "添加成功");
                updateWishlistButtonState();
            } else {
                showAlert(null, "添加失败");
            }
        });

        task.setOnFailed(e -> {
            showAlert(null, "添加失败: " + task.getException().getMessage());
        });

        new Thread(task).start();
    }

    private void removeFromWishlist() {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return wd.removeSelected(user, game);
            }
        };

        task.setOnSucceeded(e -> {
            boolean success = task.getValue();
            if (success) {
                showAlert(null, "移除成功");
                updateWishlistButtonState();
            } else {
                showAlert(null, "移除失败");
            }
        });

        task.setOnFailed(e -> {
            showAlert(null, "移除失败: " + task.getException().getMessage());
        });

        new Thread(task).start();
    }

    @FXML
    public void addAction() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("确认购买");
        confirm.setHeaderText(null);
        confirm.setContentText("确定要购买该游戏吗？");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        us.buy(user, game);
                        return null;
                    }
                };

                task.setOnSucceeded(e -> {
                    showAlert(null, "购买成功！");
                    updateBuyButtonState(); // Update button state after purchase
                });

                task.setOnFailed(e -> {
                    showAlert(null, "购买失败：" + task.getException().getMessage());
                });

                new Thread(task).start();
            }
        });
    }

    ReviewSerImpl rw = new ReviewSerImpl();

    private void loadGameData() {
        if (game == null) return;

        genreLabel.setText(game.getType());
        ratingLabel.setText(String.format("%d", game.getScore()));
        priceLabel.setText("￥" + String.format("%.2f", game.getPrice()));
        descriptionArea.setText(game.getOverview());

        conteenttable.prefWidthProperty().bind(Bindings.multiply(reviewtable.widthProperty(), 0.65));
        authortable.prefWidthProperty().bind(Bindings.multiply(reviewtable.widthProperty(), 0.15));
        timetable.prefWidthProperty().bind(Bindings.multiply(reviewtable.widthProperty(), 0.20));

        conteenttable.setCellValueFactory(new PropertyValueFactory<>("content"));
        conteenttable.setCellFactory(column -> new TableCell<>() {
            private final Text text = new Text();

            {
                text.wrappingWidthProperty().bind(conteenttable.widthProperty().subtract(10));
                setPrefHeight(Control.USE_COMPUTED_SIZE);
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
        loadReviews();
    }

    private void loadReviews() {
        List<Review> reviews = rw.getReviews(game);
        ObservableList<Review> observableReviews = FXCollections.observableArrayList(reviews);
        reviewtable.setItems(observableReviews);
    }
}
