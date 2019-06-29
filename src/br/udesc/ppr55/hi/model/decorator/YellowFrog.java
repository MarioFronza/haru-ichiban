package br.udesc.ppr55.hi.model.decorator;

import br.udesc.ppr55.hi.model.WaterLily;

public class YellowFrog extends WaterLilyDecorator {


    public YellowFrog(WaterLily waterLily) {
        super(waterLily);
        this.setImage("images/water-lily-with-yellow-frog.png");
    }


    @Override
    public String getFrogColor() {
        return "yellow";
    }

}
