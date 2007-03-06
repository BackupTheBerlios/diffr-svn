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
 * @author Andrei Mikheev
 */

package de.berlios.diffr.diffraction.algorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.diffraction.fields.impingingFields.PlaneImpingingWave;
import de.berlios.diffr.diffraction.surfaces.periodicSurface.PerfectConductivity;
import de.berlios.diffr.diffraction.surfaces.periodicSurface.PeriodicSurface;
import de.berlios.diffr.diffraction.surfaces.periodicSurface.SurfaceConductivity;
import de.berlios.diffr.diffraction.surfaces.periodicSurface.SurfaceShape;
import de.berlios.diffr.diffraction.tasks.Task;
import de.berlios.diffr.diffraction.tasks.TaskResults;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SmallPerturbationAlgorithm extends Algorithm {
  public SmallPerturbationAlgorithm() {
  }
  public TaskResults solve(Task task) throws de.berlios.diffr.diffraction.exceptions.ErrorInAlgorithmException {

      System.out.println("1 SmallPerturbationAlgorithm is being calculated now \n  Please, wait  ");
      

      Complex EPSLN;
      double B,D;
      double [] RS = new double [26];
      double [] RC = new double[26];
      double AK,EPSR,EPSI,AR,AFI,ALFA;
      double [] RS1 = new double[26];
      double [] RC1 = new double [26];
      double PI,TWPI,AK1,UGOL;
      int IPL,IBC,IALG,I,IDIM,ICAR;
      long NG;
//          READ(1,400) IPL,IBC,NG,IALG,IDIM,ICAR
      IPL = 1;
      /*if  (   ((PlaneImpingingWave)(task.getImpingingField())).getPolarization() == PlaneImpingingWave.polarizationH ) IPL = 2;*/

      IBC = 1;
//      SurfaceConductivity cond = ((PeriodicSurface)task.getSurface()).getConductivity();

      SurfaceConductivity cond = new PerfectConductivity();

      try {
        if (Class.forName(
            "de.berlios.diffr.diffraction.surfaces.periodicSurface.HeightConductivity").isInstance(cond)) {
          IBC = 2;
        }
      }
      catch (ClassNotFoundException ex) {
        ex.printStackTrace();
      }

      SurfaceShape shape = ((PeriodicSurface)task.getSurface()).getShape();
      NG = shape.getNumberOfFourierCoefficients();

      IALG = 3;
      IDIM = 10;
      ICAR = 10;

//        READ(1,410) B,D,AK,EPSR,EPSI,AR,AFI,ALFA

      B = shape.getPeriod();
      D = shape.getShift();

      TWPI =  2.0 * Math.PI;
//      AK = TWPI / ((PlaneImpingingWave)(task.getImpingingField())).getLength();

      AK = TWPI / 2.7;

      EPSR = 0; //temporarry
      EPSI = 0; //temporarry

      PlaneImpingingWave wave = (PlaneImpingingWave)(task.getImpingingField());

/*      AR = wave.getAmplitude().abs();
      AFI = wave.getAmplitude().phase();

      ALFA = wave.getAngle();*/


      AR = 1.0;
      AFI = 0.0;

      ALFA = 15.0;


//        READ(1,420) (RS(I),I=1,NG)
//        READ(1,420) (RC(I),I=1,NG)

      for (int i = 1; i<= NG; i++){
        RS[i] = ((Double)shape.getCoefficientsOfSinus().get(i-1)).doubleValue();
        RC[i] = ((Double)shape.getCoefficientsOfCosinus().get(i-1)).doubleValue();
      }

          EPSLN = new Complex(EPSR,EPSI);

         for (int i= 0; i<=25; i++){
          RS1[i] = 0;
          RC1[i] = 0;
          }
          for (int i= 1; i<=NG; i++){
            RS1[i] = - TWPI/B *RS[i];
            RC1[i] =   TWPI/B *RC[i];
          }
              AK1 = AK * B / TWPI;
              UGOL = Math.PI/180. *ALFA;
              System.out.println("2 SmallPerturbationAlgorithm is being calculated now " + "\n" + "  Please, wait  ");
//   CALL SSS0(IPL,IBC,IALG,IDIM,ICAR,NG,RS1,RC1,AK1,UGOL,EPSLN)

    SSS0 s = new SSS0(IPL,IBC,IALG,IDIM,ICAR,NG,RS1,RC1,AK1,UGOL,EPSLN);
    return s.calculate();
  }

}
