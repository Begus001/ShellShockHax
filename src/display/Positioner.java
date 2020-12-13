package display;

import com.sun.jna.platform.win32.WinDef;
import main.WinAPI;

public class Positioner
{
	private WinDef.HWND hwnd;
	private WinDef.RECT rect;
	private boolean shadow = true;
	private double dpi;

	// TODO: Fullscreen support

	public Positioner(String lockTo)
	{
		hwnd = WinAPI.findWindow(lockTo);

		if(hwnd == null)
		{
			System.err.printf("The window '%s' could not be found", lockTo);
			System.exit(0x01);
		}

		updatePosition();
	}

	public void updatePosition()
	{
		rect = WinAPI.getWindowPosition(hwnd);
		dpi = WinAPI.getDPI(hwnd) / 96.0d;
		System.out.printf("%f/%f   %f/%f\n", getLeft(), getTop(), getWidth(), getHeight());
	}

	public double getLeft()
	{
		return shadow ? rect.left + 10.0f / dpi : rect.left;
	}

	public double getTop()
	{
		return shadow ? rect.top + 45.0f / dpi : rect.top;
	}

	public double getWidth()
	{
		return getRight() - getLeft();
	}

	public double getHeight()
	{
		return getBottom() - getTop();
	}

	public double getRight()
	{
		return shadow ? rect.right - 10.0f / dpi : rect.right;
	}

	public double getBottom()
	{
		return shadow ? rect.bottom - 10.0f / dpi : rect.bottom;
	}
}
