import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;

import com.mysql.jdbc.PreparedStatement;


public class Fenetre extends JFrame implements ActionListener
{	
	private ArrayList<Contacts> liste;
	
	int compteur = 0;
	private JTextField nom1;
	private JTextField prenom1;
	private JButton b_affprec;
	private JButton b_affsuiv;
	private JButton b_quit;
	
	public Fenetre (String titre, int largeur, int longueur)
	{
		super(titre);
		liste = new ArrayList<Contacts>();
		
		JPanel panel1 = new JPanel();
		JLabel nom = new JLabel ("nom");
		nom1 = new JTextField (15);
		panel1.add(nom);
		panel1.add(nom1);
		
		JPanel panel2 = new JPanel();
		JLabel prenom = new JLabel ("prenom");
		prenom1 = new JTextField (15);
		panel2.add(prenom);
		panel2.add(prenom1);

		
		JPanel panel5 = new JPanel();
		JButton b_ajout = new JButton("Ajouter");
		JButton b_quit = new JButton("Quitter");

		b_affsuiv = new JButton("suivant");
		panel5.add(b_ajout);
		panel5.add(b_quit);
		panel5.add(b_affsuiv);
		
		b_ajout.addActionListener(this);
		b_quit.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel5);
		this.add(panel);
		
		this.setLayout(new GridLayout(3,1));
		this.getContentPane().add(panel);
	
		this.setTitle(titre); // Donner un titre � la fen�tre
		
		/* La m�thode setLocationRelativeTo permet de d�terminer le positionnement de la fen�tre. Si
		le param�tre est null, la fen�tre sera centr�e.*/
		this.setLocationRelativeTo(null);
		
		// Fermeture de la fen�tre lorsque l'on clique sur la croix (sinon la fen�tre sera ferm�e mais le programme toujours en cours d'ex�cution)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* La m�thode setSize permet de d�terminer la taille. Les deux param�tres correspondent
		respectivement � la largeur puis � la hauteur, le tout exprim� en pixels.*/
		this.setSize(largeur, longueur);
		this.setResizable(false); // On interdit le redimensionnement de la fen�tre
		this.setVisible(true); // Par d�faut la fen�tre est invisible. Derni�re instruction du constructeur
	}
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "Quitter")
		{
			System.exit(0);
		}
		else
		{
			if(e.getActionCommand() == "Ajouter")
			{
				String nom = nom1.getText();
				String prenom = prenom1.getText();
				Connection conn;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn =  DriverManager.getConnection("jdbc:mysql://localhost/contacts","root","");
					PreparedStatement statement = (PreparedStatement) conn.prepareStatement("INSERT INTO visiteur (nom,prenom) values (?,?);");
					statement.setString(1,nom);
					statement.setString(2,prenom);
					int res = statement.executeUpdate();
					statement.close();
					conn.close();
				} 
				catch(SQLException e1)
				{
					System.out.println("erreur de connection: "+e1);
				}
				catch(ClassNotFoundException e2)
				{
					System.out.println("Erreur de chargement du driver: "+e2);
				}
				nom1.setText("");
				prenom1.setText("");
			}
			else
			{
				if(e.getActionCommand() == "Suivant")
				{
					Connection conn;
					try {
						Class.forName("com.mysql.jdbc.Driver");
						conn =  DriverManager.getConnection("jdbc:mysql://localhost/contacts","root","");
						PreparedStatement statement = (PreparedStatement) conn.prepareStatement("Select * from visiteur where id = ?");// afficher le contenu de la base en utilisant un compteur pour comparer les 'id' et afficher les infos dans les JTextFields
						statement.setInt(1,compteur);
						statement.executeQuery();
						
						statement.close();
						conn.close();
						compteur++;
					} 
					catch(SQLException e1)
					{
						System.out.println("erreur de connection: "+e1);
					}
					catch(ClassNotFoundException e2)
					{
						System.out.println("Erreur de chargement du driver: "+e2);
					}
				}
			}
		}
	}
}
