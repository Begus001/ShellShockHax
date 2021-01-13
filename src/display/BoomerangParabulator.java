package display;

public class BoomerangParabulator extends Parabulator
{
	private final double boomerangmult = 0.0542;

	public BoomerangParabulator()
	{
		super();
	}

	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle)) + wind * windmult * t * t / 2 - power * Math.cos(Math.toRadians(angle)) * boomerangmult * t * t / 2) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	public int getType()
	{
		return 4;
	}

	public String getMode()
	{
		return "Boomerang mode";
	}
}
