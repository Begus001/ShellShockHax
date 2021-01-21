package display.parabulators;

public class HoverballParabulator extends Parabulator
{
	private final double hoverTime = 7.74;

	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		if(t < getApex() && getApex() <= t + timestep)
		{
			xOffset += (int) Math.round((power * Math.cos(Math.toRadians(angle))) * hoverTime * width / 1916);
		}
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))+ wind * windmult * t * t / 2) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		return point;
	}

	public int getType()
	{
		return 1;
	}

	public String getMode()
	{
		return "Hoverball mode";
	}
}
