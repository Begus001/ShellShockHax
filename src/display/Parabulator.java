package display;

import event.ParabulaEvent;
import event.ParabulaListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Parabulator
{
	private double width, height;
	private List<double[]> points;
	private int angle = 69;
	private int power = 69;
	private double gravity = 6.02;
	private int xOffset = 0, yOffset = 0;
	private GraphicsContext gc;
	private List<ParabulaListener> listeners = new ArrayList<>();

	public Parabulator(GraphicsContext gc, double width, double height)
	{
		this.gc = gc;
		this.width = width;
		this.height = height;

		points = new ArrayList<>();
	}

	public int getAngle()
	{
		return angle;
	}

	public void setAngle(int angle)
	{
		this.angle = angle;
		invokeParabulaChanged();
	}

	private void invokeParabulaChanged()
	{
		for(ParabulaListener listener : listeners)
		{
			listener.parabulaChanged(new ParabulaEvent(this, xOffset, yOffset, angle, power, gravity));
		}
	}

	public int getPower()
	{
		return power;
	}

	public void setPower(int power)
	{
		this.power = power;
		invokeParabulaChanged();
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

		calculatePoints();

		gc.setStroke(Color.RED);

		for(int i = 1; i < points.size(); i++)
		{
			gc.strokeLine(points.get(i)[0], points.get(i)[1], points.get(i - 1)[0], points.get(i - 1)[1]);
			//			System.out.printf("drawing %f/%f %f/%f\n", points.get(i)[0], points.get(i)[1], points.get(i - 1)[0], points.get(i - 1)[1]);
		}

		gc.setFont(new Font(24));
		gc.fillText("Power: " + power + " Angle: " + (angle > 90 ? 180 - angle : angle), 15, 100);
	}

	private void clear()
	{
		gc.clearRect(0, 0, width, height);
		points.clear();
	}

	private void calculatePoints()
	{
		int t = 0;
		while(true)
		{
			double[] point = new double[2];
			point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
			point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);

			points.add(point);

			if(point[0] < 0 || point[0] > width || point[1] > height)
				break;

			t++;
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
}
