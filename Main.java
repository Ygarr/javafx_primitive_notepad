import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Dodaty различні кнопки в рядок
        Menu File = new Menu("File");
        MenuItem Open = new MenuItem("Open");
        MenuItem Save = new MenuItem("Save");
        MenuItem Clear = new MenuItem("Clear");
        MenuItem Exit = new MenuItem("Exit");
        // відображення значка
        ImageView openImg = new ImageView(new Image("res/open-file.png"));
        ImageView saveImg = new ImageView(new Image("res/save-icon-1.png"));
        openImg.setFitHeight(24);//зменшуємо
        saveImg.setFitHeight(24);
        openImg.setFitWidth(24);
        saveImg.setFitWidth(24);
        openImg.setPreserveRatio(true);//зберігаємо зменшеність пікчі
        openImg.setPreserveRatio(true);
        Open.setGraphic(openImg);
        Save.setGraphic(saveImg);

        File.getItems().addAll(Open, Save, Clear, Exit);

        // Створити верхнюю строку
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        menuBar.getMenus().add(File);

        // Натисніть Exit для безпосереднього вихода з программи
        Exit.setOnAction(e -> {
            Platform.exit();
        });

        // Іконка второй лінії
        HBox two = new HBox();
        Button opens = new Button();
        Button saves = new Button();
        opens.setStyle("-fx-background-image: url('"+"res/open-file.png"+"'); "+ "-fx-background-position: center center;"+"-fx-background-repeat: stretch;"+"-fx-background-size: 32;");
        opens.setMinSize(32, 32);
        opens.setMaxSize(32, 32);
        saves.setStyle("-fx-background-image: url('"+"res/save-icon-1.png"+"');"+ "-fx-background-position: center center;"+"-fx-background-repeat: stretch;"+ "-fx-background-size: 32;");
        saves.setMinSize(32, 32);
        saves.setMinSize(32, 32);
        two.setSpacing(10);
        two.getChildren().addAll(opens, saves);

        // Третя строка встановлює колір переднего плана
        VBox qjs = new VBox();
        Text Qcolor = new Text("Колір тексту:");
        ColorPicker qspick = new ColorPicker();
        qspick.setValue(Color.SADDLEBROWN);//https: //docs.oracle.com/javase/8/javafx/api/javafx/scene/paint/Color.html
        //qjs.getChildren().addAll(Qcolor, qspick);
        two.getChildren().addAll(Qcolor, qspick);

        // Третья строка устанавливает цвет фона
        VBox bjs = new VBox();
        Text textColor = new Text("Колір рамки й фона:");
        ColorPicker bspick = new ColorPicker();
        ColorPicker bgPick = new ColorPicker();
        bspick.setValue(Color.BURLYWOOD);
        bgPick.setValue(Color.CORNSILK);
        //bjs.getChildren().addAll(textColor, bspick);
        two.getChildren().addAll(textColor, bspick,bgPick);

        // объединяем третью строку
        HBox three = new HBox();
        three.setSpacing(30);
        three.getChildren().addAll(qjs, bjs);
        three.setAlignment(Pos.CENTER);

        // верхній бокс
        VBox top = new VBox();
        Separator line1 = new Separator();
        Separator line2 = new Separator();
        top.setSpacing(3);
        top.getChildren().addAll(menuBar, two, line1, three);

        // ввод текстy
        TextArea textArea = new TextArea();
        Rectangle rectangle = new Rectangle(0, 0, 600, 650);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, textArea);
        textArea.setStyle("border:0; background-color:transparent; overflow: hidden; border-style:none;");
        rectangle.setFill(Color.color(0.5, 0.5, 0.5));

        // Цвет переднего плана меняет цвет шрифта
        qspick.setOnAction(e -> {
            changeColor(qspick, bspick, bgPick,textArea);
        });

        // цвет фона меняет цвет рамкі
        bspick.setOnAction(e -> {
            changeColor(qspick, bspick, bgPick, textArea);
        });

        //Дія стосовно фону
        bgPick.setOnAction(e -> {
            changeColor(qspick, bspick, bgPick, textArea);
        });

        // Натисніть на строку меню, чтоби очистить
        Clear.setOnAction(e -> {
            textArea.setText("");
        });

        // Натискаємо Open вверху
        Open.setOnAction(e -> {
            open(primaryStage, textArea);
        });

        // Натискаємо openimg
        opens.setOnMouseClicked(e -> {
            open(primaryStage, textArea);
        });

        // Натискаємо нагорі, аби зберегти
        Save.setOnAction(e -> {
            save(primaryStage, textArea);
        });

        // Натискаємо saveimg
        saves.setOnMouseClicked(e -> {
            save(primaryStage, textArea);
        });

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(stackPane);
        Scene scene = new Scene(root, 600, 650);
        primaryStage.setTitle("REDAHTOR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ВІдкриваємо операцію зчитування файла
    static void open(Stage stage, TextArea textArea) {
        FileChooser fp = new FileChooser();
        fp.setTitle("Обрати файл-відкрити");
        java.io.File file = fp.showOpenDialog(stage);
        if (file != null && file.exists()) {
            try {
                // Зчитуєм данні в декілька рядків тексту
                FileInputStream in = new FileInputStream(file);
                in.read(bs);
                byte[] bs = new byte[(int)file.length()];
                // Встановлюєм вміст в багаторядкове текстове поле
                textArea.setText(new String(bs));
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // збереження у фс
    static void save(Stage stage, TextArea textArea) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Вибрати файл-зберегти");
        java.io.File file = fc.showSaveDialog(stage);
        if (file!=null) {
            // Записати зміст багаторядкового текстового поля у файл, на котрий вказує файл
            try {
                // Создать вихідний потік
                FileOutputStream out = new FileOutputStream(file);
                out.write(textArea.getText().getBytes());
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // змінити колір
    private static void changeColor(ColorPicker one, ColorPicker two, ColorPicker three, TextArea textArea) {
        String temp1 = String.format("%s", one.getValue());//font
        String temp2 = String.format("%s", two.getValue());// (frame) "-fx-background-color: ___"
        String temp3 = String.format("%s", three.getValue());//background
        textArea.setStyle(String.format("-fx-text-fill: #%s; -fx-background-color: #%s", temp1.substring(2), temp2.substring(2)));
        textArea.lookup(".content").setStyle(String.format("-fx-background-color: #%s", temp3.substring(2))); //THE background(text-area-background)
    }
}
