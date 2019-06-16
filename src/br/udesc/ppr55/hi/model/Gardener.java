package br.udesc.ppr55.hi.model;

/**
 * Gardener class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 11/05/2019
 */
public abstract class Gardener extends Piece {

    protected boolean isJunior;
    protected String name;
    protected int score;

    public Gardener(String image) {
        super(image);
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = this.score + score;
        System.out.println("score"+score);
    }

    public boolean isJunior() {
        return isJunior;
    }

    public void setJunior(boolean junior) {
        isJunior = junior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
