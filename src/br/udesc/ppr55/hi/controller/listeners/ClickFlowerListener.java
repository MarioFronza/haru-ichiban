package br.udesc.ppr55.hi.controller.listeners;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.controller.command.AddFlower;
import br.udesc.ppr55.hi.controller.command.CommandInvoker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickFlowerListener implements MouseListener {

    private int x;
    private int y;
    private CommandInvoker commandInvoker;
    private IHaruController haruController;

    public ClickFlowerListener(int x, int y, IHaruController haruController) {
        this.x = x;
        this.y = y;
        this.haruController = haruController;
        this.commandInvoker = new CommandInvoker();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        commandInvoker.add(new AddFlower(x, y, haruController));
        commandInvoker.execute();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
