package Gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ScrollPane{
	JLabel label;
	JList list;
	JScrollPane spane;
	String[] towns;

	public ScrollPane(){
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

		spane = new JScrollPane(list);
		spane.setPreferredSize(new Dimension(120,90));
		JFrame frame = new JFrame("Using Lists");
		frame.setLayout(new FlowLayout());
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(spane); //automatically adds the list
		//frame.add(list);
		frame.add(label);

		frame.setVisible(true);
	}

	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new ScrollPane();
			}
		});
	}
}