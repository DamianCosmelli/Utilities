package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReaderConfig 
{
    private Properties Config;
    
    public ReaderConfig(String FileConfigPath)
    {
        try 
        {
        /**Creamos un Objeto de tipo Properties*/
         Config = new Properties();

         /**Cargamos el archivo desde la ruta especificada*/
         Config.load(new FileInputStream(SOPath.IsSOLinux(FileConfigPath)));
        } catch (Exception e)
        {
            Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
        }        
    }
    // devuelve la propiedad seteada en el archivo ".properties"
    public String getSpecificProperties(String properties)
    {
        return Config.getProperty(properties);
    }      
}
