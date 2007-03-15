package de.berlios.diffr;

import de.berlios.diffr.exceptions.*;
import de.berlios.diffr.task.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.result.*;

public class ViewFactory {
	public View makeView(Model model) throws WrongTypeException {
		if (model.getClass() == Task.class) {
			TaskView view = new TaskView();
			view.setTask((Task)model);
			return view;
		}
		if (model.getClass() == InputData.class) {
			return new SmallInputDataView();
		}
		if (model.getClass() == Result.class) {
			return new ResultView();
		}
		throw new WrongTypeException();
	}
}
