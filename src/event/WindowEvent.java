package event;

import java.util.EventObject;

public class WindowEvent extends EventObject
{
	public double x, y, width, height;

	public WindowEvent(Object source, double x, double y, double width, double height)
	{
		super(source);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
