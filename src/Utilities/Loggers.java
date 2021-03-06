package Utilities;

/**
 *
 * @author Bishops
 * Crea archivos logs, tanto para error como para actividades realizadas
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.text.SimpleDateFormat;


public class Loggers 
{        
   public enum LogsType 
   {
       ACTIVITY,
       ERROR
   }
    
    public static void LogsReader(String PathLog) 
    {
      try 
      {
        String cadena;
        FileReader f = new FileReader(SOPath.IsSOLinux(PathLog));
        BufferedReader b = new BufferedReader(f);
        int inicial = 0; //linea inicial 
        int cont = 0;     
        
        int cant = (int) b.lines().count();
        b.close();
        
        String lineas[] = new String[cant];
                
        FileReader g = new FileReader(SOPath.IsSOLinux(PathLog));
        BufferedReader c = new BufferedReader(g);
               
        while((cadena = c.readLine())!=null) 
        {          
                if (cont > inicial) 
                {
                    lineas[cont] = cadena;
                    System.out.println(" line "+lineas[cont] +" cont "+ cont);                     
                }
                cont++;           
        }  
                       
        c.close();        
      }catch(Exception e)
      {
          System.out.println(e.getMessage());
      }   
      
    }   
   
     public static void LogsWriter(LogsType ClassLog, String message) 
    {
      try 
      {
        // se establece la ruta de almacenamiento de Logs
        String PathLog = FileAdmin.ActualDrirectory()+"\\Logs";
        PathLog = SOPath.IsSOLinux(PathLog);
        
        //se verifica existescia de la ruta y si no existe se crea
        if(!FileAdmin.ExistPath(PathLog))
        {
           if(!FileAdmin.CreteDirectory(PathLog))
           {
               throw new Exception("Error al crear directorio");
           }
        }
        
        // Se establece el nombre del archivo del log
        PathLog = SOPath.IsSOLinux(PathLog+"\\"+LogsFileName());
        
        FileWriter fw = new FileWriter(PathLog, true);
        BufferedWriter fb = new BufferedWriter(fw);
        
        if(ClassLog == LogsType.ACTIVITY)
        {
            System.out.println(PathLog);
            fb.newLine();
            fb.append("[ ACTIVIDAD ] - "+ LogsDateTimeFormat() +" - "+ message);
            fb.close(); 
            
        } else if(ClassLog == LogsType.ERROR)
        {
            fb.newLine();
            fb.append("[ ERROR ] - "+ LogsDateTimeFormat() +" - "+ message);
            fb.close(); 
        }
                   
      }catch(Exception e)
      {
          System.out.println(e.getMessage());
      }   
      
    } 
     
     public static String LogsFileName()
    {
       SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd", new Locale("es_ES"));
       Date fechaDate = new Date();
       String fecha = formateador.format(fechaDate);

       return fecha+".log";
    } 
        
     public static String LogsDateTimeFormat()
    {
       SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", new Locale("es_ES"));
       Date fechaDate = new Date();
       String fecha = formateador.format(fechaDate);

       return fecha;
    } 
}
