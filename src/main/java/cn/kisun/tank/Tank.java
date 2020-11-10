package cn.kisun.tank;

import java.awt.*;

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
    private int speed = 5;

    /**
     * 是否移动
     */
    private boolean moving = false;

    /**
     * 界面引用
     */
    private TankFrame tf;

    public static int WIDTH = ResourceMgr.goodTankD.getWidth(), HEIGHT = ResourceMgr.goodTankD.getHeight();

    public Tank(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public Tank(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public Tank(int x, int y, Dir dir, int speed, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = speed;
        this.tf = tf;
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

    public void paint(Graphics g) {
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

    /**
     * 移动
     */
    private void move() {
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
    }

    /**
     * 开火
     */
    public void fire() {
        int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        tf.bullets.add(new Bullet(bX, bY, this.dir, this.tf));
    }
}
