package api;


import com.sun.jna.Library;
import com.sun.jna.Native;
import display.parabulators.Parabulator;

import java.util.concurrent.TimeUnit;

public class ShellShockAPI
{
	protected static boolean overridden = false;

	public static void listen()
	{
		new Thread(() ->
		{
			int power, angle, lastPower = -1, lastAngle = 270;

			while(true)
			{
				if(isOverridden())
				{
					lastPower = -1;
					lastAngle = 270;
					sleep(50);
					continue;
				}

				power = getPower();
				angle = getAngle();

				if(power < 0 || power > 100 || angle < -90 || angle >= 270 || (power == lastPower && angle == lastAngle))
				{
					sleep(10);
					continue;
				}

				Parabulator.setPower(power);
				Parabulator.setAngle(angle);
				lastPower = power;
				lastAngle = angle;

				sleep(10);
			}
		}).start();
	}

	private static void sleep(long ms)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch(InterruptedException e)
		{
			e.printStackTrace();
		}
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
