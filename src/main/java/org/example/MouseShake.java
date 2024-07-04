package org.example;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * 本程序实际模拟了鼠标抖动
 * 如果注释掉line53 ~ line58,程序将指挥鼠标在屏幕上画平行四边形
 * 
 * @author gongchengship@163.com
 */
public class MouseShake {
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
        System.out.println("Screen size: " + width + " * " + height);

        Point pos  = MouseInfo.getPointerInfo().getLocation();
        int lastX = pos.x;
        int lastY = pos.y;
        int mov = 1 ;

        while (true) {
            System.out.println("原始坐标: "+ pos.x + " " + pos.y + " #时间:"
                    + DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalDateTime.now(ZoneId.systemDefault())));
            //获取鼠标当前的坐标
            PointerInfo pos_info = MouseInfo.getPointerInfo();
            if (null != pos_info) {
                pos = pos_info.getLocation();
                //只对静止不动的鼠标进行操作
                if ((pos.x == lastX) && (pos.y == lastY)) {
                    //假如鼠标到达屏幕左上角
                    if (pos.y <= 0 && pos.x <= 0) {
                        mov = 1;
                    }
                    //假如鼠标到达屏幕右下角
                    if (pos.x >= width && pos.y >= height) {
                        mov = -1;
                    }

                    //鼠标向右下移动1 pixel
                    robot.mouseMove(pos.x + mov, pos.y + mov);
                    Thread.sleep(500);
                    pos  = MouseInfo.getPointerInfo().getLocation();
                    System.out.println("抖动到坐标: " + pos.x + " " + pos.y);

                    Thread.sleep(500);

                    //####start####注释掉以下3行，程序将让鼠标在屏幕画平行四边形 ####star##t##
                    // 1秒后, 鼠标鼠标向左上移动1 pixel
                    robot.mouseMove(pos.x - mov , pos.y - mov);
                    Thread.sleep(500);
                    pos  = MouseInfo.getPointerInfo().getLocation();
                    System.out.println("恢复到坐标: " + pos.x + " " + pos.y);
                    //####end####注释掉以上3行，程序将让鼠标在屏幕画平行四边形 ####end####
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

            //将这个时间改成3000有助于看到更直观的效果，
            //注意改得太小将需要使用Windows资源管理器才能关闭程序，因为鼠标自己在不停地动
            long interval = 3 * 60 *1000L; //3分钟
            long mainParamInt = interval;
            if (args != null && args.length >= 1) {
                String mainParam = args[0];
                mainParamInt = Long.parseLong(mainParam);
            }
            if (mainParamInt < 3000) {
                interval = 3000;
            } else if (mainParamInt > 10 * 60 * 1000){
                interval = 10 * 60 * 1000;
            } else {
                interval = mainParamInt;
            }
            // Thread.sleep(3000L); //调试专用
            Thread.sleep(interval);
        }
    }

}
