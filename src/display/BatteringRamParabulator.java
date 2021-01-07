package display;

import event.ParabulaListener;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class BatteringRamParabulator extends Parabulator
{
	public static double gravityfactor = 4.9;


	public int getType()
	{
		return 3;
	}

	public BatteringRamParabulator()
	{
		super();
	}

	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		if (t < getApex())
		{
			point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
			point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		}
		else
		{
			point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
			point[1] = height - yOffset - ((power * getApex() * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(getApex(), 2) -
					gravity * gravityfactor * Math.pow(t - getApex(), 2) / 2) * width / 1280);
		}
		return point;
	}

	public String getMode()
	{
		return "Battering ram mode";
	}
}
