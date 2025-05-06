/* 

i dont have enought time to make a full fleged game but i'll set up the basics without external libraries

    what the game is about?(the bare minimums)
    - a square 2d block                             --done
    - has x min time limit                          --done
    - goal collect the things that spawn randomly   --done
    - those things despawn in 5 second              --done
    - score board?                                  --done
    - colission sound???                            --done
    - Background sound??????????                    --done

    Task before the x min timer runs 0 collect as much as you can

this ideas sounds like someone came up in a matter of 20 sec while staring at the ceiling.. which it is lol
my initial plan was to use an external library 'LWJGL' for a 3d rendered environment but set up + time constrains made it seem quite not possible
so heres what im able to whip up in just 20 hrs..

after compeleting the bare minimum for this game project..
I'll be honest i'v had more fun making this game vs writing the entire TurtleGraphics java programm worth 90 marks..
*/
package AssignmentTurtleGraphicsProgram;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;

//----------------------| Req Beyond (10mark) |----------------------\\

//player stat
class player extends Rectangle {   
        //player spawn location n co-ordinates and attributes
        public int playerX = 640, playerY = 360,velx =0, vely=0, sizeW=40, sizeH=40;

        //directional movement
        //higher value = faster speed
        //calls this when pressed up arrow key
        //moves on negative y axis aka up
        public void up(int move){
            vely = -move;
            velx = 0;
        }

        //calls this when pressed up arrow key
        //moves on positive y axis aka down
        public void down(int move){
            vely = move;
            velx = 0;
        }

        //calls this when pressed left arrow key
        //moves on negative x axis aka left
        public void left(int move){
            vely = 0;
            velx = -move;
        }

        //calls this when pressed right arrow key
        //moves on positive x axis aka right
        public void right(int move){
            vely = 0;
            velx = move;
        }

}

//things players can pick up
class collectables{
    //this to spawn collectables at random locations.. within the set rasolution
    Random r = new Random();
    public int cX = r.nextInt(0,1280), cY = r.nextInt(0,720),velx =0, vely=0, c_sizeW=40, c_sizeH=40;
}
// bg sound
class audio{
    //importing audio
    //source BroCode on Youtube
    void aud_play(String path) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//drawing player and other things on screen
//action key listner Source: Youtube By 'macheads101'
class GamePanel extends JPanel implements ActionListener, KeyListener {
    
    audio sound = new audio();
    collectables ctb = new collectables();
    player player = new player();

    //timer for action listner
    Timer t = new javax.swing.Timer(5, this);

    //default game parameters.. i set it up
    int timeLeft = 50, ctb_Despawn_time=5, score=0, ctb_Total=0, ctb_caught=0,player_spd=8,wall_collission=0; 

    Timer gameT,collectablesT;
    
    //key listner
    //mainly for movements and animations + logics
    public GamePanel(){

        //gackground audio
        sound.aud_play("AssignmentTurtleGraphicsProgram/Game_SFX/BGM.wav");
        //starts playr movement
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //game timer controls everything prettymuch if it stops everything stops
        //timer wont update if i use regualr if else loop
        gameT = new Timer(1000, e -> {

            timeLeft--;
            repaint();
            if (timeLeft <= 0) {
            gameT.stop();
            // stop movement too
            t.stop();
            //stops the 5 second timer loop
            collectablesT.stop();
            //final score popup
            String stats = "            Game Over!\n\n"+ "Score: " + score + "\n"+ "Enemy Spotted: " + ctb_Total + "\n"+ "Enemy Eliminated: " + ctb_caught + "\n"+ "Player Speed: " + player_spd + "\n"+ "Edge Collision: " + wall_collission + "\n";
            javax.swing.JOptionPane.showMessageDialog(this, stats, "Game Stats", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gameT.start();

        //collectables despawn timer
        collectablesT = new Timer(1000, e -> {
            ctb_Despawn_time--;
            repaint();
            if (ctb_Despawn_time == 0) {

                System.out.println("Enemy Has Teleported!");
                //despawns/ makes the collectable decay if not picked up
                ctb.cX = ctb.r.nextInt(0, getWidth() - ctb.c_sizeW);
                ctb.cY = ctb.r.nextInt(0, getHeight() - ctb.c_sizeH);
                ctb_Despawn_time = 5;
                //total enemy spotted
                ctb_Total+=1;
            }
        });
        //restarts the timer
        collectablesT.start();
        
        //collectables respanner  
    }

    //changes the player position based on value of velx n vely
    public void actionPerformed(ActionEvent e) {
        repaint();
        //continious movement in pointed direction
        player.playerX += player.velx;
        player.playerY += player.vely;


        //set player &collctables boundry to avoid cube from disappering into the void lol..
        //source : YouTube By BroCode
        if (player.playerX<0) {
            player.playerX = 0;
        }
        if (player.playerY<0) {
            player.playerY = 0;
        }
        //for collectables
        if (ctb.cX<0) {
            ctb.cX = 0;
        }
        if (ctb.cY<0) {
            ctb.cY = 0;
        }

        //pulls the current panelsize/ window resolution  if player cords is beyond x or y of screen repaints the player into static
        //cannot figure out y it doesnt work for up and above.
        //wall border for right side 
        if (player.playerX > getWidth() - 40) {
            player.playerX = getWidth() - 40;
            //wallcollision to reduce the score by x amount ;)
            wall_collission+=1;
            score-=wall_collission;
            System.out.println("Edge Collision Detected!! Score Points Lost!!");
        }
        //wall border for bottom side
        if (player.playerY > getHeight() - 40) {
            player.playerY = getHeight() - 40;
            //wallcollision to reduce the score by x amount ;)
            wall_collission+=1;
            score-=wall_collission;
            System.out.println("Edge Collision Detected!! Score Points Lost!!");
        }

        // collission detection source : YouTube By BroCode
        // Collision detected: respawn collectable at random position
        Rectangle playerRect = new Rectangle(player.playerX, player.playerY, player.sizeW, player.sizeH);
        Rectangle collectRect = new Rectangle(ctb.cX, ctb.cY, ctb.c_sizeW, ctb.c_sizeH);
        
        if (playerRect.intersects(collectRect)) {
            //this erases the object
            ctb.cX = ctb.r.nextInt(0, getWidth() - ctb.c_sizeW);
            ctb.cY = ctb.r.nextInt(0, getHeight() - ctb.c_sizeH);
            //score & ctb_cought value update
            score= score+ 150;
            //totaal enemy caught
            ctb_caught += 1;
            //spotted enemy
            ctb_Total+=1;
            //speed increase
            player_spd+=1;
            
            //play audio when enemy eleminated
            if(ctb_caught<=5){
                sound.aud_play("AssignmentTurtleGraphicsProgram/Game_SFX/V_1E.wav");
            }
            else if(ctb_caught<=10){
                sound.aud_play("AssignmentTurtleGraphicsProgram/Game_SFX/V_2E.wav");
            }
            else if(ctb_caught<=15){
                sound.aud_play("AssignmentTurtleGraphicsProgram/Game_SFX/V_3E.wav");
            }
            else if(ctb_caught<=20){
                sound.aud_play("AssignmentTurtleGraphicsProgram/Game_SFX/V_4E.wav");
            }
            else if(ctb_caught>=25){
                sound.aud_play("AssignmentTurtleGraphicsProgram/Game_SFX/V_5E.wav");
            }
            else {
                //meow
            }

        }
        //respawns or prints the object
        repaint();
}

    //keybindings for player movement
    public void keyPressed(KeyEvent e){
        //basically it checks which key has been pressed..
        //all keys on keyboard are assigned to unique code..
        int code = e.getKeyCode();

        //condition if i press certain key..
        //universal moevment key is WASD or arrows key so i'll assign it to those for movements
        
        //move up 'W' or 'UP Arrow' Keys
        if (code==KeyEvent.VK_W || code==KeyEvent.VK_UP){
            player.up(player_spd);
        }

        //move down 'S' or 'Down Arrow' Keys
        if (code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN){
            player.down(player_spd);
        }

        //move left 'A' or 'left Arrow' Keys
        if (code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT){
            player.left(player_spd);
        }

        //move right by 
        if (code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT){
            player.right(player_spd);
        }
    }

    //render 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Draws player
        g.setColor(java.awt.Color.CYAN);
         //player character attributes
        g.fillRect(player.playerX, player.playerY, player.sizeW, player.sizeH);

        //draws timer and other UI elements
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 20));
        g.drawString("Time: " + timeLeft, 20, 40);
        g.drawString("Score: "+ score,20,80);
        g.drawString("Enemy Spotted: "+ ctb_Total,20,120);
        g.drawString("Enemy Eleminated: "+ ctb_caught,20,160);
        g.drawString("Player Speed: "+ player_spd,20,200);
        //this kinda broken atm
        g.drawString("Edge Collission: "+ wall_collission,20,240);

        //draws collectables which players can collect
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(ctb.cX, ctb.cY, ctb.c_sizeW, ctb.c_sizeH);
    }
    
    //just place holder otherwise the code wont compile (cz must implement these says the IDE)
    //key typed 
    public void keyTyped(KeyEvent e) {}
    //key released
    public void keyReleased(KeyEvent e){}
}

public class Game 
{       

        void run()//(10 marks)
        {   
            
            JFrame f = new JFrame();
            GamePanel jp = new GamePanel();
            
            f.setSize(1280,720);
            f.setVisible(true);
            //closes this game window only
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
            //just waits for window to close n executes command Source:Geeks For Geeks
            f.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    System.out.println("Game window closed!\n");
                }
            });     
            f.setTitle("Box Game :)");
            f.add(jp);
            //game window location centre
            f.setLocationRelativeTo(null);

            
            //panel setup
            jp.setBackground(java.awt.Color.BLACK);
            jp.setVisible(true);
            jp.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED));

        }

        //---------------------------| End |---------------------------\\

}