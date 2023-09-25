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
package acide.files.bytes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JOptionPane;

import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.process.parser.AcideGrammarFileCreationProcess;

/**
 * ACIDE - A Configurable IDE byte file manager.
 * 
 * @version 0.11
 */
public class AcideByteFileManager {

	/**
	 * ACIDE - A Configurable IDE byte file manager unique class instance.
	 */
	private static AcideByteFileManager _instance;

	/**
	 * Returns the ACIDE - A Configurable IDE byte file manager unique class
	 * instance.
	 * 
	 * @return the ACIDE - A Configurable IDE byte file manager unique class
	 *         instance.
	 */
	public static AcideByteFileManager getInstance() {

		if (_instance == null)
			_instance = new AcideByteFileManager();
		return _instance;
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE byte file manager.
	 */
	public AcideByteFileManager() {

	}

	/**
	 * Copies the content from the source file to the target file.
	 * 
	 * @param sourcePath
	 *            source file path.
	 * @param targetPath
	 *            target file path.
	 * @throws IOException
	 */
	public void copy(String sourcePath, String targetPath) throws IOException {

		// Gets the source file
		File sourceFile = new File(sourcePath);
		
		// Gets the target file
		File targetFile = new File(targetPath);
		
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;

		try {
			
			// Creates the file input stream
			fileInputStream = new FileInputStream(sourceFile);
			
			// Creates the file output stream
			fileOutputStream = new FileOutputStream(targetFile);
			
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1)
				fileOutputStream.write(buffer, 0, bytesRead); 
		} finally {
			
			if (fileInputStream != null)
				try {
					
					// Closes the file input stream
					fileInputStream.close();
				} catch (IOException exception) {

					// Displays an error message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s265")
							+ sourcePath, AcideLanguageManager.getInstance()
							.getLabels().getString("s266"),
							JOptionPane.ERROR_MESSAGE);

					// Updates the log
					AcideLog.getLog().error(exception.getMessage());
				}

			if (fileOutputStream != null)
				try {
					
					// Closes the file output stream
					fileOutputStream.close();
				} catch (IOException exception) {

					// Displays an error message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s267")
							+ targetPath, AcideLanguageManager.getInstance()
							.getLabels().getString("s268"),
							JOptionPane.ERROR_MESSAGE);

					// Updates the log
					AcideLog.getLog().error(exception.getMessage());
				}
		}
	}
	
	/**
	 * Reallocates the source path into the target path.
	 * 
	 * @param source
	 *            source path.
	 * @param target
	 *            target path.
	 * 
	 * @return true if the operation succeed and false in other case.
	 */
	public boolean reallocateFile(String source, String target) {

		File sourceFile = new File(source);

		// Avoids infinite loops
		if (!sourceFile.exists())
			return false;

		File targetFile = new File(target);
		if (targetFile.exists())
			targetFile.delete();
		try {
			targetFile.createNewFile();
		} catch (IOException exception) {

			// Displays an error message
			JOptionPane.showMessageDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s211"),
					AcideLanguageManager.getInstance().getLabels()
							.getString("s210"), JOptionPane.ERROR_MESSAGE);

			// Updates the log
			AcideLog.getLog().error(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s210")
							+ ": "
							+ AcideLanguageManager.getInstance().getLabels()
									.getString("s211"));
			exception.printStackTrace();
		}

		boolean saved = false;
		try {

			// Copies the binary files
			AcideByteFileManager.getInstance().copy(source, target);

			// They have been copied successfully
			saved = true;
		} catch (IOException exception) {

			// They have not been copied succesfully
			saved = false;

			// Displays an error message
			JOptionPane.showMessageDialog(
					null,
					exception.getMessage(),
					AcideLanguageManager.getInstance().getLabels()
							.getString("s945"), JOptionPane.ERROR_MESSAGE);

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}

		// If they have been copied successfully
		if (saved)

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s212")
							+ target);
		else

			// Updates the log
			AcideLog.getLog().error(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s213")
							+ target);

		// Deletes the source file
		boolean deleted = sourceFile.delete();

		// If it could been deleted
		if (deleted)

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s214")
							+ source);
		else

			// Updates the log
			AcideLog.getLog().error(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s215")
							+ source);

		return (saved && deleted);
	}
	
	/**
	 * Add the corresponding package to the java file genereted by antlr
	 * 
	 * @param source
	 *            source path.
	 * @version 0.19
	 */
	public void addPackage(String source) {
		
		String newLine = "package acide.process.parser.grammar;";

		try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
			StringBuilder sb = new StringBuilder();
			int i = 0;

			String line = reader.readLine();
			while (line != null) {
				sb.append(line).append(System.lineSeparator());
				if (i == 0)
					sb.append(newLine).append(System.lineSeparator());
				;
				line = reader.readLine();
				i++;
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(source))) {
				writer.write(sb.toString());
			} catch (IOException e) {
				// Updates the log
				AcideLog.getLog().error(e.getMessage());
			}
		} catch (IOException e) {
			// Updates the log
			AcideLog.getLog().error(e.getMessage());
		}
	}
	
	/**
	 * Extract grammar and lexer rule form source.
	 * Write the grammar rule to "syntaxRules.txt", the lexer rule to "lexicalCategories.txt"
	 * , the delimiter to "delimiter.txt" and generate "Expr.g4" with grammar and lexer rule.
	 * 
	 * @param source
	 *            source path.
	 * @version 0.19
	 */
	public void processGrammarFile(String source) {
		
		String exprFile = "Expr.g4";
		String syntaxFile = AcideGrammarFileCreationProcess.DEFAULT_PATH + "syntaxRules.txt";
		String lexicalFile = AcideGrammarFileCreationProcess.DEFAULT_PATH + "lexicalCategories.txt";
		String delimiterFile = AcideGrammarFileCreationProcess.DEFAULT_PATH + "delimiter.txt";
		
        String syntaxContent = "";
        String lexicalContent = "";
        String delimiterContent = "";
        
        boolean syntax = false;
        boolean lexical = false;
        boolean delimiter = false;
        
        // Read contents of source
        try {
            BufferedReader reader = new BufferedReader(new FileReader(source));
            String line;
            while ((line = reader.readLine()) != null) {
            	if(line.equals("grammar Expr;"))
            		continue;
            	if(line.equals("class ExprLexer extends Lexer;")){
            		lexical = true;
            		continue;
            	}
            	if(line.equals("class ExprParser extends Parser;")) {
            		lexical = false;
            		syntax = true;
            		continue;
            	}
            	if(line.equals("Statement delimiter;")) {
            		delimiter = true;
            		syntax = false;
            		continue;
            	}
            	if(delimiter)
            		delimiterContent = line;
            	if(syntax)
            		syntaxContent += line + "\n";
            	if(lexical)
            		lexicalContent += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
        	// Updates the log
        	AcideLog.getLog().error(e.getMessage());
        }
        
        // Gets the selected file editor panel
		AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel();
		
		// If delimiter content is empty
		if(!delimiterContent.equals(""))
			delimiterContent = String.valueOf(delimiterContent.charAt(0));
		
		// Set the grammar delimiter of selected file editor panel
		selectedFileEditorPanel.set_grammarDelimiter(delimiterContent);
		
		
        try {
        	// Write the "syntaxContent" to the syntaxFile
            BufferedWriter syntaxWriter = new BufferedWriter(new FileWriter(syntaxFile));
            syntaxWriter.write(syntaxContent);
            syntaxWriter.close();
            
            // Write the "lexicalContent" to the lexicalFile
            BufferedWriter lexicalWriter = new BufferedWriter(new FileWriter(lexicalFile));
            lexicalWriter.write(lexicalContent);
            lexicalWriter.close();
            
        	// Write the "delimiterContent" to the delimiterFile
            BufferedWriter delimiterWriter = new BufferedWriter(new FileWriter(delimiterFile));
            delimiterWriter.write(delimiterContent);
            delimiterWriter.close();
            
            // Write the "syntaxContent" to the exprFile
            //String[] lines = syntaxContent.split("\\r?\\n");
            //syntaxContent = "grammar Expr;\n" + String.join("\n", Arrays.copyOfRange(lines, 1, lines.length));
            syntaxContent = "grammar Expr;\n" + syntaxContent;
            
            // Write the rules to Expr.g4
            BufferedWriter g4Writer = new BufferedWriter(new FileWriter(exprFile));
            g4Writer.write(syntaxContent + "\n" + lexicalContent);
            g4Writer.close();
        } catch (IOException e) {
        	// Updates the log
        	AcideLog.getLog().error(e.getMessage());
        }
	}
	
	/**
	 * Save the grammar with specific format in to the "target" file
	 * @param target
	 * 			target path.
	 * @version 0.19
	 */
	public void saveGrammar(String target) {
		String syntaxFile = AcideGrammarFileCreationProcess.DEFAULT_PATH + "syntaxRules.txt";
		String lexicalFile = AcideGrammarFileCreationProcess.DEFAULT_PATH + "lexicalCategories.txt";
		String delimiterFile = AcideGrammarFileCreationProcess.DEFAULT_PATH + "delimiter.txt";
		
		// Creates the file content
		String textContent = "header{\npackage acide.process.parser.grammar;\n}\n";
		textContent += "class ExprLexer extends Lexer;\n";
		
		String content = "";
		
        // Read contents of lexicalFile
        try {
            BufferedReader reader = new BufferedReader(new FileReader(lexicalFile));
            String line;
            while ((line = reader.readLine()) != null) {
            	content += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
        	// Updates the log
        	AcideLog.getLog().error(e.getMessage());
        }
		
        // Append the lexical contents
        textContent += content;
        textContent += "\nclass ExprParser extends Parser;\n";
        
        content = "";
        
        // Read contents of syntaxFile
        try {
            BufferedReader reader = new BufferedReader(new FileReader(syntaxFile));
            String line;
            while ((line = reader.readLine()) != null) {
            	content += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
        	// Updates the log
        	AcideLog.getLog().error(e.getMessage());
        }
        
        // Append the grammatical contents
        textContent +=  "grammar Expr;" + System.lineSeparator() + content;

        content = "";
        
        // Read contents of delimiterFile
        try {
            BufferedReader reader = new BufferedReader(new FileReader(delimiterFile));
            String line;
            while ((line = reader.readLine()) != null) {
            	content += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
        	// Updates the log
        	AcideLog.getLog().error(e.getMessage());
        }
        
        // Append the delimiter
        textContent += "\nStatement delimiter;\n" + content;
        
        // Write the extracted substring to the target file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(target));
            writer.write(textContent);
            writer.close();
        } catch (IOException e) {
        	// Updates the log
        	AcideLog.getLog().error(e.getMessage());
        }
	}
	

}
