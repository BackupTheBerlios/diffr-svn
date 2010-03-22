package de.berlios.diffr.result;

import de.berlios.diffr.Model;

public class SeriesResult extends Model {
	private static final long serialVersionUID = 1L;
	private Result[] results = null;
	
	public SeriesResult(Result[] results) {
		this.results = results;
	}
	
	public int getPointsNumber() {
		return results.length;
	}
	
	public Result getResult(int i) {
		return results[i];
	}
	
	public void outOfDate() {
		if (results != null)
			for (Result r : results) r.outOfDate();
	}
}
