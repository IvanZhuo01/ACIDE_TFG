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
package acide.files;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * ACIDE - A Configurable IDE file extension filter manager.
 * 
 * @version 0.11
 */
public class AcideFileExtensionFilterManager extends FileFilter {

	/**
	 * ACIDE - A Configurable IDE file extension filter manager valid extensions.
	 */
	private String[] _extensions;
	/**
	 * ACIDE - A Configurable IDE file extension filter manager extension
	 * description.
	 */
	private String _description;

	/**
	 * Creates a new ACIDE - A Configurable IDE file extension filter manager with an
	 * extension given as a parameter.
	 * 
	 * @param extension
	 *            new extension.
	 */
	public AcideFileExtensionFilterManager(String extension) {

		this(new String[] { extension }, null);
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE file extension filter manager with an
	 * extension and description given as parameters.
	 * 
	 * @param extensions
	 *            extensions to set.
	 * @param description
	 *            description to set.
	 */
	public AcideFileExtensionFilterManager(String[] extensions, String description) {
		_extensions = new String[extensions.length];
		for (int index = extensions.length - 1; index >= 0; index--) {
			_extensions[index] = extensions[index].toLowerCase();
		}
		if(description == null){
			_description="(";
			for(int i=0;i<extensions.length-1;i++)
				_description+="*."+extensions[i]+";";
			_description+="*."+extensions[extensions.length-1]+") files";
		}else
			_description=description;
	}

	/**
	 * Returns true if the file contains a valid extension and false in other
	 * case.
	 * 
	 * @param file
	 *            file to check.
	 */
	public boolean accept(File file) {

		if (file.isDirectory()) {
			return true;
		}
		String name = file.getName().toLowerCase();

		for (int index = _extensions.length - 1; index >= 0; index--) {
			if (name.endsWith(_extensions[index])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE extension filter manager extension
	 * description.
	 * 
	 * @return the ACIDE - A Configurable IDE extension filter manager extension
	 *         description.
	 */
	public String getDescription() {
		return _description;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE extension filter manager extensions.
	 * 
	 * @return the ACIDE - A Configurable IDE extension filter manager extensions.
	 */
	public String[] getExtensions(){
		return _extensions;
	}
}
