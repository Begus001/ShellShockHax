package event;

import java.util.EventListener;

public interface ParabulaListener extends EventListener
{
	void parabulaChanged(ParabulaEvent e);
}
