package display;

public class FighterJetParabulator extends Parabulator
{
	public FighterJetParabulator()
	{
		super();
	}

	protected double[] getPoint(double t)
	{
		double[] point = new double[2];
		point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
		point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
		if(t < getApex() && getApex() <= t + timestep && Math.cos(Math.toRadians(angle)) != 0.0f)
		{
			points.add(getPoint(getApex()));
			if(Math.cos(Math.toRadians(angle)) < 0)
				point[0] = -1;
			else if(Math.cos(Math.toRadians(angle)) > 0)
				point[0] = width + 1;
			point[1] = getPoint(getApex())[1];
		}
		return point;
	}

	public int getType()
	{
		return 6;
	}

	public String getMode()
	{
		return "Kampffighterjet mode";
	}
}
