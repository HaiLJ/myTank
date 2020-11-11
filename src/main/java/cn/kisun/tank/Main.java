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
        for (int i = 0; i < 5; i++) {
            tf.tanks.add(new Tank((1+2*i)*Tank.WIDTH,200,tf));
        }
        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
