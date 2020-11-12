package cn.kisun.tank;

import java.awt.*;

/**
 * 子弹定义
 * @Author: KISUN
 * @CreateTime: 2020-11-10-14-56
 * @Descirption: 子弹
 */
public class Bullet {
    private int speed = 10;
    private int x,y;
    private Dir dir;
    public static int WIDTH = ResourceMgr.bulletD.getWidth(), HEIGHT = ResourceMgr.bulletD.getHeight();
    private boolean living = true;
    /**
     * 所属分组
     */
    private Group group = Group.BAD;
    /**
     * 界面引用
     */
    private TankFrame tf;

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (!living){
            tf.bullets.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
            default:
                break;
        }
        move();
    }

    private void move() {
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
        if (x<0 ||y<0 || x> TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            living = false;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * 子弹与坦克相撞方法
     * @param tank
     */
    public void collideWith(Tank tank) {
        if (this.group.equals(tank.getGroup())) {
            return;
        }
        // TODO
        Rectangle bulletRect = new Rectangle(this.x,this.y,WIDTH,HEIGHT);
        Rectangle tankRect = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank.HEIGHT);
        if (bulletRect.intersects(tankRect)) {
            int blood = tank.getBlood();
            blood--;
            if (0 == blood) {
                tank.die();
            }
            tank.setBlood(blood);
            this.die();
        }
    }

    private void die() {
        this.living = false;
    }
}
