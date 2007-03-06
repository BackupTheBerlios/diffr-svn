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
import de.berlios.diffr.diffraction.arguments.ArgumentType;
import de.berlios.diffr.diffraction.arguments.ArrayListArgument;
import de.berlios.diffr.diffraction.fields.PlaneWave;
import de.berlios.diffr.diffraction.fields.reflectedFields.ReflectedFieldOfPlaneWaves;
import de.berlios.diffr.diffraction.tasks.TaskResults;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SPM {

	int IPL;
	int IBC;
	int IDIM;
	int ICAR;
	long NG;
	double[] RS;
	double[]  RC;
	double AK;
	double UGOL;
	Complex EPSLN;
	Complex [] F;
public	Complex [] VS;
	Complex [] UN;
	Complex [] URAB = new Complex [999];

	double RD,EPGL;
    int IVS;

    Complex ASH ,EEE,W;
    int I,INUL,NMAX,IMIN,ISEC;

  public SPM(int IPL,int IBC,int IDIM,int ICAR,long NG, double[] RS, double[]  RC, double AK, double UGOL, Complex EPSLN, Complex [] F, Complex [] VS) {
	  this.IPL = IPL;
	  this.IBC = IBC;
	  this.IDIM = IDIM;
	  this.ICAR = ICAR;
	  this.NG = NG;
	  this.RS = RS;
	  this.RC = RC;
	  this.AK = AK;
	  this.UGOL = UGOL;
	  this.EPSLN = EPSLN;
	  this.F = F;
	  this.VS = VS;
	  UN = new Complex [ICAR];
  }

  /**
   * calculate
   */
  public TaskResults calculate() {
	  System.out.println("SPM is being calculated now " + "\n" );

                 IVS = 103;
//       CALL TTTME(1,IMIN,ISEC)
                 EPGL = 0.0;
  /*        IF ( IBC .EQ. 3 ) GO TO 130
          IF ( IBC .EQ. 2 ) GO TO 120
          IF ( IPL .EQ. 2 ) GO TO 115
          IF ( IDIM .EQ. 2 ) GO TO 615*/
//      CALL MMVD(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,NMAX,RD)
        MMVD alg = new MMVD(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,NMAX,RD);
        alg.calculate();

//        PlaneWave[] waves = new PlaneWave[IVS + 1];
        ArrayListArgument waves = new ArrayListArgument(
           new ArgumentType("", new PlaneWave().getClass(), new PlaneWave())
        );

        for (int k = 1; k <= IVS; k++) {
        	waves.add(new PlaneWave(new Boolean(PlaneWave.polarizationE), angle(k-INUL), waveLength(k-INUL), ZAZ.VS[k]));
        }

        ReflectedFieldOfPlaneWaves reflectedField = new ReflectedFieldOfPlaneWaves(waves);

        TaskResults results = new TaskResults(reflectedField);

/*       GO TO 999
615       CONTINUE
      CALL M2VD(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,NMAX,RD)
       GO TO 999
115      CONTINUE
          IF ( IDIM .EQ. 2 ) GO TO 618
      CALL MMVN(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,NMAX,RD)
       GO TO 999
618       CONTINUE
      CALL M2VN(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,NMAX,RD)
       GO TO 999
120      CONTINUE
         EEE = ( 0.0,1.0 )
       W = CSQRT(1.0/EPSLN)
          IF ( IPL .EQ. 2 ) GO TO 125
          ASH = -EEE*W/AK
          IF ( IDIM .EQ. 2 ) GO TO 638
       CALL MMVE(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,
    *                     NMAX,ASH,EPGL,RD)
         GO TO 999
638       CONTINUE
       CALL M2VE(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,
    *                     NMAX,ASH,EPGL,RD)
       GO TO 999
125      CONTINUE
          ASH = EEE*W*AK
          IF ( IDIM .EQ. 2 ) GO TO 658
       CALL MMVH(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,
    *                     NMAX,ASH,EPGL,RD)
        GO TO 999
658       CONTINUE
       CALL M2VH(AK,UGOL,ICAR,RS,RC,NG,UN,VS,IVS,INUL,
    *                     NMAX,ASH,EPGL,RD)
       GO TO 999
130      CONTINUE
          IF ( IPL .EQ. 2 ) GO TO 525
      CALL KGD(AK,UGOL,ICAR,RS,RC,NG,URAB,UN,VS,IVS,INUL,
    *                     NMAX,EPSLN,EPGL,RD)
           GO TO 999
525       CONTINUE
      CALL KGN(AK,UGOL,ICAR,RS,RC,NG,UN,URAB,VS,IVS,INUL,
    *                     NMAX,EPSLN,EPGL,RD)
           GO TO 999
999      CONTINUE*/
//       CALL TTTME(2,IMIN,ISEC)


//        throw new java.lang.UnsupportedOperationException("Method SPM.calculate() is not yet implemented.");

/*           OPEN (1,FILE='SNGL2.DAT')
            WRITE(1,400) RD,EPGL,INUL,NMAX,IMIN,ISEC
400       FORMAT(   2E18.8, 4I4 )
         WRITE(1,410) (VS(I),I=1,NMAX)
         WRITE(1,410) (UN(I),I=1,ICAR)
410       FORMAT( 4E18.8 )
            CLOSE (1)*/

        System.out.println("   The end of SPM calculation" + "\n" );
        return results;
  }

private Double angle(int j) {
	return new Double(Math.atan(Functions.ALAMB(j)/Functions.GMMMA(j).re()));
}

private Double waveLength(int j) {
	return new Double(DDZ.B/DDZ.AK);
}

}
