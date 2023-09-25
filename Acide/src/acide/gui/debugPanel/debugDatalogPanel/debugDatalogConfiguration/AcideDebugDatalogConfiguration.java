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
 *      	- Elena Tejeiro Pérez de Ágreda.
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
package acide.gui.debugPanel.debugDatalogPanel.debugDatalogConfiguration;

import acide.language.AcideLanguageManager;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class AcideDebugDatalogConfiguration {

    /**
     * ACIDE - A Configurable IDE trust_extension
     */
    private Trust_extension trust_extension;
    private String file;

    private static AcideDebugDatalogConfiguration instance;

    AcideDebugDatalogConfiguration(){
        setDefaultConfiguration();
    }

    public static AcideDebugDatalogConfiguration getInstance(){
        if(instance==null)
            instance = new AcideDebugDatalogConfiguration();
        return instance;
    }

    public void setDefaultConfiguration(){
        //By default, the debugger no trust to extensional predicates
        this.trust_extension=Trust_extension.NO;
        //By default, the datalog program loaded from the examples is debugged
        file="";
    }

    public String getDebugConfiguration(){
        String configuration = " ";

        if(trust_extension == Trust_extension.YES)
            configuration += "trust_extension("+trust_extension.getValue()+") ";
        if(file!=null && !file.isEmpty())
            configuration += "file('"+file+ "')";

        return configuration;
    }

    public Trust_extension getTrust_extension(){return this.trust_extension;}

    public void setTrust_extension(Trust_extension trust_extension){this.trust_extension=trust_extension;}

    public String getFile(){return this.file;}

    public void setFile(String file){this.file=file;}

    public enum Trust_extension {
        YES("yes"), NO("no");

        private String value;

        Trust_extension(String value){this.value=value;}

        public String getValue() {
            return value;
        }
    }

    public String getConfiguration(){
        String configuration=" ";

        if(this.trust_extension==Trust_extension.YES)
            configuration+="trust_extension("+this.trust_extension.getValue()+") ";
        if(this.file!=null && !this.file.isEmpty())
            configuration+="file('"+this.file+"')";

        return configuration;

    }

    public void saveConfiguration(AcideDebugDatalogConfiguration configuration){
        instance=configuration;
        try{

            //Get path to store debug configuration file
            String debugConfigurationPath= AcideResourceManager.getInstance().getProperty("debugConfigurationPath");
            debugConfigurationPath=debugConfigurationPath.substring(0,debugConfigurationPath.lastIndexOf("."))
                    +"Datalog"+debugConfigurationPath.substring(debugConfigurationPath.lastIndexOf("."));
            Properties debugProperties=new Properties();
            debugProperties.setProperty("trust_extension",this.trust_extension.getValue());
            debugProperties.setProperty("file",this.file);
            debugProperties.store(new FileOutputStream(debugConfigurationPath),null);
        }
        catch(MissedPropertyException | IOException e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, AcideLanguageManager.getInstance().getLabels()
                    .getString("s2341"));
        }
    }


    public void load(String fileName) throws MissedPropertyException{
        try{
            // Creates the file input file
            FileInputStream configurationFile=new FileInputStream(fileName);
            Properties debugProperties=new Properties();
            debugProperties.load(configurationFile);

            setProperties(debugProperties);

            // Close the configuration file input stream
            configurationFile.close();
        } catch(Exception e){
            e.printStackTrace();
            throw new MissedPropertyException(AcideLanguageManager.getInstance().getLabels()
                    .getString("s2340"));
        }
    }

    private void setProperties(Properties properties){
        setTrust_extension(properties.getProperty("trust_extension")
                .equals(Trust_extension.NO.getValue())? Trust_extension.NO:Trust_extension.YES);
        setFile(properties.getProperty("file"));
    }

}
