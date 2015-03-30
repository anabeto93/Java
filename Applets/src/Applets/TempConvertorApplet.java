package Applets;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

/*
<applet code="TempConvertorApplet" width=400 height=250>
</applet>
*/

public class TempConvertorApplet extends Applet{
	JLabel tempLabel, outputLabel;
	JTextField text;
	JButton button;
	String []temps;
	JList list;
	int tempIndex;

	static double convert(int temp, String scale){
		double result=0;
		if(scale.equals("Celsius")){
			//return 
			result = (temp - 32.0)*(5.0/9.0);
		}else if(scale.equals("Fahrenheit")){
			//return 
			result = temp*(9.0/5.0)+32.0;
		}
		return result;
	}

	public void init(){
		tempLabel = new JLabel("Temperature: ");
		outputLabel = new JLabel("");
		text = new JTextField(5);
		button = new JButton("Convert");
		temps = new String[]{"Celius","Fahrenheit"};
		list = new JList(temps);
		setLayout( new GridLayout(3,2));

		list.addListSelectionListener(new ListSelectionListener(){
			public void  valueChanged(ListSelectionEvent le){
				tempIndex = list.getSelectedIndex();
			}
		});

		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int temp = Integer.parseInt(text.getText());
				String scale = temps[tempIndex];
				outputLabel.setText(Double.toString(convert(temp, scale)));
			}
		});

		add(tempLabel);
		add(text);
		add(list);
		add(button);
		add(outputLabel);

		setVisible(true);
	}
}