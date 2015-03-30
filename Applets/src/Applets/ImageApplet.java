package Applets;

import java.awt.*;
import java.applet.*;

/*
<applet code="ImageApplet" width=400 height=300>
</applet>
*/

public class ImageApplet extends Applet {
	private Image anyImage;

	public void init(){
		anyImage = Toolkit.getDefaultToolkit().getImage("Java.jpg");
	}

	public void paint(Graphics g){
		g.drawImage(anyImage, 50,25, this);
		showStatus("image loaded");
	}
}