package display;

import event.ParabulaListener;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class HoverballParabulator extends Parabulator
{
	private final double hoverTime = 7.74;

	public int getType()
	{
		return 1;
	}


	public HoverballParabulator(Parabulator toCopy)
	{
		super(toCopy);
	}

	protected double[] GetPoint(double t)
	{
		double[] point = new double[2];
		if (t < getApex() && getApex() <= t + timestep)
		{
			xOffset += (int) Math.round((power * Math.cos(Math.toRadians(angle))) * hoverTime * width / 1916);
		}
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	public String getMode()
	{
		return "Hoverball mode";
	}
}
