package display;

import event.ParabulaListener;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class BoomerangParabulator extends Parabulator
{
	private final double boomerangmult = 0.0542;

	public int getType()
	{
		return 4;
	}


	public BoomerangParabulator(Parabulator toCopy)
	{
		super(toCopy);
	}

	protected double[] GetPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle)) - power * Math.cos(Math.toRadians(angle)) * boomerangmult * t * t / 2) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	public String getMode() {
		return "Boomerang mode";
	}
}
