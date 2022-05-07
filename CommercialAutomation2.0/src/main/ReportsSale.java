package main;

import java.time.LocalDate;

import management_models.ManagementSales;

public class ReportsSale {
	private ManagementSales salesManag;
	
	ReportsSale(ManagementSales salesManag){
		this.salesManag = salesManag;
	}
	
	public void generalReport() {
		
	}
	
	public void byPeriod(LocalDate firstDate, LocalDate lastDate) {
		
	}
	
	public void byPlate() {
		
	}
}

