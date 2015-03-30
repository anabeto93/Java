package Gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class TempConvertor{
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

	public TempConvertor(){
		tempLabel = new JLabel("Temperature: ");
		outputLabel = new JLabel("");
		text = new JTextField(5);
		button = new JButton("Convert");
		temps = new String[]{"Celcius","Fahrenheit"};
		list = new JList(temps);

		JFrame frame = new JFrame("Temperature Convertor");
		frame.setLayout(new GridLayout(3,2)); //3 rows and 2 columns
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent le){
				tempIndex = list.getSelectedIndex();
			}
		});

		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int temp = Integer.parseInt(text.getText());
				String scale = temps[tempIndex];
				outputLabel.setText(Double.toString(convert(temp,scale)));
			}
		});

		frame.add(tempLabel);
		frame.add(text);
		frame.add(list);
		frame.add(button);
		frame.add(outputLabel);
		frame.setVisible(true);
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new TempConvertor();
			}
		});
	}
}