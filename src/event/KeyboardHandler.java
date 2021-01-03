package event;

import api.ShellShockAPI;
import api.WinAPI;
import javafx.scene.paint.Color;
import main.Main;
import display.*;

public class KeyboardHandler
{
	private final int pressTime = 100;
	boolean lctrlPressed = false;

	public KeyboardHandler()
	{
		new Thread(() ->
		{
			while (true)
			{
				try
				{
					listenKeys();
				} catch (InterruptedException e)
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
		short uKey = WinAPI.getKey(0x55);

		if ((lctrl & 0xFF00) > 0 && !lctrlPressed)
		{
			lctrlPressed = true;
			keyPressed((short) 0xA2);
		}
		else if ((lctrl & 0xFF00) == 0 && lctrlPressed)
		{
			lctrlPressed = false;
			keyReleased((short) 0xA2);
		}

		if ((iKey & 0xFF00) > 0)
			keyPressed((short) 0x49);

		if ((jKey & 0xFF00) > 0)
			keyPressed((short) 0x4A);

		if ((kKey & 0xFF00) > 0)
			keyPressed((short) 0x4B);

		if ((lKey & 0xFF00) > 0)
			keyPressed((short) 0x4C);

		if ((uKey & 0xFF00) > 0)
			keyPressed((short) 0x55);
	}

	private void keyPressed(short key) throws InterruptedException
	{
		int angle, power;
		switch (key)
		{
			case 0xA2:
				Main.getScene().setFill(Color.rgb(0, 0, 0, 0.5f));
				break;

			case 0x49:
				power = (int) Main.getParabulator().getPower();
				if (Main.getParabulator().getPower() < 100)
					Main.getParabulator().setPower(power + 1);
				Thread.sleep(pressTime);
				break;

			case 0x4A:
				angle = Main.getParabulator().getAngle();
				Main.getParabulator().setAngle(angle + 1);
				Thread.sleep(pressTime);
				break;

			case 0x4B:
				power = (int) Main.getParabulator().getPower();
				Main.getParabulator().setPower(power - 1);
				Thread.sleep(pressTime);
				break;

			case 0x4C:
				angle = Main.getParabulator().getAngle();
				Main.getParabulator().setAngle(angle - 1);
				Thread.sleep(pressTime);
				break;

			case 0x55:
				switch (Main.getParabulator().getType())
				{
					case 0:
						Main.setParabulator(new HoverballParabulator(Main.getParabulator()));
						break;
					case 1:
						Main.setParabulator(new HeavyHoverballParabulator(Main.getParabulator()));
						break;
					case 2:
						Main.setParabulator(new BatteringRamParabulator(Main.getParabulator()));
						break;
					case 3:
						Main.setParabulator(new BoomerangParabulator(Main.getParabulator()));
						break;
					case 4:
						Main.setParabulator(new PayloadParabulator(Main.getParabulator()));
						break;
					case 5:
						Main.setParabulator(new FighterJetParabulator(Main.getParabulator()));
						break;
					case 6:
						Main.setParabulator(new Parabulator(Main.getParabulator()));
						break;
				}
				Thread.sleep(pressTime * 2);
				break;
		}
	}

	private void keyReleased(short key)
	{
		switch (key)
		{
			case 0xA2:
				System.out.println("released");
				Main.getScene().setFill(Color.rgb(0, 0, 0, 0.0f));
				WinAPI.focusWindow(Main.getPos().getHwnd());
				break;
		}
	}
}
