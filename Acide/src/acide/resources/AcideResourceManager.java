/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 *
 * Copyright (C) 2007-2023
 * Authors:
 * 		- Fernando Sáenz Pérez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan José Ortiz Sánchez.
 *          - Delfín Rupérez Cañas.
 *      - Version 0.7:
 *          - Miguel Martín Lázaro.
 *      - Version 0.8:
 *      	- Javier Salcedo Gómez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Gutiérrez García-Pardo.
 *      	- Elena Tejeiro Pérez de �?greda.
 *      	- Andrés Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Semíramis Gutiérrez Quintana
 *      	- Juan Jesús Marqués Ortiz
 *      	- Fernando Ordás Lorente
 *      - Version 0.17
 *      	- Sergio Domínguez Fuentes
 * 		- Version 0.18
 * 			- Sergio García Rodríguez
 * 		- Version 0.19
 * 			- Carlos González Torres
 * 			- Cristina Lara López
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
package acide.resources;

import java.util.Properties;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import acide.log.AcideLog;
import acide.resources.exception.MissedPropertyException;


/**																
 * ACIDE - A Configurable IDE resource manager. 
 * 
 * Handles all the resources of ACIDE - A Configurable IDE.								
 *					
 * @version 0.11	
 * @see Properties																												
 */
public class AcideResourceManager {

	/**
	 * ACIDE - A Configurable IDE resource manager properties configuration file.
	 */
	private static final String CONFIGURATION_FILE = "." + File.separator + "configuration" + File.separator + "configuration.properties";
	/**
	 * ACIDE - A Configurable IDE resource manager unique class instance.
	 */
	private static AcideResourceManager _instance;
	/**
	 * ACIDE - A Configurable IDE resource manager properties to load.
	 */
	private static HashMap<Object, Object> _properties;
	/**
	 * ACIDE - A Configurable IDE resource manager temporal properties if the loading fails.
	 */
	private static Properties _temporalProperties;
	
	/**
	 * <p>
	 * Creates a new ACIDE - A Configurable IDE resource manager. 
	 * </p>
	 * <p>
	 * Loads the main configuration properties file. 
	 * </p>
	 */
	public AcideResourceManager(){
		loadConfiguration();
	}

	private void loadConfiguration(){
		try {

			// Creates the file input file
			FileInputStream configurationFile = new FileInputStream(CONFIGURATION_FILE);

			// Creates the temporal properties file
			_temporalProperties = new Properties();

			// Loads the temporal properties from the file
			_temporalProperties.load(configurationFile);

			// Closes the configuration file input stream
			configurationFile.close();

			// Creates the properties hash map
			_properties = new HashMap<Object, Object>(_temporalProperties);
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE resource manager class unique instance.
	 * 
	 * @return the ACIDE - A Configurable IDE resource manager class unique instance.
	 */
	public static AcideResourceManager getInstance(){
		
		if(_instance == null)
			_instance = new AcideResourceManager();
		return _instance;
	}
	
	/**
	 * Returns the property from the list with the specified name.
	 * 
	 * @param name
	 *            property name.
	 * 
	 * @return the property from the list with the specified name.
	 * 
	 * @throws MissedPropertyException if there is a problem with the 
	 * data loading process.
	 */
	public String getProperty(String name) throws MissedPropertyException {

		String value = (String) _properties.get(name);
		
		String os = System.getProperty("os.name");
		boolean isShellPath = name.contains("shellPath");

		if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("Mac")>=0) {
			value = value.replace("\\", File.separator);
			if (isShellPath)
				value = value.replace(".exe", "");

		} else {
			if (!(name.equals("consolePanel.exitCommand") || name
					.equals("consolePanel.parameters")) && value != null) {
				value = value.replace("/", File.separator);
				if (isShellPath && !(value.contains("."))
						&& !value.contains(".exe"))
					value = value + ".exe";
			}
		}

		if (value == null)
			throw new MissedPropertyException(name);

		return value;
	}

	
	/**
	 * Returns the String introduced with the correct file separators.
	 * 
	 * @param name
	 *            String to transform.
	 * 
	 * @return String introduced with the correct file separators.
	 * 
	 */
	public String replaceSeparators(String name) {

		String value = name;	
		
		String os = System.getProperty("os.name");
		boolean isShellPath = name.contains("shellPath") ;
					
		if(os.indexOf("nix")>=0 || os.indexOf("nux")>=0 || os.indexOf("Mac")>=0)
		{
			value = value.replace("\\", File.separator);
			if(isShellPath) value= value.replace(".exe", "");
			
		}
		else {
				value = value.replace("/", File.separator);
				if(isShellPath &&!(value.contains("."))&& !value.contains(".exe") ) value=value+".exe";
		}
		return value;
	}
	
	
	/**
	 * Sets a new value to a property identified by a name given as a parameter.
	 * 
	 * @param name
	 *            name of the property to modify.
	 * @param value
	 *            new value to set.
	 */
	public void setProperty(String name, String value) {
		try {

			if(name.contains("shellPath")){
				String os = System.getProperty("os.name");
				
				if(os.indexOf("nix")>=0 || os.indexOf("nux")>=0 || os.indexOf("Mac")>=0) {
					value = value.replace(".exe", "");
				}else {
					if (!value.contains(".") && !value.contains(".exe"))
						value = value + ".exe";
				}
			}
			
			
			// Sets the property into the temporal property
			_temporalProperties.setProperty(name, value);
			
			// Stores the temporal properties file
			_temporalProperties.store(new FileOutputStream(
					CONFIGURATION_FILE), "ACIDE Configuration");
			loadConfiguration();
		} catch (Exception exception) {
			
			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}
}
