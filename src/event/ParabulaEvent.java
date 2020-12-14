package event;

import java.util.EventObject;

public class ParabulaEvent extends EventObject
{
	int xOffset, yOffset;
	int angle;
	int power;
	double gravity;

	public ParabulaEvent(Object source, int xOffset, int yOffset, int angle, int power, double gravity)
	{
		super(source);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.angle = angle;
		this.power = power;
		this.gravity = gravity;
	}
}
