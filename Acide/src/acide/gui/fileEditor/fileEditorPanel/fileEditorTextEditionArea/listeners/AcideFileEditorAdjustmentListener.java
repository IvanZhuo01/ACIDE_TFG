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
package acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.listeners;

import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Editor panel adjustment listener.
 * 
 * @version 0.11
 * @see AdjustmentListener
 */
public class AcideFileEditorAdjustmentListener implements AdjustmentListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt
	 * .event.AdjustmentEvent)
	 */
	@Override
	public void adjustmentValueChanged(final AdjustmentEvent adjustmentEvent) {

		// Gets the selected file editor panel
		AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow
				.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel();

		// Gets the event source
		Adjustable source = adjustmentEvent.getAdjustable();

		// If adjusting
		if (adjustmentEvent.getValueIsAdjusting()) {

			// VERTICAL VALUE 1
			selectedFileEditorPanel
					.getTextEditionPanelList()
					.get(0)
					.setVerticalValue(
							selectedFileEditorPanel.getTextEditionPanelList()
									.get(0).getScrollPane()
									.getVerticalScrollBar().getValue());

			// HORIZONTAL VALUE 1
			selectedFileEditorPanel
					.getTextEditionPanelList()
					.get(0)
					.setHorizontalValue(
							selectedFileEditorPanel.getTextEditionPanelList()
									.get(0).getScrollPane()
									.getHorizontalScrollBar().getValue());

			// VERTICAL VALUE 2
			selectedFileEditorPanel
					.getTextEditionPanelList()
					.get(1)
					.setVerticalValue(
							selectedFileEditorPanel.getTextEditionPanelList()
									.get(1).getScrollPane()
									.getVerticalScrollBar().getValue());

			// HORIZONTAL VALUE 2
			selectedFileEditorPanel
					.getTextEditionPanelList()
					.get(1)
					.setHorizontalValue(
							selectedFileEditorPanel.getTextEditionPanelList()
									.get(1).getScrollPane()
									.getHorizontalScrollBar().getValue());
			return;
		}
		int orientation = source.getOrientation();
		if (orientation == Adjustable.HORIZONTAL) {
		} else {
		}
		int type = adjustmentEvent.getAdjustmentType();
		switch (type) {
		case AdjustmentEvent.UNIT_INCREMENT:
			break;
		case AdjustmentEvent.UNIT_DECREMENT:
			break;
		case AdjustmentEvent.BLOCK_INCREMENT:
			break;
		case AdjustmentEvent.BLOCK_DECREMENT:
			break;
		case AdjustmentEvent.TRACK:
			break;
		}

		if (selectedFileEditorPanel != null) {

			// EDITOR1 is focused
			if (selectedFileEditorPanel.getTextEditionPanelList().get(0)
					.getScrollPane().isFocusOwner()
					|| selectedFileEditorPanel.getTextEditionPanelList().get(0)
							.getScrollPane().getVerticalScrollBar()
							.isFocusOwner()
					|| selectedFileEditorPanel.getTextEditionPanelList().get(0)
							.getScrollPane().getHorizontalScrollBar()
							.isFocusOwner()) {

				// Updates the values
				selectedFileEditorPanel
						.getTextEditionPanelList()
						.get(1)
						.getScrollPane()
						.getVerticalScrollBar()
						.setValue(
								selectedFileEditorPanel
										.getTextEditionPanelList().get(1)
										.getVerticalValue());
				selectedFileEditorPanel
						.getTextEditionPanelList()
						.get(1)
						.getScrollPane()
						.getHorizontalScrollBar()
						.setValue(
								selectedFileEditorPanel
										.getTextEditionPanelList().get(1)
										.getHorizontalValue());
				selectedFileEditorPanel
						.getTextEditionPanelList()
						.get(0)
						.setVerticalValue(
								selectedFileEditorPanel
										.getTextEditionPanelList().get(0)
										.getScrollPane().getVerticalScrollBar()
										.getValue());
				selectedFileEditorPanel
						.getTextEditionPanelList()
						.get(0)
						.setHorizontalValue(
								selectedFileEditorPanel
										.getTextEditionPanelList().get(0)
										.getScrollPane()
										.getHorizontalScrollBar().getValue());
			}

			// EDITOR2 is focused
			if (selectedFileEditorPanel.getTextEditionPanelList().get(1)
					.getScrollPane().isFocusOwner()
					|| selectedFileEditorPanel.getTextEditionPanelList().get(1)
							.getScrollPane().getVerticalScrollBar()
							.isFocusOwner()
					|| selectedFileEditorPanel.getTextEditionPanelList().get(1)
							.getScrollPane().getHorizontalScrollBar()
							.isFocusOwner()) {

				// Updates the values
				selectedFileEditorPanel
						.getTextEditionPanelList()
						.get(0)
						.getScrollPane()
						.getVerticalScrollBar()
						.setValue(
								selectedFileEditorPanel
										.getTextEditionPanelList().get(0)
										.getVerticalValue());
				selectedFileEditorPanel
						.getTextEditionPanelList()
						.get(0)
						.getScrollPane()
						.getHorizontalScrollBar()
						.setValue(
								selectedFileEditorPanel
										.getTextEditionPanelList().get(0)
										.getHorizontalValue());
				selectedFileEditorPanel
						.getTextEditionPanelList()
						.get(1)
						.setVerticalValue(
								selectedFileEditorPanel
										.getTextEditionPanelList().get(1)
										.getScrollPane().getVerticalScrollBar()
										.getValue());
				selectedFileEditorPanel
						.getTextEditionPanelList()
						.get(1)
						.setHorizontalValue(
								selectedFileEditorPanel
										.getTextEditionPanelList().get(1)
										.getScrollPane()
										.getHorizontalScrollBar().getValue());
			}
		}
	}
}
