import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.*;
import com.mysql.jdbc.PreparedStatement;

public class Connection
{
	private static ArrayList<Contacts> liste;
	private static Connection conn = null;
	private static PreparedStatement statement = null;
	public static Connection get()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/contacts","root"," ");
		}
		catch(SQLException e)
		{
			System.out.println("erreur de connection: "+e);
		}
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
		
		return conn;
	}
	public static int ajouter(String nom,String prenom) throws SQLException
	{
		int i = 0;		
		try {
			
			statement = (PreparedStatement)((java.sql.Connection) conn).prepareStatement("Insert into visiteur(nom,prenom)" +
					"values("+nom+","+prenom+");");
			i = statement.executeUpdate();
			}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		liste.add(new Contacts(nom,prenom));
		((Statement) Connection.conn).close();
		return i;
	}
	public static int Supprimer(String id)
	{
		int i=0;
		try
		{
			String req="Delete From visiteur where id ="+id+";";
			i = statement.executeUpdate(req);
			Connection.conn.fermer();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return i;
	}
	public static ArrayList<Contacts> getContact()
	{ 
		liste = new ArrayList <Contacts>();
		
		try
		{
			Connection conn = Connection.get();
			Statement s = ((java.sql.Connection) conn).createStatement();
			String req = "Select * From contact";
			ResultSet res = s.executeQuery(req);
			while(res.next())
			{
				int id = res.getInt("id");
				String nom = res.getString("nom");
				String prenom = res.getString("prenom");				
				Contacts p = new Contacts(nom, prenom);
				liste.add(p);
			}
			res.close();
			s.close();
			Connection.conn.fermer();
		}
		catch(SQLException e1)
		{
			System.out.println("erreur de connection: "+e1);
		}
		return liste;
	}
	public static void fermer() throws SQLException
	{
		((Statement) get()).close();
	}
	
}