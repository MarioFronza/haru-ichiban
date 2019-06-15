package br.udesc.ppr55.hi.model.decorator;

import br.udesc.ppr55.hi.model.WaterLily;

public class YellowEggsDecorator extends WaterLilyDecorator {

	public YellowEggsDecorator(WaterLily waterLily) {
		super(waterLily);
		this.setImage("images/water-lily-with-yellow-eggs.png");
	}
	
}
