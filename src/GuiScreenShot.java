import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;


public class GuiScreenShot extends Application {
	
	private static double startX, startY; // Coordinate di inizio del rettangolo
    private static Rectangle rectangle; // Il rettangolo da disegnare
    private static Rectangle mRectangle; //Rettangolo esterno
    private static Shape subtractedShape;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ScreenShot");
        
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Creazione di un layout di base (uno StackPane in questo caso)
        Pane root = new Pane();
        Scene scene = new Scene(root, screenWidth, screenHeight); // Definizione delle dimensioni della finestra

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOpacity(0.5);
        
        //Aggiungo rettangolo main a cui sottraggo altro rettangolo
        mRectangle = new Rectangle(0, 0, screenWidth, screenHeight);
        
        // Aggiungi un rettangolo vuoto alla scena
        rectangle = new Rectangle();
 
        
        // Sottrai il rettangolo da sottrarre dal rettangolo principale
        subtractedShape = Shape.subtract(mRectangle, rectangle);
        subtractedShape.setFill(Color.BLACK); // Imposta il colore trasparente
        
        //subtractedShape.setOpacity(0.8);
        root.getChildren().add(subtractedShape);
        
        Text text = new Text("L'apdq");
        text.setFill(Color.WHITESMOKE);
        text.setFont(new Font(35));
        text.setLayoutX(5);
        text.setLayoutY(30);
        root.getChildren().add(text);
        
        // Gestisci l'evento di clic sulla scena
        scene.setOnMousePressed(event -> {
            // Salva le coordinate di inizio del rettangolo
            startX = event.getSceneX();
            startY = event.getSceneY();
            
            // Imposta le coordinate e la dimensione del rettangolo
            rectangle.setX(startX);
            rectangle.setY(startY);
            rectangle.setWidth(0);
            rectangle.setHeight(0);
            
        });

        // Gestisci l'evento di trascinamento del mouse
        scene.setOnMouseDragged(event -> {
            // Aggiorna la dimensione del rettangolo durante il trascinamento
            double width = event.getSceneX() - startX;
            double height = event.getSceneY() - startY;

            //System.out.println(rectangle.getX() + "+" + rectangle.getY());
            
            rectangle.setWidth(width);
            rectangle.setHeight(height);
            
            // Crea una nuova forma Shape con le dimensioni aggiornate
            Shape newSubtractedShape = Shape.subtract(mRectangle, rectangle);
            newSubtractedShape.setFill(Color.BLACK);  // Imposta l'opacità

            // Rimuovi l'oggetto Shape esistente e aggiungi il nuovo
            root.getChildren().remove(subtractedShape);
            root.getChildren().add(newSubtractedShape);

            // Aggiorna la referenza all'oggetto Shape
            subtractedShape = newSubtractedShape;
        });
        
        scene.setOnMouseReleased(event -> {
        	//save image
        	
        	primaryStage.setOpacity(0);

            // Posticipa l'esecuzione del codice successivo nella successiva iterazione del ciclo di rendering
            Platform.runLater(() -> {
                // Aggiorna la scena per riflettere le modifiche di opacità
                primaryStage.setScene(scene);

                // Esegui il codice dello screenshot
                ScreenShot image = new ScreenShot(rectangle);
                image.copyimage();
                
                primaryStage.close();
            });
            
        });
        
        
        //primaryStage.setFullScreen(true);
        
        primaryStage.setScene(scene);
       //primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
