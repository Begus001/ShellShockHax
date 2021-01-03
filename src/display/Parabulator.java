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
	protected final double timestep = 0.05;
	protected double width, height;
	protected List<double[]> points;
	protected static int angle = 69;
	protected static int power = 69;
	protected double gravity = 6.02;
	protected int xOffset = 0, yOffset = 0;
	protected GraphicsContext gc;
	protected List<ParabulaListener> listeners = new ArrayList<>();

	public Parabulator(GraphicsContext gc, double width, double height)
	{
		this.gc = gc;
		this.width = width;
		this.height = height;

		points = new ArrayList<>();
		draw();
	}

	public Parabulator(Parabulator toCopy)
	{
		this(toCopy.gc, toCopy.width, toCopy.height);
		points = toCopy.points;
		angle = toCopy.angle;
		power = toCopy.power;
		gravity = toCopy.gravity;
		xOffset = toCopy.xOffset;
		yOffset = toCopy.yOffset;
		listeners = toCopy.listeners;
		draw();
	}

	public static int getAngle()
	{
		return angle;
	}

	public int getType()
	{
		return 0;
	}

	public static void setAngle(int angle)
	{
		if(angle == 1000) return;
		Parabulator.angle = angle;
		Main.getParabulator().invokeParabulaChanged();
	}

	public void invokeParabulaChanged()
	{
		for (ParabulaListener listener : listeners)
		{
			listener.parabulaChanged(new ParabulaEvent(this, xOffset, yOffset, angle, power, gravity));
		}
	}

	protected double getApex()
	{
		return power * Math.sin(Math.toRadians(angle)) / gravity;
	}

	public static int getPower()
	{
		return power;
	}

	public static void setPower(int power)
	{
		Parabulator.power = power;
		Main.getParabulator().invokeParabulaChanged();
	}

	public int getxOffset()
	{
		return xOffset;
	}

	public void setxOffset(int xOffset)
	{
		this.xOffset = xOffset;
		invokeParabulaChanged();
	}

	public int getyOffset()
	{
		return yOffset;
	}

	public void setyOffset(int yOffset)
	{
		this.yOffset = yOffset;
		invokeParabulaChanged();
	}

	public void setWidth(double width)
	{
		this.width = width;
		invokeParabulaChanged();
	}

	public void setHeight(double height)
	{
		this.height = height;
		invokeParabulaChanged();
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


		for (int i = 1; i < points.size(); i++)
		{
			gc.strokeLine(points.get(i)[0], points.get(i)[1], points.get(i - 1)[0], points.get(i - 1)[1]);
			//			System.out.printf("drawing %f/%f %f/%f\n", points.get(i)[0], points.get(i)[1], points.get(i - 1)[0], points.get(i - 1)[1]);
		}

		xOffset = xOffsetPrev;
		yOffset = yOffsetPrev;

		gc.setFont(new Font(24));
		int displayAngle = angle % 360 > 90 ? 180 - angle % 360 : angle % 360;
//		gc.fillText("Power: " + power + " Angle: " + displayAngle + " " + getMode(), 15, 100);

	}

	private void clear()
	{
		gc.clearRect(0, 0, width, height);
		points.clear();
	}

	protected double[] GetPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	private void calculatePoints()
	{
		double t = 0;
		while (true)
		{
			double[] point = GetPoint(t);
			points.add(point);

			if (point[0] < 0 || point[0] > width || point[1] > height)
				break;

			t += timestep;
		}
	}

	public void addParabulaListener(ParabulaListener listener)
	{
		listeners.add(listener);
	}

	public void removeParabulaListener(ParabulaListener listener)
	{
		listeners.remove(listener);
	}

	public String getMode()
	{
		return "";
	}
}
