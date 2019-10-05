package caballo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * El tamaño del tablero esta dado en la constante TAMAÑO_TABLERO en el método main.
 * 
 * Para probar otros tamaños, modificar dicha constante.
 * 
 * El camino final del caballo se pude ver por la numeración correlativa en el tablero.
 */

/**
 *
 * @author patricioirribarranaudon (05-10-2019)
 */
public class Caballo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {;
        
        final int TAMAÑO_TABLERO = 8;// 8, 6 y 5
     
        String matriz[][] = new String[TAMAÑO_TABLERO][TAMAÑO_TABLERO];
        int x = 0,y = 0;
        
        iniciarTablero(matriz);
        mostrar(matriz);
        
        System.out.println("Ingresar fila (Entre 0 y "+ (TAMAÑO_TABLERO - 1) +"): ");
        x = scann(TAMAÑO_TABLERO-1);
        System.out.println("Ingresar columna (Entre 0 y "+ (TAMAÑO_TABLERO - 1) +"): ");
        y = scann(TAMAÑO_TABLERO-1);

        System.out.println("------------------------------------------------------------");
        System.out.println ("****** El juego comienza en el punto: ["+x+","+y+"] ******");
        System.out.println("------------------------------------------------------------");
        System.out.print("\n");
        
        matriz[x][y]="0";
        
        mostrar(matriz);
        
        recorrer(matriz, x, y,TAMAÑO_TABLERO);
        
    }
    
    /**
     * Se encarga de contar y retornar el número de posiciones por donde el caballo aun no ha pasado.
     * 
     * @param matriz
     * @return numero de posiciones restantes por las que no ha pasado el caballo.
     */
    static int posicionesRestantes(String matriz[][]) {
        int contador=0;
        for (int x = 0; x < matriz.length; x++) {

            for (int y = 0; y < matriz[x].length; y++) {
                if(matriz[x][y].equals("*")){
                    contador++;
                }
            }
        }
        return contador;
    }
    
    /**
     * Método encargado de obtener las opciones por donde el caballo puede pasar según los datos ingresados inicialmente, para posteriormente 
     * de manera aleatoria obtener las siguiente posicion afortunada. La posicion afortunada es marcada en la matriz con su respectivo número 
     * correlativo y se actualizan los ejes x,y para continuar y repetir el proceso hasta que ya no queden mas posiciones para el caballo.
     * 
     * Si el caballo se queda sin opciones cuando aun quedan posiciones por cubrir, entonces se reinicia el tablero y 
     * cominza el proceso nuevamente.
     * 
     * @param matriz
     * @param x
     * @param y 
     */
    static void recorrer(String matriz[][], int xInicial, int yInicial, int tamañoTablero) {
        int x = xInicial;
        int y = yInicial;
        
        tamañoTablero -= 1;
        /*System.out.println("**** Puntos posibles: ****");
        System.out.print("\n");*/
        
        int contadorIntentos=1;

        int posicionesPosibles = 0;
        
        posicionesPosibles = posicionesRestantes(matriz);
        int countSig = 1;
        while (posicionesPosibles > 0) {
            
            /*System.out.println("------------------------------------------------------------");
            System.out.println("*********** Posiciones restantes: [" + posicionesPosibles + "] ***********");
            System.out.println("------------------------------------------------------------");
            System.out.print("\n");
            System.out.println("------------------------------------------------------------");
            System.out.println("*********** Intento número [" + contadorIntentos + "] ***********");
            System.out.println("------------------------------------------------------------");
            System.out.print("\n");*/
            
            List<int[]> opciones = new ArrayList<>();

            //vamos a guardar las posiciones validas desde el punto dado.
            agregaOpcionesEjeY(opciones, x,y,matriz,tamañoTablero);
            agregaOpcionesEjeX(opciones, x, y, matriz, tamañoTablero);

            if (opciones.size() > 0) {
                /*for (int r = 0; r < sd.size(); r++) {
                    System.out.println("Lista de posibilidades : [" + sd.get(r)[0] + "],[" + sd.get(r)[1] + "]");
                    System.out.print("\n");
                }*/

                int afortunado = (int) (Math.random() * opciones.size());
                /*System.out.println("---------------------------------------------");
                System.out.println("La posicion afotunada es: [" + sd.get(afortunado)[0] + "],[" + sd.get(afortunado)[1] + "]");
                System.out.println("---------------------------------------------");
                System.out.print("\n");*/

                matriz[opciones.get(afortunado)[0]][opciones.get(afortunado)[1]] = String.valueOf(countSig);
                x=opciones.get(afortunado)[0];
                y=opciones.get(afortunado)[1];
                countSig++;
                //mostrar(matriz);
            } else {
                /*System.out.println("----------------------------------------------------");
                System.out.println("************ No hay más posibilidades, se reinicia el tablero **************");
                System.out.println("----------------------------------------------------");
                System.out.print("\n");*/

                iniciarTablero(matriz);
                matriz[xInicial][yInicial]="0";
                x = xInicial;
                y = yInicial;
                countSig = 1;
                //mostrar(matriz);
                contadorIntentos++;
            }

            posicionesPosibles = posicionesRestantes(matriz);
            /*if(posicionesPosibles==1){
                System.out.println("------------------------------------------------------------");
                System.out.println("*********** Intento número [" + contadorIntentos + "] ***********");
                System.out.println("------------------------------------------------------------");
                System.out.print("\n");
                mostrar(matriz);
            }*/
            
            if(posicionesPosibles==0){
                System.out.println("------------------------------------------------------------");
                System.out.println("*********** Intento número [" + contadorIntentos + "] ***********");
                System.out.println("------------------------------------------------------------");
                System.out.print("\n");
                mostrar(matriz);
                System.out.println("------------------------------------------------------------");
                System.out.println("*********** Bien! programa finalizado! ***********");
                System.out.println("------------------------------------------------------------");
                System.out.print("\n");
                System.exit(0);
            }
        }

    }
    
    /**
     * Método encargado de validar opciones disponibles en la columna donde esta posicionado el caballo, ya sea hacia arriba como hacia abajo,
     * valida que haya un espacio para moverse dentro del rango y asi guardarlo como posible movimiento.
     * 
     * @param opciones
     * @param x
     * @param y
     * @param matriz
     * @param tamañoTablero 
     */
    static void agregaOpcionesEjeY(List<int[]> opciones, int x, int y, String matriz[][], int tamañoTablero) {
        if ((y + 2) <= tamañoTablero) {
            if ((x + 1) <= tamañoTablero) {
                int[] posicion = {x + 1, y + 2};
                if (matriz[x + 1][y + 2].equals("*")) {
                    opciones.add(posicion);
                }
            }

            if ((x - 1) >= 0) {
                int[] posicion = {x - 1, y + 2};
                if (matriz[x - 1][y + 2].equals("*")) {
                    opciones.add(posicion);
                }
            }
        }

        if ((y - 2) >= 0) {
            if ((x + 1) <= tamañoTablero) {
                int[] posicion = {x + 1, y - 2};
                if (matriz[x + 1][y - 2].equals("*")) {
                    opciones.add(posicion);
                }
            }

            if ((x - 1) >= 0) {
                int[] posicion = {x - 1, y - 2};
                if (matriz[x - 1][y - 2].equals("*")) {
                    opciones.add(posicion);
                }
            }
        }
    }
    
    /**
     * Método encargado de validar opciones disponibles en la fila donde esta posicionado el caballo, ya sea hacia la derecha o izquierda,
     * valida que haya un espacio para moverse dentro del rango y asi guardarlo como posible movimiento.
     * 
     * @param opciones
     * @param x
     * @param y
     * @param matriz
     * @param tamañoTablero 
     */
    static void agregaOpcionesEjeX(List<int[]> opciones, int x, int y, String matriz[][], int tamañoTablero) {
        if ((x + 2) <= tamañoTablero) {
            if ((y + 1) <= tamañoTablero) {
                int[] posicion = {x + 2, y + 1};
                if (matriz[x + 2][y + 1].equals("*")) {
                    opciones.add(posicion);
                }
            }

            if ((y - 1) >= 0) {
                int[] posicion = {x + 2, y - 1};
                if (matriz[x + 2][y - 1].equals("*")) {
                    opciones.add(posicion);
                }
            }
        }

        if ((x - 2) >= 0) {
            if ((y + 1) <= tamañoTablero) {
                int[] posicion = {x - 2, y + 1};
                if (matriz[x - 2][y + 1].equals("*")) {
                    opciones.add(posicion);
                }
            }

            if ((y - 1) >= 0) {
                int[] posicion = {x - 2, y - 1};
                if (matriz[x - 2][y - 1].equals("*")) {
                    opciones.add(posicion);
                }
            }
        }
    }
    
    /**
     * Obtiene el número ya sea de fila o columna ingresado por teclado y valida que sea un valor aceptado.
     * 
     * @return número escaneado
     */
    static int scann(int tamañoTablero){
        int valor = 0;
        Scanner entradaEscaner = new Scanner (System.in); 
        
        boolean valid = false;
        do {
            try {
                valor = entradaEscaner.nextInt();
                valid = validaRango(valor, tamañoTablero);
            } catch (InputMismatchException ime) {
                ime.getCause();
                System.out.println("¡Error! Solo puedes insertar números. ");
                entradaEscaner.next();
            }
        } while (!valid);
        
        return valor;
    }
    
    /**
     * Valida que el número de fila o columna ingresado esté dentro del rango.
     * 
     * @param posicion
     * @return 
     */
    static boolean validaRango(int posicion, int tamañoTablero){
        if (posicion < 0 || posicion > tamañoTablero) {
            System.out.println("Error! el número debe estar entre 0 y "+tamañoTablero);
            return false;
        }
        return true;
    } 
    
    /**
     * Se encarga de llenar la matriz con "*" para su visualización en consola.
     * 
     * @param matriz 
     */
    static void iniciarTablero(String matriz[][]){
        for (int x = 0; x < matriz.length; x++) {//filas
            for (int y = 0; y < matriz[x].length; y++) { //columnas
                matriz[x][y]="*";
            }
        }
    }
    
    /**
     * Se encarga de mostrar el tablero con los nuevos pasos dados por el caballo.
     * 
     * @param matriz 
     */
    static void mostrar(String matriz[][]){ //matriz[fila][columna]
        System.out.println("*********** Tablero "+matriz.length+"x"+matriz.length+" ***********");
        for (int x = 0; x < matriz.length; x++) {
            for (int y = 0; y < matriz[x].length; y++) {
                System.out.print("["+matriz[x][y]+"]");
                if (y != matriz[x].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

}
