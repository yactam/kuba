package model.plateau;

import model.Bille;
import model.Couleur;
import model.mouvement.Direction;
import model.mouvement.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Board extends JPanel{

    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;

    public Board(int n) {
        this.n = n;
        int k = 4 * n - 1;
        board = new Cell[k][k];
        keys  = new Long[3][k*k];
        initKeys();
        // Panel
        setPreferredSize(new Dimension(Bille.width * k, Bille.width * k));
    }

    private static void initKeys() {
        for(int i = 0; i < keys.length; i++) {
            for(int j = 0; j < keys[i].length; j++) {
                keys[i][j] = new Random().nextLong();
            }
        }
    }

    public void initBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
            }
        }
        initWhite();
        initBlack();
        initRed();
    }

    private void initWhite() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                board(i, j).setBille(new Bille(Couleur.BLANC));
                board(board.length-1 - i, board.length-1 - j).setBille(new Bille(Couleur.BLANC));
            }
        }
    }

    private void initBlack() {
        for(int i = 0; i < n; i++) {
            for(int j = board[i].length-1; j >= board[i].length-n; j--) {
                board(i, j).setBille(new Bille(Couleur.NOIR));
                board(j, i).setBille(new Bille(Couleur.NOIR));
            }
        }
    }

    private void initRed() {
        int count = 1, spaces = 1, k = 4 * n - 1;
        for(int i = 1; i < k-1; i++) {
            if(i < k/2) {
                spaces = (k/2) - i + 1;
            } else {
                spaces = i + 1 - (k/2);
            }
            for(int j = 0; j < count; j++) {
                board(i, j+spaces).setBille(new Bille(Couleur.ROUGE));
            }
            if(i < k/2) {
                count += 2;
            } else {
                count -= 2;
            }
        }
    }

    private Cell board(Position pos) {
        return board[pos.gPosX()][pos.gPosY()];
    }

    private Cell board(int i, int j) {
        return board[i][j];
    }

    public void bouger(Position pos, Direction dir) {
        int x = pos.gPosX(), y = pos.gPosY();
        int dx = dir.gDirX(), dy = dir.gDirY();

        while(estDansLimite(x+dx, y+dy) && !board(pos).estVide()) {
            x += dx;
            y += dy;
        }

        if(dx == 0) {
            for(int i = y; i != pos.gPosY(); i -= dy) {
                board(x, i).setBille(board(x, i-dy).getBille());
                board(x, i-dy).clear();
            }
        }
        if(dy == 0) {
            for(int i = x; i != pos.gPosX(); i -= dx) {
                board(i, y).setBille(board(i-dx, y).getBille());
                board(i-dx, y).clear();
            }
        }

    }

    private boolean estDansLimite(int i, int j) {
        return i >= 0 && i < board.length && j >= 0 && j < board[i].length;
    }

    @Override
    public String toString() {
        StringBuilder plateau = new StringBuilder();
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                plateau.append(cell.toString()).append(" ");
            }
            plateau.append("\n");
        }
        return plateau.toString();
    }

    @Override
    public int hashCode() {
        long zobristHash = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(!board(i, j).estVide()) {
                    switch (board[i][j].getBille().getColor()) {
                        case ROUGE -> zobristHash = zobristHash ^ keys[0][i + board[i].length * j];
                        case NOIR -> zobristHash = zobristHash ^ keys[1][i + board[i].length * j];
                        case BLANC -> zobristHash = zobristHash ^ keys[2][i + board[i].length * j];
                    }
                }
            }
        }
        return (int) zobristHash;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Board)) return false;
        return o.hashCode() == this.hashCode();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        drawGrid(graphics2D);
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(!board(j, i).estVide()) {
                    int width = Bille.width;
                    graphics2D.drawImage(board(j, i).getBille().image(), width * i, width * j, width, width, null);
                }
            }
        }
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(i != board.length-1 && j != board[i].length-1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * Bille.width + (Bille.width / 2), i * Bille.width + (Bille.width / 2), Bille.width, Bille.width);
                }
            }
        }
    }

}
