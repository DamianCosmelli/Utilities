package Utilities;
import java.sql.*;

/**
 * @author Bishops
 * Clase abstracta que implementada facilita la conecion y consulta a 
 * Base de Datos MySQL o MariaDB
 */

public abstract class DBConnect 
{
    private Connection connect = null;
    private Statement command = null;
    private ResultSet register = null;

    //metodo abstracto
    public abstract String ToTextResult();
    
    // set y get de Comando //
    public Statement getCommand() 
    {
        return command;
    }

    public void setCommand(Statement command) 
    {
        this.command = command;
    }
    
    // set y get de Registro //
    public ResultSet getRegister() 
    {
        return register;
    }

    public void setRegister(ResultSet register) 
    {
        this.register = register;
    }
    
    // get de Connexion //
    public Connection getConexion() 
    {
        return connect;
    }
    
    //contructor por defecto
    public DBConnect(){}
    
    // Metodo que establece la conexion 
    public boolean Connection (String host,String port, String pass, String user, String DB) 
    {
        try 
        {
            //Driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
            //Nombre del servidor. localhost:3306 es la ruta y el puerto de la conexión MySQl
            String servidor = "jdbc:mysql://"+host+":"+port+"/"+DB;
            
            //Se inicia la conexión
            connect = DriverManager.getConnection(servidor, user, pass);
            return true;
        } catch (Exception e) 
        {
            Loggers.LogsWriter(Loggers.LogsType.ERROR, e.getMessage());
            return false;
        }
    }
    // desconecta DB
    public boolean Disconect()
    {
        try {
            this.getConexion().close();
            return true;
        } catch (Exception e) 
        {
            Loggers.LogsWriter(Loggers.LogsType.ERROR, e.getMessage());
            return false;
        }
    }
    
    // Realiza Consulta SQL
    public boolean Query(String yourQuery)
    {
        try 
        {
            //prepara comoando
            setCommand(this.getConexion().createStatement());
            //ejecuta yourQuery
            setRegister(this.getCommand().executeQuery(yourQuery));
            return true;
        }
        catch (SQLException exSql)
        {
            Loggers.LogsWriter(Loggers.LogsType.ERROR, exSql.getMessage());
           return false;
        } 
        catch (Exception e) 
        {
           Loggers.LogsWriter(Loggers.LogsType.ERROR, e.getMessage());
           return false;
        }
    }
    
    // Muestra resultado de la busqueda de los campos establecidos
    
    public void ViewResult(String[] fields) throws SQLException
    {
        int NumFields = fields.length;
        
        while (this.getRegister().next()) 
        {
            String value = "";
            if (NumFields < 2)
            {
                System.out.println("Palabra: " + this.getRegister().getString(fields[0]));
            } 
            else
            {
                for (int i = 0; i < fields.length; i++) 
                {
                    value = value +" "+ this.getRegister().getString(fields[i]);
                }
                System.out.println("Palabra: "+value);              
            
            }
            
        }
    }
    
   
}
