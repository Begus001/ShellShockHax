package display;

public class HeavyHoverballParabulator extends Parabulator
{
	private final double hoverTime = 9.49;

	public HeavyHoverballParabulator()
	{
		super();
	}

	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		if(t < getApex() && getApex() <= t + timestep)
		{
			xOffset += (int) Math.round((power * Math.cos(Math.toRadians(angle))) * hoverTime * width / 1916);
		}
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	public int getType()
	{
		return 2;
	}

	public String getMode()
	{
		return "Heavy hoverball mode";
	}
}
