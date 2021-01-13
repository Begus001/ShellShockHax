package display;

public class BFGParabulator extends Parabulator
{
	protected static double gravity = 6.12;
	public BFGParabulator()
	{
		super();
	}

	@Override
	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))+ wind * windmult * t * t / 2) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	public int getType()
	{
		return 7;
	}

	public String getMode()
	{
		return "BFG mode";
	}
}
