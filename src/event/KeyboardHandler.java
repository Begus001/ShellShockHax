package event;

import api.WinAPI;
import javafx.scene.paint.Color;
import main.Main;

public class KeyboardHandler
{
	boolean lctrlPressed = false;

	public KeyboardHandler()
	{
		new Thread(() ->
		{
			while(true)
			{
				try
				{
					listenKeys();
				} catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void listenKeys() throws InterruptedException
	{
		short lctrl = WinAPI.getKey(0xA2);
		short iKey = WinAPI.getKey(0x49);
		short jKey = WinAPI.getKey(0x4A);
		short kKey = WinAPI.getKey(0x4B);
		short lKey = WinAPI.getKey(0x4C);

		if((lctrl & 0xFF00) > 0 && !lctrlPressed)
		{
			lctrlPressed = true;
			keyPressed((short) 0xA2);
		} else if((lctrl & 0xFF00) == 0 && lctrlPressed)
		{
			lctrlPressed = false;
			keyReleased((short) 0xA2);
		}

		if((iKey & 0xFF00) > 0)
			keyPressed((short) 0x49);

		if((jKey & 0xFF00) > 0)
			keyPressed((short) 0x4A);

		if((kKey & 0xFF00) > 0)
			keyPressed((short) 0x4B);

		if((lKey & 0xFF00) > 0)
			keyPressed((short) 0x4C);
	}

	private void keyPressed(short key) throws InterruptedException
	{
		int angle, power;
		switch(key)
		{
			case 0xA2:
				System.out.println("pressed");
				Main.getScene().setFill(Color.rgb(0, 0, 0, 0.5f));
				break;

			case 0x49:
				power = Main.getParabulator().getPower();
				Main.getParabulator().setPower(power + 1);
				Thread.sleep(150);
				break;

			case 0x4A:
				angle = Main.getParabulator().getAngle();
				Main.getParabulator().setAngle(angle + 1);
				Thread.sleep(150);
				break;

			case 0x4B:
				power = Main.getParabulator().getPower();
				Main.getParabulator().setPower(power - 1);
				Thread.sleep(150);
				break;

			case 0x4C:
				angle = Main.getParabulator().getAngle();
				Main.getParabulator().setAngle(angle - 1);
				Thread.sleep(150);
				break;
		}
	}

	private void keyReleased(short key)
	{
		switch(key)
		{
			case 0xA2:
				System.out.println("released");
				Main.getScene().setFill(Color.rgb(0, 0, 0, 0.0f));
				WinAPI.focusWindow(Main.getPos().getHwnd());
				break;
		}
	}
}
