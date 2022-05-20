/*
 * 
 */
package PROGRAMA_01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class aemet {
    
    //DECLARACION DE VARIABLES GLOBALES
    static Scanner teclado=new Scanner(System.in);
    static ArrayList<String> lista_TempMaximas=new ArrayList<String>();
    static ArrayList<String> lista_TempMinimas=new ArrayList<String>();
    static int num_lineas=0;
    
    public static int leerOpcionUsuario(){
        int anio_consulta=0;
        System.out.println("Introduce el año del que quiere consultar los datos meteorológicos:");
        do{
            anio_consulta=teclado.nextInt();
            if (anio_consulta<2000 || anio_consulta>2019) 
                System.out.println("No hay datos sobre el año introducido. Inténtelo de nuevo:");
        }while(anio_consulta<2000 || anio_consulta>2019);
        
        return anio_consulta;
    }
    
    public static void leerArchivo(int anio){
        //DECLARACION DE VARIABLES
        File archivo_anio=new File(".\\tenerife\\"+anio+".txt");        
        
        //LECTURA DEL ARCHIVO Y RECOGIDA DE DATOS EN LAS VARIABLES
        try{
            Scanner lector=new Scanner(archivo_anio);
             while(lector.hasNextLine()){
                lector.next();
                lista_TempMaximas.add(lector.next());
                lista_TempMinimas.add(lector.next());
                num_lineas++;
                //He quitado todas las últimas líneas vacías de los archivos porque sino me daba una NoSuchElementException.
            }
//            System.out.println(num_lineas);
            lector.close();           
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }      
    }
    
    public static double calcular_TempMaxima_Superior(){
        double temp_Maxima_Superior=0;
        for(String temp : lista_TempMaximas){
            if(Double.parseDouble(temp)>temp_Maxima_Superior){
                temp_Maxima_Superior=Double.parseDouble(temp);
            }
        }
        return temp_Maxima_Superior;
    }
    
    public static double calcular_TempMaxima_Inferior(){
        double temp_Maxima_Inferior=100;
        for(String temp : lista_TempMaximas){
            if(Double.parseDouble(temp)<temp_Maxima_Inferior){
                temp_Maxima_Inferior=Double.parseDouble(temp);
            }
        }
        return temp_Maxima_Inferior;
    }
    
    public static double calcular_TempMinima_Superior(){
        double temp_Minima_Superior=0;
        for(String temp : lista_TempMinimas){
            if(Double.parseDouble(temp)>temp_Minima_Superior){
                temp_Minima_Superior=Double.parseDouble(temp);
            }
        }
        return temp_Minima_Superior;
    }
    
    public static double calcular_TempMinima_Inferior(){
        double temp_Minima_Inferior=100;
        for(String temp : lista_TempMinimas){
            if(Double.parseDouble(temp)<temp_Minima_Inferior){
                temp_Minima_Inferior=Double.parseDouble(temp);
            }
        }
        return temp_Minima_Inferior;
    }
    
    public static double calcular_TempMaxima_Media(){
        double sumaTemp_Maximas=0;
        for(String temp : lista_TempMaximas){
            sumaTemp_Maximas+=Double.parseDouble(temp);
        }
        return sumaTemp_Maximas/num_lineas;
    }
    
    public static double calcular_TempMinima_Media(){
        double sumaTemp_Minimas=0;
        for(String temp : lista_TempMinimas){
            sumaTemp_Minimas+=Double.parseDouble(temp);
        }
        return sumaTemp_Minimas/num_lineas;
    }
    
    public static double calcular_TempIntermedia(){
        double sumaTemp_Maximas=0;
        double sumaTemp_Minimas=0;
        
        for(String temp : lista_TempMaximas){
            sumaTemp_Maximas+=Double.parseDouble(temp);
        }
        
        for(String temp : lista_TempMinimas){
            sumaTemp_Minimas+=Double.parseDouble(temp);
        }
        return ((sumaTemp_Maximas/num_lineas)+(sumaTemp_Minimas/num_lineas))/2;
    }
    
    public static double calcular_DiferenciaMayor_Temp(){
        double tempMax, tempMin;
        double diferenciaMayor=0;
        for(int i=0; i<num_lineas; i++){
            if((Double.parseDouble(lista_TempMaximas.get(i))- Double.parseDouble(lista_TempMinimas.get(i))) > diferenciaMayor){
                diferenciaMayor= Double.parseDouble(lista_TempMaximas.get(i))- Double.parseDouble(lista_TempMinimas.get(i));
            }
        }
        return diferenciaMayor;
    }
    
    public static void main(String[] args) {
        
        //LLAMADA A FUNCIONES PARA LEER OPCIÓN DEL USUARIO Y LEER ARCHIVO CORRESPONDIENTE
        try{
            leerArchivo(leerOpcionUsuario()); 
        }catch(NumberFormatException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }        
        
        //TEMPERATURA MAX. MÁS ALTA Y MÁS BAJA
        System.out.println("Tº Máxima más alta y más baja: "+calcular_TempMaxima_Superior()+"  ---  "+calcular_TempMaxima_Inferior());
        System.out.println("*****************************************************************");
        
        //TEMPERATURA MÍN. MÁS ALTA Y MÁS BAJA
        System.out.println("Tº Mínima más alta y más baja: "+calcular_TempMinima_Superior()+"  ---  "+calcular_TempMinima_Inferior());
        System.out.println("*****************************************************************");
        
        //TEMPERATURA MAX. MEDIA
        System.out.println("Tº Máxima media: "+calcular_TempMaxima_Media());
        System.out.println("*****************************************************************");
        
        //TEMPERATURA MÍN. MEDIA
        System.out.println("Tº Mínima media: "+calcular_TempMinima_Media());
        System.out.println("*****************************************************************");
        
        //TEMPERATURA INTERMEDIA
        System.out.println("Tº Intermedia: "+calcular_TempIntermedia());
        System.out.println("*****************************************************************");
        
        //DÍA DE MAYOR DIFERENCIA TEMPERATURA MÁX Y MÍN
        System.out.println("Tº del día de Mayor Diferencia entre Máxima y Mínima: "+calcular_DiferenciaMayor_Temp());
        System.out.println("*****************************************************************");
        
    }
    
}
