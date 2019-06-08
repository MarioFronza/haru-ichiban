package br.udesc.ppr55.hi.model;

import br.udesc.ppr55.hi.model.visitor.Visitor;

/**
 * Table class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 07/04/2019
 */
public class Table {

    private Piece[][] grid;

    public Piece[][] getGrid() {
        return grid;
    }

    public void setGrid(Piece[][] table) {
        this.grid = table;
    }

    public void accept(Visitor visitor) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j].accept(visitor);
                if (i <= 3 && j <= 3) {
                    boolean redWinner = verify1RedWinner(i, j);
                    boolean yellowWinner = verify1YellowWinner(i, j);
                    if (redWinner) {
                        System.out.println("Vermelho pontuou 1");
                    }
                    if (yellowWinner) {
                        System.out.println("Amarelo pontuou 1");
                    }
                }

                boolean redWinner = verify2RedWinner(i, j);
                boolean yellowWinner = verify2YellowWinner(i, j);
                if (redWinner) {
                    System.out.println("Vermelho pontuou 2");
                }
                if (yellowWinner) {
                    System.out.println("Amarelo pontuou 2");
                }

                boolean red3Winner = verify3RedWinner(i, j);
                boolean yello3wWinner = verify3YellowWinner(i, j);
                if (red3Winner) {
                    System.out.println("Vermelho pontuou 3");
                }
                if (yello3wWinner) {
                    System.out.println("Amarelo pontuou 3");
                }

                boolean red4Winner = verify4RedWinner(i, j);
                boolean yello4wWinner = verify4YellowWinner(i, j);
                if (red4Winner) {
                    System.out.println("Vermelho pontuou 4");
                }
                if (yello4wWinner) {
                    System.out.println("Amarelo pontuou 4");
                }


            }
        }
    }

    public boolean verify1RedWinner(int x, int y) {
        if (grid[x][y].getClass() == RedFlower.class && grid[x + 1][y].getClass() == RedFlower.class && grid[x][y + 1].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class) {
            return true;
        }
        return false;
    }

    public boolean verify1YellowWinner(int x, int y) {
        if (grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y].getClass() == YellowFlower.class && grid[x][y + 1].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class) {
            return true;
        }
        return false;
    }

    //

    public boolean verify2RedWinner(int x, int y) {
        if (x <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y].getClass() == RedFlower.class && grid[x + 2][y].getClass() == RedFlower.class && grid[x + 3][y].getClass() == RedFlower.class) {
            return true;
        }

        if (y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x][y + 1].getClass() == RedFlower.class && grid[x][y + 2].getClass() == RedFlower.class && grid[x][y + 3].getClass() == RedFlower.class) {
            return true;
        }
        return false;
    }

    public boolean verify2YellowWinner(int x, int y) {
        if (x <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y].getClass() == YellowFlower.class && grid[x + 2][y].getClass() == YellowFlower.class && grid[x + 3][y].getClass() == YellowFlower.class) {
            return true;
        }

        if (y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x][y + 1].getClass() == YellowFlower.class && grid[x][y + 2].getClass() == YellowFlower.class && grid[x][y + 3].getClass() == YellowFlower.class) {
            return true;
        }
        return false;
    }

    //

    public boolean verify3RedWinner(int x, int y) {
        if (x <= 1 && y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class && grid[x + 2][y + 2].getClass() == RedFlower.class && grid[x + 3][y + 3].getClass() == RedFlower.class) {
            return true;
        }

        if (x >= 3 && y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x - 1][y + 1].getClass() == RedFlower.class && grid[x - 2][y + 2].getClass() == RedFlower.class && grid[x - 3][y + 3].getClass() == RedFlower.class) {
            return true;
        }
        return false;
    }

    public boolean verify3YellowWinner(int x, int y) {
        if (x <= 1 && y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class && grid[x + 2][y + 2].getClass() == YellowFlower.class && grid[x + 3][y + 3].getClass() == YellowFlower.class) {
            return true;
        }

        if (x >= 3 && y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x - 1][y + 1].getClass() == YellowFlower.class && grid[x - 2][y + 2].getClass() == YellowFlower.class && grid[x - 3][y + 3].getClass() == YellowFlower.class) {
            return true;
        }
        return false;
    }

    //

    public boolean verify4RedWinner(int x, int y) {
        if (x == 0 && y == 0 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class && grid[x + 2][y + 2].getClass() == RedFlower.class && grid[x + 3][y + 3].getClass() == RedFlower.class && grid[x + 4][y + 4].getClass() == RedFlower.class) {
            return true;
        }

        if (x == 4 && y == 0 && grid[x][y].getClass() == RedFlower.class && grid[x - 1][y + 1].getClass() == RedFlower.class && grid[x - 2][y + 2].getClass() == RedFlower.class && grid[x - 3][y + 3].getClass() == RedFlower.class && grid[x - 4][y + 4].getClass() == RedFlower.class) {
            return true;
        }
        return false;
    }

    public boolean verify4YellowWinner(int x, int y) {
        if (x == 0 && y == 0 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class && grid[x + 2][y + 2].getClass() == YellowFlower.class && grid[x + 3][y + 3].getClass() == YellowFlower.class && grid[x + 4][y + 4].getClass() == YellowFlower.class) {
            return true;
        }

        if (x == 4 && y == 0 && grid[x][y].getClass() == YellowFlower.class && grid[x - 1][y + 1].getClass() == YellowFlower.class && grid[x - 2][y + 2].getClass() == YellowFlower.class && grid[x - 3][y + 3].getClass() == YellowFlower.class && grid[x - 4][y + 4].getClass() == YellowFlower.class) {
            return true;
        }
        return false;
    }


}
