package org.example;

import java.awt.*;

/**
 * 本程序实际模拟了鼠标抖动
 */
public class MouseMove {
    public static void main(String[] args) throws Exception{
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e1) {
            e1.printStackTrace();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double width = screenSize.getWidth() -1d;
        final double height = screenSize.getHeight() -1d;
        System.out.println("Screen size: " + width + "*" + height);

        Point pos  = MouseInfo.getPointerInfo().getLocation();
        int lastX = pos.x;
        int lastY = pos.y;
        int mov = 1 ;

        while (true) {
            System.out.println("移动前: "+ pos.x + " " + pos.y);
            //获取鼠标当前的坐标
            PointerInfo pos_info = MouseInfo.getPointerInfo();
            if (null != pos_info) {
                pos = pos_info.getLocation();
                //只对静止不动的鼠标进行操作
                if ((pos.x == lastX) && (pos.y == lastY)) {
                    System.out.println("moving!");
                    //假如鼠标到达屏幕左上角
                    if (pos.y <= 0 && pos.x <= 0) {
                        mov = 1;
                    }
                    //假如鼠标到达屏幕右下角
                    if (pos.x >= width && pos.y >= height) {
                        mov = -1;
                    }

                    //移动鼠标到新位置
                    robot.mouseMove(pos.x + mov, pos.y + mov);
                    pos  = MouseInfo.getPointerInfo().getLocation();
                    System.out.println("移动后: " + pos.x + " " + pos.y);
                }

                //鼠标移动后，重新获取鼠标指针的坐标
                pos_info = MouseInfo.getPointerInfo();
                if (null != pos_info) {
                    pos = pos_info.getLocation();
                    //将鼠标当前坐标记录下来，作为鼠标最近一次所在的位置，作为下鼠标移动时的起始位置
                    lastX = pos.x;
                    lastY = pos.y;
                }
            } else {
                System.out.println("Get location fail!");
                break;
            }

            Thread.sleep( 1 * 60_000L);
        }
    }

}
