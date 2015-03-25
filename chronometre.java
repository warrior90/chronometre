import java.awt.*;
import java.awt.event.*;

public class Chronometre extends Frame implements ActionListener
{
	Button demarrer,pauser,stopper,initialiser;
	MonCanva c;
	Thread t;
	boolean stop=false;
	
	Chronometre(String s)
	{
		super(s);
		Panel p=new Panel();
		
		
		demarrer=new Button("Demarrer");
		pauser=new Button("Pauser");
		stopper=new Button("Stopper");
		initialiser=new Button("Initialiser");
		
		p.add(demarrer);
		p.add(pauser);
		
		p.add(stopper);
		p.add(initialiser);
		p.setBackground(Color.lightGray);
				
		
		c=new MonCanva();
		c.setBackground(Color.black );
		c.setSize(200,100);
		
		add(c,"Center");
		add(p,"South");
		
		setVisible(true);
		
		setSize(300,100);
		
		demarrer.addActionListener(this);
		
		pauser.addActionListener(this);
		
		stopper.addActionListener(this);
		
		initialiser.addActionListener(this);
		
		addWindowListener(new Fermer());
	}
	
	
	public void actionPerformed(ActionEvent e)
	
	{
		Button b=(Button)e.getSource();
		if(b==demarrer)
		{
			if(t==null || !t.isAlive())
			{
				if(stop == true)//dans le cas où on appuie sur le bouton stopper avant 
			
					c.dixiemeseconde=0;//initialisation du chronomètre
				
				stop=false;
				t=new Thread(c);
				t.start();
			}
			else
				t.resume();
		}
		else if(b==pauser)
		{
			if(t!=null)
				t.suspend();
		
		}
		else if(b==stopper)
		{
			if(t!=null)
			{
				t.stop();
				stop=true;
			
			}
		}
		else if(b==initialiser)
		
		{
			c.dixiemeseconde=0;
			c.repaint();
		}
	}
		
	
	public static void main(String [] args)
	{
		Chronometre cr=new Chronometre("Chronometre");
	}
}

class MonCanva extends Canvas implements Runnable
{
	long dixiemeseconde=0, millis;
		
	public void run ()
	{
		try
		{
			while (true)
			{
				// Dessine le compteur (appel indirect à la méthode paint ()),
				// puis l'augmente de 1
				repaint ();
				dixiemeseconde++;
				// Arrête le thread pendant 1/10 s (100 ms)
				Thread.sleep (100);
			}
		}
		catch (InterruptedException e) { }
	}
	
	public void paint (Graphics gc)
	{
		// Dessine le temps écoulé sous forme de hh:mm:ss:d en blanc et helvetica
		gc.setColor (Color.white);
		gc.setFont(new Font("Helvetica", Font.ITALIC, 30));
		/*gc.drawString (dixiemeseconde / 36000
		               + ":" + (dixiemeseconde / 6000) % 6 + (dixiemeseconde / 600) % 10
                   + ":" + (dixiemeseconde / 100) % 6  + (dixiemeseconde / 10) % 10
                   + ":" + dixiemeseconde % 10,
                   80, 30);*/
		millis=dixiemeseconde*100;
		
		gc.drawString (millis / 3600000
		               + ":" + (millis%3600000)/60000
                   + ":" + (millis%60000)/1000
                   + ":" + (millis % 1000)/100,
                   80, 30);
		
	}
}

class Fermer extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}

}

