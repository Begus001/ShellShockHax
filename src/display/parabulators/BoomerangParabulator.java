package display.parabulators;

import display.parabulators.Parabulator;

public class BoomerangParabulator extends Parabulator
{
	protected final String name = "Boomerang";
	protected final double modifier = 0.0542d;

	@Override
	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle)) + wind * windmult * t * t / 2 - power * Math.cos(Math.toRadians(angle)) * modifier * t * t / 2) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	@Override
	public String getName()
	{
		return name;
	}
}
