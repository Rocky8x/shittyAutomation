package com.auto.core;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout.Constraints;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class LiveReport extends JFrame implements ITestListener {

	public static final long serialVersionUID = 1;

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		setTitle(context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public LiveReport() {

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 700);
		setBackground(Color.darkGray);

		GridBagLayout	grid	= new GridBagLayout();
		grid.rowHeights = new int[] {50,50};
		Constraints row = new Constraints( );
		JPanel		panel	= new JPanel(grid);
		panel.setBounds(5,5,WIDTH,HEIGHT);

		// add components to panel
		JTextField jtTcName = new JTextField();
		jtTcName.setText("Test case ");
		jtTcName.setBounds(0,0,WIDTH, 25);
		panel.add(jtTcName);
		
		JTextField jtResult = new JTextField();
		jtResult.setText("Result");
		jtResult.setBounds(0,0,WIDTH, 25);
		panel.add(jtResult);


		
		JScrollPane scrollPane = new JScrollPane(panel);
		add(scrollPane);

		paintAll(getGraphics());
	}
}
