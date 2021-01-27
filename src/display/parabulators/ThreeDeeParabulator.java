package display.parabulators;

import display.parabulators.Parabulator;

public class ThreeDeeParabulator extends Parabulator
{
	protected final String name = "3D-Bomb";
	protected final double modifier = 0.00499d;

	@Override
	protected void calculatePoints()
	{
		double[] point = new double[2];
		point[0] = xOffset;
		point[1] = height - yOffset;
		points.add(point);

		point = new double[2];
		point[0] = xOffset + power * modifier * Math.cos(Math.toRadians(angle)) * width;
		point[1] = height - yOffset - power * modifier * Math.sin(Math.toRadians(angle)) * width;
		points.add(point);
	}

	@Override
	public String getName()
	{
		return name;
	}
}
