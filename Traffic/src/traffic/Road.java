package traffic;

import java.awt.*;

public class Road {
	public void paintRoad(Graphics g)
	{
		
		//just testing rendering hints
		Graphics2D brush = (Graphics2D) g;
		brush.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		//rendering ends here
		//draw road first
		brush.setColor(Color.gray);
		brush.fillRect(0,0,600,600);
		//temporary white lines to divide the map
		brush.setColor(Color.white);
		brush.drawLine(300, 0, 300, 600);//centerline
		brush.drawLine(0, 300, 600, 300);//centerline
		brush.drawLine(295, 0, 295, 600);//inner left line
		brush.drawLine(305, 0, 305, 600);//inner right line
		brush.drawLine(0, 295, 600, 295);//inner top line
		brush.drawLine(0, 305, 600, 305);//inner bottom line
		//car lanes
		brush.drawLine(295-40,0,295-40,600);//N1 lane 1 or S1 lane 2
		brush.drawLine(305+40,0,305+40,600);//S1 lane 1 or N1 lane 2
		brush.drawLine(0,295-40,600,295-40);//E1 lane 1 or W1 lane 2
		brush.drawLine(0, 305+40, 600, 305+40);//W1 lane 1 or E1 lane 2
		//pavements
		brush.drawLine(295-50, 0, 295-50, 600);//N1 left pavement
		brush.drawLine(305+50,0,305+50,600);//N1 right pavement
		brush.drawLine(0,295-50,600,295-50);//W1 top pavement
		brush.drawLine(0,305+50, 600, 305+50);//W1 bottom pavement		
	}

}
