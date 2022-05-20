/*
 * 
 */
package PROGRAMA_03;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Bombones {
    
    //DECLARACION DE VARIABLES GLOBALES
    static HashMap<String,Integer> lista_AlumnosConBombones=new HashMap<>();
    
    public static void leerArchivoAlumnos(int n_alumnos, int n_bombones){
        File archivoAlumnos=new File("./participants.cvs");
        String nuevo_alumno;
        Random random = new Random();
        int randomBombones;
        
        try{
            RandomAccessFile RAF_archivoAlumnos=new RandomAccessFile(archivoAlumnos,"r"); 
            while(lista_AlumnosConBombones.size()<n_alumnos){
                RAF_archivoAlumnos.seek((int)(Math.random() * RAF_archivoAlumnos.length())); 
                nuevo_alumno=RAF_archivoAlumnos.readLine();
                if(nuevo_alumno.matches(".*a-zA-Z*.*") && !nuevo_alumno.contains("Nombre")){
                    randomBombones = random.nextInt(n_bombones + 1) + 1;
                    lista_AlumnosConBombones.put(nuevo_alumno, randomBombones);
                }
            }
    
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    
    public static void main(String[] args) throws NoHayBombonesParaTiException{
        int n_alumnosJuegan=0, bombonesMax=0;
        String alumno;
        Scanner teclado=new Scanner(System.in);
        
        System.out.println("¿Cuántos alumnos juegan?");
        n_alumnosJuegan=teclado.nextInt();
        System.out.println("¿Cuántos bombones máximo podrá tener cada alumno?");
        bombonesMax=teclado.nextInt();
        
        try{
            leerArchivoAlumnos(n_alumnosJuegan, bombonesMax);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } 
        
        System.out.println("--------------------------------------------");
        System.out.println("Escribe el nombre del alumno cuya información quieres ver:");
        alumno=teclado.nextLine();
        
        String array_NombreyApellido[]=alumno.split(" ");
        String nombre=array_NombreyApellido[0];
        String apellido1=array_NombreyApellido[1];
        String apellido2=array_NombreyApellido[2];
                
        if(lista_AlumnosConBombones.containsKey("\""+nombre+"\","+apellido1+" "+apellido2+"\"")){
            System.out.println("El alumno "+ alumno+" tiene "+lista_AlumnosConBombones.get("\""+nombre+"\","+apellido1+" "+apellido2+"\"")+"bombones");  
        }else{
            throw new NoHayBombonesParaTiException("Al alumno no le ha tocado ningún bombón.");
        }
        
    }

}
