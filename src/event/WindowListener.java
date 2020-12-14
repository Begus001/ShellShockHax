package event;

import java.util.EventListener;

public interface WindowListener extends EventListener
{
	void windowMoved(WindowEvent e);
	void windowResized(WindowEvent e);
}