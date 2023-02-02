package dominio;

public class PilaAA {
    // Atributos
    private String pila[];
    private String dato;
    private int tope = -1;
    private int max = 0;
    private boolean res;

    PilaAA(int max) {// el constructor pide el tamaÃ±o de la pila
        this.max = max;
        pila = new String[max];// se crea un arreglo del tamano pasado ateriormente
        dato = "";
    }


    public boolean llena() {// MÃ©todo para cuando la pila esta llena
        if (tope == (max - 1)) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }

    public boolean vacia() {// MÃ©todo para cuando la pila es vacia
        if (tope == -1) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }

    // Los mÃ©todos push y pop te permiten simular una pila de objetos LIFO (Ãºltimo
    // en entrar, primero en salir).
    public boolean push(String dato) {// Metodo por si la pia esta llena
        if (llena()) {
            System.err.print("Error: Pila llena");// Si la pila esta llena nos manda un error
        } else {// Si la pila esta vacia continua con el progama
            tope++;
            pila[tope] = dato;
            res = true;
        }
        return res;
    }

    public String pop() {
        if (vacia()) {
            System.err.print("Sub-Desbordamiento: Pila vacia");
        } else {
            dato = pila[tope];
            tope--;
        }
        return dato;
    }

    public String getTope() {// Retorna un valor
        String top = "";
        if (vacia()) {
        } else
            top = pila[tope];
        return top;
    }

    
}
