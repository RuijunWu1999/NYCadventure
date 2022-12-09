import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Obstacles {
    List<Obstacle> obstaclesList = new ArrayList<>(20);

    Obstacles()
    {
        int standardDpoints = -15;

        int[][] xyPoints = { {250,0}, {300,0}, {185,150}, {145,150}};
        // Up-stream River LEFT
        // Clock wise sequence
        obstaclesList.add(new Obstacle(xyPoints,standardDpoints));

        xyPoints = new int[][]{{125, 190}, {155, 190}, {130,300},{140,400 }, {70,400} };
        // mid-stream river LEFT
        obstaclesList.add(new Obstacle(xyPoints,standardDpoints));

        xyPoints = new int[][]{{650,0}, {700,0}, {700,100}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));

        xyPoints = new int[][]{{700,60},{700,100},{650,70},{500,90},{500,60},{600,60},{630,40}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));

        xyPoints = new int[][]{{500,60},{500,130},{555,155},{520,170},{540,190},{560,175},{550,175},{550,155},{550,130},{530,130},{530,100}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));

        xyPoints = new int[][]{{500,70},{470,70},{420,65},{400,100},{420,100},{445,130},{500,120}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));

        xyPoints = new int[][]{{320,140},{275,190},{300,200},{335,155}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));

        xyPoints = new int[][]{{250,230},{245,270},{270,290},{270,230}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));

        xyPoints = new int[][]{{270,270},{240,350},{230,330},{250,270}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));

        xyPoints = new int[][]{{230,320},{175,325},{190,335},{240,330}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));

        xyPoints = new int[][]{{175,325},{140,350},{142,400},{190,335}};
        obstaclesList.add(new Obstacle(xyPoints, standardDpoints));
    }

    public List<Obstacle> getObstacles()
    {
        return this.obstaclesList;
    }

    public List<Obstacle> intersectedObstacles(Node nextNode)
    {
        Rectangle nextRect = nextNode.getRectanlge();
        //Stream obstaclesStream = obstaclesList.parallelStream();
        Stream obstaclesStream = obstaclesList.stream();
        /*
        boolean result = (boolean) obstaclesStream.reduce(false, (a, b) -> {
            boolean b1 = ((Obstacle) a).rect.intersects(nextRect)
                    || ((Obstacle) b).rect.intersects(nextRect);
            return b1;
        });
         */
        return (List<Obstacle>) obstaclesStream
                    .filter( (x) -> ((Obstacle) x).shape.intersects(nextRect))
                    .collect(Collectors.toList());
    }
    public void draw(Graphics g)
    {
        Stream obstaclesStream = obstaclesList.stream();
        //Stream只能被消费一次，当其调用了终止操作后便说明其已被消费掉了。 如果还想重新使用，可考虑在原始数据中重新获得。
        obstaclesStream.forEach( (x) -> {((Obstacle) x).draw(g);});
        // Worked!!!
        /*
        for (Obstacle ob : obstaclesList ) {
            ob.draw(g);
        }
         */
    }
    class Obstacle{
        //Rectangle rect;
        Polygon shape;
        int deductionPoints;

        Obstacle(int[][] xyPoints, int dPoints)
        {
            deductionPoints = dPoints;
            shape = new Polygon();
            for (int[] xy : xyPoints) {
                shape.addPoint(xy[0], xy[1]);
            }
            /*
            int[] xPoints = new int[xyPoints.length];
            int[] yPoints = new int[xyPoints.length];
            for (int i = 0; i < xyPoints.length; i++) {
                xPoints[i] = xyPoints[i][0];
                yPoints[i] = xyPoints[i][1];
            }
            //int[] xPoints = Arrays.stream(xyPoints).mapToInt(p -> p[0]).toArray();
            //int[] yPoints = Arrays.stream(xyPoints).mapToInt(p -> p[1]).toArray();
            shape = new Polygon(xPoints,yPoints, xyPoints.length);
             */
        }
/*
  xPoints[0] = x1 + dx;
  yPoints[0] = y1 + dy;
  xPoints[1] = x1 - dx;
  yPoints[1] = y1 - dy;
  xPoints[2] = x2 - dx;
  yPoints[2] = y2 - dy;
  xPoints[3] = x2 + dx;
  yPoints[3] = y2 + dy;
  g.fillPolygon(xPoints, yPoints, 4);
*/
        public void draw(Graphics g) {
            // g.setColor(Color.DARK_GRAY);
            g.setColor(Color.BLUE);
            g.fillPolygon(this.shape);
            //g.fillRect(rect.x, rect.y, rect.width, rect.height);//表示坐标
        }

    }
}
