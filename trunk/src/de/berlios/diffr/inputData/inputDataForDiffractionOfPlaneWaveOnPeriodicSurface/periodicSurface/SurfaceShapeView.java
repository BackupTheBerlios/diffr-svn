package de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface;

import java.util.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import de.berlios.diffr.*;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.exceptions.WrongTypeException;

public class SurfaceShapeView extends View {
	private SurfaceShape surfaceShape;
	private DataString periodData;
	private DataStringView periodView;
	private DataString shiftData;
	private DataStringView shiftView;
	private DataString coefNumberData;
	private DataStringView coefNumberView;
	private JTable table;
	private JScrollPane scrollTable;
	public SurfaceShapeView(SurfaceShape shape) {
		surfaceShape = shape;
		periodData = new DataString("period", new Double(surfaceShape.getPeriod()));
		shiftData = new DataString("shift", new Double(surfaceShape.getShift()));
		coefNumberData = new DataString("Number of Fourier coefficients", new Integer(surfaceShape.getFourierCoefficients().size()));
		periodView = new DataStringView(periodData);
		shiftView = new DataStringView(shiftData);
		coefNumberView = new DataStringView(coefNumberData);
		periodData.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				try {
					surfaceShape.setPeriod(((Double)periodData.getValue()).doubleValue());
				} catch (ObjectIsnotEditableException e) {
					JOptionPane.showMessageDialog(null, "You can`t change this now");
					try {
						periodData.removeModelChangingListener(this);
						periodData.setValue(new Double(surfaceShape.getPeriod()));
						periodData.addModelChangingListener(this);
						periodView.renew();
					} catch (WrongTypeException e1) {e1.printStackTrace();
					} catch (ObjectIsnotEditableException e1) {e1.printStackTrace();}
				}
			}
		});
		shiftData.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				try {
					surfaceShape.setShift(((Double)shiftData.getValue()).doubleValue());
				} catch (ObjectIsnotEditableException e) {
					JOptionPane.showMessageDialog(null, "You can`t change this now");
					try {
						shiftData.removeModelChangingListener(this);
						shiftData.setValue(new Double(surfaceShape.getShift()));
						shiftData.addModelChangingListener(this);
						shiftView.renew();
					} catch (WrongTypeException e1) {e1.printStackTrace();
					} catch (ObjectIsnotEditableException e1) {e1.printStackTrace();}
				}
			}
		});
		coefNumberData.addModelChangingListener(new ModelChangingListener() {
			public void modelWasChanged(Model model) {
				try {
					ArrayList newCoefs = new ArrayList();
					for (int a=0;a<((Integer)coefNumberData.getValue()).intValue();a++)
						newCoefs.add(new FourierCoefficient(0, 0));
					surfaceShape.setFourierCoefficients(newCoefs);
				} catch (ObjectIsnotEditableException e) {
					JOptionPane.showMessageDialog(null, "You can`t change this now");
					try {
						coefNumberData.removeModelChangingListener(this);
						coefNumberData.setValue(new Integer(surfaceShape.getFourierCoefficients().size()));
						coefNumberData.addModelChangingListener(this);
						coefNumberView.renew();
					} catch (WrongTypeException e1) {e1.printStackTrace();
					} catch (ObjectIsnotEditableException e1) {e1.printStackTrace();}
				}
				validateTree();
				repaint();
			}
		});
		makeFourierCoefficientsTable();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(10));
		this.add(periodView);
		this.add(Box.createVerticalStrut(10));
		this.add(shiftView);
		this.add(Box.createVerticalStrut(10));
		this.add(coefNumberView);
		this.add(Box.createVerticalStrut(10));
		this.add(scrollTable);
		this.validate();
	}
	public double getModelSizeX() {
		return surfaceShape.getPeriod();
	}
	public double getModelSizeY() {
		return 0;
	}
	public void drawImage(Graphics g, double scale) {
		g.setColor(new Color(50, 50, 50));
		int centerX = g.getClipBounds().width / 2;
		int centerY = g.getClipBounds().height / 2;
		g.drawLine((int)(centerX - surfaceShape.getPeriod() / 2 * scale), centerY, (int)(centerX + surfaceShape.getPeriod() / 2 * scale), centerY);
	}
	private void makeFourierCoefficientsTable() {
		TableModel model = new AbstractTableModel() {
			public int getColumnCount() { return 3; }
	        public int getRowCount() { return surfaceShape.getFourierCoefficients().size();}
	        public Object getValueAt(int row, int col) {
	        	if (col==0)
					return new Integer(row);
	        	if (col==1)
					return new Double(((FourierCoefficient)surfaceShape.getFourierCoefficients().get(row)).getCoefficientOfCosinis());
	        	return new Double(((FourierCoefficient)surfaceShape.getFourierCoefficients().get(row)).getCoefficientOfSinis());
	        }
	        public String getColumnName(int n) {
	        	switch (n) {
	        	case 0:return "¹";
	        	case 1:return "coef. of cos.";
	        	case 2:return "coef. of sin.";
	        	}
	        	return "";
	        }
	        public boolean isCellEditable(int y, int x) {
	        	if (x>0) return true;
	        	return false;
	        }
		};
		table = new JTable(model) {
			public void editingStopped(ChangeEvent e) {
				try {
					double cosVal;
					double sinVal;
					if (table.getSelectedColumn() == 1) {
						cosVal = Double.parseDouble((String)table.getCellEditor().getCellEditorValue());
						sinVal = ((FourierCoefficient)surfaceShape.getFourierCoefficients().get(table.getSelectedRow())).getCoefficientOfSinis();
					} else {
						sinVal = Double.parseDouble((String)table.getCellEditor().getCellEditorValue());
						cosVal = ((FourierCoefficient)surfaceShape.getFourierCoefficients().get(table.getSelectedRow())).getCoefficientOfCosinis();
					}
					ArrayList newCoef = surfaceShape.getFourierCoefficients();
					newCoef.set(table.getSelectedRow(), new FourierCoefficient(cosVal, sinVal));
					surfaceShape.setFourierCoefficients(newCoef);
				} catch (ObjectIsnotEditableException e1) {
					JOptionPane.showMessageDialog(null, "You can`t change this now");
				}
				catch (Exception e1) {}
				super.editingStopped(e);
			}
		};
		scrollTable = new JScrollPane(table);
	}
}
