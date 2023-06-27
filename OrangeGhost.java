package pacman;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;


public class OrangeGhost extends ghost_model {
    Random random = new Random();
    static EnumSet.Direction nextOrangeDirection = EnumSet.Direction.up;

    static boolean isEvolved = false;
    static Image evolvesIcon = new ImageIcon("img/orange_evolves.png").getImage();//鬼的圖片

    OrangeGhost() {
        Icon = new ImageIcon("img/orange_ghost.png").getImage();
        direction = EnumSet.Direction.up;
        x = offsetX + blockSize * 12;
        y = offsetY + blockSize * 10;
        gridX = 10;
        gridY = 10;
        mode = EnumSet.Mode.goOut;

    }

    @Override
    public Image GetIcon() {
        switch (mode) {
            case frightened -> {
                return fright;
            }
            case evolved -> {
                return evolvesIcon;
            }
            default -> {
                return Icon;
            }
        }
    }

    @Override
    protected void Eaten(Pacman pacman) {
        if (Math.abs(x - pacman.x) < (blockSize / 2) && Math.abs(y - pacman.y) < (blockSize / 2)) {//被pacman吃掉
            PacmanGame.score = PacmanGame.score + 10;
            x = offsetX + blockSize * 9;
            y = offsetY + blockSize * 10;
            mode = EnumSet.Mode.goOut;
        }

    }

    @Override
    public void Hit(Pacman pacman) {
        if (mode == EnumSet.Mode.frightened) {
            Eaten(pacman);
        } else {
            PacmanGame.hit(x, y);
        }
    }

    @Override
    public EnumSet.Direction GetDirection(Pacman pacman) {
        gridX = (x - offsetX) / blockSize;        //判斷鬼的位置
        gridY = (y - offsetY) / blockSize;
        if (mode == EnumSet.Mode.normal) {                    //亂走模式
            if (((y - offsetY) % blockSize) == 0 && ((x - offsetX) % blockSize) == 0) {        //確保鬼會走完一個格子才轉身
                switch (PacmanGame.MapData[gridX + 20 * (gridY - 1) - 1] & 15) {        //判斷鬼該面朝哪
                    case 1:                    //上有牆壁
                        if (direction == EnumSet.Direction.up) {                //鬼從下上來
                            if (Math.random() * 2 > 1) {                    //左或右
                                direction = EnumSet.Direction.left;
                            } else {
                                direction = EnumSet.Direction.right;
                            }
                        } else if (direction == EnumSet.Direction.right || direction == EnumSet.Direction.left) {            //鬼從左或右過來
                            if (Math.random() * 2 > 1) {                    //1/2換邊
                                direction = EnumSet.Direction.down;
                            }
                        }


                        break;
                    case 2:                    //下有牆壁
                        if (direction == EnumSet.Direction.down) {                //鬼從上下來
                            if (Math.random() * 2 > 1) {
                                direction = EnumSet.Direction.left;
                            } else {
                                direction = EnumSet.Direction.right;
                            }
                        } else if (direction == EnumSet.Direction.right || direction == EnumSet.Direction.left) {            //鬼從左或右過來
                            if (Math.random() * 2 > 1) {
                                direction = EnumSet.Direction.up;
                            }
                        }
                        break;
                    case 4:                    //左有牆壁
                        if (direction == EnumSet.Direction.left) {                //鬼從右過來
                            if (Math.random() * 2 > 1) {        //鬼在目標上面
                                direction = EnumSet.Direction.down;
                            } else {
                                direction = EnumSet.Direction.up;
                            }
                        } else if (direction == EnumSet.Direction.up || direction == EnumSet.Direction.down) {            //鬼從上或下過來
                            if (Math.random() * 2 > 1) {
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
                            if (Math.random() * 2 > 1) {        //鬼在目標上面
                                direction = EnumSet.Direction.down;
                            } else {
                                direction = EnumSet.Direction.up;
                            }
                        } else if (direction == EnumSet.Direction.up || direction == EnumSet.Direction.down) {            //鬼從上或下過來
                            if (Math.random() * 2 > 1) {
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
                        do {
                            nextOrangeDirection = EnumSet.Direction.values()[random.nextInt(EnumSet.Direction.values().length)];
                        } while (nextOrangeDirection == direction);
                        direction = nextOrangeDirection;
                        break;
                }
            }

        } else if (mode == EnumSet.Mode.evolved) {
            if (((y - offsetY) % blockSize) == 0 && ((x - offsetX) % blockSize) == 0) {        //確保鬼會走完一個格子才轉身
                if ((int) (Math.random() * 2) == 0) {                //讓鬼的移動較好看
                    if (gridX - pacman.gridX > 0) {
                        direction = EnumSet.Direction.left;
                    } else {
                        direction = EnumSet.Direction.right;
                    }
                } else {
                    if (gridY - pacman.gridY > 0) {
                        direction = EnumSet.Direction.up;
                    } else {
                        direction = EnumSet.Direction.down;
                    }
                }

            }
        } else if (mode == EnumSet.Mode.frightened) {
            if (((y - offsetY) % blockSize) == 0 && ((x - offsetX) % blockSize) == 0) {
                direction = frightened(gridX, gridY, direction,pacman);
            }

        } else if (mode == EnumSet.Mode.goOut) {
            direction = EnumSet.Direction.up;
            if (((y - offsetY) % blockSize) == 0 && ((x - offsetX) % blockSize) == 0) {
                if (gridY == 7) {
                    mode = EnumSet.Mode.normal;
                    direction = EnumSet.Direction.right;
                }
            }

        }
        return direction;
    }


    @Override
    public void Move(Pacman pacman) {
        direction = GetDirection(pacman);
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
        x = offsetX + blockSize * 12;
        y = offsetY + blockSize * 10;//橘鬼的位置
        mode = EnumSet.Mode.goOut;        //橘鬼模式
        isEvolved = false;
    }


}
