# loverpackage com.longg.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    //初始数据
    int length; //蛇的长度
    int[] snakeX = new int[600]; //蛇的坐标X
    int[] snakeY = new int[500]; //蛇的坐标Y
    String direction; //R：右，L：左，U：上，D：下

    boolean isStart = false; //游戏是否开始
    Timer timer = new Timer(60,this);

    //1.定义一个食物
    int foodx;
    int foody;
    Random random = new Random();

    //死亡判断
    boolean isFail = false;

    //积分系统
    int score;

    //构造器调用init方法
    public GamePanel(){
        init();
        //获取键盘的监听事件
        this.setFocusable(true); //让键盘的焦点聚集到游戏上
        this.addKeyListener(this); //在当前这个类实现键盘监听的接口
        timer.start(); //让时间动起来
    }

    //初始化
    public void init(){
        length =1;
        snakeX[0] = 400;snakeY[0] = 300; //头部坐标
        snakeX[1] = 375;snakeY[1] = 300; //第一个身体坐标
        direction = "R"; //初始化蛇头方向

        //在游戏范围内随机生成一个食物
        foodx = 25 * random.nextInt(75);
        foody = 25 * random.nextInt(38);

        score = 0;
    }

    //画板： 画界面，画蛇
    //Graphics ：画笔
    @Override
    protected void paintComponent(Graphics g) {

        this.setBackground(Color.white); //设置背景的颜色
        g.fillRect(0,0,1920,1000);//绘制游戏区域

        //画一条静态的小蛇
        if (direction.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        } else if (direction.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        } else if (direction.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        } else if (direction.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }


        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]); //蛇的身体长度通过length来控制
        }
        //画食物
        Data.food.paintIcon(this,g,foodx,foody);

        //画积分
        g.setColor(Color.CYAN); //设置画笔颜色
        g.setFont(new Font("微软雅黑",Font.BOLD,30)); //设置字体
        g.drawString("长度：" + length,1680,220);
        g.drawString("分数：" + score,1680,255);

        //游戏提示：是否开始
        if (isStart == false) {
            //画一个文字，String
            g.setColor(Color.white); //设置画笔颜色
            g.setFont(new Font("微软雅黑",Font.BOLD,60)); //设置字体
            g.drawString("按下空格开始游戏",720,520); //设置内容
        }
        //失败提醒
        if (isFail) {
            //画一个文字，String
            g.setColor(Color.red); //设置画笔颜色
            g.setFont(new Font("微软雅黑",Font.BOLD,50)); //设置字体
            g.drawString("游戏失败",860,340); //设置内容
            g.drawString("按下空格重新开始",760,440); //设置内容
        }

    }

    //接受键盘的输入：监听
    @Override
    public void keyPressed(KeyEvent e) {
        //获取按下的键盘是哪个键
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) { //如果按下的是空格键
            if (isFail) { //失败，游戏再来一遍
                isFail = false;
                init();
            }else { //暂停游戏
                isStart = !isStart;
            }

            repaint(); //刷新界面
        }


        //键盘控制走向
        if (keyCode == KeyEvent.VK_LEFT){
            if (direction == "R") {
                direction = "R";
            }else{
                direction = "L";
            }

        }else if (keyCode == KeyEvent.VK_RIGHT){
            if (direction == "L") {
                direction = "L";
            }else{
                direction = "R";
            }
        }else if (keyCode == KeyEvent.VK_UP){
            if (direction == "D") {
                direction = "D";
            }else{
                direction = "U";
            }
        }else if (keyCode == KeyEvent.VK_DOWN){
            if (direction == "U") {
                direction = "U";
            }else{
                direction = "D";
            }
        }
    }
    //定时器，监听时间
    // 帧 : 执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏处于开始状态,并且游戏没有结束
        if (isStart && isFail == false) {
            //右移
            for (int i = length - 1; i > 0; i--) { //除了脑袋，身体都向前移动
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            //通过控制方向让头部移动
            if (direction.equals("R")) {
                snakeX[0] = snakeX[0] + 25; //头部移动
                if (snakeX[0] > 1900) { isFail = true;} //边界判断
            }
            if (direction.equals("L")) {
                snakeX[0] = snakeX[0] - 25; //头部移动
                if (snakeX[0] < 0) { isFail = true;} //边界判断
            }
            if (direction.equals("U")) {
                snakeY[0] = snakeY[0] - 25; //头部移动
                if (snakeY[0] < 0) { isFail = true;} //边界判断
            }
            if (direction.equals("D")) {
                snakeY[0] = snakeY[0] + 25; //头部移动
                if (snakeY[0] > 1000) { isFail = true;} //边界判断
            }
            //如果小蛇的头和食物坐标重合了

            if (snakeX[0] == foodx && snakeY[0] == foody) {
                //长度+1
                length++;

                //闪烁问题
                snakeX[length-1] = foodx - 1;
                snakeY[length-1] = foody;

                score = score + 10;
                //随机生成食物
                foodx = 25 * random.nextInt(75);
                foody = 25 * random.nextInt(38);

            }

            //结束判断
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail = true;
                }
            }

            //刷新界面
            repaint();
        }
        timer.start(); //让时间动起来

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //释放某个键
    }
    @Override
    public void keyTyped(KeyEvent e) {
        //键盘按下，弹起 ：敲击
    }

}
