package FRONT.View;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicView extends Application {

    private MediaPlayer mediaPlayer;
    private Label statusLabel = new Label("请选择一首歌曲");

    @Override
    public void start(Stage primaryStage) {
        // 按钮定义
        Button chooseButton = new Button("选择歌曲");
        Button playButton = new Button("播放");
        Button pauseButton = new Button("暂停");
        Button resumeButton = new Button("继续");

        // 按钮点击事件
        chooseButton.setOnAction(e -> chooseSong(primaryStage));
        playButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.play();
                statusLabel.setText("播放中");
            }
        });
        pauseButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                statusLabel.setText("已暂停");
            }
        });
        resumeButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.play(); // resume 和 play 同一个方法
                statusLabel.setText("继续播放");
            }
        });

        VBox root = new VBox(10, chooseButton, playButton, pauseButton, resumeButton, statusLabel);
        root.setStyle("-fx-padding: 20; -fx-alignment: center");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("简单音乐播放器");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 选择文件的方法
    private void chooseSong(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择音乐文件");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("音频文件", "*.mp3", "*.wav", "*.aac")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();  // 停止上一个播放
            }
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            statusLabel.setText("已加载：" + file.getName());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

