package Utilities;

/**
 *
 * @author Bishops
 * Clase abstracta para la conexion de Bases de Datos MariaDB.
 */
public abstract class DBConnector 
{
    protected String pHost;
    protected String pPort;
    protected String pUser;
    protected String pPass;
    
    public abstract boolean Connect();
    public abstract boolean Disconnect();
    
    public String[] SelectResult(String[] Fields, String[] Conditions)
    {
        return null;
    }
    public boolean InsertProcess(String[] Fields, String[] Values)
    {
        return true;
    }
}
