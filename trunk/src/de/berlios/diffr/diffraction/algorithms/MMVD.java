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


public class MMVD {

	int ICAR;
	long NG1;
	double[] RS1;
	double[]  RC1;
	double AK1;
	double UGOL;
	Complex [] VS;
	Complex [] UN;

	double RD1;
    int IVS;

    int INUL,NMAX;
	
	public MMVD(double AK1, double UGOL, int ICAR,double[] RS1, double[]  RC1, long NG1,Complex [] UN,Complex [] VS, int IVS, int INUL, int NMAX, double RD1){
			  this.ICAR = ICAR;
			  this.NG1 = NG1;
			  this.RS1 = RS1;
			  this.RC1 = RC1;
			  this.AK1 = AK1;
			  this.UGOL = UGOL;
			  this.VS = VS;
			  this.UN = UN;
			  this.IVS = IVS; 
			  this.INUL = INUL;  
			  this.NMAX = NMAX; 
			  this.RD1 = RD1;
			  
			  ZAZ.VS = VS;
	}
	
	  public void calculate() {
          System.out.println("   MMVD is being calculated now 1" + "\n" );

          
    	  
          Complex   S;
          VS = new Complex[IVS+1];
          for (int k = 0; k <= IVS; k++) {
          	VS[k]=new Complex(0.0, 0.0);
          	ZAZ.VS[k]=new Complex(0.0, 0.0);
          }
          Complex[] VVS = new Complex[52];
          UN = new Complex [ICAR+1];
          for (int k = 0; k <= ICAR; k++) {
        	  UN[k]=new Complex(0.0, 0.0);
          }
            double RD;
            double /*T,B,AK,PI,*/ H,Y,SNSS;
/*            double[] RS,RC;*/
            double G,ALF,BET;
            int NMAX,N8,/*  NG, */  I1,I2;
            int J,/*NNN,*/ MX,MN,NGG;
/*           COMMON/DDZ/ T,B,AK,PI,EEE /*
           /*  COMMON/ZAZ/NNN */
/*           COMMON/DSV/RS(25),RC(25),NG */
                SNSS = Math.sin(UGOL); 
                  MX = new Double( Math.floor(AK1 * ( 1 - SNSS ))).intValue(); 
                  MN = new Double( Math.floor(AK1 * ( 1 + SNSS ))).intValue(); 
                  INUL =MN + 1;
                  NMAX = INUL + MX;
                  DSV.NG=NG1;
              DDZ.PI= Math.PI;
              DDZ.B=2.0*DDZ.PI;
              DDZ.EEE = new Complex(0.0,1.0);
              DDZ.AK=AK1;
              ZAZ.NNN= new Double( Math.floor(DDZ.AK*Math.sin(UGOL))).intValue();   
             DDZ.T=2*DDZ.PI*(DDZ.AK*Math.sin(UGOL)-ZAZ.NNN);
             System.out.println("   MMVD is being calculated now 2" + "\n" );
             for (int i = 1; i<=DSV.NG; i++) {
            	 DSV.RS[i]=RS1[i];
            	 DSV.RC[i]=RC1[i];
   			}
             System.out.println(
  " First order small perturbation method in the \n" +
       "     problem of diffraction on a \n" +
       "    perfectly conducting surface \n" +
       "        ( E - polarization ) \n" +
       "            in process \n\n\n" +
       "      Author - Andrew Mikheev :\n" +
       "  Institute for problems in mechanics \n" +
       "      USSR Academy of Sciences, \n" +
       "  Moscow 117526, pr. Vernadskogo 101 \n" +
       "        Phone 434 - 92 - 63   \n");
             
             System.out.println("   MMVD is being calculated now 3" + "\n" );
             
        VVS[(int)DSV.NG+1] = new Complex(-1.0,0.0);
        for ( int i=1; i<= DSV.NG; i++){
            VVS[(int)DSV.NG+1+i]=DDZ.EEE.mul(new Complex(DDZ.AK*Math.cos(UGOL),0.0)).mul(new Complex(DSV.RC[i],-DSV.RS[i]));
            VVS[(int)DSV.NG+1-i]=DDZ.EEE.mul(new Complex(DDZ.AK*Math.cos(UGOL),0.0)).mul(new Complex(DSV.RC[i],DSV.RS[i]));
        }
        
        
        
        
//             DO 50 I = 1, NMAX
             for ( int i=1; i<= NMAX; i++){
               VS[i] = new Complex(0.0, 0.0);
             }

             System.out.println("   MMVD is being calculated now 4" + "\n" );
             
             I1 = 1;
           if ( INUL - DSV.NG > 1 ) I1 = INUL - (int)DSV.NG;
           I2 = NMAX;
           if ( INUL + DSV.NG < NMAX ) I2 = INUL + (int)DSV.NG;
  //           DO 60 I = I1, I2
           for (int i = I1; i<=I2; i++) {
                 J = i - INUL +(int)DSV.NG +1;
                 VS[i] = VVS[J];
                 ZAZ.VS[i] = VVS[J];
           }
                RD = 0.0;

                System.out.println("   MMVD is being calculated now 5" + "\n" );
                
                //         DO 54 J = 1, NMAX
           for (int j = 1; j<=NMAX; j++) {
                N8 = j - INUL + ZAZ.NNN;
                RD = RD +  VS[j].abs()*VS[j].abs()*((Functions.GMMMA(N8)).re());
           }
          RD = 1.0 - RD / (Functions.GMMMA(ZAZ.NNN)).re();
          RD1 = RD;
          
          System.out.println("   MMVD is being calculated now 6" + "\n" );
          
        H=DDZ.B/(ICAR-1.0);
        Y=-H;
//        DO 743 I = 1 ,ICAR
        for (int i=1; i<=ICAR; i++){
        Y=Y+H;
        G=Functions.AAAG(Y);
        ALF=Math.cos(G);
        BET=Math.sin(G);
        S= Functions.DU0DN(Y);
        NGG=2* (int) DSV.NG + 1;
//        DO 745 JJ=1,NGG
        for (int jj=1; jj<=NGG;jj++) {
        	
        J=jj-1-(int)DSV.NG;
        S=S.add(
        		DDZ.EEE.mul(VVS[(int)DSV.NG+J+1]).mul(
        		
        		(new Complex(Functions.ALAMB(ZAZ.NNN+J)*BET,0.0).add(Functions.GMMMA(ZAZ.NNN+J).mul(new Complex(-ALF,0.0)))
        		
        		).mul(
        
                 Functions.CEXP(
                		 DDZ.EEE.mul(
                				
                	new Complex( Functions.ALAMB(ZAZ.NNN+J)*Y,0.0).add(  
                			Functions.GMMMA(ZAZ.NNN+J).mul(new Complex(-Functions.XX(Y),0.0))
                			
                	)
                				 
                		 		)
                		 )
                 ))
        	    );
        }
//  745   CONTINUE

        UN[i] = S;
           
        }
        System.out.println("   MMVD is being calculated now 7" + "\n" );
        
//  743    CONTINUE

	  }

}
