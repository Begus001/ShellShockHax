package event;

import display.Parabulator;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.Main;


public class MouseHandler implements EventHandler<MouseEvent>
{
	@Override
	public void handle(MouseEvent e)
	{
		if(e.getEventType() == MouseEvent.MOUSE_CLICKED)
		{
			Parabulator.setxOffset((int) e.getX());
			Parabulator.setyOffset((int) (Main.getPos().getHeight() - e.getY()));
		}
	}
}
