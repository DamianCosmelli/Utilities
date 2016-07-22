package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAdmin 
{   
    public static String ActualDrirectory()
    {
        return System.getProperty("user.dir");
    }
    
    public static File[] FileLists(String Path)
    {   
        File files = new File(Path);
        File[] ficheros = files.listFiles();
        return ficheros;
    }
        
    public static boolean FilesCopy(File[] ArrayFiles, String Destino )
    {
        try {
            for (int i = 0; i < ArrayFiles.length; i++) 
            {     
                String FileName = ArrayFiles[i].toPath().getFileName().toString();

                String Destino2 = Destino + "\\" + FileName;

                File inFile = new File(ArrayFiles[i].toString());
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
    Logger.LogsWriter(Logger.LogsType.ERROR, e.toString());
    return false;
}        
    }
    
    public static boolean ExistPath(String PathVerify)
    {
        File fichero = new File(PathVerify);
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
            File fichero = new File(PathCreate);
            fichero.mkdirs();
            return true;
      } catch (Exception e)
      {
          Logger.LogsWriter(Logger.LogsType.ERROR, e.toString());
          return false;
      }
    }
}
