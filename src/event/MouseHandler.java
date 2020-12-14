package event;

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
			Main.getParabulator().setxOffset((int) e.getX());
			Main.getParabulator().setyOffset((int) (Main.getPos().getHeight() - e.getY()));
			System.out.printf("%d/%d   %f/%f\n", Main.getParabulator().getxOffset(), Main.getParabulator().getyOffset(), e.getX(), Main.getPos().getHeight() - e.getY());
		}
	}
}
