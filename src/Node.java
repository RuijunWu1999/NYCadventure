import java.awt.*;

class Node {
    int row;
    int col;
    String dir;//方向
    Node next;
    Node pre;

    Node(int row, int col, String dir) {
        this.row = row;
        this.col = col;
        this.dir = dir;
    }

    public void draw(Graphics g) {
        g.fillOval(col * Snake.span, row * Snake.span, Snake.span, Snake.span);//坐标

    }

    public Rectangle getRectanlge() {
        return new Rectangle(this.col * Snake.span, this.row * Snake.span, Snake.span, Snake.span);
    }
}
