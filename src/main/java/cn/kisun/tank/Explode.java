package cn.kisun.tank;

import java.awt.*;

/**
 * 爆炸效果
 * @Author: KISUN
 * @CreateTime: 2020-11-12-15-49
 * @Descirption:
 */
public class Explode {
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int x, y;

    private int step = 0;

    /**
     * 界面引用
     */
    private TankFrame tf;

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (step >= 0) {
            g.drawImage(ResourceMgr.explodes[step++],x,y,null);
        }
        if (step >= ResourceMgr.explodes.length) {
            this.tf.explodes.remove(this);
        }
    }
}
