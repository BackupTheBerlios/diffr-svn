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

package de.berlios.diffr.diffraction.arguments;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;

import de.berlios.diffr.diffraction.arguments.ArrayListArgumentPanel.*;

public class ArrayListArgumentPanel extends ArgumentPanel {
  private ArrayListArgument list;
  private ElementList root = new ElementList(new Element(null));
  private ArgumentType argument;
  public ArrayListArgumentPanel(ArgumentType argument, Object object) {
    list = (ArrayListArgument) object;
    this.argument = argument;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    showInfo();
    showElements();
    this.add(root.element);
  }
  public Object getValue() {
    renewList();
    return list;
  }

  private void updatePanel() {
    this.updateUI();
  }

  private void showElements() {
    for (int elementNumber = list.size() - 1; elementNumber >= 0; elementNumber--)
      new ElementList(new Element(list.get(elementNumber)), root);
  }

  private void showInfo() {
    String elementsType = list.getElementsArgument().getType().getName();
    String hint = argument.getHint();
    JLabel label = new JLabel(hint + " : List of " + elementsType);
    this.add(label);
  }

  private void renewList() {
    ArrayListArgument newList = new ArrayListArgument(list.getElementsArgument());
    ElementList current = root.nextElement;
    while (current != null) {
      newList.add(current.element.panel.getValue());
      current = current.nextElement;
    }
    list = newList;
  }

  private class ElementList {
    private Element element;
    private ElementList nextElement = null;
    private ElementList parentElement = null;
    private ElementList This = this;
    public ElementList(Element element) {
      this.element = element;
      addListeners(element);
    }

    private void addListeners(Element element) {
      element.addListenersToButtons(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            new ElementList(new Element(), This);
            valueWasChanged();
          }
        },
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            remove();
            valueWasChanged();
          }
        }
      );
    }
    public ElementList(Element element, ElementList parent) {
      this.parentElement = parent;
      this.element = element;
      this.nextElement = parent.nextElement;
      if (nextElement != null) {
        if (nextElement.element != null) {
          element.add(nextElement.element);
          parent.element.remove(nextElement.element);
        }
        nextElement.parentElement = this;
      }
      parent.nextElement = this;
      parent.element.add(element);
      updatePanel();
      addListeners(element);
    }
    public void remove() {
      parentElement.element.remove(element);
      parentElement.nextElement = nextElement;
      if (nextElement != null) {
        parentElement.element.add(nextElement.element);
        nextElement.parentElement = parentElement;
      }
      updatePanel();
     }
  }
  private class Element extends JPanel {
    private JButton addElementButton = new JButton("Add new");
    private JButton removeElementButton = new JButton("Remove");
    private ArgumentPanel panel;
    public Element() {
      super();
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      ArgumentPanelFactory factory = new ArgumentPanelFactory() {
        public void panelValueWasChanged() {
          valueWasChanged();
        }
      };
      panel = factory.getArgumentPanel(list.getElementsArgument(), list.getElementsArgument().getInitialValue());
      this.add(panel);
      this.add(removeElementButton);
      this.add(addElementButton);
    }
    public Element(Object object) {
      super();
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      if (object != null) {
        ArgumentPanelFactory factory = new ArgumentPanelFactory() {
          public void panelValueWasChanged() {
            valueWasChanged();
          }
        };
        panel = factory.getArgumentPanel(
            list.getElementsArgument(),
            object
            );
        this.add(panel);
        this.add(removeElementButton);
      }
      this.add(addElementButton);
    }

    private void addListenersToButtons(ActionListener l1, ActionListener l2) {
      addElementButton.addActionListener(l1);
      removeElementButton.addActionListener(l2);
    }
    public void addElement() {}
    public void removeElement() {}
  }
}
