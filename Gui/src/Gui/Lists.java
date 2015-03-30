package Gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Lists{
	JLabel label;
	JList list;
	String[] towns;

	public Lists(){
		label = new JLabel("");
		towns = new String[]{"Yendi","Tamale","Bolga","Sunyani","Gusheigu","Bimbilla"};
		list = new JList(towns);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent le){
				int index = list.getSelectedIndex();
				if(index != -1){
					label.setText("Place to visit is "+towns[index]);
				}
			}
		});

		JFrame frame = new JFrame("Using Lists");
		frame.setLayout(new FlowLayout());
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(list);
		frame.add(label);

		frame.setVisible(true);
	}

	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Lists();
			}
		});
	}
}