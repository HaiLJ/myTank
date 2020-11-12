package cn.kisun.tank;

/**
 * 启动入口
 * @Author: KISUN
 * @CreateTime: 2020-11-10-11-04
 * @Descirption: 主线程
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf  = new TankFrame();
        tf.initBadTank();
        new Thread(()->new Audio("audio/war1.wav").loop()).start();
        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
