package main;

import display.Positioner;
import event.WindowEvent;
import event.WindowListener;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application implements WindowListener
{
	private Stage main;

	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		main = stage;

		Positioner pos = new Positioner("ShellShock Live");

		Group root = new Group();
		Pane pane = new Pane();
		Canvas canvas = new Canvas(pos.getWidth(), pos.getHeight());
		canvas.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0);");

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.RED);

		pane.getChildren().add(canvas);
		root.getChildren().add(pane);

		Scene scene = new Scene(root);
		scene.setFill(Color.rgb(0, 0, 0, 0.9f));

		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);

		gc.fillRect(50, 50, pos.getWidth() - 100, pos.getHeight() - 100);

		stage.show();

		pos.addWindowListener(this);

		stage.setX(pos.getX());
		stage.setY(pos.getY());
		stage.setWidth(pos.getWidth());
		stage.setHeight(pos.getHeight());
	}

	@Override
	public void windowChanged(WindowEvent e)
	{
		main.setX(e.x);
		main.setY(e.y);
		main.setWidth(e.width);
		main.setHeight(e.height);

		if(Double.isInfinite(e.x) || Double.isInfinite(e.y) || Double.isInfinite(e.width) || Double.isInfinite(e.height))
			System.exit(0);
	}
}
