import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Places {
    List<Place> placesList = new ArrayList<>(20);

    Places()
    {
        int[] nyStock = {150, 300, Snake.span, Snake.span};
        placesList.add(new Place(nyStock,-20, "res/nystock.jpg", "NY Stock Exchange"));
        int[] empireStateBuilding = {205, 205, Snake.span, Snake.span};
        placesList.add(new Place(empireStateBuilding, -20, "res/EmpireStateBuilding0.jpg", "Empire State Building"));
        int[] timeSquare = {185, 175, Snake.span, Snake.span};
        placesList.add(new Place(timeSquare, -20, "res/TimeSquare0.jpg", "Time Square"));
        int[] lincolnHarborPark = {120, 160, Snake.span, Snake.span};
        placesList.add(new Place(lincolnHarborPark, -20, "res/LincolnHarborPark0.jpg", "Lincoln Harbor Park"));
        int[] nyHallOfScience = {535, 210, Snake.span, Snake.span};
        placesList.add(new Place(nyHallOfScience, -20, "res/nyHallOfScience0.png", "NY Hall of Science"));
        int[] hamiltonGrangeNationalMemorial = {300, 10, Snake.span, Snake.span};
        placesList.add(new Place(hamiltonGrangeNationalMemorial, -20, "res/HamiltonGrangeNationalMemorial0.jpg", "Hamilton Grange National Memorial"));
        int[] statueOfLiberty = {50, 360, Snake.span,Snake.span};
        placesList.add(new Place(statueOfLiberty, -20, "res/StatueOfLiberty0.jpg", "Statue Of Liberty"));
        int[] regalUaKaufmanAstoria = {350, 180, Snake.span, Snake.span};
        placesList.add(new Place(regalUaKaufmanAstoria, -20, "res/RegalUaKaufmanAstoria0.jpg", "Regal UA Kaufman Astoria"));
        int[] theEvergreensCemetery = {430, 360, Snake.span, Snake.span};
        placesList.add(new Place(theEvergreensCemetery, -20, "res/TheEvergreensCemetery0.jpg", "The Evergreens Cemetery"));
    }

    public List<Place> getPlacesList()
    {
        return this.placesList;
    }

    public List<Place> intersectedPlaces(Node nextNode)
    {
        Rectangle nextRect = nextNode.getRectanlge();
        //Stream obstaclesStream = obstaclesList.parallelStream();
        Stream placesStream = placesList.stream();
        /*
        boolean result = (boolean) obstaclesStream.reduce(false, (a, b) -> {
            boolean b1 = ((Obstacle) a).rect.intersects(nextRect)
                    || ((Obstacle) b).rect.intersects(nextRect);
            return b1;
        });
         */
        return (List<Place>) placesStream
                .filter( (x) -> ((Place) x).shape.intersects(nextRect))
                .collect(Collectors.toList());
    }
    public void draw(Graphics g)
    {
        Stream placesStream = placesList.stream();
        //Stream只能被消费一次，当其调用了终止操作后便说明其已被消费掉了。 如果还想重新使用，可考虑在原始数据中重新获得。
        placesStream.forEach( (x) -> {((Place) x).draw(g);});
        // Worked!!!
        /*
        for (Obstacle ob : obstaclesList ) {
            ob.draw(g);
        }
         */
    }
    class Place {
        //Rectangle rect;
        Rectangle shape;
        int addPoints;
        ImageIcon image;
        String description;

        Place(int[] rectPoints, int aPoints, String imagePath, String strDescription)
        {
            description = strDescription;
            image = new ImageIcon(imagePath);
            image.setImage(image.getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT));
            addPoints = aPoints;
            shape = new Rectangle(rectPoints[0], rectPoints[1],rectPoints[2],rectPoints[3]);

        }
        public void draw(Graphics g) {
            g.setColor(Color.CYAN);
            g.fillRoundRect(shape.x,shape.y,shape.width,shape.height,15,15);
            //g.fillRect(rect.x, rect.y, rect.width, rect.height);//表示坐标
        }

        public void showInfo(){
            ctrlButtons.isMove = false;
            JOptionPane.showInternalConfirmDialog(null, this.description, "Info"
                    , JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, this.image);

            try {
                Thread.sleep(400);
                // Sleep a while for snake's smooth move
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ctrlButtons.isMove = true;
        }
    }
}
