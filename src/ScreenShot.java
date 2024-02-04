import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;

public class ScreenShot {
	
	private static String folderPath= "/home/" + System.getProperty("user.name") + "/Immagini/";
	
	// Creazione del file con titolo basato sulla data e l'ora
	String timestamp = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss").format(new Date());
	String fileName = "Screenshot " + timestamp + ".png";
	File outputFile = new File(folderPath + fileName);
	
	WritableImage imgReturn;
	
	public ScreenShot(Rectangle rectangle) {
        
        Robot robot= new Robot();
        
        Rectangle2D rectangle2D = new Rectangle2D(
                rectangle.getX(),
                rectangle.getY(),
                rectangle.getWidth(),
                rectangle.getHeight()
        );
        
        imgReturn = robot.getScreenCapture(null, rectangle2D);
        try {
			ImageIO.write(SwingFXUtils.fromFXImage(imgReturn, null),
			        "png", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println(imgReturn);
	}
	
	
	public void copyimage() {
		
		// Copia l'immagine negli Appunti
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putImage(imgReturn);
        clipboard.setContent(content);
	}
	
}