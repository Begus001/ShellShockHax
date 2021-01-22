package event;

import api.ShellShockAPI;
import api.WinAPI;
import display.parabulators.*;
import javafx.scene.paint.Color;
import main.Main;

import java.util.concurrent.TimeUnit;

public class KeyboardHandler
{
	private final int pressTime = 75;
	private boolean delay = false;

	private boolean lCtrlPressed = false;
	private boolean lShiftPressed = false;
	private boolean lShiftToggled = false;
	private boolean uKeyPressed = false;
	private boolean hKeyPressed = false;

	public KeyboardHandler()
	{
		new Thread(() ->
		{
			while(true)
			{
				listenKeys();
				sleep(1);
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

		if(WinAPI.getKey(Keys.uKey.val))
			uKeyPressed();
		else
			uKeyReleased();

		if(WinAPI.getKey(Keys.hKey.val))
			hKeyPressed();
		else
			hKeyReleased();

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

		Main.setSceneOpaque();
	}

	private void lCtrlReleased()
	{
		if(!lCtrlPressed)
			return;
		lCtrlPressed = false;

		Main.setSceneTransparent();
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
		if(uKeyPressed)
			return;
		uKeyPressed = true;

		Main.nextParabulator();
	}

	private void uKeyReleased()
	{
		if(!uKeyPressed)
			return;
		uKeyPressed = false;
	}

	private void hKeyPressed()
	{
		if(hKeyPressed)
			return;
		hKeyPressed = true;

		Main.prevParabulator();
	}

	private void hKeyReleased()
	{
		if(!hKeyPressed)
			return;
		hKeyPressed = false;
	}

	public enum Keys
	{
		lCtrl(0xA2),
		lShift(0xA0),
		iKey(0x49),
		jKey(0x4A),
		kKey(0x4B),
		lKey(0x4C),
		uKey(0x55),
		hKey(0x48);

		public final int val;

		Keys(int val)
		{
			this.val = val;
		}
	}
}
