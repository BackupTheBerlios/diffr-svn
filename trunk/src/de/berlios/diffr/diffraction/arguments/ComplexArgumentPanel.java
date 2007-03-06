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

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import Org.netlib.math.complex.Complex;

public class ComplexArgumentPanel extends ArgumentPanel {
  private ComplexArgumentPanel() {}
  private ArgumentType argument;
  private JTextField textA = new JTextField();
  private JTextField textB = new JTextField();
  public ComplexArgumentPanel(ArgumentType p, Object object) {
    this.argument = p;
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.add(new JLabel(p.getHint() + " (Complex) A = "));
    textA.setMaximumSize(new Dimension(200, 20));
    textB.setMaximumSize(new Dimension(200, 20));
    this.add(textA);
    this.add(new JLabel(" B = "));
    this.add(textB);
    textA.setText(Double.toString(((Complex)object).norm()));
    textB.setText(Double.toString(((Complex)object).arg()));
    CaretListener caretListener = new CaretListener() {
      public void caretUpdate(CaretEvent e) {
        valueWasChanged();
      }
    };
    textA.addCaretListener(caretListener);
    textB.addCaretListener(caretListener);
  }
  public Object getValue() {
    try {
      return new Complex(Double.parseDouble(textA.getText()), Double.parseDouble(textB.getText()));
    }
    catch (NumberFormatException ex) {
      return argument.getInitialValue();
    }
  }

}
