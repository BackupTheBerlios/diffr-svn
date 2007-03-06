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
import java.awt.*;
import java.awt.event.*;

public class BooleanArgumentPanel extends ArgumentPanel {
  private BooleanArgumentPanel() {}
  private ArgumentType argument;
  private JCheckBox check;
  public BooleanArgumentPanel(ArgumentType p, Object object) {
    this.argument = p;
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    check = new JCheckBox(p.getHint() + " (Boolean) ");
    this.add(check);
    check.setBorderPaintedFlat(((Boolean)object).booleanValue());
    check.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        valueWasChanged();
      }
    });
  }
  public Object getValue() {
    return new Boolean(check.isBorderPaintedFlat());
  }
}
