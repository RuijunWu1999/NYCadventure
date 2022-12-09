import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ctrlButtons extends JPanel{
    public static final int btnHeight = 100;
    public static boolean isMove=true;//表示运行状态
    SnakeGrid snakeGrid;
    public JLabel hpJL;
    public JLabel scoresJL;
    public JLabel roundsJL;
    ctrlButtons(SnakeGrid snakeGrid){
        this.snakeGrid=snakeGrid;
        this.setBounds(0, SnakeGrid.windowHeight+10, SnakeGrid.windowWidth, btnHeight);
        //this.setPreferredSize(new Dimension(SnakeGrid.windowWidth, btnHeight));
        this.setLayout(null);

        JButton jb1 = new JButton("Pause");
        jb1.setBounds(10,10,100,30);
        JButton jb2 = new JButton("Resume");
        jb2.setBounds(120,10,100,30);
        JButton jb3 = new JButton("Restart");
        jb3.setBounds(230,10,100,30);
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        hpJL = new JLabel("HP:");
        hpJL.setBounds(350,10,100,30);
        scoresJL = new JLabel("SCORES:");
        scoresJL.setBounds(460,10,100,30);
        roundsJL = new JLabel("ROUNDS:");
        roundsJL.setBounds(570,10,100,30);
        this.add(hpJL);
        this.add(scoresJL);
        this.add(roundsJL);
        jb1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isMove=false;

            }
        });
        jb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isMove=true;
                snakeGrid.setFocusable(true);
                snakeGrid.requestFocus();
            }
        });
        jb3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {//重新创建蛇等 重新开始游戏
                snakeGrid.snakeThread.stopThread();

                Food food = new Food();
                snakeGrid.food=food;
                snakeGrid.snake=new Snake(food, snakeGrid);
                snakeGrid.snake.updateCtrlButtonsSTATUS();
                //snakeGrid.snake.setCtrlbuttons(snakeGrid.);
                //Snake.isAlive =true;
                isMove=true;
                SnakeGrid.SnakeThread st = snakeGrid.new SnakeThread();
                snakeGrid.snakeThread=st;
                st.start();

                snakeGrid.setFocusable(true);
                snakeGrid.requestFocus();
            }
        });

    }

    public void updateHP(int newHP)
    {
        this.hpJL.setText("HP:".concat(String.valueOf(newHP)));
    }

    public void updateSCORES(int newSCORES)
    {
        this.scoresJL.setText("SCORES:".concat(String.valueOf(newSCORES)));
    }

    public void updateROUNDS(int newROUNDS)
    {
        this.roundsJL.setText("ROUNDS:".concat(String.valueOf(newROUNDS)));
    }
}