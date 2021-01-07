package display;

import event.ParabulaEvent;
import event.ParabulaListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;

import java.util.ArrayList;
import java.util.List;

public class Parabulator
{
	protected static final double timestep = 0.05;
	protected static final double gravity = 6.02;

	protected static final List<ParabulaListener> listeners = new ArrayList<>();

	protected static int angle = 69;
	protected static int power = 69;
	protected static double width, height;

	protected static int xOffset = 0, yOffset = 0;
	protected static GraphicsContext gc;

	protected final List<double[]> points = new ArrayList<>();

	public Parabulator()
	{
		draw();
	}

	public void draw()
	{
		clear();

		int xOffsetPrev = xOffset;
		int yOffsetPrev = yOffset;
		gc.setStroke(Color.RED);


		xOffset = (int) Math.round(xOffset + Math.cos(Math.toRadians(angle)) * 25 * width / 1916);
		yOffset = (int) Math.round(yOffset + Math.sin(Math.toRadians(angle)) * 25 * width / 1916);

		calculatePoints();


		for(int i = 1; i < points.size(); i++)
		{
			gc.strokeLine(points.get(i)[0], points.get(i)[1], points.get(i - 1)[0], points.get(i - 1)[1]);
			//			System.out.printf("drawing %f/%f %f/%f\n", points.get(i)[0], points.get(i)[1], points.get(i - 1)[0], points.get(i - 1)[1]);
		}

		xOffset = xOffsetPrev;
		yOffset = yOffsetPrev;

		gc.setFont(new Font(24));
		int displayAngle = angle % 360 > 90 ? 180 - angle % 360 : angle % 360;
		gc.fillText("Power: " + power + " Angle: " + displayAngle + " " + getMode(), 15, 100);

	}

	private void clear()
	{
		gc.clearRect(0, 0, width, height);
		points.clear();
	}

	private void calculatePoints()
	{
		double t = 0;
		while(true)
		{
			double[] point = getPoint(t);
			points.add(point);

			if(point[0] < 0 || point[0] > width || point[1] > height)
				break;

			t += timestep;
		}
	}

	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	public Parabulator(GraphicsContext gc, double width, double height)
	{
		Parabulator.gc = gc;
		Parabulator.width = width;
		Parabulator.height = height;

		draw();
	}

	public static int getAngle()
	{
		return angle;
	}

	public static void setAngle(int angle)
	{
		if(angle == 1000)
			return;
		Parabulator.angle = angle;
		invokeParabulaChanged();
	}

	public static void invokeParabulaChanged()
	{
		for(ParabulaListener listener : listeners)
		{
			listener.parabulaChanged(new ParabulaEvent(Main.getParabulator(), xOffset, yOffset, angle, power, gravity));
		}
	}

	public static int getPower()
	{
		return power;
	}

	public static void setPower(int power)
	{
		Parabulator.power = power;
		invokeParabulaChanged();
	}

	public static int getyOffset()
	{
		return yOffset;
	}

	public static void setyOffset(int offset)
	{
		yOffset = offset;
		invokeParabulaChanged();
	}

	public static void setWidth(double w)
	{
		width = w;
		invokeParabulaChanged();
	}

	public static void setHeight(double h)
	{
		height = h;
		invokeParabulaChanged();
	}

	public static void addParabulaListener(ParabulaListener listener)
	{
		listeners.add(listener);
	}

	public static void removeParabulaListener(ParabulaListener listener)
	{
		listeners.remove(listener);
	}

	public static int getxOffset()
	{
		return xOffset;
	}

	public static void setxOffset(int offset)
	{
		xOffset = offset;
		invokeParabulaChanged();
	}

	public int getType()
	{
		return 0;
	}

	protected double getApex()
	{
		return power * Math.sin(Math.toRadians(angle)) / gravity;
	}

	public String getMode()
	{
		return "";
	}
}
