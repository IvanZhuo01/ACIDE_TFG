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
package acide.configuration.lexicon.delimiters;

import java.io.Serializable;

import acide.configuration.utils.ObjectList;

/**
 * ACIDE - A Configurable IDE lexicon delimiters manager.
 * 
 * @version 0.11
 * @see Serializable
 */
public class AcideLexiconDelimitersManager implements Serializable {

	/**
	 * ACIDE - A Configurable IDE lexicon delimiters manager class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE lexicon delimiters manager object list.
	 */
	private ObjectList _list;

	/**
	 * Creates a new ACIDE - A Configurable IDE lexicon delimiters manager.
	 */
	public AcideLexiconDelimitersManager() {

		super();

		// Creates the object list
		_list = new ObjectList();
	}

	/**
	 * Returns the delimiter at the position given as a parameter.
	 * 
	 * @param index
	 *            position to get.
	 * @return the delimiter at the position given as a parameter.
	 */
	public String getDelimiterAt(int index) {
		return (String) _list.getObjectAt(index);
	}

	/**
	 * Insert a new delimiter.
	 * 
	 * @param delimiter
	 *            new value to set.
	 */
	public void setDelimiter(String delimiter) {
		_list.insert(_list.size(), delimiter);
	}

	/**
	 * Returns the list size.
	 * 
	 * @return the list size.
	 */
	public int getSize() {
		return _list.size();
	}

	/**
	 * Inserts a new delimiter in the ACIDE - A Configurable IDE lexicon
	 * delimiters manager list.
	 * 
	 * @param delimiter
	 *            new delimiter to insert.
	 */
	public void insertDelimiter(String delimiter) {

		boolean found = false;

		for (int index = 0; index < getSize(); index++) {

			// Gets the delimiter at the index
			String delimiterAtIndex = getDelimiterAt(index);

			if (delimiterAtIndex.equals(delimiter))
				found = true;
		}

		if (!found)
			setDelimiter(delimiter);
	}

	/**
	 * Deletes a delimiter given as a parameter.
	 * 
	 * @param delimiter
	 *            delimiter to delete.
	 */
	public void deleteDelimiter(String delimiter) {

		for (int index = 0; index < this.getSize(); index++) {

			String s1 = this.getDelimiterAt(index);
			if (s1.equals(delimiter))
				_list.removeAt(index);
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE lexicon delimiters manager object
	 * list.
	 * 
	 * @return the ACIDE - A Configurable IDE lexicon delimiters manager object
	 *         list.
	 */
	public ObjectList getList() {
		return _list;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE lexicon delimiters
	 * manager object list.
	 * 
	 * @return the object list.
	 */
	public void setList(ObjectList list) {
		_list = list;
	}
}
