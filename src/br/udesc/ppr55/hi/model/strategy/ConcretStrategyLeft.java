package br.udesc.ppr55.hi.model.strategy;

import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.Table;
import br.udesc.ppr55.hi.model.Water;

public class ConcretStrategyLeft implements MoveStrategyWaterLily {
    @Override
    public Table move(Table table) {
        int x = table.getCurrentX();
        int y = table.getCurrentY();

        if (y - 1 != -1) {
            if (table.getGrid()[x][y - 1].getClass() == Water.class) {
                Piece auxPosition = table.getGrid()[x][y - 1];
                table.getGrid()[x][y - 1] = table.getGrid()[x][y];
                table.getGrid()[x][y] = auxPosition;
                return table;
            } else {
                int auxX = -1;
                for (int i = y - 1; i >= 0; i--) {
                    if (table.getGrid()[x][i].getClass() == Water.class) {
                        auxX = i;
                    }
                }
                if (auxX != -1) {
                    for (int i = auxX; i < y; i++) {
                        Piece auxPosition = table.getGrid()[x][i];
                        table.getGrid()[x][i] = table.getGrid()[x][i + 1];
                        table.getGrid()[x][i + 1] = auxPosition;
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
