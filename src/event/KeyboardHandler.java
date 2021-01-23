package event;

import api.ShellShockAPI;
import api.WinAPI;
import display.parabulators.Parabulator;
import main.Main;

import java.util.concurrent.TimeUnit;

public class KeyboardHandler
{
	private static boolean chatMode = false;
	private final int pressTime = 75;
	private boolean delay = false;
	private boolean lCtrlPressed = false;
	private boolean lShiftPressed = false;
	private boolean lShiftToggled = false;
	private boolean uKeyPressed = false;
	private boolean oKeyPressed = false;
	private boolean rShiftPressed = false;

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

	public static boolean isChatMode()
	{
		return chatMode;
	}

	protected void listenKeys()
	{
		if(WinAPI.getKey(Keys.rShift.val))
			rShiftPressed();
		else
			rShiftReleased();

		if(chatMode)
			return;

		if(WinAPI.getKey(Keys.lCtrl.val))
			lCtrlPressed();
		else
			lCtrlReleased();

		if(WinAPI.getKey(Keys.lShift.val))
			lShiftPressed();
		else
			lShiftReleased();

		if(WinAPI.getKey(Keys.oKey.val))
			oKeyPressed();
		else
			oKeyReleased();

		if(WinAPI.getKey(Keys.uKey.val))
			uKeyPressed();
		else
			uKeyReleased();

		if(ShellShockAPI.isOverridden())
		{
			if(WinAPI.getKey(Keys.iKey.val))
				iKeyPressed();

			if(WinAPI.getKey(Keys.jKey.val))
				jKeyPressed();

			if(WinAPI.getKey(Keys.kKey.val))
				kKeyPressed();

			if(WinAPI.getKey(Keys.lKey.val))
				lKeyPressed();
		}

		if(delay)
			sleep(pressTime);
	}

	private void rShiftPressed()
	{
		if(rShiftPressed)
			return;
		rShiftPressed = true;

		chatMode = !chatMode;
		Parabulator.invokeParabulaChanged();
	}

	private void rShiftReleased()
	{
		if(!rShiftPressed)
			return;
		rShiftPressed = false;
	}

	protected void lCtrlPressed()
	{
		if(lCtrlPressed)
			return;
		lCtrlPressed = true;

		Main.setSceneOpaque();
	}

	protected void lCtrlReleased()
	{
		if(!lCtrlPressed)
			return;
		lCtrlPressed = false;

		Main.setSceneTransparent();
		WinAPI.focusWindow(Main.getPos().getHwnd());
	}

	protected void lShiftPressed()
	{
		if(lShiftPressed)
			return;
		lShiftPressed = true;
		lShiftToggled = !lShiftToggled;

		ShellShockAPI.setOverridden(lShiftToggled);
		Parabulator.invokeParabulaChanged();
	}

	protected void lShiftReleased()
	{
		if(!lShiftPressed)
			return;
		lShiftPressed = false;
	}

	protected void oKeyPressed()
	{
		if(oKeyPressed)
			return;
		oKeyPressed = true;

		Main.nextParabulator();
	}

	protected void oKeyReleased()
	{
		if(!oKeyPressed)
			return;
		oKeyPressed = false;
	}

	protected void uKeyPressed()
	{
		if(uKeyPressed)
			return;
		uKeyPressed = true;

		Main.prevParabulator();
	}

	protected void uKeyReleased()
	{
		if(!uKeyPressed)
			return;
		uKeyPressed = false;
	}

	protected void iKeyPressed()
	{
		if(Parabulator.getPower() < 100)
			Parabulator.setPower(Parabulator.getPower() + 1);

		delay = true;
	}

	protected void jKeyPressed()
	{
		Parabulator.setAngle(Parabulator.getAngle() + 1);
		delay = true;
	}

	protected void kKeyPressed()
	{
		if(Parabulator.getPower() > 0)
			Parabulator.setPower(Parabulator.getPower() - 1);

		delay = true;
	}

	protected void lKeyPressed()
	{
		Parabulator.setAngle(Parabulator.getAngle() - 1);
		delay = true;
	}

	protected void sleep(long ms)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private enum Keys
	{
		lCtrl(0xA2),
		lShift(0xA0),
		iKey(0x49),
		jKey(0x4A),
		kKey(0x4B),
		lKey(0x4C),
		uKey(0x55),
		oKey(0x4F),
		rShift(0xA1);

		public final int val;

		Keys(int val)
		{
			this.val = val;
		}
	}
}
