package de.berlios.diffr.inputData.surface;

import java.util.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import de.berlios.diffr.*;
import de.berlios.diffr.exceptions.ObjectIsnotEditableException;
import de.berlios.diffr.exceptions.WrongTypeException;

public class SurfaceShapeView extends View {
	private static final long serialVersionUID = 1L;
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
		coefNumberData = new DataString("Number of Fourier harmonics", new Integer(surfaceShape.getFourierCoefficients().size()));
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
					ArrayList<FourierCoefficient> newCoefs = new ArrayList<FourierCoefficient>();
					for (int a=0;a<((Integer)coefNumberData.getValue()).intValue();a++) {
						if (a<surfaceShape.getFourierCoefficients().size())
							newCoefs.add(surfaceShape.getFourierCoefficients().get(a));
						else
							newCoefs.add(new FourierCoefficient(0, 0));
					}
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
				table.addNotify();
			}
		});
		makeFourierCoefficientsTable();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(30));
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
		return (maxHeight - minHeight)*2;
	}
	private double maxHeight = 0;
	private double minHeight = 0;
	public void drawImage(Graphics g, double scale) {
		int centerX = g.getClipBounds().width / 2;
		int centerY = g.getClipBounds().height / 2;
		double newMaxHeight = Double.MIN_VALUE;
		double newMinHeight = Double.MAX_VALUE;
		double x=0;
		double lastX = 0;
		double lastY = 0;
		while (x<surfaceShape.getPeriod()) {
			double y = 0;
			Iterator<FourierCoefficient> i = surfaceShape.getFourierCoefficients().iterator();
			int n = 0;
			while (i.hasNext()) {
				FourierCoefficient c = i.next();
				n++;
				y += c.getCoefficientOfCosinus() * Math.cos(n * x * 2 * Math.PI / surfaceShape.getPeriod());
				y += c.getCoefficientOfSinus() * Math.sin(n * x * 2 * Math.PI / surfaceShape.getPeriod());
			}
			if (y<newMinHeight) newMinHeight = y;
			if (y>newMaxHeight) newMaxHeight = y;
			lastX = x;
			lastY = y;
			x += 1 / scale;
		}
		if ((maxHeight != newMaxHeight) || (minHeight != newMinHeight)) {
			maxHeight = newMaxHeight;
			minHeight = newMinHeight;
		}
		{
			g.setColor(Color.green);
			int x1 = (int)(centerX - surfaceShape.getPeriod() / 2 * scale);
			int x2 = (int)(centerX + surfaceShape.getPeriod() / 2 * scale);
			int yv = (int)(centerY + maxHeight * scale); 
			g.drawLine(x1, yv, x2, yv);
			for (double a=0;a<=surfaceShape.getPeriod();a+=surfaceShape.getPeriod()/4) {
				int vx = x1+(int)(a*scale);
				g.drawLine(vx, yv-2, vx, yv+2);
				double v = (int)(a*100); v/=100;
				g.drawString(""+v, vx-10, yv+15);
			}
		}
		
		x=0;
		lastX = 0;
		lastY = 0;
		g.setColor(new Color(0, 0, 255));
		while (x<surfaceShape.getPeriod()) {
			double y = 0;
			Iterator<FourierCoefficient> i = surfaceShape.getFourierCoefficients().iterator();
			int n = 0;
			while (i.hasNext()) {
				FourierCoefficient c = i.next();
				n++;
				y += c.getCoefficientOfCosinus() * Math.cos(n * x * 2 * Math.PI / surfaceShape.getPeriod());
				y += c.getCoefficientOfSinus() * Math.sin(n * x * 2 * Math.PI / surfaceShape.getPeriod());
			}
			int x1 = (int)(centerX - surfaceShape.getPeriod() / 2 * scale + lastX * scale);
			int x2 = (int)(centerX - surfaceShape.getPeriod() / 2 * scale + x * scale);
			int y1 = (int)(centerY - lastY * scale + maxHeight * scale);
			int y2 = (int)(centerY - y * scale + maxHeight * scale);
			if (x>0) g.drawLine(x1, y1, x2, y2);
			lastX = x;
			lastY = y;
			x += 1 / scale;
		}
	}
	private void makeFourierCoefficientsTable() {
		TableModel model = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;
			public int getColumnCount() { return 3; }
	        public int getRowCount() { return surfaceShape.getFourierCoefficients().size();}
	        public Object getValueAt(int row, int col) {
	        	if (col==0)
					return new Integer(row+1);
	        	if (col==1)
					return new Double(((FourierCoefficient)surfaceShape.getFourierCoefficients().get(row)).getCoefficientOfCosinus());
	        	return new Double(((FourierCoefficient)surfaceShape.getFourierCoefficients().get(row)).getCoefficientOfSinus());
	        }
	        public String getColumnName(int n) {
	        	switch (n) {
	        	case 0:return "#";
	        	case 1:return "coef. at cos.";
	        	case 2:return "coef. at sin.";
	        	}
	        	return "";
	        }
	        public boolean isCellEditable(int y, int x) {
	        	if (x>0) return true;
	        	return false;
	        }
		};
		table = new JTable(model) {
			private static final long serialVersionUID = 1L;
			public void editingStopped(ChangeEvent e) {
				try {
					double cosVal;
					double sinVal;
					if (table.getSelectedColumn() == 1) {
						cosVal = Double.parseDouble((String)table.getCellEditor().getCellEditorValue());
						sinVal = ((FourierCoefficient)surfaceShape.getFourierCoefficients().get(table.getSelectedRow())).getCoefficientOfSinus();
					} else {
						sinVal = Double.parseDouble((String)table.getCellEditor().getCellEditorValue());
						cosVal = ((FourierCoefficient)surfaceShape.getFourierCoefficients().get(table.getSelectedRow())).getCoefficientOfCosinus();
					}
					ArrayList<FourierCoefficient> newCoef = surfaceShape.getFourierCoefficients();
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
