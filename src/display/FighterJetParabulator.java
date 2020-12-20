package display;

import event.ParabulaListener;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class FighterJetParabulator extends Parabulator
{


		public int getType()
		{
			return 6;
		}


		public FighterJetParabulator(Parabulator toCopy)
		{
			super(toCopy);
		}

		protected double[] GetPoint(double t)
		{
			double[] point = new double[2];
			point[0] = xOffset + (power * t * Math.cos(Math.toRadians(angle))) * width / 1280;
			point[1] = height - yOffset - ((power * t * Math.sin(Math.toRadians(angle)) - (1.0f / 2.0f) * gravity * Math.pow(t, 2)) * width / 1280);
			if (t < getApex() && getApex() <= t + timestep && Math.cos(Math.toRadians(angle)) != 0.0f)
			{
				points.add(GetPoint(getApex()));
				if (Math.cos(Math.toRadians(angle)) < 0)
					point[0] = -1;
				else if (Math.cos(Math.toRadians(angle)) > 0)
					point[0] = width + 1;
				point[1] = GetPoint(getApex())[1];
			}
			return point;
		}

		public String getMode() {
			return "Kampffighterjet mode";
		}
}
