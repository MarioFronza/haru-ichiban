package br.udesc.ppr55.hi.model.decorator;

import br.udesc.ppr55.hi.model.WaterLily;

public class RedFrog extends WaterLilyDecorator {

    public RedFrog(WaterLily waterLily) {
        super(waterLily);
        this.setImage("images/water-lily-with-pink-frog.png");
    }


    @Override
    public String getFrogColor() {
        return "red";
    }

}
