package AssignmentTurtleGraphicsProgram;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import uk.ac.leedsbeckett.oop.LBUGraphics;



public class MainClass extends LBUGraphics
{       
        //----------------------| Requirement 1 |----------------------\\
        public MainClass()//(10 marks)
        {
                JFrame M_frame= new JFrame();
                M_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exits when closed
                M_frame.add(this); // object from turtlegraphics
                M_frame.setLayout(new FlowLayout());// not necessary but i'll keep it anyways
                M_frame.pack();
                M_frame.setVisible(true);
                about();// shows LBU trutle graphics current version
        }

        //---------------------------| End |---------------------------\\
        public void processCommand(String command)//
        {

        }
        public static void main(String[] args) //
        {
                new MainClass();
        }


        
}