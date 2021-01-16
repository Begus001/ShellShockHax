package api;


import com.sun.jna.Library;
import com.sun.jna.Native;
import display.Parabulator;

import java.util.concurrent.TimeUnit;

public class ShellShockAPI
{
	protected static boolean overridden = false;

	public static void listen()
	{
		new Thread(() ->
		{
			while(true)
			{
				if(isOverridden())
				{
					try
					{
						TimeUnit.MILLISECONDS.sleep(100);
					} catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					continue;
				}

				Parabulator.setPower(getPower());
				Parabulator.setAngle(getAngle());
				try
				{
					TimeUnit.MILLISECONDS.sleep(100);
				} catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static boolean isOverridden()
	{
		return overridden;
	}

	public static void setOverridden(boolean overridden)
	{
		ShellShockAPI.overridden = overridden;
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
