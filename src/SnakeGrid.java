import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;

/**
 * @author Swyee
 **/
public class SnakeGrid extends JPanel {
    final static int windowWidth = 710;
    final static int windowHeight = 400;
    final String imageFilePath = "res/map-2.jpg";
    final String musicFilePath = "res/music.mid";
    Food food = new Food();
    private ctrlButtons ctrlb;
    Snake snake = new Snake(food, this);//创建蛇
    ImageIcon image ;
    //File f= new File(musicFilePath);//音乐文件地址
    SnakeThread snakeThread = new SnakeThread();
    static Obstacles obstacles = new Obstacles();
    static Places places = new Places();
    SnakeGrid(){
        image = new ImageIcon(imageFilePath);//图片文件地址
        image.setImage(image.getImage().getScaledInstance(windowWidth,windowHeight,Image.SCALE_SMOOTH));

        this.setBounds(0, 0, windowWidth, windowHeight);
        this.setBackground(Color.black);
        snakeThread.start();
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snake.keyboard(e);
            }
        });
    }

    public static Places.Place intersectedPlace(Node nextNode) {
        List<Places.Place> anyPlace = places.intersectedPlaces(nextNode);
        if (anyPlace.size() > 0) return anyPlace.get(0);
        else return null;
    }

    /**
     * 设置画笔
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //关于如何清空已经绘制的图形：
        //这个方法在刚才其实也有提及。就是调用父类的paint()函数，画面重新绘制一下。就是要在绘制之前将线条的颜色设置回你原本页面的颜色。
        // 父类的paint()函数，其实就很像用油漆刷墙一样，将整个页面归一为这个线条的颜色。
        image.paintIcon(this, g, 0, 0); //设置背景图片
        obstacles.draw(g);
        places.draw(g);

        snake.move();//蛇移动
        snake.draw(g);//重新画蛇本体
        food.draw(g);//重新画food
    }

    public static Obstacles.Obstacle intersectedObstacle(Node nextNode)
    {
        List<Obstacles.Obstacle> anyObstacle = obstacles.intersectedObstacles(nextNode);
        if (anyObstacle.size() > 0) return anyObstacle.get(0);
        else return null;
    }
    //读取音乐文件
    void Music(){
        try {
            musicClip musicObject = new musicClip();
            musicObject.playMusic(musicFilePath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setCtrlB(ctrlButtons ctrlB) {
        ctrlb = ctrlB;
    }

    public ctrlButtons getCtrlB() {
        return ctrlb;
    }

    /*
    void MusicOLD(){
        try {
            URI uri = f.toURI();
            URL url = uri.toURL();
            AudioClip aau= Applet.newAudioClip(url);
            aau.loop();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     */
    class SnakeThread extends Thread{
        boolean flag = true;
        @Override
        public void run() {
            while(snake.isAlive() &&flag){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(snake.isAlive() && ctrlButtons.isMove){
                    repaint();
                    //其实我们对paint()函数多次调用的需求就是通过repaint()函数实现的。我们在程序中调用repaint()函数，
                    // 其实就是对我们写的paint()函数再次进行调用。
                    //
                    //或者：removeAll()、repaint()、updataUI()三剑客就可以刷新、更换、重写内容了，其实repaint()可要可不要。
                }

            }
            if(!flag==false){
                JOptionPane.showMessageDialog(SnakeGrid.this, "Game Over");
            }

        }
        public void stopThread(){
            flag=false;
        }
    }
}