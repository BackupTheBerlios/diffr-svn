package de.berlios.diffr;

import java.awt.*;
import de.berlios.diffr.exceptions.*;
import de.berlios.diffr.task.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForVerySimpleTask.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.*;
import de.berlios.diffr.inputData.inputDataForDiffractionOfPlaneWaveOnPeriodicSurface.periodicSurface.*;
import de.berlios.diffr.result.*;
import de.berlios.diffr.result.resultForVerySimpleTask.*;
import de.berlios.diffr.result.resultForDiffractionOfPlaneWaveOnPriodicSurface.*;

public class ViewFactory {
	public View makeView(Model model) throws WrongTypeException {
		if (model == null) {
			throw new NullPointerException();
		}
		if (model.getClass() == Task.class) {
			return new TaskView((Task)model);
		}
		if (model.getClass() == InputData.class) {
			return new SmallInputDataView((InputData)model);
		}
		if (model.getClass() == Result.class) {
			ResultView view = new ResultView();
			view.setResult((Result)model);
			return view;
		}
		if (model.getClass() == DataString.class) {
			return new DataStringView( (DataString) model);
		}
		if (model.getClass() == ImpingingPlaneWave.class) {
			return new ImpingingPlaneWaveView( (ImpingingPlaneWave) model);
		}
		if (model.getClass() == PeriodicSurface.class) {
			return new PeriodicSurfaceView((PeriodicSurface)model);
		}
		if (model.getClass() == ReflectedFieldOfPlaneWaves.class) {
			return new ReflectedFieldOfPlaneWavesView((ReflectedFieldOfPlaneWaves)model);
		}
		throw new WrongTypeException();
	}
}
