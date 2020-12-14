package event;

import java.util.EventListener;

public interface WindowListener extends EventListener
{
	void windowChanged(WindowEvent e);
}