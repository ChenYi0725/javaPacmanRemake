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
    static JLabel countdownLabel = new JLabel("");    //�إ߼���(pacman�˼�)
    static JLabel pacmanLabel = new JLabel(new ImageIcon("img/left.png"));    //�إ߼���(pacman)
    static JLabel scoreBarLabel = new JLabel("Score:");    //�إ߼���(����)
    static JLabel highestScoreBarLabel = new JLabel("Highest Score: 0");    //�إ߼���(�̰���)
    static JLabel lastScoreBarLabel = new JLabel("Last Score: 0");    //�إ߼���(�W���o��)

    static JLabel startLabel = new JLabel("Press Space to Start");    //�إ߼���(�}�l���ܤ�r)
    //    static JPanel pan=new JPanel();
    static long startTime, currentTime;

//    static ImageIcon upIcon = new ImageIcon("img/pacman_up.png");            //����������|�U���Ϥ�
//    static ImageIcon downIcon = new ImageIcon("img/pacman_down.png");
//    static ImageIcon leftIcon = new ImageIcon("img/pacman_left.png");
//    static ImageIcon rightIcon = new ImageIcon("img/pacman_right.png");
    static ImageIcon livesIcon = new ImageIcon("img/pacman_right.png");
    static ImageIcon cherry = new ImageIcon("img/cherry.png");


    static int randomX, randomY;
    //===============
    static int frameReserveX = 40, frameReserveY = 100;                        //��ثO�d��m
    final static int offsetX = 13, offsetY = 72;            //���ⰾ����m
    private static final int quantityOfArenaSide = 20;        //���a���䪺����ƶq
    final static int blockSize = 30;        //����j�p			//��18
    static int pacmanSpeed = 10;            //pacman�t��
    static int ghostSpeed = 6;        //�����t��

// ================


    //-----------------�H�U���ݭn���m���ܼ�	

    static int clearedLevel = 0;
    static int score = 0;                //����
    static int lives = 3;                //lives
    //static int heat=0;
    static int highestScore = 0;
    static int lastScore = 0;    //�إ߼���(�W���o��)
    static boolean isGameStart = false;
    static boolean isPacmanDead = false; //���a�O�_���`
    static boolean isPelletEaten = false;    //�O�_�Y��j�O�Y
//    static boolean isUp = false;        //�{�b���U���ӫ���
//    static boolean isDown = false;
//    static boolean isLeft = false;
//    static boolean isRight = false;

    static int pelletLeft = 6;        //�ѤU�h�֤j�O�Y
    static int dotLeft = 160;        //�ѤU�h���I�I(�]�t�j�O�Y)
    static boolean isTrollRemain = true;


    static int pacmanX = offsetX + blockSize * 10, pacmanY = offsetY + blockSize * 12;                //pacman��m
    static int pacmanGridX = 10, pacmanGridY = 12;                //pacman��m(��l)
    static EnumSet.Direction pacmanDirection = EnumSet.Direction.left;        //pacman���諸��V �W1 �U2 ��3 �k4


    static int[] MapData = {                    //���d
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 37, 19, 19, 17, 19, 19, 19, 25, 0, 0, 21, 19, 19, 19, 17, 19, 19, 41, 0,
            0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0,
            0, 20, 19, 19, 18, 19, 19, 19, 24, 0, 0, 20, 19, 19, 19, 18, 19, 19, 24, 0,
            0, 28, 0, 0, 0, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 0, 0, 0, 28, 0,
            0, 20, 19, 19, 25, 0, 5, 3, 2, 3, 3, 2, 3, 9, 0, 21, 19, 19, 24, 0,
            0, 28, 0, 0, 28, 0, 12, 0, 0, 0, 0, 0, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 0, 28, 0, 12, 0, 5, 1, 1, 9, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 20, 19, 19, 16, 19, 8, 0, 6, 2, 2, 10, 0, 4, 19, 16, 19, 19, 24, 0,            //0 ��ê�� 1�W��� 2�U��� 4����� 8�k��� 16���I 32�j�O�Y 64�������Ӧ����I(����Q�r���f�X�{��ê��)
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
    static int[] initialMapData = {                    //���d
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 37, 19, 19, 17, 19, 19, 19, 25, 0, 0, 21, 19, 19, 19, 17, 19, 19, 41, 0,
            0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 28, 0, 0, 28, 0,
            0, 20, 19, 19, 18, 19, 19, 19, 24, 0, 0, 20, 19, 19, 19, 18, 19, 19, 24, 0,
            0, 28, 0, 0, 0, 0, 0, 0, 28, 0, 0, 28, 0, 0, 0, 0, 0, 0, 28, 0,
            0, 20, 19, 19, 25, 0, 5, 3, 2, 3, 3, 2, 3, 9, 0, 21, 19, 19, 24, 0,
            0, 28, 0, 0, 28, 0, 12, 0, 0, 0, 0, 0, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 28, 0, 0, 28, 0, 12, 0, 5, 1, 1, 9, 0, 12, 0, 28, 0, 0, 28, 0,
            0, 20, 19, 19, 16, 19, 8, 0, 6, 2, 2, 10, 0, 4, 19, 16, 19, 19, 24, 0,            //0 ��ê�� 1�W��� 2�U��� 4����� 8�k��� 16���I 32�j�O�Y 64�������Ӧ����I(����Q�r���f�X�{��ê��)
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
    };                //���d����l���

    public PacmanGame() {                    //�غc��
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
        //addKeyListener(new Pacman.keyboardAdapter());
        setStartLabel();
        this.add(startLabel);
        this.add(countdownLabel);
        drawScoreBar();

    }

    //---------------------------��
    public static void hit(int ghostX, int ghostY) {    //�Q���I��
        if (Math.abs(ghostX - pacmanX) < (blockSize / 2) && Math.abs(ghostY - pacmanY) < (blockSize / 2)) {
            checkIsDead();
        }
    }

    public void setCountdownLabel() {
        Font countdownFont = new Font("Arial", Font.BOLD, 15);
        countdownLabel.setForeground(Color.red);
        countdownLabel.setFont(countdownFont);
    }

    public void setStartLabel() {        //�]�w�}�l���ܤ�r
        Font startNotificationFont = new Font("Arial", Font.BOLD, 40);
        startLabel.setFont(startNotificationFont);
        startLabel.setForeground(Color.yellow);
        startLabel.setLocation(150, 420);

    }


    public void dotClear() {        //�ˬd���d�O�_����
        if (dotLeft == 0) {    //�p�G���d����
            resetAllLevel();
            clearedLevel++;
        }
    }

    public void drawCherry(Graphics2D g) {        //�e�X���
        int cx = blockSize + 5, cy = 720;
        for (int i = clearedLevel; i > 0; i--) {
            g.drawImage(cherry.getImage(), 30 + cx * (i - 1), cy, this);
        }
    }


    public void drawScoreBar() {            //�e������
        scoreBarLabel.setText("Score:" + score);
        scoreBarLabel.setLocation(0, 0);
        scoreBarLabel.setForeground(Color.white);
        scoreBarLabel.setFont(layoutFont);
        this.add(scoreBarLabel);
    }


    public void drawHighestScoreBar() {            //�e�̰�������
        highestScoreBarLabel.setText("Highest Score:" + highestScore);
        highestScoreBarLabel.setLocation(450, 0);
        highestScoreBarLabel.setForeground(Color.white);
        highestScoreBarLabel.setFont(layoutFont);
        this.add(highestScoreBarLabel);
    }

    public void drawLastScoreBar() {            //�e�W��������
        lastScoreBarLabel.setText("Last Score:" + lastScore);
        lastScoreBarLabel.setLocation(480, 25);
        lastScoreBarLabel.setForeground(Color.white);
        lastScoreBarLabel.setFont(layoutFont);
        this.add(lastScoreBarLabel);
    }

    public void drawMap(Graphics2D graphics2D, int[] map) {            //�e�X�a��
        int x, y;
        for (int i = 0; i < 400; i++) {
            x = i / 20;        //�]�wxy
            y = i % 20;
            if (map[y * 20 + x] == 0) {
                graphics2D.setColor(Color.blue);                //�e���
                if (y == 7 && x > 7 && x < 12) {
                    graphics2D.drawRect(frameReserveX + x * blockSize, frameReserveY + y * blockSize, blockSize, blockSize);
                } else {
                    graphics2D.fillRect(frameReserveX + x * blockSize, frameReserveY + y * blockSize, blockSize, blockSize);
                }

            }
            if ((map[y * 20 + x] & 16) != 0) {                //��l�]�w�����I
                graphics2D.setColor(Color.white);
                graphics2D.fillOval(frameReserveX + 10 + x * blockSize, frameReserveY + 10 + y * blockSize, 15, 15);            //�첾10->�ܤ�
            } else if ((map[y * 20 + x] & 32) != 0) {                //��l�]�w���j�O�Y
                graphics2D.setColor(Color.orange);
                graphics2D.fillOval(frameReserveX + 4 + x * blockSize, frameReserveY + 4 + y * blockSize, 23, 23);            //�첾10->�ܤ�
            }

        }

    }




    public void drawLivesIcon(Graphics2D g) {            //�e�X�ͩR��
        int hx = blockSize + 15, hy = 30;
        for (int i = lives; i > 0; i--) {
            g.drawImage(livesIcon.getImage(), hx * (i - 1), hy, this);
        }
    }
//    public void setPacmanIcon() {            //�]�wpacman���Ϯ�
//        if (isUp && !isDown && !isLeft && !isRight) {
//            pacmanLabel.setIcon(upIcon);
//        } else if (!isUp && isDown && !isLeft && !isRight) {
//            pacmanLabel.setIcon(downIcon);
//        } else if (!isUp && !isDown && isLeft && !isRight) {
//            pacmanLabel.setIcon(leftIcon);
//        } else if (!isUp && !isDown && !isLeft && isRight) {
//            pacmanLabel.setIcon(rightIcon);
//        }
//    }

    public void createPacman() {
        pacmanLabel.setIcon(pacman.upIcon);        //�إ�pacman
        pacmanLabel.setLocation(pacmanX, pacmanY);
        pacmanLabel.setSize(blockSize, blockSize);
        this.add(pacmanLabel);
    }

    public void gameStart() {        //�C���}�l
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

    public static void resetAllLevel() {                        //�����d�������m (�Y��or���z)
        countdownLabel.setText("");
        startLabel.setText("Press Space to Start");
        pacmanLabel.setIcon(null);
        if (clearedLevel > 20) {
            ghostSpeed = 10;
        } else {
            ghostSpeed = 6;
        }

        lives = 3;                //lives
        isGameStart = false;
        isPacmanDead = false; //���a�O�_���`
        isUp = false;        //�{�b���U���ӫ���
        isDown = false;
        isLeft = false;
        isRight = false;
        pelletLeft = 6;        //�ѤU�h�֤j�O�Y
        dotLeft = 160;        //�ѤU�h���I�I
        isTrollRemain = true;
        pacmanX = offsetX + blockSize * 10;
        pacmanY = offsetY + blockSize * 12;                //pacman��m
        pacmanGridX = 10;
        pacmanGridY = 12;                //pacman��m(��l)
        pacmanDirection = EnumSet.Direction.left;        //pacman���諸��V �W1 �U2 ��3 �k4

        redGhost.reset();
        pinkGhost.reset();
        blueGhost.reset();
        orangeGhost.reset();
        System.arraycopy(initialMapData, 0, MapData, 0, 400);


    }

    public static void loseALife() {//�u���]��m
        countdownLabel.setText("");
        startLabel.setText("Press Space to Start");
        pacmanLabel.setIcon(null);
        pacmanX = offsetX + blockSize * 10;
        pacmanY = offsetY + blockSize * 12;                //pacman��m
        pacmanGridX = 10;
        pacmanGridY = 12;                //pacman��m(��l)
        isUp = false;        //�{�b���U���ӫ���
        isDown = false;
        isLeft = false;
        isRight = false;


        redGhost.reset();
        pinkGhost.reset();
        blueGhost.reset();
        orangeGhost.reset();

        isGameStart = false;
    }


    public static void checkIsDead() {        //�ˬd�O�_���`
        lives = lives - 1;
        if (lives == 0) {
            loseAllLife();
        } else {
            loseALife();
        }

    }

    public void ghostRecover() {                //����_
        countdownLabel.setText("");
        isPelletEaten = false;
        pinkGhost.mode = EnumSet.Mode.normal;        //���Ҧ�
        redGhost.mode = EnumSet.Mode.normal;        //���Ҧ�
        blueGhost.mode = EnumSet.Mode.normal;        //���Ҧ�
        if (orangeGhost.mode == EnumSet.Mode.frightened) {
            orangeGhost.mode = EnumSet.Mode.normal;
        }

    }

    public void redGhostActive(Graphics graphics) {
        redGhost.Hit();
        redGhost.Move();
        graphics.drawImage(redGhost.GetIcon(), redGhost.x, redGhost.y, this);

    }

    public void pinkGhostActive(Graphics graphics) {
        pinkGhost.Hit();
        pinkGhost.Move();
        graphics.drawImage(pinkGhost.GetIcon(), pinkGhost.x, pinkGhost.y, this);
    }

    public void blueGhostActive(Graphics graphics) {
        blueGhost.Hit();
        blueGhost.Move();
        graphics.drawImage(blueGhost.GetIcon(), blueGhost.x, blueGhost.y, this);

    }

    public void orangeGhostActive(Graphics graphics) {
        orangeGhost.Hit();
        orangeGhost.Move();
        graphics.drawImage(orangeGhost.GetIcon(), orangeGhost.x, orangeGhost.y, this);

    }

    public void paintComponent(Graphics graphics) {            //���gpaint��k�|�ɭP�Ӯe��������L�ե�L�kø�s
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        pacmanMove();

        drawMap(graphics2D, MapData);        //�e�X�a��

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
            countdownLabel.setLocation(pacmanX + 4, pacmanY + 3);
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
        repaint();                        //���ƦA�e�@��	-->�|�Ϧ���Ƥ@������

    }


    //--------------�Y�쨧�l
    public static void eatDot() {
        //Graphics g=getGraphics();
        if ((MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 16) != 0) {            //�p�G�Y����I
            MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] = MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] - 16 + 64;    //�M�����I�A�ì������B�쥻�����I
            dotLeft--;
            score = score + 1;
        } else if ((MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 32) != 0) {        //�p�G�Y����I
            MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] = MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] - 32;    //�M���j�O�Y
            dotLeft--;
            score = score + 5;
            if (isTrollRemain) {
                if (((int) (Math.random() * pelletLeft)) == 0) {        //�Y�찲�j�O�Y
                    switch ((int) (Math.random() * 3) + 1) {
                        case 1 -> {            //�ǰe���H����m
                            randomX = ((int) (Math.random() * (quantityOfArenaSide - 2)) + 2);    //�إ��H����m(��l)
                            randomY = ((int) (Math.random() * (quantityOfArenaSide - 2)) + 2);
                            while (MapData[randomX + 20 * (randomY - 1) - 1] == 0 ||                                                    //�p�G�ǰe�����
                                    (Math.abs(randomX - redGhost.gridX) <= 1 && Math.abs(randomY - redGhost.gridY) <= 1) ||    //�p�G�ǰe�찭����
                                    (Math.abs(randomX - blueGhost.gridX) <= 1 && Math.abs(randomY - blueGhost.gridY) <= 1) ||
                                    (Math.abs(randomX - pinkGhost.gridX) <= 1 && Math.abs(randomY - pinkGhost.gridY) <= 1) ||
                                    (Math.abs(randomX - orangeGhost.gridX) <= 1 && Math.abs(randomY - orangeGhost.gridY) <= 1) ||
                                    (Math.abs(randomX - pacmanGridX) <= 1 && Math.abs(randomY - pacmanGridY) <= 1) ||                //�p�G�ǰe��ۤv����
                                    ((MapData[randomX + 20 * (randomY - 1) - 1] & 32) != 0) ||                                //�p�G�ǰe��m���j�O�Y
                                    ((randomX > 8 && randomX < 13) && ((randomY > 8 && randomY < 11)))                            //�p�G�ǰe�찭�������I
                            ) {
                                randomX = ((int) (Math.random() * (quantityOfArenaSide - 2)) + 2);    //�إ��H����m
                                randomY = ((int) (Math.random() * (quantityOfArenaSide - 2)) + 2);
                            }
                            pacmanX = offsetX + blockSize * randomX;    //�⪱�a�ǰe���H����m
                            pacmanY = offsetY + blockSize * randomY;
                            pacmanGridX = randomX;
                            pacmanGridY = randomY;
                        }
                        case 2 -> {        //�����[�t
                            redGhost.x = redGhost.gridX * blockSize + offsetX;
                            redGhost.y = redGhost.gridY * blockSize + offsetY;        //���Ⱝ�]�w�b��l��
                            blueGhost.x = blueGhost.gridX * blockSize + offsetX;
                            blueGhost.y = blueGhost.gridY * blockSize + offsetY;
                            pinkGhost.x = pinkGhost.gridX * blockSize + offsetX;
                            pinkGhost.y = pinkGhost.gridY * blockSize + offsetY;
                            orangeGhost.x = orangeGhost.gridX * blockSize + offsetX;
                            orangeGhost.y = orangeGhost.gridY * blockSize + offsetY;
                            ghostSpeed = 10;
                        }
                        case 3 -> {        //��ﰭ��mode3
                            orangeGhost.mode = EnumSet.Mode.evolved;
                            orangeGhost.isEvolved = true;
                        }
                    }
                    isTrollRemain = false;
                } else {                    //�Y��u���j�O�Y
                    if (clearedLevel <= 10) {
                        startTime = System.currentTimeMillis();    //�����ɶ�
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
                startTime = System.currentTimeMillis();    //�����ɶ�
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
//--------------�Y�쨧�l		


    public void pacmanMove() {                //����

        if (isUp && !isDown && !isLeft && !isRight) {                //�W
            pacmanDirection = EnumSet.Direction.up;
            if (!((MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 1) > 0) || (pacmanY - offsetY) % blockSize > 0) {

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
            if (!((MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 2) > 0) || (pacmanY - offsetY) % blockSize > 0) {        //�p�G��e��l�k��i�q��||�٨S���쩳
                if ((pacmanX - offsetX) % blockSize != 0) {                                //�p�G���⭱�e��������P�w��m���e�S�����->�B���쥿�T��m
                    pacmanX = (pacmanGridX) * blockSize + offsetX;
                }

                pacmanY = pacmanY + pacmanSpeed;
                if (((pacmanY - offsetY) % blockSize) == (2 * blockSize / 3)) {            //�P�_�{�b��l
                    pacmanGridY = pacmanGridY + 1;
                }

            }
        } else if (!isUp && !isDown && isLeft && !isRight) {
            pacmanDirection = EnumSet.Direction.left;
            if (!((MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 4) > 0) || (pacmanX - offsetX) % blockSize > 0) {
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
            if (!((MapData[pacmanGridX + 20 * (pacmanGridY - 1) - 1] & 8) > 0) || (pacmanX - offsetX) % blockSize > 0) {
                if ((pacmanY - offsetY) % blockSize != 0) {
                    pacmanY = (pacmanGridY) * blockSize + offsetY;
                }

                pacmanX = pacmanX + pacmanSpeed;
                if (((pacmanX - offsetX) % blockSize) == (2 * blockSize / 3)) {
                    pacmanGridX = pacmanGridX + 1;
                }
            }
        }

        eatDot();
        pacmanLabel.setLocation(pacmanX, pacmanY);
    }


//----------------��L��ť

    class keyboardAdapter extends KeyAdapter {        //�������O�B�A�t��
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameStart();
                pacmanLabel.setIcon(pacman.upIcon);
            }
//            if (isGameStart) {
//                if (e.getKeyCode() == KeyEvent.VK_DOWN) {            //���U�U��V���
//                    isDown = true;
//                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
//                    isUp = true;
//                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//                    isRight = true;
//                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//                    isLeft = true;
//                }
//            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
//            if (e.getKeyCode() == KeyEvent.VK_DOWN) {            //���U�U��V���
//                isDown = false;
//
//            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
//                isUp = false;
//
//            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//                isRight = false;
//
//            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//                isLeft = false;
//            }

        }


    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }


}