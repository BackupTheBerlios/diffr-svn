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

import Org.netlib.math.complex.Complex;
public abstract class ArgumentPanelFactory {
  public ArgumentPanelFactory() {}
  public ArgumentPanel getArgumentPanel(ArgumentType argument, Object object) {
    Class argumentClass = argument.getType();
    if (argumentClass.getName() == "java.lang.Long") return new LongArgumentPanel(argument, object) {
      public void valueWasChanged() {
        panelValueWasChanged();
      }
    };
    if (argumentClass.getName() == "java.lang.Double") return new DoubleArgumentPanel(argument, object) {
      public void valueWasChanged() {
        panelValueWasChanged();
      }
    };
    if (argumentClass.getName() == "java.lang.Boolean") return new BooleanArgumentPanel(argument, object) {
      public void valueWasChanged() {
        panelValueWasChanged();
      }
    };
    if (argumentClass.equals(new Complex(1, 0).getClass())) return new ComplexArgumentPanel(argument, object) {
      public void valueWasChanged() {
        panelValueWasChanged();
      }
    };
    if (argumentClass.getName() == new ArrayListArgument(null).getClass().getName()) return new ArrayListArgumentPanel(argument, object) {
      public void valueWasChanged() {
        panelValueWasChanged();
      }
    };
    return new UnknownArgumentPanel(argument, object) {
      public void valueWasChanged() {
        panelValueWasChanged();
      }
    };
  }
  public abstract void panelValueWasChanged();
}
