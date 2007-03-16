package de.berlios.diffr;

import de.berlios.diffr.exceptions.*;
import de.berlios.diffr.task.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.inputData.inputDataForVerySimpleTask.*;
import de.berlios.diffr.result.*;
import de.berlios.diffr.algorithms.*;
import de.berlios.diffr.result.resultForVerySimpleTask.*;

public class ViewFactory {
	public View makeView(Model model) throws WrongTypeException {
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
		if (model.getClass() == PlaneMirrowSurface.class) {
			return new PlaneMirrowSurfaceView( (PlaneMirrowSurface) model);
		}
		if (model.getClass() == VerySimpleWave.class) {
			return new VerySimpleWaveView( (VerySimpleWave) model);
		}
		if (model.getClass() == ReflectedWave.class) {
			return new ReflectedWaveView( (ReflectedWave) model);
		}
		if (model.getClass() == AlgorithmParameter.class) {
			return new AlgorithmParameterView( (AlgorithmParameter) model);
		}
		throw new WrongTypeException();
	}
}
