package cn.kisun.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 界面窗口
 *
 * @Author: KISUN
 * @CreateTime: 2020-11-10-11-04
 * @Descirption: 界面窗口
 */
public class TankFrame extends Frame {
    /**
     * 游戏界面的长和宽
     */
    static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;
    static final int MY_TANK_X = 200;
    static final int MY_TANK_Y = 800;
    private Random random = new Random();
    /**
     * 主坦克
     * */
    Tank myTank = new Tank(MY_TANK_X,MY_TANK_Y, Dir.UP, Group.GOOD, 10,5, this);
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Tank> tanks = new ArrayList<Tank>();
    List<Explode> explodes = new ArrayList<>();
    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war V1.0");
        setVisible(true);
        this.addKeyListener(new MyKeyListener());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    Image offScreenImage = null;
    /**
     * paint()方法之前执行
     * @param g
     */
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }
    /**
     * 界面绘制
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        g.drawString("敌人的数量：" + tanks.size(), 10, 80);
        g.drawString("explodes：" + explodes.size(), 10, 100);
        g.drawString("主坦克血量：" + myTank.getBlood(), 10, 120);
        g.setColor(c);

        myTank.paint(g);

        for (int i = 0; i <  tanks.size(); i++) {
            tanks.get(i).paintBad(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
            new Thread(() -> new Audio("audio/explode.wav").play()).start();
        }
        for(int i=0; i<bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
            bullets.get(i).collideWith(myTank);
        }
    }
    /**
     * 按键监听
     */
    class MyKeyListener extends KeyAdapter {
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        /**
         *  设置方向
         */
        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
            } else {
                myTank.setMoving(true);
                if (bL) {
                    myTank.setDir(Dir.LEFT);
                }
                if (bU) {
                    myTank.setDir(Dir.UP);
                }
                if (bR) {
                    myTank.setDir(Dir.RIGHT);
                }
                if (bD) {
                    myTank.setDir(Dir.DOWN);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                case KeyEvent.VK_I:
                    initBadTank();
                    break;
                case KeyEvent.VK_A:
                    initAll();
                    break;
                case KeyEvent.VK_B:
                    addBlood();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }
    }

    public void initAll() {
        initBadTank();
        initMyTank();
    }

    public void initMyTank() {
        System.out.println("主坦克复位");
        myTank.setX(MY_TANK_X);
        myTank.setY(MY_TANK_Y);
        myTank.setDir(Dir.UP);
        myTank.setBlood(10);
    }

    /**
     * 加血
     */
    public void addBlood() {
        myTank.setBlood(myTank.getBlood() + 10);
    }

    public void initBadTank() {
        System.out.println("敌方坦克复位");
        tanks.clear();
        for (int i = 0; i < 8; i++) {
            tanks.add(new Tank((1+2*i)*Tank.WIDTH,200 + random.nextInt(50) - random.nextInt(50),Dir.DOWN,Group.BAD,Boolean.TRUE,this));
        }
    }

}
