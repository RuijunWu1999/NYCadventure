import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * @author Swyee
 */
public class Snake {
    SnakeGrid snakeGrid;
    public static final int span=20;//间距
    public static final String up="u";
    public static final String down="d";
    public static final String left="l";
    public static final String right="r";
    Node body;//蛇的身体
    Node head;//蛇的头部
    Node tail;//蛇的头部
    Food food;
    //public static boolean isAlive;
    private boolean allowLandmarkAddHP = true;
    private int healthPoints = 100;
    private int rounds = 1;
    private int scores = 0;
    Snake(Food food, SnakeGrid snakeGrid){
        this.snakeGrid = snakeGrid;
        body = new Node(5,20,left);
        head = body;
        tail = body;
        this.food=food;
        for (int i = 0; i < 2; i++) {
            addHead(nextNodeInDirection());
        }
        // Total Length is 3
        //isAlive = true;
        //healthPoints = 100;
        if (snakeGrid.getCtrlB() != null)
        {
            updateCtrlButtonsSTATUS();
        }
    }

    public void updateCtrlButtonsSTATUS() {
        snakeGrid.getCtrlB().updateHP(this.healthPoints);
        snakeGrid.getCtrlB().updateSCORES(this.scores);
        snakeGrid.getCtrlB().updateROUNDS(this.rounds);
    }

    public boolean isAlive(){
        return healthPoints > 0;
    }

    /*
    把蛇画出来
     */
    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        //showHP(head, g);
        for(Node n=head;n!=null;n=n.next){
            n.draw(g);
            g.setColor(Color.green);
        }

    }

    private void showHP(Node head, Graphics g) {
        int srow = head.row - 1;
        if (head.row == 0) {
            srow = 1;
        }
        /*
        switch (head.row) {
            case 0 -> {
                srow = 1;
                break;
            }
            case (SnakeGrid.windowHeight / span) -> {
                srow = (SnakeGrid.windowHeight / span) - 1;
                break;
            }
        }
         */
        int scol = head.col + 1;
        if (head.col >= (SnakeGrid.windowWidth / span) -1) {
            scol = (SnakeGrid.windowWidth / span) -2;
        }
        g.drawString(String.valueOf(healthPoints),scol*span, (srow+1)*span);
        //g.fillOval(scol*span, srow*span, span,span);//坐标
    }

    /*
    调用键盘的上下左右键
    head.dir记录现在操作的是什么按钮，从而更改蛇的状态
    向上移送时，下键失效，其他四个方向也是如此判断
     */
    public void keyboard(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                if(head.dir.equals(down)){
                    break;
                }
                head.dir=up;
                ctrlButtons.isMove = true;
                break;
            case KeyEvent.VK_DOWN:
                if(head.dir.equals(up)){
                    break;
                }
                head.dir=down;
                ctrlButtons.isMove = true;
                break;
            case KeyEvent.VK_LEFT:
                if(head.dir.equals(right)){
                    break;
                }
                head.dir=left;
                ctrlButtons.isMove = true;
                break;
            case KeyEvent.VK_RIGHT:
                if(head.dir.equals(left)){
                    break;
                }
                head.dir=right;
                ctrlButtons.isMove = true;
                break;
            default:
                break;
        }
    }
    /*
    增加头部
     */
    public void addHead(Node node){
        //Node node = nextNodeInDirection();
        node.next=head;
        head.pre=node;
        head=node;
    }

    private Node nextNodeInDirection() {
        Node node = null;
        switch (head.dir){
            case "l":
                node = new Node(head.row,head.col-1,head.dir);
                break;
            case "r":
                node = new Node(head.row,head.col+1,head.dir);
                break;
            case "u":
                node = new Node(head.row-1,head.col,head.dir);
                break;
            case "d":
                node = new Node(head.row+1,head.col,head.dir);
                break;
            default:
                break;
        }
        return node;
    }

    /*
    删除尾部
     */
    public void deleteTail(){
        tail.pre.next=null;
        tail=tail.pre;
    }
    /*
    增加move方法
     */
    public void move() {
        Node nextNode = nextNodeInDirection();
        if (isNextMoveLegal(nextNode))
        {
            addHead(nextNode);
            if(this.getSnakeRectangle().intersects(food.getCoordinates()))
            {
                if ( SnakeGrid.intersectedPlace(food.getNode()) != null ) {
                    //this.healthPoints += 8;
                    updateHP(8);
                    this.scores += 200;
                }
                else
                {
                    //this.healthPoints += 5;
                    updateHP(5);
                    this.scores += 100;
                }

                food.repearShow();
                this.allowLandmarkAddHP = true;
                this.rounds ++;
                updateCtrlButtonsSTATUS();
            }
            deleteTail();
        }
        else {
            updateCtrlButtonsSTATUS();
        }
    }
    private void updateHP(int deltaHP)
    {
        healthPoints = (healthPoints+deltaHP) > 100 ? 100 : (healthPoints+deltaHP);
    }
    private boolean isNextMoveLegal(Node nextNode) {
        if (isHittingWalls(nextNode) || isBitingSelf(nextNode)) return false;
        Obstacles.Obstacle ob = SnakeGrid.intersectedObstacle(nextNode);
        if ( ob != null) {
            //healthPoints += ob.deductionPoints;
            updateHP(ob.deductionPoints);
            ctrlButtons.isMove = false;
            updateCtrlButtonsSTATUS();
            return false;
        }
        Places.Place plc1 = SnakeGrid.intersectedPlace(head);
        if ( plc1 != null  )
        // 头碰但脖子未碰，是第一碰到；脖子也碰，是正在穿过；
        {
            Places.Place plc2 = SnakeGrid.intersectedPlace(head.next);
            //ctrlButtons.isMove = false;
            if (plc2 == null)
            {
                plc1.showInfo();
                if (this.allowLandmarkAddHP)
                {
                    //this.healthPoints += 2;
                    updateHP(2);
                    this.allowLandmarkAddHP = false;
                    updateCtrlButtonsSTATUS();
                }
            }
        }
        return true;
    }

    private boolean isBitingSelf(Node nextNode) {
        Node n;
        for (n = head; n != null; n = n.next)
        {
            if (n.col == nextNode.col && n.row == nextNode.row)
            {
                healthPoints -= 10;
                ctrlButtons.isMove = false;
                return true;
            }
        }
        return false;
    }

    private boolean isHittingWalls(Node nextNode) {
        if ((nextNode.col < 0 || nextNode.row < 0)
                || (nextNode.row >= SnakeGrid.windowHeight/span || nextNode.col >= SnakeGrid.windowWidth/span) )
        {
            healthPoints -= 10;
            ctrlButtons.isMove = false;
            return true;
        }
        return false;
    }

    public Rectangle getSnakeRectangle(){//获取蛇头的坐标

        return new Rectangle(head.col*span,head.row*span,span,span);

    }
}