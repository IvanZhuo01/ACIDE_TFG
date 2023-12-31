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
package acide.gui.toolBarPanel.consolePanelToolBar.utils;

import acide.language.AcideLanguageManager;

/**
 * Defines the parameter types for the shell tool bar commands of ACIDE - A
 * Configurable IDE.
 * 
 * @version 0.11
 */
public enum AcideParameterType {

	/**
	 * No parameter.
	 */
	NONE,
	/**
	 * Type text.
	 */
	TEXT,
	/**
	 * Type file.
	 */
	FILE,
	/**
	 * Type directory.
	 */
	DIRECTORY;

	/**
	 * Parse a parameter type given as a parameter from string to ParameterType.
	 * 
	 * @param parameterTypeString
	 *            parameter to parse.
	 * @return the parsed value.
	 */
	public static AcideParameterType fromStringToEnum(String parameterTypeString) {

		if (parameterTypeString.matches(AcideLanguageManager.getInstance()
				.getLabels().getString("s1005")))
			return NONE;
		if (parameterTypeString.matches(AcideLanguageManager.getInstance()
				.getLabels().getString("s1006")))
			return TEXT;
		if (parameterTypeString.matches(AcideLanguageManager.getInstance()
				.getLabels().getString("s1007")))
			return FILE;
		if (parameterTypeString.matches(AcideLanguageManager.getInstance()
				.getLabels().getString("s1008")))
			return DIRECTORY;

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {

		switch (this) {

		case NONE:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s1005");
		case TEXT:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s1006");
		case FILE:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s1007");
		case DIRECTORY:
			return AcideLanguageManager.getInstance().getLabels()
					.getString("s1008");
		}
		return null;
	}
}
