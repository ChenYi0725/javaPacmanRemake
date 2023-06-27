package pacman;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class Pacman implements ActionListener{
    static int blockSize=PacmanGame.blockSize;
    static int offsetX=PacmanGame.offsetX;
    static int offsetY=PacmanGame.offsetY;
    static int pacmanSpeed=PacmanGame.pacmanSpeed;

    static ImageIcon upIcon = new ImageIcon("img/pacman_up.png");            //獲取相應路徑下的圖片
    static ImageIcon downIcon = new ImageIcon("img/pacman_down.png");
    static ImageIcon leftIcon = new ImageIcon("img/pacman_left.png");
    static ImageIcon rightIcon = new ImageIcon("img/pacman_right.png");
    static int lives = 3;                //lives
    static boolean isPacmanDead = false; //玩家是否死亡
    static boolean isPelletEaten = false;    //是否吃到大力丸
    static boolean isUp = false;        //現在按下哪個按件
    static boolean isDown = false;
    static boolean isLeft = false;
    static boolean isRight = false;
    static int pacmanX = offsetX + blockSize * 10, pacmanY = offsetY + blockSize * 12;                //pacman位置
    static int pacmanGridX = 10, pacmanGridY = 12;                //pacman位置(格子)
    static EnumSet.Direction pacmanDirection = EnumSet.Direction.left;        //pacman面對的方向 上1 下2 左3 右4




    public void setPacmanIcon() {            //設定pacman的圖案
        if (isUp && !isDown && !isLeft && !isRight) {
            PacmanGame.pacmanLabel.setIcon(upIcon);
        } else if (!isUp && isDown && !isLeft && !isRight) {
            PacmanGame.pacmanLabel.setIcon(downIcon);
        } else if (!isUp && !isDown && isLeft && !isRight) {
            PacmanGame.pacmanLabel.setIcon(leftIcon);
        } else if (!isUp && !isDown && !isLeft && isRight) {
            PacmanGame.pacmanLabel.setIcon(rightIcon);
        }
    }




    public void pacmanMove() {                //移動

        if (isUp && !isDown && !isLeft && !isRight) {                //上
            pacmanDirection = EnumSet.Direction.up;
            if (!((PacmanGame.MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 1) > 0) || (pacmanY - offsetY) % blockSize > 0) {

                if ((pacmanX - offsetX) % blockSize != 0) {
                    pacmanX = (pacmanGridX) * blockSize + offsetX;
                }

                pacmanY = pacmanY - pacmanSpeed;
                if (((pacmanY - offsetY) % blockSize) == (blockSize / 3)) {
                    pacmanGridY = pacmanGridY - 1;
                }
            }
        } else if (!isUp && isDown && !isLeft && !isRight) {
            pacmanDirection = EnumSet.Direction.down;
            if (!((PacmanGame.MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 2) > 0) || (pacmanY - offsetY) % blockSize > 0) {        //如果當前格子右邊可通行||還沒走到底
                if ((pacmanX - offsetX) % blockSize != 0) {                                //如果角色面前有牆壁但判定位置面前沒有牆壁->矯正到正確位置
                    pacmanX = (pacmanGridX) * blockSize + offsetX;
                }

                pacmanY = pacmanY + pacmanSpeed;
                if (((pacmanY - offsetY) % blockSize) == (2 * blockSize / 3)) {            //判斷現在格子
                    pacmanGridY = pacmanGridY + 1;
                }

            }
        } else if (!isUp && !isDown && isLeft && !isRight) {
            pacmanDirection = EnumSet.Direction.left;
            if (!((PacmanGame.MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 4) > 0) || (pacmanX - offsetX) % blockSize > 0) {
                if ((pacmanY - offsetY) % blockSize != 0) {
                    pacmanY = (pacmanGridY) * blockSize + offsetY;
                }

                pacmanX = pacmanX - pacmanSpeed;
                if (((pacmanX - offsetX) % blockSize) == (blockSize / 3)) {
                    pacmanGridX = pacmanGridX - 1;
                }
            }
        } else if (!isUp && !isDown && !isLeft && isRight) {
            pacmanDirection = EnumSet.Direction.right;
            if (!((PacmanGame.MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 8) > 0) || (pacmanX - offsetX) % blockSize > 0) {
                if ((pacmanY - offsetY) % blockSize != 0) {
                    pacmanY = (pacmanGridY) * blockSize + offsetY;
                }

                pacmanX = pacmanX + pacmanSpeed;
                if (((pacmanX - offsetX) % blockSize) == (2 * blockSize / 3)) {
                    pacmanGridX = pacmanGridX + 1;
                }
            }
        }

        PacmanGame.eatDot();
        PacmanGame.pacmanLabel.setLocation(pacmanX, pacmanY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PacmanGame.repaint();
    }


    static class keyboardAdapter extends KeyAdapter {        //內部類別、適配器
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {


            if (PacmanGame.isGameStart) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {            //按下下方向鍵時
                    isDown = true;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    isUp = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    isRight = true;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    isLeft = true;
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {            //按下下方向鍵時
                isDown = false;

            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                isUp = false;

            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                isRight = false;

            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                isLeft = false;
            }

        }


    }

}