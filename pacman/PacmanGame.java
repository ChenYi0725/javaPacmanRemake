package pacman;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;


public class PacmanGame extends JPanel implements ActionListener {
    public static RedGhost redGhost;
    public static BlueGhost blueGhost;
    public static PinkGhost pinkGhost;
    public static OrangeGhost orangeGhost;

    private static Pacman pacman;

    private final Font layoutFont = new Font("Arial", Font.BOLD, 20);
    static JLabel countdownLabel = new JLabel("");    //建立標籤(pacman倒數)
    static JLabel pacmanLabel = new JLabel(new ImageIcon("img/left.png"));    //建立標籤(pacman)
    static JLabel scoreBarLabel = new JLabel("Score:");    //建立標籤(分數)
    static JLabel highestScoreBarLabel = new JLabel("Highest Score: 0");    //建立標籤(最高分)
    static JLabel lastScoreBarLabel = new JLabel("Last Score: 0");    //建立標籤(上次得分)

    static JLabel startLabel = new JLabel("Press Space to Start");    //建立標籤(開始提示文字)
    static long startTime, currentTime;

    static ImageIcon livesIcon = new ImageIcon("img/pacman_right.png");
    static ImageIcon cherry = new ImageIcon("img/cherry.png");

    static int randomX, randomY;
    //===============
    static int frameReserveX = 40, frameReserveY = 100;                        //邊框保留位置
    final static int offsetX = 13, offsetY = 72;            //角色偏移位置
    private static final int quantityOfArenaSide = 20;        //場地的邊的方塊數量
    final static int blockSize = 30;        //方塊大小			//原18
    static int pacmanSpeed = 10;            //pacman速度
    static int ghostSpeed = 6;        //鬼的速度

// ================
    //-----------------以下為需要重置的變數
    static int clearedLevel = 0;
    static int score = 0;                //分數
    static int lives = 3;                //lives
    static int highestScore = 0;
    static int lastScore = 0;    //建立標籤(上次得分)
    static boolean isGameStart = false;
    static boolean isPacmanDead = false; //���a�O�_���`
    static boolean isPelletEaten = false;    //�O�_�Y��j�O�Y

    static int pelletLeft = 6;        //�ѤU�h�֤j�O�Y
    static int dotLeft = 160;        //�ѤU�h���I�I(�]�t�j�O�Y)
    static boolean isTrollRemain = true;

    static int[] MapData = {                    //關卡//0 障礙物 1上邊界 2下邊界 4左邊界 8右邊界 16白點 32大力丸 64紀錄本來有白點(防止十字路口出現障礙物)
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 37, 19, 19, 17, 19, 19, 19, 25, 0, 0, 21, 19, 19, 19, 17, 19, 19, 41, 0,
            0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0,
            0, 20, 19, 19, 18, 19, 19, 19, 24, 0, 0, 20, 19, 19, 19, 18, 19, 19, 24, 0,
            0, 28, 0, 0, 0, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 0, 0, 0, 28, 0,
            0, 20, 19, 19, 25, 0, 5, 3, 2, 3, 3, 2, 3, 9, 0, 21, 19, 19, 24, 0,
            0, 28, 0, 0, 28, 0, 12, 0, 0, 0, 0, 0, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 0, 28, 0, 12, 0, 5, 1, 1, 9, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 20, 19, 19, 16, 19, 8, 0, 6, 2, 2, 10, 0, 4, 19, 16, 19, 19, 24, 0,            
            0, 28, 0, 0, 28, 0, 12, 0, 0, 0, 0, 0, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 37, 26, 0, 4, 3, 3, 3, 3, 3, 3, 8, 0, 22, 41, 0, 28, 0,
            0, 28, 0, 28, 0, 0, 28, 0, 0, 0, 0, 0, 0, 28, 0, 0, 28, 0, 28, 0,
            0, 28, 0, 28, 0, 21, 18, 17, 19, 19, 19, 19, 17, 18, 25, 0, 28, 0, 28, 0,
            0, 20, 19, 18, 19, 24, 0, 28, 0, 0, 0, 0, 28, 0, 20, 19, 18, 19, 24, 0,
            0, 28, 0, 0, 0, 28, 0, 28, 0, 0, 0, 0, 28, 0, 28, 0, 0, 0, 28, 0,
            0, 20, 19, 19, 19, 26, 0, 20, 19, 19, 19, 19, 24, 0, 22, 19, 19, 19, 24, 0,
            0, 28, 0, 0, 0, 0, 0, 28, 0, 0, 0, 0, 28, 0, 0, 0, 0, 0, 28, 0,
            0, 38, 19, 19, 19, 19, 19, 18, 19, 19, 19, 19, 18, 19, 19, 19, 19, 19, 42, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    static int[] initialMapData = {                    
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 37, 19, 19, 17, 19, 19, 19, 25, 0, 0, 21, 19, 19, 19, 17, 19, 19, 41, 0,
            0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0,
            0, 20, 19, 19, 18, 19, 19, 19, 24, 0, 0, 20, 19, 19, 19, 18, 19, 19, 24, 0,
            0, 28, 0, 0, 0, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 0, 0, 0, 28, 0,
            0, 20, 19, 19, 25, 0, 5, 3, 2, 3, 3, 2, 3, 9, 0, 21, 19, 19, 24, 0,
            0, 28, 0, 0, 28, 0, 12, 0, 0, 0, 0, 0, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 0, 28, 0, 12, 0, 5, 1, 1, 9, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 20, 19, 19, 16, 19, 8, 0, 6, 2, 2, 10, 0, 4, 19, 16, 19, 19, 24, 0,            
            0, 28, 0, 0, 28, 0, 12, 0, 0, 0, 0, 0, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 37, 26, 0, 4, 3, 3, 3, 3, 3, 3, 8, 0, 22, 41, 0, 28, 0,
            0, 28, 0, 28, 0, 0, 28, 0, 0, 0, 0, 0, 0, 28, 0, 0, 28, 0, 28, 0,
            0, 28, 0, 28, 0, 21, 18, 17, 19, 19, 19, 19, 17, 18, 25, 0, 28, 0, 28, 0,
            0, 20, 19, 18, 19, 24, 0, 28, 0, 0, 0, 0, 28, 0, 20, 19, 18, 19, 24, 0,
            0, 28, 0, 0, 0, 28, 0, 28, 0, 0, 0, 0, 28, 0, 28, 0, 0, 0, 28, 0,
            0, 20, 19, 19, 19, 26, 0, 20, 19, 19, 19, 19, 24, 0, 22, 19, 19, 19, 24, 0,
            0, 28, 0, 0, 0, 0, 0, 28, 0, 0, 0, 0, 28, 0, 0, 0, 0, 0, 28, 0,
            0, 38, 19, 19, 19, 19, 19, 18, 19, 19, 19, 19, 18, 19, 19, 19, 19, 19, 42, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };                

    public PacmanGame() {                    
        blueGhost = new BlueGhost();
        redGhost = new RedGhost();
        pinkGhost = new PinkGhost();
        orangeGhost = new OrangeGhost();
        pacman = new Pacman();

        setCountdownLabel();

        drawHighestScoreBar();
        drawLastScoreBar();
        setBackground(Color.black);
        addKeyListener(new keyboardAdapter());
        addKeyListener(new Pacman.keyboardAdapter());
        setStartLabel();
        this.add(startLabel);
        this.add(countdownLabel);
        drawScoreBar();

    }

    //---------------------------鬼
    public static void hit(int ghostX, int ghostY) {    //被鬼碰撞
        if (Math.abs(ghostX - pacman.x) < (blockSize / 2) && Math.abs(ghostY - pacman.y) < (blockSize / 2)) {
            checkIsDead();
        }
    }

    public void setCountdownLabel() {
        Font countdownFont = new Font("Arial", Font.BOLD, 15);
        countdownLabel.setForeground(Color.red);
        countdownLabel.setFont(countdownFont);
    }

    public void setStartLabel() {        //設定開始提示文字
        Font startNotificationFont = new Font("Arial", Font.BOLD, 40);
        startLabel.setFont(startNotificationFont);
        startLabel.setForeground(Color.yellow);
        startLabel.setLocation(150, 420);

    }


    public void dotClear() {        //檢查關卡是否結束
        if (dotLeft == 0) {    //如果關卡結束
            resetAllLevel();
            clearedLevel++;
        }
    }

    public void drawCherry(Graphics2D g) {        //畫出櫻桃
        int cx = blockSize + 5, cy = 720;
        for (int i = clearedLevel; i > 0; i--) {
            g.drawImage(cherry.getImage(), 30 + cx * (i - 1), cy, this);
        }
    }


    public void drawScoreBar() {            //畫分數欄
        scoreBarLabel.setText("Score:" + score);
        scoreBarLabel.setLocation(0, 0);
        scoreBarLabel.setForeground(Color.white);
        scoreBarLabel.setFont(layoutFont);
        this.add(scoreBarLabel);
    }


    public void drawHighestScoreBar() {            //畫最高分數欄
        highestScoreBarLabel.setText("Highest Score:" + highestScore);
        highestScoreBarLabel.setLocation(450, 0);
        highestScoreBarLabel.setForeground(Color.white);
        highestScoreBarLabel.setFont(layoutFont);
        this.add(highestScoreBarLabel);
    }

    public void drawLastScoreBar() {            //畫上次分數欄
        lastScoreBarLabel.setText("Last Score:" + lastScore);
        lastScoreBarLabel.setLocation(480, 25);
        lastScoreBarLabel.setForeground(Color.white);
        lastScoreBarLabel.setFont(layoutFont);
        this.add(lastScoreBarLabel);
    }

    public void drawMap(Graphics2D graphics2D, int[] map) {            //畫出地圖
        int x, y;
        for (int i = 0; i < 400; i++) {
            x = i / 20;        //�]�wxy
            y = i % 20;
            if (map[y * 20 + x] == 0) {
                graphics2D.setColor(Color.blue);                //畫牆壁
                if (y == 7 && x > 7 && x < 12) {
                    graphics2D.drawRect(frameReserveX + x * blockSize, frameReserveY + y * blockSize, blockSize, blockSize);
                } else {
                    graphics2D.fillRect(frameReserveX + x * blockSize, frameReserveY + y * blockSize, blockSize, blockSize);
                }

            }
            if ((map[y * 20 + x] & 16) != 0) {                //初始設定有白點
                graphics2D.setColor(Color.white);
                graphics2D.fillOval(frameReserveX + 10 + x * blockSize, frameReserveY + 10 + y * blockSize, 15, 15);            //位移10->至中
            } else if ((map[y * 20 + x] & 32) != 0) {               //初始設定有大力丸
                graphics2D.setColor(Color.orange);
                graphics2D.fillOval(frameReserveX + 4 + x * blockSize, frameReserveY + 4 + y * blockSize, 23, 23);            
            }

        }

    }

    public void drawLivesIcon(Graphics2D g) {            //畫出生命值
        int hx = blockSize + 15, hy = 30;
        for (int i = lives; i > 0; i--) {
            g.drawImage(livesIcon.getImage(), hx * (i - 1), hy, this);
        }
    }


    public void createPacman() {
        pacmanLabel.setIcon(pacman.upIcon);        //建立pacman
        pacmanLabel.setLocation(pacman.x, pacman.y);
        pacmanLabel.setSize(blockSize, blockSize);
        this.add(pacmanLabel);
    }

    public void gameStart() {        //遊戲開始
        createPacman();
        isGameStart = true;
        startLabel.setText("");
        if (isPacmanDead) {
            checkIsDead();
        }
    }


    public static void loseAllLife() {
        isGameStart = false;
        if (score > highestScore) {
            highestScore = score;
        }
        lastScore = score;
        clearedLevel = 0;
        score = 0;
        resetAllLevel();
    }

    public static void resetAllLevel() {                        //把關卡全部重置 (吃完or死透)
        countdownLabel.setText("");
        startLabel.setText("Press Space to Start");

        if (clearedLevel > 20) {
            ghostSpeed = 10;
        } else {
            ghostSpeed = 6;
        }

        lives = 3;                //lives
        isGameStart = false;
        isPacmanDead = false; //玩家是否死亡
        pelletLeft = 6;        //剩下多少大力丸
        dotLeft = 160;        //剩下多少點點
        isTrollRemain = true;

       pacman.reset();

        redGhost.reset();
        pinkGhost.reset();
        blueGhost.reset();
        orangeGhost.reset();
        System.arraycopy(initialMapData, 0, MapData, 0, 400);


    }

    public static void loseALife() {            //只重設位置
        countdownLabel.setText("");
        startLabel.setText("Press Space to Start");

        pacman.reset();

        redGhost.reset();
        pinkGhost.reset();
        blueGhost.reset();
        orangeGhost.reset();

        isGameStart = false;
    }


    public static void checkIsDead() {        //檢查是否死亡
        lives = lives - 1;
        if (lives == 0) {
            loseAllLife();
        } else {
            loseALife();
        }

    }

    public void ghostRecover() {                //鬼恢復
        countdownLabel.setText("");
        isPelletEaten = false;
        pinkGhost.mode = EnumSet.Mode.normal;        //鬼模式
        redGhost.mode = EnumSet.Mode.normal;        
        blueGhost.mode = EnumSet.Mode.normal;        
        if (orangeGhost.mode == EnumSet.Mode.frightened) {
            orangeGhost.mode = EnumSet.Mode.normal;
        }

    }

    public void redGhostActive(Graphics graphics) {
        redGhost.Hit(pacman);
        redGhost.Move(pacman);
        graphics.drawImage(redGhost.GetIcon(), redGhost.x, redGhost.y, this);

    }

    public void pinkGhostActive(Graphics graphics) {
        pinkGhost.Hit(pacman);
        pinkGhost.Move(pacman);
        graphics.drawImage(pinkGhost.GetIcon(), pinkGhost.x, pinkGhost.y, this);
    }

    public void blueGhostActive(Graphics graphics) {
        blueGhost.Hit(pacman);
        blueGhost.Move(pacman);
        graphics.drawImage(blueGhost.GetIcon(), blueGhost.x, blueGhost.y, this);

    }

    public void orangeGhostActive(Graphics graphics) {
        orangeGhost.Hit(pacman);
        orangeGhost.Move(pacman);
        graphics.drawImage(orangeGhost.GetIcon(), orangeGhost.x, orangeGhost.y, this);

    }

    public void paintComponent(Graphics graphics) {            //重寫paint方法會導致該容器中的其他組件無法繪製
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        pacman.pacmanMove();

        drawMap(graphics2D, MapData);       //畫出地圖

        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isGameStart) {
            redGhostActive(graphics);
            pinkGhostActive(graphics);
            blueGhostActive(graphics);
            orangeGhostActive(graphics);
        }


        if (isPelletEaten) {
            countdownLabel.setLocation(pacman.x + 4, pacman.y + 3);
            countdownLabel.setText(String.valueOf(8 - (currentTime - startTime) / 1000));
            currentTime = System.currentTimeMillis();
            if ((currentTime - startTime) >= 8000) {
                ghostRecover();
            }

        }
        drawScoreBar();
        drawHighestScoreBar();
        drawLastScoreBar();
        setStartLabel();
        pacman.setPacmanIcon();

        drawLivesIcon(graphics2D);
        drawCherry(graphics2D);
        dotClear();
        repaint();                        //重複再畫一次	-->會使此函數一直重複

    }


    //--------------吃到豆子
    public static void eatDot() {
        if ((MapData[pacman.gridX + 20 * (pacman.gridY - 1) - 1] & 16) != 0) {             //如果吃到白點
            MapData[pacman.gridX + 20 * (pacman.gridY - 1) - 1] = MapData[pacman.gridX + 20 * (pacman.gridY - 1) - 1] - 16 + 64;    //清除白點，並紀錄此處原本有白點
            dotLeft--;
            score = score + 1;
        } else if ((MapData[pacman.gridX + 20 * (pacman.gridY - 1) - 1] & 32) != 0) {        //如果吃到橘點
            MapData[pacman.gridX + 20 * (pacman.gridY - 1) - 1] = MapData[pacman.gridX + 20 * (pacman.gridY - 1) - 1] - 32;    //清除大力丸
            dotLeft--;
            score = score + 5;
            if (isTrollRemain) {
                if (((int) (Math.random() * pelletLeft)) == 0) {        //吃到假大力丸
                    switch ((int) (Math.random() * 3) + 1) {
                        case 1 -> {            //傳送到隨機位置
                            randomX = ((int) (Math.random() * (quantityOfArenaSide - 2)) + 2);    //建立隨機位置(格子)
                            randomY = ((int) (Math.random() * (quantityOfArenaSide - 2)) + 2);
                            while (MapData[randomX + 20 * (randomY - 1) - 1] == 0 ||                                                    //如果傳送到牆壁
                                    (Math.abs(randomX - redGhost.gridX) <= 1 && Math.abs(randomY - redGhost.gridY) <= 1) ||    //如果傳送到鬼旁邊
                                    (Math.abs(randomX - blueGhost.gridX) <= 1 && Math.abs(randomY - blueGhost.gridY) <= 1) ||
                                    (Math.abs(randomX - pinkGhost.gridX) <= 1 && Math.abs(randomY - pinkGhost.gridY) <= 1) ||
                                    (Math.abs(randomX - orangeGhost.gridX) <= 1 && Math.abs(randomY - orangeGhost.gridY) <= 1) ||
                                    (Math.abs(randomX - pacman.gridX) <= 1 && Math.abs(randomY - pacman.gridY) <= 1) ||                //如果傳送到自己旁邊
                                    ((MapData[randomX + 20 * (randomY - 1) - 1] & 32) != 0) ||                                //如果傳送位置有大力丸
                                    ((randomX > 8 && randomX < 13) && ((randomY > 8 && randomY < 11)))                            //如果傳送到鬼的重生點
                            ) {
                                randomX = ((int) (Math.random() * (quantityOfArenaSide - 2)) + 2);    //建立隨機位置
                                randomY = ((int) (Math.random() * (quantityOfArenaSide - 2)) + 2);
                            }
                            pacman.x = offsetX + blockSize * randomX;    //把玩家傳送到隨機位置
                            pacman.y = offsetY + blockSize * randomY;
                            pacman.gridX = randomX;
                            pacman.gridY = randomY;
                        }
                        case 2 -> {       //幫鬼加速
                            redGhost.x = redGhost.gridX * blockSize + offsetX;
                            redGhost.y = redGhost.gridY * blockSize + offsetY;        //先把鬼設定在格子內
                            blueGhost.x = blueGhost.gridX * blockSize + offsetX;
                            blueGhost.y = blueGhost.gridY * blockSize + offsetY;
                            pinkGhost.x = pinkGhost.gridX * blockSize + offsetX;
                            pinkGhost.y = pinkGhost.gridY * blockSize + offsetY;
                            orangeGhost.x = orangeGhost.gridX * blockSize + offsetX;
                            orangeGhost.y = orangeGhost.gridY * blockSize + offsetY;
                            ghostSpeed = 10;
                        }
                        case 3 -> {         //把橘鬼變mode3
                            orangeGhost.mode = EnumSet.Mode.evolved;
                            orangeGhost.isEvolved = true;
                        }
                    }
                    isTrollRemain = false;
                } else {                    //吃到真的大力丸
                    if (clearedLevel <= 10) {
                        startTime = System.currentTimeMillis();    //紀錄時間
                        isPelletEaten = true;
                        redGhost.mode = EnumSet.Mode.frightened;
                        pinkGhost.mode = EnumSet.Mode.frightened;
                        blueGhost.mode = EnumSet.Mode.frightened;
                        if (!orangeGhost.isEvolved) {
                            orangeGhost.mode = EnumSet.Mode.frightened;
                        }
                    }
                }

                pelletLeft--;

            } else {
                startTime = System.currentTimeMillis();    //紀錄時間
                isPelletEaten = true;
                redGhost.mode = EnumSet.Mode.frightened;
                pinkGhost.mode = EnumSet.Mode.frightened;
                blueGhost.mode = EnumSet.Mode.frightened;
                if (!orangeGhost.isEvolved) {
                    orangeGhost.mode = EnumSet.Mode.frightened;
                }
            }

        }

    }

//----------------鍵盤監聽

    class keyboardAdapter extends KeyAdapter {        //內部類別、適配器
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameStart();
                pacmanLabel.setIcon(pacman.upIcon);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}