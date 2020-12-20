package main;

import display.*;
import event.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application implements WindowListener, ParabulaListener
{
	private static Stage main;
	private static int state = 0;
	private static Canvas canvas;


	private static Parabulator parabulator;
	private static Scene scene;
	private static Positioner pos;

	public static Positioner getPos()
	{
		return pos;
	}

	public static Parabulator getParabulator()
	{
		return parabulator;
	}
	public static void setParabulator(Parabulator parabulator) { Main.parabulator = parabulator; }

	public static Scene getScene()
	{
		return scene;
	}

	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		main = stage;

		pos = new Positioner("ShellShock Live");

		Group root = new Group();
		Pane pane = new Pane();
		canvas = new Canvas(pos.getWidth(), pos.getHeight());
		canvas.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0);");

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.RED);

		pane.getChildren().add(canvas);
		root.getChildren().add(pane);

		scene = new Scene(root);
		scene.setFill(Color.rgb(0, 0, 0, 0.0f));

		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		stage.setTitle("ShellShock Hax");

		parabulator = new Parabulator(gc, pos.getWidth(), pos.getHeight());
		parabulator.draw();

		stage.show();

		scene.addEventFilter(MouseEvent.MOUSE_CLICKED, new event.MouseHandler());
		new KeyboardHandler();
		parabulator.addParabulaListener(this);
		pos.addWindowListener(this);

		stage.setX(pos.getX());
		stage.setY(pos.getY());
		stage.setWidth(pos.getWidth());
		stage.setHeight(pos.getHeight());
	}


	@Override
	public void windowMoved(WindowEvent e)
	{
		main.setX(e.x);
		main.setY(e.y);

		checkRunning(e);
	}

	@Override
	public void windowResized(WindowEvent e)
	{
		main.setWidth(e.width);
		main.setHeight(e.height);

		canvas.setWidth(e.width);
		canvas.setHeight(e.height);

		parabulator.setWidth(e.width);
		parabulator.setHeight(e.height);

		checkRunning(e);
	}

	private void checkRunning(WindowEvent e)
	{
		if (Double.isInfinite(e.x) || Double.isInfinite(e.y) || Double.isInfinite(e.width) || Double.isInfinite(e.height))
			System.exit(0);
	}

	@Override
	public void parabulaChanged(ParabulaEvent e)
	{
		parabulator.draw();
	}
}
