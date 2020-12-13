package main;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class WinAPI
{
	public interface User32 extends StdCallLibrary
	{
		User32 INSTANCE = (User32) Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

		WinDef.HWND FindWindow(String lpClassName, String lpWindowName);
		boolean GetWindowRect(WinDef.HWND hwnd, WinDef.RECT lpRect);
		WinDef.UINT GetDpiForWindow(WinDef.HWND hwnd);
	}

	public static WinDef.HWND findWindow(String name)
	{
		return User32.INSTANCE.FindWindow(null, name);
	}

	public static WinDef.RECT getWindowPosition(WinDef.HWND hwnd)
	{
		WinDef.RECT rect = new WinDef.RECT();
		User32.INSTANCE.GetWindowRect(hwnd, rect);
		return rect;
	}

	public static int getDPI(WinDef.HWND hwnd)
	{
		return User32.INSTANCE.GetDpiForWindow(hwnd).intValue();
	}

	// TODO: Move to foreground
}
