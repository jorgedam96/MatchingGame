package es.ies;

import java.awt.AWTException;
import java.util.Scanner;

/**
 * @author Jorge
 */
public class MatrizParejas {
    
    public static void main(String[] args) throws AWTException, InterruptedException {
        
        int tablero[][] = leerTablero();
        String matriz[][] = matrizToString(tablero);
        borrarConsola();
        crearParejas(tablero);
        elegirPareja(tablero);
    }
    
    public static int[][] leerTablero() {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el tamaño del tablero");
        System.out.println("EJ: si se introduce un 4 genera un tablero de 4x4");
        int alto = sc.nextInt();
        int ancho = alto;
        
        while ((ancho * alto) % 2 != 0) {
            System.err.println("Introduce otro numero,casillas impares.");
            alto = sc.nextInt();
            ancho = alto;
        }
        
        int tablero[][] = new int[alto][ancho];
        return tablero;
    }
    
    public static void imprimirTableroVacio(int tablero[][]) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print("[X]");
            }
            System.out.println("");
        }
    }
    
    public static void imprimirTablero(int tablero[][],int tiempo) throws InterruptedException {
        tiempo=tiempo*1000;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print("[" + tablero[i][j] + "]");
            }
            System.out.println("");
        }
        Thread.sleep(tiempo);
    }
    
    public static int[][] crearParejas(int tablero[][]) throws InterruptedException {
        int numParejas = tablero.length * tablero.length / 2;
        int[] vectorParejas = new int[numParejas];
        int cont = 0;
//creamos un vector con numeros al azar de longitud la mitad del total de la matriz
        for (int i = 0; i < numParejas; i++) {
            vectorParejas[i] = (int) (Math.random() * (99 - 10 + 1) + 10);
        }

//recorremos la matriz metiendo los valores del vector 2 veces
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (cont == numParejas) {
                    cont = 0;
                    //desordenamos el vector antes de meterlo por segunda vez
                    desordenarVector(vectorParejas);
                    
                }
                
                tablero[i][j] = vectorParejas[cont];
                
                cont++;
            }
        }
        
        return tablero;
    }

//    public static void desordenarVector(int vectorParejas[]) {
//        int vector[] = new int[vectorParejas.length];
//        int cont = 0;
//        for (int i = vector.length - 1; i >= 0; i--) {
//            vector[i] = vectorParejas[cont];
//            cont++;
//        }
//        for (int j = 0; j < vectorParejas.length; j++) {
//            vectorParejas[j] = vector[j];
//        }
//    }
//    public static void desordenarMatriz(int tablero[][]) {
//        int matriz[][] = new int[tablero.length][tablero.length];
//        for (int i = 0; i < matriz.length; i++) {
//            for (int j = 0; j < matriz[0].length; j++) {
//                matriz[i][j] = tablero[i][j];
//            }
//        }
//        for (int i = 0; i < tablero.length; i++) {
//            for (int j = 0; j < tablero[0].length; j++) {
//                tablero[i][j]=matriz[j][i];
//            }
//            
//        }
//    }
    public static void desordenarVector(int vectorParejas[]) {
        int tamaño = vectorParejas.length;
        int aleatorio, aux;
        
        for (int i = 0; i < tamaño; i++) {
            aleatorio = (int) (Math.random() * tamaño - 1);
            aux = vectorParejas[i];
            vectorParejas[i] = vectorParejas[aleatorio];
            vectorParejas[aleatorio] = aux;
        }
    }
    
    public static void elegirPareja(int tablero[][]) throws AWTException, InterruptedException {
        boolean acierto = false;
        int fallos = 0;
        int golpe1, golpe2, golpe3, golpe4,tiempo;
        int num1 = 0, num2 = 1;
        
        String matriz[][] = matrizToString(tablero);
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el tiempo que quieres ver el tablero");
        System.out.println("Recomendado para 4x4 1 segundo");
        tiempo=sc.nextInt();
        while (acierto == false) {
            borrarConsola();
            imprimirTablero(tablero,tiempo);
            borrarConsola();
            imprimirMatriz(matriz);
            
            System.out.println("CARTA 1");
            System.out.print("Introduce la casilla del alto: ");
            golpe1 = sc.nextInt();
            System.out.print("Introduce la casilla del ancho: ");
            golpe2 = sc.nextInt();
            golpe1 = golpe1 - 1;
            golpe2 = golpe2 - 1;
            System.out.println("");
            
            System.out.println("CARTA 2");
            System.out.print("Introduce la casilla del alto: ");
            golpe3 = sc.nextInt();
            System.out.print("Introduce la casilla del ancho: ");
            golpe4 = sc.nextInt();
            golpe3 = golpe3 - 1;
            golpe4 = golpe4 - 1;
            System.out.println("");

//comprueba si el primer golpe y el segundo coinciden en el numero descubierto
            boolean fijar = false;
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[0].length; j++) {
                    if (golpe1 == i && golpe2 == j) {
                        num1 = tablero[i][j];
                    }
                    if (golpe3 == i && golpe4 == j) {
                        num2 = tablero[i][j];
                    }
                    if (num1 == num2) {
                        fijar = true;
                    } else {
                        fijar = false;
                    }
                }
            }
            
            if (fijar == true) {
                System.out.println("acierto");
                fijarMatriz(matriz, golpe1, golpe2, golpe3, golpe4, num1, num2);
                imprimirMatriz(matriz);
                fijar = false;
                Thread.sleep(500);
                if (comprobarFinal(matriz) == true) {
                    acierto = true;
                }
                
            } else {
                System.err.println("fallo");
                fallos++;
            }
            
        }
        System.out.println("HAS GANADO");
        System.out.println("Has fallado " + fallos + " veces");
    }

    //si la matriz todavía tiene alguna X no hemos terminado.
    public static boolean comprobarFinal(String matriz[][]) {
        boolean ganar = false;
        int cont = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j].equals("XX")) {
                    cont++;
                }
            }
        }
        if (cont == 0) {
            ganar = true;
        }
        return ganar;
    }
    
    public static void imprimirMatriz(String matriz[][]) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println("");
        }
    }

    //edita la matriz originalmente llena de X para llenarla con los valores e ir mostrándola.
    public static void fijarMatriz(String matriz[][], int golpe1, int golpe2, int golpe3, int golpe4, int num1, int num2) {
        
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (i == golpe1 && j == golpe2) {
                    matriz[i][j] = Integer.toString(num1);
                }
                if (i == golpe3 && j == golpe4) {
                    matriz[i][j] = Integer.toString(num2);
                }
            }
        }
        
    }
    
    public static String[][] matrizToString(int tablero[][]) {
        String matriz[][] = new String[tablero.length][tablero[0].length];
        
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j] = "XX";
            }
        }
        
        return matriz;
        
    }
    
    public static void borrarConsola() throws AWTException, InterruptedException {
        
        java.awt.Robot pressbot = new java.awt.Robot();
        Thread.sleep(800);
        pressbot.keyPress(17); // mantiene CTRL
        pressbot.keyPress(76); // mantiene L
        pressbot.keyRelease(17); // suelta CTRL 
        pressbot.keyRelease(76); // suelta L
        Thread.sleep(800);
        
    }
    
}
