package api;


import com.sun.jna.Library;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.Native;

public class ShellShockAPI
{
	public static WinDef.DWORD getPower()
	{
		return ShellConnect.INSTANCE.GetPower();
	}

	public static WinDef.DWORD getAngle()
	{
		return ShellConnect.INSTANCE.GetAngle();
	}

	public interface ShellConnect extends Library
	{
		ShellConnect INSTANCE = Native.load("ShellShockLib", ShellConnect.class);

		WinDef.DWORD GetPower();
		WinDef.DWORD GetAngle();
	}
}
