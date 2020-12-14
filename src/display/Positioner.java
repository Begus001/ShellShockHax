package display;

import api.WinAPI;
import com.sun.jna.platform.win32.WinDef;
import event.WindowEvent;
import event.WindowListener;

import java.util.ArrayList;
import java.util.List;

public class Positioner
{
	private WinDef.HWND hwnd;
	private WinDef.RECT rect;
	private boolean shadow = true;
	private double dpi;
	private double lastX, lastY, lastWidth, lastHeight;

	private final List<WindowListener> listeners = new ArrayList<>();

	public Positioner(String lockTo)
	{
		hwnd = WinAPI.findWindow(lockTo);

		if(hwnd == null)
		{
			System.err.printf("The window '%s' could not be found", lockTo);
			System.exit(0x01);
		}

		rect = WinAPI.getWindowPosition(hwnd);

		lastX = getX();
		lastY = getY();
		lastWidth = getWidth();
		lastHeight = getHeight();

		WinAPI.focusWindow(hwnd);

		new Thread(() ->
		{
			while(true)
				updatePosition();
		}).start();
	}

	public double getX()
	{
		return shadow ? rect.left + 10.0f / dpi : rect.left;
	}

	public double getY()
	{
		return shadow ? rect.top + 45.0f / dpi : rect.top;
	}

	public double getWidth()
	{
		return getX1() - getX();
	}

	public double getHeight()
	{
		return getY1() - getY();
	}

	public void updatePosition()
	{
		rect = WinAPI.getWindowPosition(hwnd);
		dpi = WinAPI.getDPI(hwnd) / 96.0d;

		if(getX() != lastX || getY() != lastY || getWidth() != lastWidth || getHeight() != lastHeight)
		{
			lastX = getX();
			lastY = getY();
			lastWidth = getWidth();
			lastHeight = getHeight();

			for(WindowListener l : listeners)
			{
				l.windowChanged(new WindowEvent(this, getX(), getY(), getWidth(), getHeight()));
			}
		}
	}

	public double getX1()
	{
		return shadow ? rect.right - 10.0f / dpi : rect.right;
	}

	public double getY1()
	{
		return shadow ? rect.bottom - 10.0f / dpi : rect.bottom;
	}

	public void addWindowListener(WindowListener listener)
	{
		listeners.add(listener);
	}

	public void removeWindowListener(WindowListener listener)
	{
		listeners.remove(listener);
	}
}
