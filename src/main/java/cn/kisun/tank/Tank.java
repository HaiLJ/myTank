package cn.kisun.tank;

import java.awt.*;
import java.util.Random;

/**
 * 坦克定义
 *
 * @Author: KISUN
 * @CreateTime: 2020-11-10-14-21
 * @Descirption: 坦克
 */
public class Tank {
    /**
     * 位置
     */
    private int x, y;
    /**
     * 方向
     */
    private Dir dir = Dir.DOWN;

    /**
     * 速度
     */
    private int speed = 1;

    /**
     * 血量
     */
    private int blood = 1;

    /**
     * 是否移动
     */
    private boolean moving = false;

    /**
     * 是否活着
     */
    private boolean living = true;

    /**
     * 所属分组
     */
    private Group group = Group.BAD;

    private Random random = new Random();

    /**
     * 界面引用
     */
    private TankFrame tf;

    public static int WIDTH = ResourceMgr.goodTankD.getWidth(), HEIGHT = ResourceMgr.goodTankD.getHeight();

    public Tank(int x, int y, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.group = group;
        this.tf = tf;
    }

    public Tank(int x, int y, Dir dir, Group group, Boolean moving, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.moving = moving;
        this.tf = tf;
    }

    public Tank(int x, int y, Dir dir, Group group, int speed, int blood, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.speed = speed;
        this.blood = blood;
        this.tf = tf;
    }

    public void setBlood(int blood) {
        this.blood = blood;
        if (this.blood > 0) {
            this.living = true;
        }
    }

    public int getBlood() {
        return blood;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void paint(Graphics g) {
        if (!living) {
            return;
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.goodTankL,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.goodTankU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.goodTankR,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.goodTankD,x,y,null);
                break;
            default:
                break;
        }
        move();
    }

    public void paintBad(Graphics g) {
        if (!living) {
            tf.tanks.remove(this);
            return;
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.badTankL,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.badTankU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.badTankR,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.badTankD,x,y,null);
                break;
            default:
                break;
        }
        move();
    }
    /**
     * 移动
     */
    private void move() {
        if (!living) {
            return ;
        }
        if (!moving) {
            return;
        }
        switch (dir) {
            case LEFT:
                x -= speed;
                break;
            case UP:
                y -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case DOWN:
                y += speed;
                break;
            default:
                break;
        }
        if (this.group.equals(Group.BAD)) {
            if (random.nextInt(100) > 97 ) {
                this.fire();
            }
            if (random.nextInt(100) > 97) {
                randomDir();
            }
        }

        if (this.group.equals(Group.GOOD)) {
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
        }
        boundsCheck();
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    private void boundsCheck() {
        if (this.x < 2) {
            x = 2;
            this.dir = Dir.RIGHT;
        }
        if (this.y < 60) {
            y = 60;
            this.dir = Dir.DOWN;
        }
        if (this.x > TankFrame.GAME_WIDTH- Tank.WIDTH -2) {
            x = TankFrame.GAME_WIDTH - Tank.WIDTH -2;
            this.dir = Dir.LEFT;
        }
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT -2 ) {
            y = TankFrame.GAME_HEIGHT -Tank.HEIGHT -2;
            this.dir = Dir.UP;
        }
    }

    /**
     * 开火
     */
    public void fire() {
        int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        tf.bullets.add(new Bullet(bX, bY, this.dir,this.group, this.tf));
        if (this.group == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }

    public void die() {
        this.living = false;
        int eX = this.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
        int eY = this.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
        this.tf.explodes.add(new Explode(eX, eY, tf));
    }
}
