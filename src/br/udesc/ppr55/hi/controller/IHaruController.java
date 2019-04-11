package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observed;

public interface IHaruController extends Observed {

    String getPiece(int col, int row);
    void itemClicked(int x, int y);
    void initializeBoard();

}
