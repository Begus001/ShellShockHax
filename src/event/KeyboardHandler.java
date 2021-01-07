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
				power = (int) Parabulator.getPower();
				if (Parabulator.getPower() < 100)
					Parabulator.setPower(power + 1);
				Thread.sleep(pressTime);
				break;

			case 0x4A:
				angle = Parabulator.getAngle();
				Parabulator.setAngle(angle + 1);
				Thread.sleep(pressTime);
				break;

			case 0x4B:
				power = (int) Parabulator.getPower();
				Parabulator.setPower(power - 1);
				Thread.sleep(pressTime);
				break;

			case 0x4C:
				angle = Parabulator.getAngle();
				Parabulator.setAngle(angle - 1);
				Thread.sleep(pressTime);
				break;

			case 0x55:
				switch (Main.getParabulator().getType())
				{
					case 0:
						Main.setParabulator(new HoverballParabulator());
						break;
					case 1:
						Main.setParabulator(new HeavyHoverballParabulator());
						break;
					case 2:
						Main.setParabulator(new BatteringRamParabulator());
						break;
					case 3:
						Main.setParabulator(new BoomerangParabulator());
						break;
					case 4:
						Main.setParabulator(new PayloadParabulator());
						break;
					case 5:
						Main.setParabulator(new FighterJetParabulator());
						break;
					case 6:
						Main.setParabulator(new Parabulator());
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
				Main.getScene().setFill(Color.rgb(0, 0, 0, 0.0f));
				WinAPI.focusWindow(Main.getPos().getHwnd());
				break;
		}
	}
}
