package br.udesc.ppr55.hi.controller.strategy;

import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.Table;
import br.udesc.ppr55.hi.model.Water;

public class ConcretStrategyUp implements MoveStrategyWaterLily {
    @Override
    public Table move(Table table) {
        int x = table.getCurrentX();
        int y = table.getCurrentY();

        if (x - 1 != -1) {
            if (table.getGrid()[x - 1][y].getClass() == Water.class) {
                Piece auxPosition = table.getGrid()[x - 1][y];
                table.getGrid()[x - 1][y] = table.getGrid()[x][y];
                table.getGrid()[x][y] = auxPosition;
                return table;
            } else {
                int auxX = -1;
                for (int i = x - 1; i >= 0; i--) {
                    if (table.getGrid()[i][y].getClass() == Water.class)
                        auxX = i;

                    if (auxX != -1)
                        break;
                }
                if (auxX != -1) {
                    for (int i = auxX; i < x; i++) {
                        Piece auxPosition = table.getGrid()[i][y];
                        table.getGrid()[i][y] = table.getGrid()[i + 1][y];
                        table.getGrid()[i + 1][y] = auxPosition;
                    }
                    return table;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }
}
