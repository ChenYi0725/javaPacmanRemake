package pacman;

import javax.swing.*;


class PacmanWindow extends JFrame{
	static int blockSize=30;
	static int reserveX =40;
	static int reserveY =100;
	
	public PacmanWindow(){
		setTitle("Pacman");
		setSize(blockSize*20+ reserveX *2,blockSize*20+ reserveY *2);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		PacmanGame a=new PacmanGame();
		
		a.setFocusable(true);
		add(a);
		setVisible(true);
	}
	
	public static void main(String[] args){
		PacmanWindow pacmanWindow = new PacmanWindow();
    }
	
	
	
}

