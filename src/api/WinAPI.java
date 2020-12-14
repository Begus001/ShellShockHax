package api;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class WinAPI
{
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

	public static void focusWindow(WinDef.HWND hwnd)
	{
		User32.INSTANCE.SetForegroundWindow(hwnd);
	}

	public static short getKey(int key)
	{
		return User32.INSTANCE.GetKeyState(key);
	}

	public interface User32 extends StdCallLibrary
	{
		User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

		WinDef.HWND FindWindow(String lpClassName, String lpWindowName);

		WinDef.BOOL GetWindowRect(WinDef.HWND hwnd, WinDef.RECT lpRect);

		WinDef.UINT GetDpiForWindow(WinDef.HWND hwnd);

		WinDef.BOOL SetForegroundWindow(WinDef.HWND hwnd);

		short GetKeyState(int nVirtKey);
	}
}
