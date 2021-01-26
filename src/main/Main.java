package main;

import api.ShellShockAPI;
import display.Positioner;
import display.parabulators.Parabulator;
import event.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application implements WindowListener, ParabulaListener
{
	private static final List<Parabulator> parabulators = new ArrayList<>();
	private static Stage main;
	private static int state = 0;
	private static Canvas canvas;
	private static Parabulator parabulator;
	private static Scene scene;
	private static Positioner pos;
	private static int parabulatorIndex = 0;

	public static Positioner getPos()
	{
		return pos;
	}

	public static Parabulator getParabulator()
	{
		return parabulator;
	}

	public static void nextParabulator()
	{
		if(parabulatorIndex + 1 >= parabulators.size())
			parabulatorIndex = 0;
		else
			parabulatorIndex++;
		updateParabulator();
	}

	private static void updateParabulator()
	{
		parabulator = parabulators.get(parabulatorIndex);
		parabulator.draw();
	}

	public static void prevParabulator()
	{
		if(parabulatorIndex - 1 < 0)
			parabulatorIndex = parabulators.size() - 1;
		else
			parabulatorIndex--;
		updateParabulator();
	}

	public static void setSceneOpaque()
	{
		scene.setFill(Color.rgb(0, 0, 0, 0.5f));
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
		setSceneTransparent();

		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		stage.setTitle("ShellShock Hax");

		parabulator = new Parabulator(gc, pos.getWidth(), pos.getHeight());
		parabulator.draw();
		loadParabulators();
		ShellShockAPI.listen();

		stage.show();

		scene.addEventFilter(MouseEvent.MOUSE_CLICKED, new event.MouseHandler());
		new KeyboardHandler();
		Parabulator.addParabulaListener(this);
		pos.addWindowListener(this);

		stage.setX(pos.getX());
		stage.setY(pos.getY());
		stage.setWidth(pos.getWidth());
		stage.setHeight(pos.getHeight());
	}

	public static void setSceneTransparent()
	{
		scene.setFill(Color.rgb(0, 0, 0, 0.0f));
	}

	private static void loadParabulators()
	{
		parabulators.add(parabulator);
		parabulators.addAll(ParabulatorLoader.loadParabulators());
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

		Parabulator.setWidth(e.width);
		Parabulator.setHeight(e.height);

		checkRunning(e);
	}

	private void checkRunning(WindowEvent e)
	{
		if(Double.isInfinite(e.x) || Double.isInfinite(e.y) || Double.isInfinite(e.width) || Double.isInfinite(e.height))
			System.exit(0);
	}

	@Override
	public void parabulaChanged(ParabulaEvent e)
	{
		parabulator.draw();
	}
}
