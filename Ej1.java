import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Ej1 {
	public static Connection conexion;

	public static void main(String[] args) {
		boolean salir = false;
		abrirConexion();
		while (salir == false){
			String cadena = "Elija una accion:\n1 - Crear Base de datos\n2 - Crear Tablas\n3 - Añadir Contenido\n0 - Salir";
			Integer eleccion=Integer.parseInt(JOptionPane.showInputDialog(cadena));
			if (eleccion == 1) {
				crearBD();
			}else if(eleccion==0) {
				salir =true;
			} else if(eleccion==2) {
				CrearTablas();
			} else if(eleccion==3) {
				AñadirContenido();
			}else {
				JOptionPane.showInternalMessageDialog(null, "NO EXISTE LA OPCION");
			}	
		}
		
	}
	
	//METODO CONECTARSE A LA BASE DE DATOS
	public static void abrirConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion=DriverManager.getConnection("jdbc:mysql://192.168.1.130:3306?useTimezone=true&serverTimezone=UTC","remote","Dr123456");//credenciales temporales
			System.out.print("Server Connected");
			fecha();
		}catch(SQLException | ClassNotFoundException ex ){
			System.out.print("No se ha podido conectar con mi base de datos");
			fecha();
			System.out.println(ex.getMessage());	
		}
		
	}
	
	//METODO CREAR BASE DE DATOS
	public static void crearBD() {
		try {
			
			String NombreBD = JOptionPane.showInputDialog("Dame el Nombre de la base de datos");
			String Query="CREATE DATABASE IF NOT EXISTS "+ NombreBD;
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Error creando la DB.");
		}	
		
	}
	
	//METODO CREAR TABLAS
	public static void CrearTablas() {
		try {
			String db = JOptionPane.showInputDialog("Dame el Nombre de la base de datos que usaras");
			String Querydb = "USE "+db;
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			String NumTabla = JOptionPane.showInputDialog("Cuantas tablas vas a crear");
			int NumTablas = Integer.parseInt(NumTabla);
			for (int cont = 0;  cont < NumTablas; cont++) {
				String NomTabla = JOptionPane.showInputDialog("Dame el nombre de la tabla");
				String NumEntidade = JOptionPane.showInputDialog("Cuantas entidades va ha tener");
				int NumEntidades = Integer.parseInt(NumEntidade);
				String total = "";
				for (int contE = 0;  contE < NumEntidades; contE++) {
					String NomEntidades = JOptionPane.showInputDialog("Dame el nombre de la entidad y si es forana el siguiente campo dejalo vacio");
					String TipoEntidad = JOptionPane.showInputDialog("Dame el tipo de la entidad");
					total += (NomEntidades+" "+TipoEntidad)+",";
					
				}
				total = total.substring(0, total.length()-1);

				String Query = "CREATE TABLE IF NOT EXISTS "+ NomTabla+" ("+
						total+");";
					Statement st= conexion.createStatement();
					st.executeUpdate(Query);
					System.out.println("Tabla creada con exito!");
				
				
			}
			
		}catch (SQLException ex){
			System.out.println(ex.getMessage());
			System.out.println("Error creando tabla.");
			
		}
		
	}
	
	//AÑADIR CONTENIDO
	public static void AñadirContenido(){
		
		try {
			String db = JOptionPane.showInputDialog("Dame el Nombre de la base de datos que usaras");
			String Querydb = "USE "+db;
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			String nomtabla = JOptionPane.showInputDialog("Que tabla usaras");
			String NomEntidades = "";
			String entidades = JOptionPane.showInputDialog("Cuantas entidades tiene la tabla");
			int CantEntidades= Integer.parseInt(entidades);
			ArrayList ContenidoEntidades = new ArrayList();
			for (int x = 0; x < CantEntidades;x++) {
				String Entidades = JOptionPane.showInputDialog("Nombre entidades");
				NomEntidades += Entidades+",";
			}
			NomEntidades = NomEntidades.substring(0, NomEntidades.length()-1);
			for (int x = 0; x < CantEntidades;x++) {
				String ContEntidades = JOptionPane.showInputDialog("Contenido entidades");
				
				
				ContenidoEntidades.add(ContEntidades);
				System.out.print(ContenidoEntidades);
			}
			CantEntidades-=1;
			for (int a = 0; a < CantEntidades; ) {
				String Query = "INSERT INTO " + nomtabla +  "("+NomEntidades+")" + "VALUE("
						+ "\"" + ContenidoEntidades.get(a) + "\"); ";
				Statement st = conexion.createStatement();;
				st.executeUpdate(Query);
				a+=1;
			}
					
		} catch (SQLException ex ) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error en el almacenamiento");
		}
		
	
		
	}
	
	//METODO MOSTRAR FECHA
	public static void fecha() {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		System.out.println(" - " + hourdateFormat.format(date));
		}

}
