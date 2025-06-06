package front.Controller;


import back.DAO.DaoImpl.ReviewDaoImpl;
import back.DAO.ReviewDao;
import back.ENTITY.Game;
import back.ENTITY.Review;
import back.ENTITY.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

public class gameController {
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

    private final ReviewDao reviewDao = new ReviewDaoImpl();

    private Game game;
    public void setGame(Game game) {
        this.game = game;
        loadGameData();
    }
    private User user;
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
    public void initData() {
        System.out.println("用户名：" + user.getName());
        System.out.println("游戏名：" + game.getName());
    }
    private void loadReviews() {

        List<Review> reviews = reviewDao.getByName(game.getName());
        ObservableList<Review> observableReviews = FXCollections.observableArrayList(reviews);
        reviewtable.setItems(observableReviews);
    }

}
