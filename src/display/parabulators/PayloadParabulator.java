package display.parabulators;

public class PayloadParabulator extends Parabulator
{
	protected final String name = "Payload";

	@Override
	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		if(t < getApex() && getApex() <= t + timestep)
		{
			points.add(new double[]{
							xOffset + (power * getApex() * Math.cos(Math.toRadians(angle)) + wind * windmult * getApex() * getApex() / 2) * width / 1280,
							height - yOffset - ((power * getApex() * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(getApex(), 2)) * width / 1280)
			});
			points.add(new double[]{
					xOffset + (power * getApex() * Math.cos(Math.toRadians(angle))+ wind * windmult * getApex() * getApex() / 2) * width / 1280,
					height
			});
			points.add(new double[]{
							xOffset + (power * getApex() * Math.cos(Math.toRadians(angle)) + wind * windmult * getApex() * getApex() / 2) * width / 1280,
							height - yOffset - ((power * getApex() * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(getApex(), 2)) * width / 1280)
			});
		}
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
