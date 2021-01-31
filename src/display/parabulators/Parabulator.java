package display.parabulators;

import api.ShellShockAPI;
import display.GraphicsTextbox;
import event.KeyboardHandler;
import event.ParabulaEvent;
import event.ParabulaListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Parabulator implements Comparable<Parabulator>
{
	protected static final double timestep = 0.05;
	protected static final double gravity = 6.02;
	protected static final int wind = 0;
	protected static final double windmult = 0.00765;

	protected static final List<ParabulaListener> listeners = new ArrayList<>();

	protected static int angle = 69;
	protected static int power = 69;
	protected static double width, height;

	protected static int xOffset = 0, yOffset = 0;

	protected static GraphicsContext gc;

	protected final List<double[]> points = new ArrayList<>();

	protected final GraphicsTextbox tb = new GraphicsTextbox(gc);

	protected final String name = "Default";

	public static void init(GraphicsContext gc, double width, double height)
	{
		Parabulator.gc = gc;
		Parabulator.width = width;
		Parabulator.height = height;
	}

	public static List<Parabulator> loadParabulators()
	{
		List<Parabulator> parabulators = new ArrayList<>();
		List<String> parabsToLoad = new ArrayList<>();

		try
		{
			String rootPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

			if(rootPath.endsWith(".jar"))
			{
				ZipFile zip = new ZipFile(rootPath);

				for(Enumeration<?> list = zip.entries(); list.hasMoreElements(); )
				{
					ZipEntry current = (ZipEntry) list.nextElement();
					if(!current.getName().startsWith("display/parabulators/") || !current.getName().endsWith(".class"))
						continue;
					parabsToLoad.add(current.getName().replace(".class", "").replace("display/parabulators/", ""));
				}

				zip.close();
			} else
			{
				File parabulatorDirectory = new File(rootPath + "/display/parabulators");

				for(File current : Objects.requireNonNull(parabulatorDirectory.listFiles()))
					parabsToLoad.add(current.getName().replace(".class", ""));
			}

			ClassLoader cl = new URLClassLoader(new URL[]{Parabulator.class.getResource("/")});

			for(String current : parabsToLoad)
			{
				Class<?> clazz;
				try
				{
					clazz = cl.loadClass("display.parabulators." + current);
				} catch(NullPointerException e)
				{
					System.err.printf("Couldn't load %s\n", current);
					continue;
				}

				parabulators.add((Parabulator) clazz.getConstructor().newInstance());
				System.out.println("Loaded class " + current);
			}

		} catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | URISyntaxException | IOException e)
		{
			System.err.println("Couldn't load parabulators");
			e.printStackTrace();
		}

		Collections.sort(parabulators);

		return parabulators;
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

		int displayAngle = angle % 360 > 90 ? 180 - angle % 360 : angle % 360;

		tb.clearText();
		tb.setPosition(new int[]{15, 80});

		tb.addLine("Power: " + power + " Angle: " + displayAngle);
		tb.addLine("Weapon: " + getName());
		tb.addLine("Overridden: " + (ShellShockAPI.isOverridden() ? "Yes" : "No"));
		if(KeyboardHandler.isChatMode())
			tb.addLine("CHAT MODE");

		tb.draw();
	}

	public void clear()
	{
		gc.clearRect(0, 0, width, height);
		points.clear();
	}

	protected void calculatePoints()
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

	public String getName()
	{
		return name;
	}

	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle)) + wind * windmult * t * t / 2) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	@Override
	public int compareTo(Parabulator p)
	{
		if(getName() == null || p.getName() == null)
			return 0;

		return getName().compareTo(p.getName());
	}

	protected double getApex()
	{
		return power * Math.sin(Math.toRadians(angle)) / gravity;
	}
}
