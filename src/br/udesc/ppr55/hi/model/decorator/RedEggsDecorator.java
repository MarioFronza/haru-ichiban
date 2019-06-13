package br.udesc.ppr55.hi.model.decorator;

import br.udesc.ppr55.hi.model.WaterLily;

public class RedEggsDecorator extends WaterLilyDecorator {

	public RedEggsDecorator(WaterLily waterLily) {
		super(waterLily);
		this.setImage("images/water-lily-with-pink-eggs.png");
	}

}
