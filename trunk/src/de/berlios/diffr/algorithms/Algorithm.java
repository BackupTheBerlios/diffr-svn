package de.berlios.diffr.algorithms;

import de.berlios.diffr.result.*;
import de.berlios.diffr.inputData.*;
import de.berlios.diffr.exceptions.*;

public abstract class Algorithm {
	public abstract Result run(InputData inputData) throws ErrorInAlgorithmException;
}
