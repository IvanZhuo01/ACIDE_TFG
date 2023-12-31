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
package acide.resources.exception;

import java.util.ResourceBundle;

import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE missed property exception.
 * 
 * @version 0.11
 * @see Exception
 */
public class MissedPropertyException extends Exception {

	/**
	 * ACIDE - A Configurable IDE missed property exception class serial version
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE missed property exception parameter which
	 * raised the exception.
	 */
	private String _parameter;
	/**
	 * ACIDE - A Configurable IDE missed property exception language labels.
	 */
	private static ResourceBundle _labels = AcideLanguageManager.getInstance()
			.getLabels();

	/**
	 * Creates a new ACIDE - A Configurable IDE missed property exception.
	 * 
	 * @param parameter
	 *            parameter that raised the exception.
	 */
	public MissedPropertyException(String parameter) {

		// Creates the exception
		super(_labels.getString("s426") + parameter + "'");

		// Updates the log
		AcideLog.getLog().info(_labels.getString("s427") + parameter + "'");

		// Stores the parameter which raised the exception
		_parameter = parameter;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE missed property exception
	 * parameter which raised the exception.
	 * 
	 * @return the ACIDE - A Configurable IDE missed property exception
	 *         parameter which raised the exception.
	 */
	public String getParameter() {
		return _parameter;
	}
}
