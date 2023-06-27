package pacman;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class ghost_model {
    Image fright = new ImageIcon("img/frightened.png").getImage();//鬼的圖片
    Image Icon;
    int x, y;
    int gridX, gridY;

    EnumSet.Direction direction;
    int ghostSpeed=PacmanGame.ghostSpeed;
    int offsetX=PacmanGame.offsetX;
    int offsetY=PacmanGame.offsetY;
    int blockSize=PacmanGame.blockSize;
    EnumSet.Mode mode;

    public EnumSet.Direction frightened(int ghostX, int ghostY, EnumSet.Direction direction) {
        switch (PacmanGame.MapData[ghostX + 20 * (ghostY - 1) - 1] & 15) {        //判斷鬼該面朝哪
            case 1:                    //上有牆壁
                if (direction == EnumSet.Direction.up) {                //鬼從下上來
                    if ((ghostX - PacmanGame.pacmanGridX) >= 0) {        //鬼在人右邊
                        direction = EnumSet.Direction.right;
                    } else {
                        direction = EnumSet.Direction.left;
                    }
                } else if (direction == EnumSet.Direction.right || direction == EnumSet.Direction.left) {            //鬼從左或右過來
                    if ((ghostY - PacmanGame.pacmanGridY) > 0) {            //人跟鬼
                        direction = EnumSet.Direction.down;
                    }
                }

                break;
            case 2:                    //下有牆壁
                if (direction == EnumSet.Direction.down) {                //鬼從上下來
                    if ((ghostX - PacmanGame.pacmanGridX) >= 0) {        //鬼在人右邊
                        direction = EnumSet.Direction.right;
                    } else {
                        direction = EnumSet.Direction.left;
                    }
                } else if (direction == EnumSet.Direction.right || direction == EnumSet.Direction.left) {            //鬼從左或右過來
                    if ((ghostY - PacmanGame.pacmanGridY) < 0) {
                        direction = EnumSet.Direction.up;
                    }
                }
                break;
            case 4:                    //左有牆壁
                if (direction == EnumSet.Direction.left) {                //鬼從右過來
                    if ((ghostY - PacmanGame.pacmanGridY) >= 0) {        //鬼在人上面
                        direction = EnumSet.Direction.down;
                    } else {
                        direction = EnumSet.Direction.up;
                    }
                } else if (direction == EnumSet.Direction.up || direction == EnumSet.Direction.down) {            //鬼從上或下過來
                    if ((ghostX - PacmanGame.pacmanGridX) > 0) {
                        direction = EnumSet.Direction.right;
                    }
                }
                break;
            case 5:                    //上左有牆壁
                if (direction == EnumSet.Direction.left) {
                    direction = EnumSet.Direction.down;
                } else if (direction == EnumSet.Direction.up) {
                    direction = EnumSet.Direction.right;
                }
                break;
            case 6:                    //下左有牆壁
                if (direction == EnumSet.Direction.left) {
                    direction = EnumSet.Direction.up;
                } else if (direction == EnumSet.Direction.down) {
                    direction = EnumSet.Direction.right;
                }
                break;
            case 8:                    //右有牆壁
                if (direction == EnumSet.Direction.right) {                //鬼從左過來
                    if ((ghostY - PacmanGame.pacmanGridY) >= 0) {        //鬼在人上面
                        direction = EnumSet.Direction.down;
                    } else {
                        direction = EnumSet.Direction.up;
                    }
                } else if (direction == EnumSet.Direction.up || direction == EnumSet.Direction.down) {            //鬼從上或下過來
                    if ((ghostX - PacmanGame.pacmanGridX) < 0) {
                        direction = EnumSet.Direction.left;
                    }
                }
                break;
            case 9:                    //上右有牆壁
                if (direction == EnumSet.Direction.right) {
                    direction = EnumSet.Direction.down;
                } else if (direction == EnumSet.Direction.up) {
                    direction = EnumSet.Direction.left;
                }
                break;
            case 10:                //下右有牆壁
                if (direction == EnumSet.Direction.right) {
                    direction = EnumSet.Direction.up;
                } else if (direction == EnumSet.Direction.down) {
                    direction = EnumSet.Direction.left;
                }
                break;
            case 0:                    //十字路口
                if (direction == EnumSet.Direction.left || direction == EnumSet.Direction.right) {                //面對左或右
                    if ((ghostY - PacmanGame.pacmanGridY) <= 0) {        //鬼在人上面
                        direction = EnumSet.Direction.up;
                    } else if ((ghostY - PacmanGame.pacmanGridY) >= 0) {    //鬼在人下面
                        direction = EnumSet.Direction.down;
                    }
                } else {                                    //面對上或下
                    if ((ghostX - PacmanGame.pacmanGridX) <= 0) {        //鬼在人左邊
                        direction = EnumSet.Direction.left;
                    } else if ((ghostX - PacmanGame.pacmanGridX) >= 0) {    //鬼在人右邊
                        direction = EnumSet.Direction.right;
                    }
                }


                break;
        }
        return direction;
    }


    public abstract Image GetIcon();

    protected abstract void Eaten();



    public abstract void Hit();


    public abstract EnumSet.Direction GetDirection();

    public abstract void Move();

    public abstract void reset();

}
