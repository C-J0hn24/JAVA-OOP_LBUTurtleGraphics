package AssignmentTurtleGraphicsProgram;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import uk.ac.leedsbeckett.oop.LBUGraphics;

import java.util.Arrays;
import java.util.Scanner;

//----------------------| Requirement 1 |----------------------\

class MainFrame extends LBUGraphics{
    Scanner scan = new Scanner(System.in);

    public MainFrame()// 5 marks (window)
    {
        JFrame M_frame= new JFrame();
        M_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exits when closed
        M_frame.add(this); // object from turtlegraphics
        M_frame.setLayout(new FlowLayout());// not necessary but i'll keep it anyways
        M_frame.pack();
        M_frame.setVisible(true);
    }

//Command Processing --Requirement 1

    public void c_command()//(5 marks) console input
    {
        Boolean check = false;

        do // loops until correct syntax is given
        {
            System.out.print("Console Command :> ");
            String User_input = scan.nextLine();

            if (User_input.equalsIgnoreCase("about"))
            {
                about();// 5 marks (command shows LBU trutle graphics current version)
                System.out.println('\n'+"Command Execution Complete ;) "+'\n');
                check =true;
            }
            else {
                System.out.println("Syntax error Please Enter 'About'"+'\n');
            }
        }
        while (check!=true);
    }
//----------------------| Requirement 2 Part 1 |----------------------\
    //directional movement
    class movement
    {
        void movementCheck(String move)
        {
            //for directional movement by x amount
            String[] direction = move.split("[,\\.\\ ]");//splits the command n saves in array 
            System.out.println("Command Executed: " + move);

            if (direction.length > 1)// checks the index lenght,if > 1 uses that as parameter 
            {   
                
                //exception handeling Source : W3School
                try 
                {   // checks if the parameter is an int or string in direction index 1
                    int angle= Integer.parseInt(direction[1]);
                    String angle2="-"+angle;

                    //Requirement 3 Part 1 Validating Commands. 15 Marks-------------------------------\
                    //Correctly bounds parameters (4 Marks)
                    if (angle < 0 || angle > 360) //checks parameter bound (Default range is 0 to 360 +ve)
                    {
                        System.out.println('\n' + "|xxx| Command |> " +move+ " <| Parameter out of Bound |xxx|" + '\n'+"|xxx| Note: Default Parameter Range is 0 to 360 (+ve) |xxx|"+ '\n' );
                        return;
                    }
                    else{

                        //dynamically calls movement function built into LBU Turtle graphics
                        if (direction[0].equalsIgnoreCase("left")) 
                        {
                            left(angle); // Turn left by the specified angle
                            System.out.println("Moved to the "+direction[0]+" by "+direction[1]+" Degree! "+'\n');
                            displayMessage("Moved to the "+direction[0]+" by "+direction[1]+" Degree! "+'\n');
                        } 
                        else if (direction[0].equalsIgnoreCase("right")) {
                            right(angle); // Turn right by the specified angle
                            System.out.println("Moved to the "+direction[0]+" by "+direction[1]+" Degree! "+'\n');

                        } 
                        else if (direction[0].equalsIgnoreCase("move")) {
                            forward(angle); // Move Forward by x amount of pixels
                            System.out.println(direction[0]+"d by "+direction[1]+" Pixels! "+'\n');

                        } 
                        else if (direction[0].equalsIgnoreCase("reverse")) {
                            forward(angle2); // Move backward by x amount of pixels
                            System.out.println(direction[0]+"d by "+direction[1]+" Pixels! "+'\n');

                        } 
                        //requirement 5 2 marks draws square n returns to same spot
                        else if (direction[0].equalsIgnoreCase("square")) {

                            drawOn();//lets me draw things
                            System.out.println("Lines are now Invisible!"+'\n');
                        
                            int i =0;
                            System.out.println('\n'+"Initial Coordinates(x/y) :"+getxPos()+"/"+getyPos()+'\n');
                            do{
                                forward(angle);//takes in parameter
                                right(90);
                                i++;
                                System.out.println("xCo-ords:"+getxPos()+'\n'+"yCo-ords:"+getyPos()+'\n');
                            }
                            while (i!=4);
                            System.out.println('\n'+"Final Coordinates(x/y) :"+getxPos()+"/"+getyPos()+'\n');
                        
                            displayMessage("ALL DONE!");
                            System.out.println("Created :"+direction[0]+" with "+direction[1]+" Lenght Value! :)"+'\n');

                        } 

                        else {
                            System.out.println('\n' +"Error: Unsupported direction '" + direction[0] + "'."+ '\n');
                            
                        }
                    }


                } 
                //Requirement 3 Part 2 Validating Commands. 15 Marks-------------------------------\
                //Detects non numeric data for a parameter. (4 marks)
                catch(NumberFormatException e)//checks format (accepts only int)
                {
                    System.out.println('\n' + "|xxx| Command |> " +move+ " <| Invalid Parameter |xxx|" + '\n' );
                    
                    if (direction[0].equalsIgnoreCase("move") || direction[0].equalsIgnoreCase("reverse")){
                        System.out.println("Hint: To Move in a Spicific Angle Try ||> '" +direction[0]+ " + number' (eg: move,reverse 69) command :)"+'\n');  
                    }
                    else{
                        System.out.println("Hint: To Move in a Spicific Angle Try ||> '" +direction[0]+ " + number' (eg: left,right 69) command :)"+'\n');  
                    }
                } 
            } 
            
            //Requirement 3 Part 3 Validating Commands. 15 Marks-------------------------------\
            //Detects valid command with missing parameter. (3 marks)
            else 
            {   //move to default value 90 if input is only left,right,etc
                //dynamically calls movement function built into LBU Turtle graphics
                if (direction[0].equalsIgnoreCase("left")) 
                {
                    left(); // Turn left by 90 degree (default)
                    System.out.println("Moved to the "+move+" by 90 Degree! "+'\n');
                    System.out.println("Hint: To Move in a Spicific Angle Try ||> '" +move+ " + number' (eg: left 69 or right 420) command :)"+'\n');
                } 
                
                else if (direction[0].equalsIgnoreCase("right")) 
                {
                    right(); // Turn right by 90 degree (default)
                    System.out.println("Moved to the "+move+" by 90 Degree! "+'\n');
                    System.out.println("Hint: To Move in a Spicific Angle Try ||> '" +move+ " + number' (eg: left 69 or right 420) command :)"+'\n');
                } 

                else if (direction[0].equalsIgnoreCase("move")) 
                {
                    forward(45); // forward by 45 pixels (default)
                    System.out.println(move+"d by 45 Pixels! "+'\n');
                    System.out.println("Hint: To Move in a Spicific Angle Try ||> '" +move+ " + number' (eg: move 69 or reverse) command :)"+'\n');
               
                } 
                else if (direction[0].equalsIgnoreCase("reverse")) 
                {
                    forward(-45); // backward by 45 pixels (default)
                    System.out.println(move+"d by 45 Pixels! "+'\n');
                    System.out.println("Hint: To Move in a Spicific Angle Try ||> '" +move+ " + number' (eg: move 69 or reverse) command :)"+'\n');
               
                } 

                //requirement 5 draws square 2 marks
                else if (direction[0].equalsIgnoreCase("square")) {

                    drawOn();//draws square
                    System.out.println("Lines are now Invisible!"+'\n');
                    
                    int i =0;
                    System.out.println('\n'+"Initial Coordinates(x/y) :"+getxPos()+"/"+getyPos()+'\n');
                    do{
                        forward(90);//default parameter
                        right(90);
                        i++;
                        System.out.println("xCo-ords:"+getxPos()+'\n'+"yCo-ords:"+getyPos()+'\n');
                    }
                    while (i!=4);
                    System.out.println('\n'+"Final Coordinates(x/y) :"+getxPos()+"/"+getyPos()+'\n');
                
                    displayMessage("ALL DONE!");
                    System.out.println("Square Complete! (Default value: 90) "+'\n');
                    System.out.println("Hint: To Enter Spicific Length Value Try ||> '" +move+ " + Length' (eg: Square 69) command :)"+'\n');

                } 
                //reverse to do
                else {
                    System.out.println('\n' +"Error: Unsupported direction '" + direction[0] + "'."+ '\n');
                    
                }
            }
        }
    }

    //Pen Colour
    class PenColour
    {   
        //pen colour RGB
        void RGB(String RGBValue){
            String[] RGBColour = RGBValue.split("[,\\.\\ ]");//splits the command n saves in array 
            System.out.println("Command Executed: " + RGBValue);

            //System.out.println(RGBValue+'\n'+R+'\n'+G+'\n'+B+'\n');

            if (RGBColour.length==4) // input index must be 4
            {
                //string to int conversion
                int R = Integer.parseInt(RGBColour[1]);
                int G = Integer.parseInt(RGBColour[2]);
                int B = Integer.parseInt(RGBColour[3]);

                try{
                    //seting up bound for lgbt value
                    if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255) {

                        if (R < 0 || R > 255) {
                            displayMessage("RGB 'RED' Value Out of Bound (0-255)");
                            System.out.println("RGB Value Out of Bound! Red Value : " + R + '\n' + "Accepted Range (0-255)" + '\n');
                        }
                        if (G < 0 || G > 255) {
                            displayMessage("RGB 'GREEN' Value Out of Bound (0-255)");
                            System.out.println("RGB Value Out of Bound! Green Value : " + G + '\n' + "Accepted Range (0-255)" + '\n');
                        }
                        if (B < 0 || B > 255) {
                            displayMessage("RGB 'Blue' Value Out of Bound (0-255)");
                            System.out.println("RGB Value Out of Bound! Blue Value : " + B + '\n' + "Accepted Range (0-255)" + '\n');
                        }
                        return; //ends loop sets no color
                    }
                    //this sets up the actual color
                    setPenColour(new Color(R, G, B));
                    displayMessage("New RGB Colour Has been Set!");
                    System.out.println("Your New Color has been set to : "+'\n'+"Red: "+R+'\n'+"Green: "+G+'\n'+"Blue: "+B+'\n');
                }
                    
                catch (NumberFormatException e) {
                    displayMessage("Invalid input!");
                    System.out.println('\n' + "|xxx| Command |> " +RGBValue+ " <| Parameter Out of Bound |xxx|" + '\n' );
                }
            }

            else{
                System.out.println('\n' + "|xxx| Command |> " +RGBValue+ " <| Not Enough Parameter |xxx|" + '\n'+"Hint: Try 'rgb (0-255) (0-255) (0-255)' "+ '\n'+"Value Range : (0-255)"+ '\n' );
            }

        }

        //pen color atleast 5 colors (Black , Green , Red , White , Blue , Pink)
        void Colour(String P_Colour)
        {
            System.out.println("Command Executed : " + P_Colour);
            
            if(P_Colour.equalsIgnoreCase("black")){
                setPenColour(java.awt.Color.BLACK);
                System.out.println("Pen Colour has been set to : " + P_Colour+'\n');
            }
            else if(P_Colour.equalsIgnoreCase("red")){
                setPenColour(java.awt.Color.RED);
                System.out.println("Pen Colour has been set to : " + P_Colour+'\n');
            }
            else if(P_Colour.equalsIgnoreCase("white")){
                setPenColour(java.awt.Color.WHITE);
                System.out.println("Pen Colour has been set to : " + P_Colour+'\n');
            }
            else if(P_Colour.equalsIgnoreCase("green")){
                setPenColour(java.awt.Color.GREEN);
                System.out.println("Pen Colour has been set to : " + P_Colour+'\n');
            }
            else if(P_Colour.equalsIgnoreCase("blue")){
                setPenColour(java.awt.Color.blue);
                System.out.println("Pen Colour has been set to : " + P_Colour+'\n');
            }
            else if(P_Colour.equalsIgnoreCase("pink")){
                setPenColour(java.awt.Color.pink);
                System.out.println("Pen Colour has been set to : " + P_Colour+'\n');
            }
            else {
                //error message..
                System.out.println('\n' + "|xxx| Command |> " +P_Colour+ " <| Unavailable |xxx|" + '\n' );
    
            }

        }
    }

 //source w3school + geekforgeeks

 //checks for confirmation GUI JOption Yes/no
public boolean confirmSaveBeforeAction() {
    
    //console debug warning
    System.out.print('\n'+"Current Canvas Status : Unsaved Data!"+'\n');
    
    int response = JOptionPane.showConfirmDialog(null, 
        "Unsaved Data! Want to Save?", "Unsaved Changes", 
        JOptionPane.YES_NO_CANCEL_OPTION, 
        JOptionPane.WARNING_MESSAGE);

    //just for consol feed 
    if (response == JOptionPane.YES_OPTION) {
        System.out.println('\n' + "Current Canvas Status : Data Saved (OverWritten)" + '\n');
    }
    else if (response == JOptionPane.NO_OPTION) {
        System.out.println('\n' + "Current Canvas Status : Data Unsaved (Continued Without Saving) " + '\n');
    }
    else if (response == JOptionPane.CANCEL_OPTION) {
        System.out.println('\n' + "Current Canvas Status : Save Operation Aborted" + '\n');
    }
    
    return response != JOptionPane.CANCEL_OPTION;
}
//----------------------| Requirement 4 Part 1  |----------------------\\

class ImageFileHandle{
    //loads the saved files
    void load_execute(String txtFileName) throws IOException{
        File file = new File(txtFileName);

        //checks for file
        if (!file.exists()){
            System.out.println('\n'+"File : '"+txtFileName+"' does not exist."+'\n');
            return;
        }
        
        //reads from the Command_Logs file
        BufferedReader reader = new BufferedReader(new FileReader(txtFileName));
        String command;
        while ((command = reader.readLine()) != null) {
            processCommand(command); // Execute each command
        }
        reader.close();
        System.out.println('\n'+"Commands loaded & executed from: " + txtFileName);
   }

   //saving files kinda janky but it works
    void file(String f_stat) throws IOException{
        String f_name="image.jpg";
        String txt_name="Command_Logs.txt";
        
        //source w3school + geekforgeeks
        if (f_stat.equalsIgnoreCase("save")) { //save image
            File outputfile = new File(f_name);
            ImageIO.write(getBufferedImage(), "jpg", outputfile);

             //console alert for save operation
             System.out.println('\n'+"Current Canvas Status : Save Operation Initiated :)"+'\n');
            System.out.println("Current Canvas Status : Image Save Complete :)");
            JOptionPane.showMessageDialog(null, "Image has been saved successfully as: " + f_name, "Save Image", JOptionPane.INFORMATION_MESSAGE);
        } 
        else if (f_stat.equalsIgnoreCase("load_cmd")) { //loads commands
            
            //console alert
            System.out.println("Current Canvas Status : Executing Saved Commands..."+'\n');
            load_execute(txt_name);

            System.out.println("Current Canvas Status : Command Execution Complete :)"+'\n');
            JOptionPane.showMessageDialog(null, "Commands have been loaded and executed from:  " + txt_name, "Load Commands", JOptionPane.INFORMATION_MESSAGE);
        } 
        else if (f_stat.equalsIgnoreCase("load_img")) { // lodas image
            try {
                File inputfile = new File(f_name);
                BufferedImage image = ImageIO.read(inputfile);
                setBufferedImage(image);

                System.out.println("Current Canvas Status : Image Render Successful!"+'\n');
                JOptionPane.showMessageDialog(null, "Image has been loaded successfully from: " + f_name, "Load Image", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                System.out.println("Current Canvas Status : Error - Image File Not Found"+'\n');

                JOptionPane.showMessageDialog(null, "File does not exist!", "Load Image", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Current Canvas Status : Error - Pack it up lol"+'\n');

            JOptionPane.showMessageDialog(null, "Unsupported file operation: " + f_stat, "Error", JOptionPane.ERROR_MESSAGE);
        }
            
    }
}
 
class TextFileHandle //2marks

{
   void txtFile(ArrayList<String> command_Logs)//creates a command log file which have been executed previously
   {
        //source w3school + geekforgeeks
        try {
            String txtFileName = "Command_Logs.txt";
            File myObj = new File(txtFileName);
            FileWriter myWriter = new FileWriter(txtFileName);
    
            if (myObj.createNewFile()) {
                //console alert
                System.out.print("Current Canvas Status : Command Save File Created "+txtFileName+'\n');
                //popup alert
                JOptionPane.showMessageDialog(null, "Command Save File created: " + myObj.getName(), "Save Commands", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.print("Current Canvas Status : Commands Save File '"+txtFileName+"' Already Exists! "+'\n');

                JOptionPane.showMessageDialog(null, "Command Save File already exists. Overwriting: " + myObj.getName(), "Save Commands", JOptionPane.WARNING_MESSAGE);
                System.out.print("Current Canvas Status : Save File Overwritten -< "+txtFileName+'\n');
                
            }
    
            for (String command : command_Logs) {
                myWriter.write(command + System.lineSeparator());
            }
            myWriter.close();
            //console alert
            System.out.print("Current Canvas Status : Command Data Saved in "+txtFileName+'\n');
            System.out.println('\n'+"Current Canvas Status : Save Operation Complete :)"+'\n');
            
            //pop up alert
            JOptionPane.showMessageDialog(null, "Commands have been saved to: " + txtFileName, "Save Commands", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.out.print('\n'+"Current Canvas Status : Save Error RIP! XP "+'\n');

            JOptionPane.showMessageDialog(null, "Unable to save commands!", "Save Commands", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
   }

}

class FileSave{

    ImageFileHandle f = new ImageFileHandle();//save iamge
    TextFileHandle t = new TextFileHandle();//command save

    void manual_save(String save_alt){

        try {
            // Save the image & commands
            f.file(save_alt);//saves image
            t.txtFile(Command_Logs); // Save the command logs to a text file for the current instance
            return; // ends loop if user does nothing
        }
        catch (IOException e) {
            System.out.println('\n'+"Something went wrong! :( "+'\n');
        }
    }

};
//---------------------------| Requirement 5 Part 1 End |---------------------------\\

@Override//overwride about to show up my name(4marks)
public void about(){
    
    super.about();//draws oop (executes parent class code)
    displayMessage("Edited By: C-J0hn24 (Srijan Thapa)");
    /* 
    //this kinda buggy

    JLabel l = new JLabel("   Edited By: C-J0hn24 (Srijan Thapa)");
    l.setForeground(java.awt.Color.WHITE); //white text color
    this.add(l);
    this.revalidate();//text wont show up without this
    
    */
}

void penwidth(String THICC){

    String[] Pen_is_THICC = THICC.split("[,\\.\\ ]");//splits the command n saves in array 
    System.out.println("Command Executed: " +THICC);

    if (Pen_is_THICC.length == 2){

        try {
            //just some silly terminal feed :)
                if (Integer.parseInt(Pen_is_THICC[1])<0){
                    System.out.println('\n' +"It's Okay Size Don't Matter!"+'\n'+"Your Pen_is not THICC enough :'(" +'\n' +"(Your Size: "+Pen_is_THICC[1]+")"+'\n' +"But it can be fixed Using (1-30) :)"+'\n');
                    return;
                }
                if (Integer.parseInt(Pen_is_THICC[1])>30){
                    System.out.println('\n' +"OMG It's MASSIVE!!!"+'\n'+"Your Pen_is EXTREAMLY THICC FOR ME!! "+'\n' +"(Your Size: "+Pen_is_THICC[1]+")"+'\n'+"I can only Handle (1-30) (T_T) "+'\n');
                    return;
                }

            //sets Pen thiccness
            setStroke(Integer.parseInt(Pen_is_THICC[1]));
            System.out.println("Current Pen THICCNESS : "+getStroke()+'\n');
            displayMessage("Current Pen THICCNESS : "+getStroke());

        } catch (NumberFormatException e) {
            displayMessage("Invalid input!");
            System.out.println('\n' + "|xxx| Command |> " +THICC+ " <| THICCness Out of Bound |xxx|" + '\n' );
        }

    }

    else{
        System.out.println('\n' + "|xxx| Command |> " +Pen_is_THICC+ " <| Your Pen_is Not THICC enough |xxx|" + '\n'+"Hint: Try 'penwidth 10' "+ '\n'+"Value Range : (0-255)"+ '\n' );
    }

}

class triangles {
    //takes one parameter all angles are set to 120
    void eq_triangle(String triangle){
        String[] split_t = triangle.split("[,\\.\\ ]");//splits the command n saves in array 
        System.out.println("Command Executed: " +triangle);
        
        if (split_t.length == 2){
            try {
                int sideLength = Integer.parseInt(split_t[1]);

                // setting up the bound so turtle wont go out of window
                if (sideLength <= 0 || sideLength > 120) {
                    System.out.println('\n' + "|xxx| Command |> " + triangle + " <| Parameter out of Bound |xxx|" + '\n' + "Hint: Use a positive integer between 1 and 120." + '\n');
                    displayMessage("Value out of bound use(0-120).");
                    return;
                }

                int i = 0;
                System.out.println('\n' + "Initial Coordinates(x/y) :" + getxPos() + "/" + getyPos() + '\n');
                drawOn();

                do {
                    forward(sideLength); // Use validated parameter
                    right(120); // Default angle
                    i++;
                    System.out.println("xCo-ords:" + getxPos() + '\n' + "yCo-ords:" + getyPos() + '\n');
                } while (i != 3);

                System.out.println('\n' + "Final Coordinates(x/y) :" + getxPos() + "/" + getyPos() + '\n');

                displayMessage("ALL DONE!");
                System.out.println("Triangle Complete!" + '\n');
            } catch (NumberFormatException e) {
                System.out.println('\n' + "|xxx| Command |> " + triangle + " <| Invalid Parameter |xxx|" + '\n' + "Hint: Use a positive integer betn (1-120)." + '\n');
                displayMessage("Invalid Parameter! Hint: triangle 33.");
            }
        } else {
            System.out.println('\n' + "|xxx| Command |> " + triangle + " <| Invalid Parameter |xxx|" + '\n' + "Hint: Use a positive integer betn (1-120)." + '\n');
            displayMessage("Invalid Command!");
        }
    }

    //for this o copied my own code from above method n made somechanges cz im lazy

    void any_triangle(String triangle){
        
        String[] split_t = triangle.split("\\s+");//splits the command n saves in array 
        System.out.println("Command Executed: " +triangle);
        
        //more or less than 4 index then error :)
        if (split_t.length == 4 && split_t[0].equalsIgnoreCase("triangle")){
            try {

                int a = Integer.parseInt(split_t[1]);
                int b = Integer.parseInt(split_t[2]);
                int c = Integer.parseInt(split_t[3]);
                
                //checks input expecially sides
                if(a <= 0 || a > 250 || b <= 0 || b > 250 || c <= 0 || c > 250 || a + b <= c || a + c <= b || b + c <= a) {
                    System.out.println('\n' + "|xxx| Command |> " + triangle + " <| Invalid Sides |xxx|" + '\n' +"Hint: Stay positive! Give positive input :)" + '\n'+"Bound Range is (0-255)" + '\n');
                    displayMessage("Invalid triangle sides!.");
                    return;
                }

                //main code to draw triangle
                System.out.println('\n' + "Initial Coordinates(x/y) :" + getxPos() + "/" + getyPos() + '\n');
                drawOn();

                //borrowed this from StackOverflow :)
                //cos law
                double angleA = Math.toDegrees(Math.acos((b*b + c*c - a*a) / (2.0 * b * c)));
                double angleB = Math.toDegrees(Math.acos((a*a + c*c - b*b) / (2.0 * a * c)));
                double angleC = 180.0 - angleA - angleB;

                    System.out.println("xCo-ords:" + getxPos() + '\n' + "yCo-ords:" + getyPos() + '\n');
                    forward(a);
                    //changed to string cz it only accepts in string as whole number
                    right((int) Math.round(180 - angleC));

                    System.out.println("xCo-ords:" + getxPos() + '\n' + "yCo-ords:" + getyPos() + '\n');
                    forward(b);
                    right((int) Math.round(180 - angleA));

                    System.out.println("xCo-ords:" + getxPos() + '\n' + "yCo-ords:" + getyPos() + '\n');
                    forward(c);
                    right((int) Math.round(180 - angleB));
                    
 
                System.out.println('\n' + "Final Coordinates(x/y) :" + getxPos() + "/" + getyPos() + '\n');

                displayMessage("ALL DONE!");
                System.out.println("Triangle Complete!" + '\n');
            } 
            
            catch (NumberFormatException e) {
                System.out.println('\n' + "|xxx| Command |> " + triangle + " <| Invalid Parameter |xxx|" + '\n' + "Hint: Use positive integers for sides (e.g., triangle 12 23 34)." + '\n');
                displayMessage("Invalid Parameter! Hint: Use numbers.");
            }
        } 
        
        else {
            System.out.println('\n' + "|xxx| Command |> " + triangle + " <| Invalid Command |xxx|" + '\n' + "Hint: Use format 'triangle side1 side2 side3' with positive integers." + '\n');
            displayMessage("Invalid Command!");
        }
    }
}


//-------------------------------| Requirement 5 End -------------------------------\\


//---------------------------| Requirement 2 Part 1 End |---------------------------\\

    //requirement 4 command log //2 marks
    ArrayList<String> Command_Logs = new ArrayList<>();

    public void processCommand(String command)// processes command in the Text Box
    {   
        movement movement = new movement();
        PenColour pen = new PenColour();
        triangles triangles = new triangles();

        // for comparison
        String alt = command.toString();
        // pen color checker
        String[] PenColourList = {"black", "red", "white", "blue", "pink", "green"};
        //requirement 4 save,load,etc
        String[] FileOperation = {"save", "load_img","load_cmd"};

        if(alt.equalsIgnoreCase("about"))//(5 marks) Textbox Input
        {
            System.out.println("Command Executed : " + alt);
            //LBU Turtle Graphics Version info
            about();
            System.out.println("Command Execution Complete ;) "+'\n');
            Command_Logs.add(alt);
        }

//----------------------| Requirement 2,3,4,5 |----------------------\\

        else if (alt.equalsIgnoreCase("penup"))//2 marks
        {
            System.out.println("Command Executed : " + alt);
            //Places the pen up n doesn't show movments as lines
            drawOff();
            System.out.println("Lines are now Invisible!"+'\n');
            Command_Logs.add(alt);
        }

        else if (alt.equalsIgnoreCase("pendown"))//2 marks
        {
            System.out.println("Command Executed : " + alt);
            //Places the pen down n shows movments as lines
            drawOn();
            System.out.println("Lines are now Visible!"+'\n');
            Command_Logs.add(alt);
        }

        //directional movement with parameters //3 marks left
        else if (alt.toLowerCase().startsWith("left"))//checks if it strats with a spicific word
        {   
            movement.movementCheck(alt);
            Command_Logs.add(alt);
        }

        //directional movement with parameters //3 marks right
        else if (alt.toLowerCase().startsWith("right"))//checks if it strats with a spicific word
        {   
            movement.movementCheck(alt);
            Command_Logs.add(alt);
        }

        //directional movement with parameters //3 marks forward
        else if (alt.toLowerCase().startsWith("move"))//checks if it strats with a spicific word
        {   
            movement.movementCheck(alt);
            Command_Logs.add(alt);
        }

        //directional movement with parameters //3 marks backward
        else if (alt.toLowerCase().startsWith("reverse"))//checks if it strats with a spicific word
        {   
            movement.movementCheck(alt);
            Command_Logs.add(alt);
        }

        else if (alt.equalsIgnoreCase("reset"))//1 marks
        {
            System.out.println("Command Executed : " + alt);
            //canvas reset to initial state
            reset();
            penwidth("1");
            setPenColour(java.awt.Color.red);
            Command_Logs.add(alt);
            displayMessage("Thing have been set back to default state!");
            System.out.println("Thing have been set back to default state!"+'\n');
            
        }

        else if (alt.equalsIgnoreCase("clear")) {   
            //spent 2hrs trying to make use of confirmSaveBeforeAction() method
            //was going for the effeciency route
            //tried many things but none that icould think of woked :')
            //ended up making separate joption panel for this..lol i wanna unalive myself (T_T)

            FileSave fs = new FileSave();//saves things 
            
            System.out.println("Command Executed : " + alt);
            System.out.println("Alert : This will Erase Everything! (Prior Saves Excluded)");

            //confirmSaveBeforeAction() method copy paste from above
            int response = JOptionPane.showConfirmDialog(null, 
            "Do you want to save before clearing?", "Clear Canvas", 
            JOptionPane.YES_NO_CANCEL_OPTION, 
            JOptionPane.WARNING_MESSAGE);
            
                if (response == JOptionPane.YES_OPTION) {
                    fs.manual_save("save");//added
                    clear();//added :)
                    System.out.println("Canvas Cleared after Saving!"+'\n');
                    clearInterface();
                    System.out.println("Interface Cleared after Saving!"+'\n');

                } 
                else if (response == JOptionPane.NO_OPTION) {
                    clear();//added :)
                    System.out.println("Canvas Cleared without Saving!"+'\n');
                    clearInterface();
                    System.out.println("Interface Cleared without after Saving!"+'\n');
                }
                else {
                    System.out.println("Clear operation cancelled!"+'\n');
                } 
        }

        //3 marks
        //pen color atleast 6 colors (Black , Green , Red , White , Blue , Pink)
        //compares the colours in the "PenColourList" and assigns the matching parameter
        else if (Arrays.asList(PenColourList).contains(alt.toLowerCase()))//Idea by: Me! Syntax Source by: Stack overflow (Compare String to Arrays.asList values with if statement)
        {
            pen.Colour(alt);
            Command_Logs.add(alt);
        }
        
        //image&file handeling save/load
        else if (Arrays.asList(FileOperation).contains(alt.toLowerCase())) {

            //loads image
            ImageFileHandle f = new ImageFileHandle();
            //saves file
            FileSave fs = new FileSave();

            try {
                //loads image or commands
                if (alt.equalsIgnoreCase("load_img") || alt.equalsIgnoreCase("load_cmd")) {
                    if (confirmSaveBeforeAction()) {
                        f.file(alt); //loads image
                    }
                    return;
                }
                // Save the image & commands
                else if (alt.equalsIgnoreCase("save")) { 
                    if (confirmSaveBeforeAction()){
                     fs.manual_save(alt);   
                    System.out.println('\n'+"Current Canvas Status : Save Operation Complete :)"+'\n');
                    }
                    return;
                    
                }
            }
            catch (IOException e) {
                System.out.println('\n'+"Something went wrong! :( "+'\n');
            }
        }

        //make square n make turtle walk on it
        else if(alt.toLowerCase().startsWith("square")){
           movement.movementCheck(alt);
           Command_Logs.add(alt);
        }
       
        //custom lgbt colour setup for pen
       else if (alt.toLowerCase().startsWith("rgb")){
        pen.RGB(alt);
        Command_Logs.add(alt);
       }
       
       //THICC pen ;)
       else if (alt.toLowerCase().startsWith("penwidth")){ 
        penwidth(alt);
        Command_Logs.add(alt);
       }
       
        //the illuminati
        else if (alt.toLowerCase().startsWith("triangle")) {

            String[] split_t = alt.split("[,\\.\\ ]"); //splits command

            //for equilateral
            if (split_t.length == 2) { 
            triangles.eq_triangle(alt);
            Command_Logs.add(alt);
            } 
            //for any triangle
            else if (split_t.length == 4) { 
            triangles.any_triangle(alt);
            Command_Logs.add(alt);
            } 
            //Invalid input message
            else {
            System.out.println('\n' + "|xxx| Command |> " + alt + " <| Invalid Parameter |xxx|" + '\n' + "Hint: Use 'triangle 12' for equilateral or 'triangle 3 4 5' for any triangle." + '\n');
            displayMessage("Invalid Command! Hint: triangle 12 or triangle 3 4 5."+'\n');
            }
        }

        //extras beyond requierments (10marks)
        //this shows all availavle commands that i'v added
        else if (alt.equalsIgnoreCase("help")){

            //ik icould've saved this into a txtfile n loaded it up instead but this works to..
            String helpMsg =
            "Available Commands:\n\n" +
            "about                              - Show program info\n" +
            "penup                              - Lift the pen (no drawing)\n" +
            "pendown                            - Lower the pen (draw)\n" +
            "left [0-360]                       - Turn left by angle (default 90)\n" +
            "right [0-360]                      - Turn right by angle (default 90)\n" +
            "move [0-360]                       - Move forward by pixels (default 45)\n" +
            "reverse [0-360]                    - Move backward by pixels (default 45)\n" +
            "square [0-360]                     - Draw a square (default 90)\n" +
            "triangle [0-360]                   - Draw equilateral triangle\n" +
            "triangle a b c                     - Draw triangle with sides a, b, c\n" +
            "penwidth [1-30]                    - Set pen thickness\n" +
            "rgb R G B                          - Set pen color (0-255 each)\n" +
            "black/red/white/blue/pink/green    - Set pen color\n" +
            "save                               - Save image and commands\n" +
            "load_img                           - Load saved image\n" +
            "load_cmd                           - Load and execute saved commands\n" +
            "reset                              - Reset canvas to default\n" +
            "clear                              - Clear canvas (with save prompt)\n" +
            "help                               - Show this help message\n"+
            "gg                                 - Launches A 2D Game\n";
        //console output
        System.out.println(helpMsg);
        
        //text formatting makes things look nicer! Source: Geeks for geeks
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(helpMsg);
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));

        //for independant window..  Source: Geeks for geeks
        JDialog d = new JDialog();
        d.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        d.setTitle("Help - Shows All Useable Commands ;)");
        //shows the text in formatted form
        d.getContentPane().add(textArea);
        d.setSize(610,440);
        //pops up at centre
        d.setLocationRelativeTo(null);
        d.setVisible(true);

        Command_Logs.add(alt);
        }

        else if (alt.equalsIgnoreCase("gg")){
            javax.swing.JOptionPane.showMessageDialog(this,"WASD or Arrows to Move\nDestryo White Cubes To Earn Points! :)\nWatch out for Timer!\nHit Edges Lose Points :( \nMore points = Faster you go!", "How To Play", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            Game gg = new Game();
            gg.run();
            displayMessage("Game Executed!");
            System.out.println("Game is Running...");
        }
       //Requirement 3 Part 4 Validating Commands. 15 Marks-------------------------------\\
        //Reports invalid commands not on the list(4 marks)
        else {
            //error message..
            System.out.println('\n' + "|xxx| Command |> " +alt+ " <| Unavailable |xxx|" + '\n' );
            JOptionPane.showMessageDialog(null,"|xxx| Command |> " +alt+ " <| Unavailable |xxx|", "Invalid Command", JOptionPane.ERROR_MESSAGE);
            
        }
    }
}
//---------------------------| End |---------------------------\\


public class TurtleGraphics{

    public static void main(String[] args) {
        MainFrame MF = new MainFrame();

        //remove '//' from //MF.c_command(); to execute command from console(only works once)
        //MF.c_command();        
    }
}