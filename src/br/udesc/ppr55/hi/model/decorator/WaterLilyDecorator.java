package br.udesc.ppr55.hi.model.decorator;

import br.udesc.ppr55.hi.model.WaterLily;

public abstract class WaterLilyDecorator extends WaterLily {
	
	private WaterLily waterLily;
	
	public WaterLilyDecorator(WaterLily waterLily) {
		this.waterLily = waterLily;
	}
	
	public WaterLily getWaterLily() {
		return waterLily;
	}

	public String getFrogColor() {
		return "";
	}
	
}
