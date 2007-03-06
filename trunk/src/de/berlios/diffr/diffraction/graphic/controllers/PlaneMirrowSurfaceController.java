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

package de.berlios.diffr.diffraction.graphic.controllers;

import javax.swing.*;
import java.awt.*;

import de.berlios.diffr.diffraction.exceptions.WrongClassException;
import de.berlios.diffr.diffraction.graphic.Controllable;
import de.berlios.diffr.diffraction.surfaces.PlaneMirrowSurface;

public class PlaneMirrowSurfaceController extends Controller {
  private PlaneMirrowSurfaceController() {}

  private PlaneMirrowSurface surface;

  private JPanel panel = new JPanel() {
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(new Color(255, 0, 0));
      g.drawLine(0, (int)panel.getSize().getHeight() / 2, (int)panel.getSize().getWidth(), (int)panel.getSize().getHeight() / 2);
    }
  };

  public PlaneMirrowSurfaceController(Controllable object) throws WrongClassException {
      try {
        this.surface = (PlaneMirrowSurface) object;
        panel.setBackground(new Color(0, 0, 0));
      }
      catch (Exception ex) {
        throw new WrongClassException();
      }
  }

  public JPanel getPanel() {
    return panel;
  }

  public void drawControllable(Graphics g, int width, int height) {
    g.setColor(new Color(255, 0, 0));
    g.drawLine(0, height / 2, width, height / 2);
  }

  public Controllable getControllable() {
    return surface;
  }
}
