import javax.swing.JFrame;

    public class Main {

        public static void main(String[] args) {
            //call in a JFrame
            JFrame obj = new JFrame();
            Gameplay game= new Gameplay();
            obj.setBounds(10, 10, 708, 600);
            obj.setTitle("Brick Breaker Game!");
            obj.setResizable(false);
            obj.setVisible(true);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            obj.add(game);

        }
    }
