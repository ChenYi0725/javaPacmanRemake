package pacman;

import javax.swing.*;

import java.awt.event.*;

public class Pacman implements ActionListener{
    static int blockSize=PacmanGame.blockSize;
    static int offsetX=PacmanGame.offsetX;
    static int offsetY=PacmanGame.offsetY;
    static int speed =PacmanGame.pacmanSpeed;

    static ImageIcon upIcon = new ImageIcon("img/pacman_up.png");            //獲取相應路徑下的圖片
    static ImageIcon downIcon = new ImageIcon("img/pacman_down.png");
    static ImageIcon leftIcon = new ImageIcon("img/pacman_left.png");
    static ImageIcon rightIcon = new ImageIcon("img/pacman_right.png");
    static boolean isUp = false;        //現在按下哪個按件
    static boolean isDown = false;
    static boolean isLeft = false;
    static boolean isRight = false;
    static int x = offsetX + blockSize * 10, y = offsetY + blockSize * 12;                //pacman位置
    static int gridX = 10, gridY = 12;                //pacman位置(格子)
    static EnumSet.Direction direction = EnumSet.Direction.left;        //pacman面對的方向 上1 下2 左3 右4



    public void reset(){
        PacmanGame.pacmanLabel.setIcon(null);
        direction = EnumSet.Direction.left;
        x = offsetX + blockSize * 10;
        y = offsetY + blockSize * 12;                //pacman位置
        gridX = 10;
        gridY = 12;                //pacman位置(格子)
        isUp = false;        //現在按下哪個按件
        isDown = false;
        isLeft = false;
        isRight = false;
    }
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
            direction = EnumSet.Direction.up;
            if (!((PacmanGame.MapData[gridX + 20 * (gridY - 1) - 1] & 1) > 0) || (y - offsetY) % blockSize > 0) {

                if ((x - offsetX) % blockSize != 0) {
                    x = (gridX) * blockSize + offsetX;
                }

                y = y - speed;
                if (((y - offsetY) % blockSize) == (blockSize / 3)) {
                    gridY = gridY - 1;
                }
            }
        } else if (!isUp && isDown && !isLeft && !isRight) {
            direction = EnumSet.Direction.down;
            if (!((PacmanGame.MapData[gridX + 20 * (gridY - 1) - 1] & 2) > 0) || (y - offsetY) % blockSize > 0) {        //如果當前格子右邊可通行||還沒走到底
                if ((x - offsetX) % blockSize != 0) {                                //如果角色面前有牆壁但判定位置面前沒有牆壁->矯正到正確位置
                    x = (gridX) * blockSize + offsetX;
                }

                y = y + speed;
                if (((y - offsetY) % blockSize) == (2 * blockSize / 3)) {            //判斷現在格子
                    gridY = gridY + 1;
                }

            }
        } else if (!isUp && !isDown && isLeft && !isRight) {
            direction = EnumSet.Direction.left;
            if (!((PacmanGame.MapData[gridX + 20 * (gridY - 1) - 1] & 4) > 0) || (x - offsetX) % blockSize > 0) {
                if ((y - offsetY) % blockSize != 0) {
                    y = (gridY) * blockSize + offsetY;
                }

                x = x - speed;
                if (((x - offsetX) % blockSize) == (blockSize / 3)) {
                    gridX = gridX - 1;
                }
            }
        } else if (!isUp && !isDown && !isLeft && isRight) {
            direction = EnumSet.Direction.right;
            if (!((PacmanGame.MapData[gridX + 20 * (gridY - 1) - 1] & 8) > 0) || (x - offsetX) % blockSize > 0) {
                if ((y - offsetY) % blockSize != 0) {
                    y = (gridY) * blockSize + offsetY;
                }

                x = x + speed;
                if (((x - offsetX) % blockSize) == (2 * blockSize / 3)) {
                    gridX = gridX + 1;
                }
            }
        }

        PacmanGame.eatDot();
        PacmanGame.pacmanLabel.setLocation(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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