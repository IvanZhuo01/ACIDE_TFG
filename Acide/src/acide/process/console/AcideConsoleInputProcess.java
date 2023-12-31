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
package acide.process.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import acide.log.AcideLog;

/**
 * Handles the process input thread to read the commands in the ACIDE - A
 * Configurable IDE console panel.
 * 
 * @version 0.11
 * @see Thread
 */
public class AcideConsoleInputProcess extends Thread {

	/**
	 * Buffered writer.
	 */
	private BufferedWriter _writer;
	/**
	 * Input stream.
	 */
	private InputStream _input;

	/**
	 * Creates a new input process thread.
	 * 
	 * @param writer
	 *            new buffered reader.
	 * @param input
	 *            new input stream.
	 */
	public AcideConsoleInputProcess(BufferedWriter writer, InputStream input) {
		_writer = writer;
		_input = input;
	}

	/**
	 * Main method of the input process thread.
	 */
	public synchronized void run() {

		try {
			
			// Flushes the writer
			_writer.flush();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(_input));

			int i = 0;

			// Using this avoids the possibility to enter in a block
			if (_input.available() > 0) {

				while ((i = bufferedReader.read()) != -1) {
					_writer.write((char) i);
					_writer.flush();
				}
			}
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}
}
