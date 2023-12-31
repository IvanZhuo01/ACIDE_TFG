/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 *
 * Copyright (C) 2007-2023
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
 * 		- Version 0.18
 * 			- Sergio Garc�a Rodr�guez
 * 		- Version 0.19
 * 			- Carlos Gonz�lez Torres
 * 			- Cristina Lara L�pez
 * 			- Yuejie Xu
 * 			- Yihang Zhuo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package acide.main;

import java.awt.KeyboardFocusManager;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.listeners.AcideKeyEventDispatcher;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.splashScreen.AcideSplashScreenWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;

/**
 * ACIDE - A Configurable IDE main class.
 * 
 * @version 0.11
 */
public class AcideMain {
	/**
	 * Executes ACIDE - A Configurable IDE.
	 */
	private static void executeApplication() {

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see java.lang.Runnable#run()
				 */
				public void run() {

					// Shows the splash screen
					AcideSplashScreenWindow.getInstance().showSplashScreenWindow();

				}
			});
		} catch (InterruptedException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();

		} catch (InvocationTargetException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			public void run() {

				// Updates the log
				AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s555"));

				// Updates the log
				AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1027"));

				// Loads the ACIDE - A Configurable IDE workbench configuration
				AcideWorkbenchConfiguration.getInstance().load();

				// Closes the splash screen
				AcideSplashScreenWindow.getInstance().closeSplashScreenWindow();

				// Stars the system keys observer thread
				AcideKeyEventDispatcher systemKeysObserver = new AcideKeyEventDispatcher();

				// Shows the main window
				AcideMainWindow.getInstance().showAcideMainWindow();
				
				// Creates the keyboard event dispatcher
				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
				manager.addKeyEventDispatcher(new AcideKeyEventDispatcher());
			}
		});
		
	}

	/**
	 * Sets the look and feel of ACIDE - A Configurable IDE.
	 */
	public static void setLookAndFeel() {

		try {

			// Sets the operative system look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			// Updates the log
			AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s549"));
		} catch (ClassNotFoundException exception) {

			// Updates the log
			AcideLog.getLog().error(AcideLanguageManager.getInstance().getLabels().getString("s550")
					+ exception.getMessage() + AcideLanguageManager.getInstance().getLabels().getString("s551"));
			exception.printStackTrace();
		} catch (InstantiationException exception) {

			// Updates the log
			AcideLog.getLog().error(AcideLanguageManager.getInstance().getLabels().getString("s552")
					+ exception.getMessage() + AcideLanguageManager.getInstance().getLabels().getString("s551"));
			exception.printStackTrace();
		} catch (IllegalAccessException exception) {

			// Updates the log
			AcideLog.getLog().error(AcideLanguageManager.getInstance().getLabels().getString("s553")
					+ exception.getMessage() + AcideLanguageManager.getInstance().getLabels().getString("s551"));
			exception.printStackTrace();
		} catch (UnsupportedLookAndFeelException exception) {

			// Updates the log
			AcideLog.getLog().error(AcideLanguageManager.getInstance().getLabels().getString("s554")
					+ exception.getMessage() + AcideLanguageManager.getInstance().getLabels().getString("s551"));
			exception.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Main method of the application.
	 * </p>
	 * <p>
	 * Creates and configures the application log, load the project configuration
	 * and builds the main window of the application.
	 * </p>
	 * <p>
	 * Runs the application in the event dispatching thread to make the access to
	 * the swing components thread safe.
	 * </p>
	 * 
	 * @param args entry arguments for the application.
	 */
	public static void main(String[] args) {

		// Starts the log
		AcideLog.startLog();

		try {

			// Sets the ACIDE - A Configurable IDE language
			AcideLanguageManager.getInstance().setLanguage(AcideResourceManager.getInstance().getProperty("language"));
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}

		// Sets the Look and Feel
		setLookAndFeel();

		// Executes the application
		executeApplication();
		
		/*
		 * An attempt has been made to fix the problem that causes the word $success to
		 * be displayed when starting the ACIDE console for the first time. This is
		 * probably a thread execution order problem. As a workaround, the console is
		 * checked to see if it displays the word $success on startup, and if so, it is
		 * restarted to load with the correct configuration. 
		 * You have to check the behaviour of threads 5 and 6, depending on their order you get one result or
		 * another.
		 * 
		 */
		
		/*String s = AcideMainWindow.getInstance().getConsolePanel().getContent();
		if (!s.contains("*****")) {
			AcideMainWindow.getInstance().getConsolePanel().resetConsole();
		}*/
	
	}
}
