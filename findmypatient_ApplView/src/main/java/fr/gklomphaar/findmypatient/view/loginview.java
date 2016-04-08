package fr.gklomphaar.findmypatient.view;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import fr.gklomphaar.findmypatient.controller.IdentityController;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import sun.misc.BASE64Decoder;

import java.io.*;


public class loginview
{
	
	//Variables for the GUI
	private  JFrame obj;
	private  JTextField loginField;
	private  JTextField passField;
	
	private  JLabel username;
	private  JLabel password;
	private  JTextArea pta;
	
	private  JButton log_in;
	private  JButton cancel;
	
	private  JPanel panel;
	private  JPanel panel1;
	private  JPanel panel2;
	
	private final IdentityController identityController;
    
	public loginview(final IdentityController identityController)
	{
		this.identityController = identityController;
		
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    obj = new JFrame("Find  My Patient");
		
	    
	    //Declaration for the Variables and positioning of panels.
		JPanel panel  = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
    	
    	JLabel label = new JLabel(new ImageIcon("./src/main/resources/images/hospital.png"));
        panel.add(label);
		obj.add(panel,BorderLayout.NORTH);
	
		panel1.setLayout(new GridLayout(3,2));
		username = new JLabel(" **** Username **** :");
		password = new JLabel(" **** Password **** :");
		loginField = new JTextField("");
		passField = new JPasswordField("");
		panel1.add(username);
		panel1.add(loginField);
		panel1.add(password);
		panel1.add(passField);
		log_in = new JButton("Login");
		cancel = new JButton("Cancel");
		panel1.add(log_in);
		panel1.add(cancel);
		obj.getContentPane().add(panel1,BorderLayout.CENTER);
		
		// Login button
		log_in.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				tryLogin();
			}		
		});
		// Also capture enter for login
		passField.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyPressed(KeyEvent e) {
	    		if(e.getKeyChar() == KeyEvent.VK_ENTER)
	    		{
	    			tryLogin();
	    		}
	    	}
	    });
		loginField.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyPressed(KeyEvent e) {
	    		if(e.getKeyChar() == KeyEvent.VK_ENTER)
	    		{
	    			tryLogin();
	    		}
	    	}
	    });
		// Cancel button
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent sig)
			{
				System.exit(0);
			}
		});

		obj.setBounds(100, 100, 550, 500);
		obj.setResizable(false);
		obj.setVisible(true);			
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private void tryLogin()
	{
		// Setup the connection to the DB
		try {
			this.identityController.setupDatabase();
		} catch (DaoInitializationException e) {
			JOptionPane.showMessageDialog(log_in, 
							"Could not connect to the database. Make sure the database is running at: " +
							this.identityController.getConfiguration().getDatabaseURL(), "Database connection error", JOptionPane.ERROR_MESSAGE);
		}
		
		// Login to the system.
		try {
			final String stringName = loginField.getText();
			final String stringPass = passField.getText();
			
			// Login
			identityController.getUserAuthority().login(stringPass, stringPass);
			
			// Hide the login window
			obj.setVisible(false);
						
			// Show new window
			PatientOverviewView patientOverview = new PatientOverviewView(identityController);
			patientOverview.setVisible(true);
			
		}
		// Check for authority
		catch (NoAuthorityException e1) {
			
			JOptionPane.showMessageDialog(log_in, "Incorrect credentials", "Login failed", JOptionPane.ERROR_MESSAGE);
		}
		// Check for DAO problems
		catch (DaoLoadObjectException e1) {
			
			JOptionPane.showMessageDialog(log_in, "Problems with retrieving credential information", "Login failed", JOptionPane.ERROR_MESSAGE);
		}
	}
}