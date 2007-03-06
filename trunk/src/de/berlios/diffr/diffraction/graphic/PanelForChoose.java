/* 
 * This file is part of the diffr project.
 * Copyright (C) 2006-2007, diffr team
 * All rights reserved.
 * 
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * */
/**
 * Created on 08.12.2006
 * 
 * @author Petr Mikheev
 */

package de.berlios.diffr.diffraction.graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.LayoutManager;;

public class PanelForChoose extends JPanel {
  private JComboBox boxOfObjects = new JComboBox();
  ArrayList listeners=new ArrayList();
  public void addPanelForChooseListener(PanelForChooseListener l) {
    listeners.add(l);
  }
  public void removePanelForChooseListener(PanelForChooseListener l) {
    listeners.remove(l);
  }

  private boolean isChoosedFirst;

  public PanelForChoose(Object[] objects, String hint, String errorHint, boolean isChoosedFirst) {
    boolean atLeastOneObjectExist = (objects!=null) && (objects.length>0);
    this.isChoosedFirst = isChoosedFirst;
    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

    JLabel hintText=new JLabel(hint);
    this.add(Box.createVerticalStrut(20));
    this.add(hintText);

    if (atLeastOneObjectExist) {
      showListOfTasks(objects);
    } else {
      thereIsNotAnyObjectsError(errorHint);
    }
    this.add(Box.createVerticalStrut(20));

    boxOfObjects.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OKButtonPressed();
      }
    });
  }

  private void thereIsNotAnyObjectsError(String errorHint) {
    JLabel message=new JLabel(errorHint);
    this.add(new JScrollPane(message));
  }

  public void showListOfTasks(Object[] objects) {
    boxOfObjects=new JComboBox(objects);
    if (!isChoosedFirst) boxOfObjects.setSelectedIndex(-1);
    boxOfObjects.setMaximumSize(new Dimension(500,20));
    this.add(boxOfObjects);
  }

  private void OKButtonPressed() {
    for (int number = 0; number < listeners.size(); number++) {
      PanelForChooseListener listener = (PanelForChooseListener) listeners.get(number);
      listener.OKButtonPressed(boxOfObjects.getSelectedItem());
    }
  }

}
