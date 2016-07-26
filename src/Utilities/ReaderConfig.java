package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReaderConfig 
{
    private Properties Config;
    
    public ReaderConfig(String FileConfig)
    {
        try 
        {
        /**Creamos un Objeto de tipo Properties*/
         Config = new Properties();

         /**Cargamos el archivo desde la ruta especificada*/
         Config.load(new FileInputStream(FileAdmin.ActualDrirectory()+"\\src\\"+FileConfig));
        } catch (Exception e)
        {
            Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
        }        
    }
    
    public String getMethodo() 
    {                
        return Config.getProperty("methodo");
    }
    
    public String getQuery() 
    {                
        return Config.getProperty("query");
    }


       
}
