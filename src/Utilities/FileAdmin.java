package Utilities;

/**
 *
 * @author Bishops
 * Trabaja en la creacion de archivos.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileAdmin 
{   
    public static String ActualDrirectory()
    {
        return System.getProperty("user.dir");
    }
    
    public static File[] FileLists(String Path)
    {   
        File files = new File(SOPath.IsSOLinux(Path));
        File[] ficheros = files.listFiles();
        return ficheros;
    }
        
    public static boolean FilesCopy(File[] ArrayFiles, String Destiny )
    {
        try {
            for (int i = 0; i < ArrayFiles.length; i++) 
            {     
                String FileName = ArrayFiles[i].toPath().getFileName().toString();

                String Destino2 = Destiny + "\\" + FileName;
                Destino2 = SOPath.IsSOLinux(Destino2);

                File inFile = new File(SOPath.IsSOLinux(ArrayFiles[i].toString()));
                File outFile = new File(Destino2);

                FileInputStream in = new FileInputStream(inFile);
                FileOutputStream out = new FileOutputStream(outFile);

                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) 
                    {
                      out.write(buf, 0, len);
                    }

                in.close();
                out.close();
            }
            return true;
} catch(IOException e) 
{
    Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
    return false;
}        
    }
    
    // Verifica la existencia de un archivo. Devuelve true si este existe. 
    public static boolean ExistPath(String PathVerify)
    {
        File fichero = new File(SOPath.IsSOLinux(PathVerify));
        if (fichero.exists())
        {
            return true;
        }
        else{ return false;}
        
    }
    
    public static boolean CreteDirectory(String PathCreate)
    {
      try
      {
            File fichero = new File(SOPath.IsSOLinux(PathCreate));
            fichero.mkdirs();
            return true;
      } catch (Exception e)
      {
          Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
          return false;
      }
    }
    
    public static void FileReader(String PathFile) 
    {
      try 
      {
        String LineString;
        FileReader fr = new FileReader(SOPath.IsSOLinux(PathFile));
        BufferedReader br = new BufferedReader(fr);
               
        while((LineString = br.readLine())!=null) 
        {          
          System.out.println(LineString);                     
                        
        }                      
        br.close();
        
      }catch(Exception e)
      {
          Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
      }   
      
    }
    
    public static void FileWriter(String PathFile, String FileName, boolean Condition, String message) 
    {
      try 
      {
                
        /*
        * se verifica existencia de la ruta y si no existe se crea
        * Tambien reescribe el path si el sistema es linux
        */
          
        if(!FileAdmin.ExistPath(PathFile))
        {
           if(!FileAdmin.CreteDirectory(PathFile))
           {
               throw new Exception("Error al crear directorio");
           }
        }
        
        /*
        * Se crea el archivo especificado en el path declarado, 
        * si el archivo existe se abre y adiciona el texto (condicion true)
        * si el archivo no existe se crea en el path especificado (condicion false)
        */
                
        String PathFinal = PathFile+FileName;
        // reescribe el path si el sistema es linux
        PathFinal = SOPath.IsSOLinux(PathFinal);
        
        FileWriter fw = new FileWriter(PathFinal, Condition);
        BufferedWriter bw = new BufferedWriter(fw);
        
            //System.out.println(PathFinal);
            bw.newLine();
            bw.append(message);
            bw.close(); 
                              
      }catch(Exception e)
      {
          Loggers.LogsWriter(Loggers.LogsType.ERROR, e.toString());
      }   
      
    }
    
    //crea un filename iniciado con la fecha actual yyMMdd_name
    public static String CreateFileName(String Name,String Extension)
    {
       SimpleDateFormat formateador = new SimpleDateFormat("yyMMdd_", new Locale("es_ES"));
       Date fechaDate = new Date();
       String fecha = formateador.format(fechaDate);

       return fecha+Name+"."+Extension;
    } 
    
    //elimina el archivo pasado por parametro
    public static boolean DeleteFile(String filepath)
    {
        File root = new File( filepath );
        return root.delete();
       
    }
}
