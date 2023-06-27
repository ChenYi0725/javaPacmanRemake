package pacman;

import java.awt.Image;

import javax.swing.ImageIcon;

public class BlueGhost extends ghost_model {
    static int blueTargetX, blueTargetY;    //藍鬼的目標
    //鬼模式

    BlueGhost() {
        Icon = new ImageIcon("img/blue_ghost.png").getImage();
        x = PacmanGame.offsetX + PacmanGame.blockSize * 11;
        y = PacmanGame.offsetY + PacmanGame.blockSize * 10;
        gridX = 11;
        gridY = 10;
        mode = EnumSet.Mode.goOut;
        direction = EnumSet.Direction.up;
    }

    @Override
    public Image GetIcon() {
        if (mode == EnumSet.Mode.frightened) {
            return fright;
        } else {
            return Icon;         //劃出紅鬼
        }
    }

    @Override
    protected void Eaten() {
        if (Math.abs(x - PacmanGame.pacmanX) < (blockSize / 2) && Math.abs(y - PacmanGame.pacmanY) < (PacmanGame.blockSize / 2)) {//被pacman吃掉
            PacmanGame.score = PacmanGame.score + 10;
            x = offsetX + blockSize * 11;
            y = offsetY + blockSize * 10;
            mode = EnumSet.Mode.goOut;
        }
    }

    @Override
    public void Hit() {
        if (mode == EnumSet.Mode.frightened) {
            Eaten();
        } else {
            PacmanGame.hit(x, y);
        }
    }

    @Override
    public EnumSet.Direction GetDirection() {
        gridX = (x - PacmanGame.offsetX) / PacmanGame.blockSize;        //判斷鬼的位置
        gridY = (y - PacmanGame.offsetY) / PacmanGame.blockSize;
        if (mode == EnumSet.Mode.normal) {
            if (((y - PacmanGame.offsetY) % PacmanGame.blockSize) == 0 && ((x - PacmanGame.offsetX) % PacmanGame.blockSize) == 0) {        //確保鬼會走完一個格子才轉身
                switch (PacmanGame.MapData[gridX + 20 * (gridY - 1) - 1] & 15) {        //判斷鬼該面朝哪
                    case 1:                    //上有牆壁
                        if (direction == EnumSet.Direction.up) {                //鬼從下上來
                            if ((gridX - blueTargetX) >= 0) {        //鬼在目標右邊
                                direction = EnumSet.Direction.left;
                            } else {
                                direction = EnumSet.Direction.right;
                            }
                        } else if (direction == EnumSet.Direction.right || direction == EnumSet.Direction.left) {            //鬼從左或右過來
                            if ((gridY - blueTargetY) < 0) {
                                direction = EnumSet.Direction.down;
                            }
                        }


                        break;
                    case 2:                    //下有牆壁
                        if (direction == EnumSet.Direction.down) {                //鬼從上下來
                            if ((gridX - blueTargetX) >= 0) {        //鬼在目標右邊
                                direction = EnumSet.Direction.left;
                            } else {
                                direction = EnumSet.Direction.right;
                            }
                        } else if (direction == EnumSet.Direction.right || direction == EnumSet.Direction.left) {            //鬼從左或右過來
                            if ((gridY - blueTargetY) > 0) {
                                direction = EnumSet.Direction.up;
                            }
                        }
                        break;
                    case 4:                    //左有牆壁
                        if (direction == EnumSet.Direction.left) {                //鬼從右過來
                            if ((gridY - blueTargetY) <= 0) {        //鬼在目標上面
                                direction = EnumSet.Direction.down;
                            } else {
                                direction = EnumSet.Direction.up;
                            }
                        } else if (direction == EnumSet.Direction.up || direction == EnumSet.Direction.down) {            //鬼從上或下過來
                            if ((gridX - blueTargetX) < 0) {
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
                            if ((gridY - blueTargetY) <= 0) {        //鬼在目標上面
                                direction = EnumSet.Direction.down;
                            } else {
                                direction = EnumSet.Direction.up;
                            }
                        } else if (direction == EnumSet.Direction.up || direction == EnumSet.Direction.down) {            //鬼從上或下過來
                            if ((gridX - blueTargetX) > 0) {
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
                            if ((gridY - blueTargetY) <= 0) {        //鬼在目標上面
                                direction = EnumSet.Direction.down;
                            } else if ((gridY - blueTargetY) >= 0) {    //鬼在目標下面
                                direction = EnumSet.Direction.up;
                            }
                        } else {                                    //面對上或下
                            if ((gridX - blueTargetX) <= 0) {        //鬼在人左邊
                                direction = EnumSet.Direction.right;
                            } else if ((gridX - blueTargetX) >= 0) {    //鬼在目標右邊
                                direction = EnumSet.Direction.left;
                            }
                        }
                        break;
                }
            }
        } else if (mode == EnumSet.Mode.frightened) {
            if (((y - PacmanGame.offsetY) % PacmanGame.blockSize) == 0 && ((x - PacmanGame.offsetX) % PacmanGame.blockSize) == 0) {
                direction = frightened(gridX, gridY, direction);
            }

        } else if (mode == EnumSet.Mode.goOut) {
            direction = EnumSet.Direction.up;
            if (((y - PacmanGame.offsetY) % PacmanGame.blockSize) == 0 && ((x - PacmanGame.offsetX) % PacmanGame.blockSize) == 0) {
                if (gridY == 7) {
                    mode = EnumSet.Mode.normal;
                    direction = EnumSet.Direction.right;
                }
            }

        }
        return direction;
    }

    @Override
    public void Move() {
        direction = GetDirection();
        switch (direction) {
            case up ->        //向上
                    y = y - ghostSpeed;
            case down ->        //向下
                    y = y + ghostSpeed;
            case left ->        //向左
                    x = x - ghostSpeed;
            case right ->        //向右
                    x = x + ghostSpeed;
        }


    }

    @Override
    public void reset() {
        x = offsetX + blockSize * 11;
        y = offsetY + blockSize * 10;//藍鬼的位置
        mode = EnumSet.Mode.goOut;        //鬼模式
    }


}
