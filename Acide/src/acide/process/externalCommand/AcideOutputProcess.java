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
package acide.process.externalCommand;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JTextPane;

import acide.log.AcideLog;

/**
 * <p>
 * Handles the ACIDE - A Configurable IDE output process thread.
 * </p>
 * <p>
 * Prints the output of the ACIDE - A Configurable IDE console process
 * into a text pane.
 * </p>
 * 
 * @version 0.11
 * @see Thread
 */
public class AcideOutputProcess extends Thread {

	/**
	 * ACIDE - A Configurable IDE input stream.
	 */
	private InputStream _inputStream;
	/**
	 * ACIDE - A Configurable IDE text component.
	 */
	private JTextPane _textComponent;
	/**
	 * ACIDE - A Configurable IDE
	 */
	private StringBuffer _stringBuffer;

	/**
	 * Creates a new ACIDE - A Configurable IDE output process thread.
	 * 
	 * @param inputStream
	 *            input stream.
	 * @param textComponent
	 *            text component.
	 * @see InputStream
	 */
	public AcideOutputProcess(InputStream inputStream,
			JTextPane textComponent) {

		// Stores the input stream
		_inputStream = inputStream;

		// Stores the console panel
		_textComponent = textComponent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public synchronized void run() {

		try {

			// Creates the input stream reader
			InputStreamReader inputStreamReader = new InputStreamReader(
					_inputStream);

			// Creates the buffered reader
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			// Creates the string buffer
			_stringBuffer = new StringBuffer(1024);

			int character = 0;

			while ((character = bufferedReader.read()) != -1) {

				// If it is not the carriage return
				if (character != 13)
					_stringBuffer.append((char) character);

				// When the buffer reader is empty
				if (!bufferedReader.ready()) {

					// Adds the text to the text component
					_textComponent.setText(_textComponent.getText().concat(_stringBuffer.toString()));

					// Clears the buffer
					_stringBuffer.delete(0, _stringBuffer.length());
				}
			}

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}
}
