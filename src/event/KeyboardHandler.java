package event;

import api.ShellShockAPI;
import api.WinAPI;
import display.*;
import javafx.scene.paint.Color;
import main.Main;

import java.util.concurrent.TimeUnit;

public class KeyboardHandler
{
	private final int pressTime = 75;
	private boolean lCtrlPressed = false;
	private boolean lShiftPressed = false;
	private boolean lShiftToggled = false;
	private boolean delay = false;

	public KeyboardHandler()
	{
		new Thread(() ->
		{
			while(true)
			{
				listenKeys();
				sleep(10);
			}
		}).start();
	}

	private void listenKeys()
	{
		if(WinAPI.getKey(Keys.lCtrl.val))
			lCtrlPressed();
		else
			lCtrlReleased();

		if(WinAPI.getKey(Keys.lShift.val))
			lShiftPressed();
		else
			lShiftReleased();

		if(!ShellShockAPI.isOverridden())
			return;

		if(WinAPI.getKey(Keys.iKey.val))
			iKeyPressed();

		if(WinAPI.getKey(Keys.jKey.val))
			jKeyPressed();

		if(WinAPI.getKey(Keys.kKey.val))
			kKeyPressed();

		if(WinAPI.getKey(Keys.lKey.val))
			lKeyPressed();

		if(WinAPI.getKey(Keys.uKey.val))
			uKeyPressed();

		if(delay)
			sleep(pressTime);
	}

	private void sleep(long ms)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void lCtrlPressed()
	{
		if(lCtrlPressed)
			return;
		lCtrlPressed = true;

		Main.getScene().setFill(Color.rgb(0, 0, 0, 0.5f));
	}

	private void lCtrlReleased()
	{
		if(!lCtrlPressed)
			return;
		lCtrlPressed = false;

		Main.getScene().setFill(Color.rgb(0, 0, 0, 0.0f));
		WinAPI.focusWindow(Main.getPos().getHwnd());
	}

	private void lShiftPressed()
	{
		if(lShiftPressed)
			return;
		lShiftPressed = true;
		lShiftToggled = !lShiftToggled;

		ShellShockAPI.setOverridden(lShiftToggled);
		Parabulator.invokeParabulaChanged();
	}

	private void lShiftReleased()
	{
		if(!lShiftPressed)
			return;
		lShiftPressed = false;
	}

	private void iKeyPressed()
	{
		if(Parabulator.getPower() < 100)
			Parabulator.setPower(Parabulator.getPower() + 1);

		delay = true;
	}

	private void jKeyPressed()
	{
		Parabulator.setAngle(Parabulator.getAngle() + 1);
		delay = true;
	}

	private void kKeyPressed()
	{
		if(Parabulator.getPower() > 0)
			Parabulator.setPower(Parabulator.getPower() - 1);

		delay = true;
	}

	private void lKeyPressed()
	{
		Parabulator.setAngle(Parabulator.getAngle() - 1);
		delay = true;
	}

	private void uKeyPressed()
	{
		switch(Main.getParabulator().getType())
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
				Main.setParabulator(new BFGParabulator());
				break;
			case 7:
				Main.setParabulator(new Parabulator());
		}

		sleep(pressTime * 2);
	}

	public enum Keys
	{
		lCtrl(0xA2),
		lShift(0xA0),
		iKey(0x49),
		jKey(0x4A),
		kKey(0x4B),
		lKey(0x4C),
		uKey(0x55);

		public final int val;

		Keys(int val)
		{
			this.val = val;
		}
	}
}
