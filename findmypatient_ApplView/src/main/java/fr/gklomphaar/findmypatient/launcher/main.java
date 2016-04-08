/**
 * 
 */
package fr.gklomphaar.findmypatient.launcher;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import fr.gklomphaar.findmypatient.view.loginview;
import fr.gklomphaar.findmypatient.config.exceptions.ConfigurationFileException;
import fr.gklomphaar.findmypatient.controller.IdentityController;


public class main {
	public static void main(String[] args) {
		
		// Startup
		try {
			// Create the controller
			final IdentityController controller = new IdentityController();
			
			// Start GUI
			System.out.println("Starting GUI");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						loginview obj = new loginview(controller);
						System.out.println("Program running");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		} 
		// Configuration loading failed
		catch (ConfigurationFileException e1) {
			JOptionPane.showMessageDialog(null, "Error loading configuration files", "Startup failed", JOptionPane.ERROR_MESSAGE);
		}
	}

}
