/*
 * 
 */
package PROGRAMA_01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class aemet {

    //DECLARACION DE VARIABLES GLOBALES
    static Scanner teclado = new Scanner(System.in);
    static ArrayList<Double> lista_TempMaximas = new ArrayList<Double>();
    static ArrayList<Double> lista_TempMinimas = new ArrayList<Double>();
    static int num_lineas = 0;

    public static int leerOpcionUsuario() {
        int anio_consulta = 0;
        System.out.println("Introduce el año del que quiere consultar los datos meteorológicos:");
        do {
            anio_consulta = teclado.nextInt();
            if (anio_consulta < 2000 || anio_consulta > 2019) {
                System.out.println("No hay datos sobre el año introducido. Inténtelo de nuevo:");
            }
        } while (anio_consulta < 2000 || anio_consulta > 2019);

        return anio_consulta;
    }

    public static void leerArchivo(int anio) {
        //DECLARACION DE VARIABLES
        File archivo_anio = new File(".\\tenerife\\" + anio + ".txt");

        //LECTURA DEL ARCHIVO Y RECOGIDA DE DATOS EN LAS VARIABLES
        try {
            Double diferenciaMaxima = 0.0;
            Scanner lector = new Scanner(archivo_anio);
            while (lector.hasNextLine()) {
                String fecha = lector.next();
                lista_TempMaximas.add(lector.nextDouble());
                lista_TempMinimas.add(lector.nextDouble());
                num_lineas++;
                //He quitado todas las últimas líneas vacías de los archivos porque sino me daba una NoSuchElementException.
            }
//            System.out.println(num_lineas);
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static double calcularTemperaturaMaxima(ArrayList<Double> lista) {
        //Tomo el primer elemento del ArrayList como máximo temporal
        double temp_Maxima = lista.get(0);
        for (Double temperatura : lista) {
            if (temperatura > temp_Maxima) {
                temp_Maxima = temperatura;
            }
        }
        return temp_Maxima;
    }

    public static double calcularTemperaturaMinima(ArrayList<Double> lista) {
        double temp_Maxima = 100;
        for (Double temperatura : lista) {
            if (temperatura < temp_Maxima) {
                temp_Maxima = temperatura;
            }
        }
        return temp_Maxima;
    }

//    public static double calcular_TempMinima_Superior(){
//        double temp_Minima_Superior=0;
//        for(String temp : lista_TempMinimas){
//            if(Double.parseDouble(temp)>temp_Minima_Superior){
//                temp_Minima_Superior=Double.parseDouble(temp);
//            }
//        }
//        return temp_Minima_Superior;
//    }
//    
//    public static double calcular_TempMinima_Inferior(){
//        double temp_Minima_Inferior=100;
//        for(String temp : lista_TempMinimas){
//            if(Double.parseDouble(temp)<temp_Minima_Inferior){
//                temp_Minima_Inferior=Double.parseDouble(temp);
//            }
//        }
//        return temp_Minima_Inferior;
//    }
    public static double calcularTemperaturaMedia(ArrayList<Double> lista) throws NullPointerException {
        if (lista.isEmpty()) {
            throw new NullPointerException("La lista de temperaturas está vacía");
        }
        double sumaTemperaturas = 0;
        for (Double temperatura : lista) {
            sumaTemperaturas += temperatura;
        }
        return sumaTemperaturas / lista.size();
    }

//    public static double calcular_TempMinima_Media(){
//        double sumaTemp_Minimas=0;
//        for(String temp : lista_TempMinimas){
//            sumaTemp_Minimas+=Double.parseDouble(temp);
//        }
//        return sumaTemp_Minimas/num_lineas;
//    }
    public static double calcularTemperaturaIntermedia(ArrayList<Double> lista1, ArrayList<Double> lista2) {
        Double media=0.0;
        try{
            media=(calcularTemperaturaMedia(lista1) + calcularTemperaturaMedia(lista2)) / 2;
        }catch (NullPointerException e){
            //Al menenos una de las dos listas está vacía
            throw  e;
        }return media;
    }

    public static String calcularDiferenciaMayorTemp(ArrayList<Double> lista1, ArrayList<Double> lista2, ArrayList<String> fechas) {
        double diferenciaMayorTemporal = 0;
        int posicionTemporal=0;
        //Para cada par de temperaturas diarias, clculo la diferencia entre ellas.
        for (int i = 0; i < num_lineas; i++) {
            Double diferenciaCalculada=lista1.get(i) - lista2.get(i);
            if (diferenciaCalculada> diferenciaMayorTemporal) {
                diferenciaMayorTemporal = diferenciaCalculada;
                posicionTemporal=i;
            }
        }
        return (diferenciaMayorTemporal+" del día "+fechas.get(posicionTemporal));
    }

    public static void main(String[] args) {

        //LLAMADA A FUNCIONES PARA LEER OPCIÓN DEL USUARIO Y LEER ARCHIVO CORRESPONDIENTE
        try {
            leerArchivo(leerOpcionUsuario());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        //TEMPERATURA MAX. MÁS ALTA Y MÁS BAJA
        System.out.println("Tº Máxima más alta y más baja: " + calcularTemperaturaMaxima(lista_TempMaximas) + "  ---  " + calcularTemperaturaMinima(lista_TempMaximas));
        System.out.println("*****************************************************************");

        //TEMPERATURA MÍN. MÁS ALTA Y MÁS BAJA
        System.out.println("Tº Mínima más alta y más baja: " + calcularTemperaturaMaxima(lista_TempMinimas) + "  ---  " + calcularTemperaturaMinima(lista_TempMinimas));
        System.out.println("*****************************************************************");

        //TEMPERATURA MAX. MEDIA
        System.out.println("Tº Máxima media: " + calcularTemperaturaMedia(lista_TempMaximas));
        System.out.println("*****************************************************************");

        //TEMPERATURA MÍN. MEDIA
        System.out.println("Tº Mínima media: " + calcularTemperaturaMedia(lista_TempMinimas));
        System.out.println("*****************************************************************");

        //TEMPERATURA INTERMEDIA
        System.out.println("Tº Intermedia: " + calcularTemperaturaIntermedia(lista_TempMaximas,lista_TempMinimas));
        System.out.println("*****************************************************************");

        //DÍA DE MAYOR DIFERENCIA TEMPERATURA MÁX Y MÍN
        System.out.println("Tº de Mayor Diferencia entre Máxima y Mínima: " + calcular_DiferenciaMayor_Temp());
        System.out.println("*****************************************************************");

    }

}
