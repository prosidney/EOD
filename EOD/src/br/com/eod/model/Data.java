package br.com.eod.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Data extends Date implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GregorianCalendar gc = new GregorianCalendar();
	
	public Data() {
		super();
	}

	public Data(long date) {
		super(date);
	}
	
	public int getDia(){
		gc.setTime(this);
		return gc.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getMes(){
		gc.setTime(this);
		return gc.get(Calendar.MONTH);
	}
	
	public int getAno(){
		gc.setTime(this);
		return gc.get(Calendar.YEAR);
	}
}
