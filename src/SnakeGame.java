import javax.swing.*;
import java.awt.*;

/**
 * @author Swyee
 **/
public class SnakeGame extends JFrame {
    SnakeGrid snakeGrid= new SnakeGrid();
    ctrlButtons ctrlButtons = new ctrlButtons(snakeGrid);
    SnakeGame(){
        this.setBounds(100, 50, SnakeGrid.windowWidth, SnakeGrid.windowHeight + ctrlButtons.btnHeight);//设置窗口大小
        this.setLayout(null);//更改layout 以便添加组件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭窗口的状态
        this.setResizable(false);//窗口不可以改变大小
        this.add(snakeGrid);
        this.add(ctrlButtons);
        snakeGrid.setCtrlB(ctrlButtons);
        snakeGrid.snake.updateCtrlButtonsSTATUS();
        //设置焦点
        snakeGrid.setFocusable(true);
        snakeGrid.requestFocus();
        snakeGrid.Music();//调用打开音乐的方法
        this.setVisible(true);//设置焦点状态为true
    }

    public SnakeGrid getJFrame()
    {
        return snakeGrid;
    }

    public static SnakeGame sg;
    public static void main(String[] args) {

        sg = new SnakeGame();
    }

}

