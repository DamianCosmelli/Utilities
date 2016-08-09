package Utilities;

/**
 *
 * @author Bishops
 * Evalua si el SO es Linux o Windows
 */
public class SOPath 
{
   /* 
    * Evalua si el SO es Linux, de ser asi convierte el path al sistema de linux
    * remplazando "\\" por "/"
    */
        
    public static String IsSOLinux(String Path)
    {
        String sSO = System.getProperty("os.name").toUpperCase();
 
        if(sSO.contains("LINUX"))
        {
            return Path.replace("\\", "/");
        }
        return Path;
    }
    
}
