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

import java.awt.*;
import javax.swing.*;

import de.berlios.diffr.diffraction.graphic.controllers.Controller;

public class TaskImage extends JPanel {
  private Controller[] controllers;
  public TaskImage(Controller[] controllers) {
    this.controllers = controllers;
    this.setBackground(new Color(0, 0, 0));
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int controllerNumber = 0; controllerNumber < controllers.length; controllerNumber++) {
      if (controllers[controllerNumber] != null)
        controllers[controllerNumber].drawControllable(g, this.getWidth(), this.getHeight());
    }
  }
}
