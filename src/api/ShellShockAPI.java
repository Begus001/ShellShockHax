package api;


import com.sun.jna.Library;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.Native;
import display.Parabulator;

public class ShellShockAPI
{
	public static void listen()
	{
		new Thread(() ->
		{
			while(true)
			{
				Parabulator.setPower(getPower());
				Parabulator.setAngle(getAngle());
				try
				{
					Thread.sleep(100);
				} catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	private static int getPower()
	{
		return ShellConnect.INSTANCE.GetPower();
	}

	private static int getAngle()
	{
		return ShellConnect.INSTANCE.GetAngle();
	}

	public interface ShellConnect extends Library
	{
		ShellConnect INSTANCE = Native.load("ShellShockLib", ShellConnect.class);

		int GetPower();
		int GetAngle();
	}
}
