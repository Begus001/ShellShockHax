package display;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class GraphicsTextbox
{
	private final GraphicsContext gc;
	private final List<String> text = new ArrayList<>();

	private Color textColor = Color.WHITE;
	private Color backgroundColor = Color.rgb(0, 0, 0, 0.5f);

	private String fontFamily = "";
	private int fontSize = 24;
	private int lineSpacing = 5;
	private int margin = 15;

	private int[] position = new int[]{0, 0};

	public GraphicsTextbox(GraphicsContext gc)
	{
		this.gc = gc;
	}

	public GraphicsTextbox(GraphicsContext gc, int fontSize, int lineSpacing, int margin, int[] position)
	{
		this.gc = gc;
		setFontSize(fontSize);
		setLineSpacing(lineSpacing);
		setMargin(margin);
		setPosition(position);
	}

	public void draw()
	{
		if(text.size() < 1)
			return;

		Color prevColor = (Color) gc.getFill();
		Font prevFont = gc.getFont();

		Font font = new Font(fontFamily, fontSize);
		FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
		int vmargin = (int) (margin - fm.getDescent() / 2);

		gc.setFill(backgroundColor);
		gc.fillRect(position[0], position[1], getMaxWidth(text, fm) + margin * 2, fm.getAscent() * text.size() + lineSpacing * (text.size() - 1) + vmargin * 2);

		gc.setFill(textColor);
		gc.setFont(font);
		gc.setTextBaseline(VPos.BASELINE);
		for(int i = 0; i < text.size(); i++)
			gc.fillText(text.get(i), position[0] + margin, position[1] + vmargin + (fontSize + lineSpacing) * i + fontSize);

		gc.setFill(prevColor);
		gc.setFont(prevFont);
	}

	protected float getMaxWidth(List<String> text, FontMetrics fm)
	{
		float max = 0.0f, tmp;
		for(String line : text)
			if((tmp = fm.computeStringWidth(line)) > max)
				max = tmp;

		return max;
	}

	public int getMargin()
	{
		return margin;
	}

	public void setMargin(int margin)
	{
		if(margin > 0)
			this.margin = margin;
	}

	public String getFontFamily()
	{
		return fontFamily;
	}

	public void setFontFamily(String fontFamily)
	{
		this.fontFamily = fontFamily;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public void setFontSize(int fontSize)
	{
		if(fontSize > 0)
			this.fontSize = fontSize;
	}

	public int getLineSpacing()
	{
		return lineSpacing;
	}

	public void setLineSpacing(int lineSpacing)
	{
		this.lineSpacing = lineSpacing;
	}

	public int[] getPosition()
	{
		return position;
	}

	public void setPosition(int[] position)
	{
		if(position.length > 0 && position[0] >= 0 && position[1] >= 0)
			this.position = position;
	}

	public Color getTextColor()
	{
		return textColor;
	}

	public void setTextColor(Color textColor)
	{
		this.textColor = textColor;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public String getLine(int lineNumber)
	{
		return text.get(lineNumber);
	}

	public void addLine(String line)
	{
		text.add(line);
	}

	public void clearText()
	{
		text.clear();
	}
}
