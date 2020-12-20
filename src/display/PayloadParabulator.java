package display;

import event.ParabulaListener;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class PayloadParabulator extends Parabulator
{

	public int getType()
	{
		return 5;
	}


	public PayloadParabulator(Parabulator toCopy)
	{
		super(toCopy);
	}

	protected double[] GetPoint(double t)
	{
		double[] point = new double[2];
		if (t < getApex() && getApex() <= t + timestep)
		{
			points.add(new double[] {
					xOffset + (power * getApex() * Math.cos(Math.toRadians(angle))) * width / 1280,
					height - yOffset - ((power * getApex() * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(getApex(), 2)) * width / 1280)
			});
			points.add(new double[] {
					xOffset + (power * getApex() * Math.cos(Math.toRadians(angle))) * width / 1280,
					height
			});
			points.add(new double[] {
					xOffset + (power * getApex() * Math.cos(Math.toRadians(angle))) * width / 1280,
					height - yOffset - ((power * getApex() * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(getApex(), 2)) * width / 1280)
			});
		}
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	public String getMode() {
		return "Payload mode";
	}
}
