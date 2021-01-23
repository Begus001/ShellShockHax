package display.parabulators;

public class BFGParabulator extends Parabulator
{
	protected final String name = "BFG/Seagull";
	protected static double gravity = 6.12;

	@Override
	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle)) + wind * windmult * t * t / 2) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	@Override
	public String getName()
	{
		return name;
	}
}
