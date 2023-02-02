package dominio;

import java.util.ArrayList;

import excepciones.ExpException;
import excepciones.StackException;

public class Convertidor {

    public static ArrayList<ArrayList> getExpresionesP(ArrayList<String> expresiones) {
        ArrayList<ArrayList> conversiones = new ArrayList<ArrayList>();
        ArrayList<String> expresionUnidaDigitos = new ArrayList<String>();
        try {
            for (int i = 0; i < expresiones.size(); i++) {
                String infija = expresiones.get(i);
                verificarExpresion(infija);
                expresionUnidaDigitos = unirDigito(infija);
                ArrayList<String> postfija = convertirApostfija(expresionUnidaDigitos);
                conversiones.add(postfija);
                
            }
        } catch (ExpException e) {
            e.printStackTrace();
            conversiones.clear();
        }
        return conversiones;
    }

    public static ArrayList<String> convertirApostfija(ArrayList<String> infija) {// se crea el metodo
                                                                                  // convertirApostfija y se le ingresa
                                                                                  // la
        // operacion infija como argumento
        PilaAA pila = new PilaAA(50);
        ArrayList<String> expresionesPostFijas = new ArrayList<String>();

        for (int i = 0; i < infija.size(); i++) {// se recorre la operacion infija para hacer la conversion. se
                                                 // detendra cuando haya recorrido cada
            String simbolo = infija.get(i); // elemento de la operacion infija
            if (esOperador(simbolo)) {
                if (simbolo.equals(")")) {

                    while (!pila.getTope().equals("("))
                        expresionesPostFijas.add(pila.pop());
                    if (pila.getTope().equals("("))
                        pila.pop();
                }
                if (pila.vacia()) {
                    if (!simbolo.equals(")"))
                        pila.push(simbolo);
                } else {
                    if (!simbolo.equals(")")) {
                        int pe = prioridadExp(simbolo);
                        int pp = prioridadPila(pila.getTope());
                        if (pe > pp) {
                            pila.push(simbolo);
                        } else {
                            expresionesPostFijas.add(pila.pop());
                            pila.push(simbolo);
                        }
                    }
                }
            } else
                expresionesPostFijas.add(simbolo);
        }
        while (!pila.vacia()) {
            expresionesPostFijas.add(pila.pop());
        }
        return expresionesPostFijas;
    }

    public static int prioridadExp(String x) { // se le aasigna la prioridad a cada operador de la expresion
        if (x.equals("^"))
            return 4;
        if (x.equals("*") || x.equals("/"))
            return 2;
        if (x.equals("+") || x.equals("-"))
            return 1;
        if (x.equals("("))
            return 5;
        if (x.equals(")"))
            return 6;
        return 0;
    }

    public static int prioridadPila(String x) { // se le asigna prioridad a cada operador de la pila
        if (x.equals("^"))
            return 3;
        if (x.equals("*") || x.equals("/"))
            return 2;
        if (x.equals("+") || x.equals("-"))
            return 1;
        if (x.equals("("))
            return 0;
        if (x.equals(")"))
            return 0;
        return 0;
    }

    public static boolean esOperador(String letra) {
        if (letra.equals("*") || letra.equals("/") || letra.equals("+") || letra.equals("-") || letra.equals("^")
                || letra.equals("(")
                || letra.equals(")")) {
            return true; // se compara letra con cada uno de los operadores
        } else
            return false;
    }

    public static boolean esNumero(String letra){
        int num;
        try {
            num = Integer.parseInt(letra);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static String resultados(ArrayList<String> expresiones) throws StackException { // FunciÃ³n que usa una pila y
                                                                                           // evalÃºa las expreciones en
                                                                                           // postfijo, se van aÃ±adiendo
                                                                                           // cada dato de la expreciÃ³n
                                                                                           // de izquierda a derecha con
                                                                                           // push(), si el token por
                                                                                           // aÃ±adir es un operando
                                                                                           // entonces se envÃ­a a otra
                                                                                           // funciÃ³n para hacer la
                                                                                           // operacion
        Stack<String> pila = new Stack<String>(50);

        String resultados = "";
        int contador = 0;
        int contadorAux = 0;
        int resultado = 0;

        while (contador < expresiones.size()) {

            while (contadorAux < expresiones.size()) {
                String token = expresiones.get(contadorAux);

                if (token.equals("+") || token.equals("-") || token.equals("/") || token.equals("*")
                        || token.equals("^")) { // Evaluar si el token es un operando

                    // En dado casi que lo sea significa que los dos datos anteriores al operando
                    // deben ser operados
                    String numDerecho = pila.pop(); // El primero sacado es el numero que va por la derecha
                    String numIzquierdo = pila.pop(); // EL segundo sacado es el numero que va por la izquierda

                    resultado = operar(numIzquierdo, numDerecho, token); // Funcion que manda a los dos numeros y su
                                                                         // operador para poder operarlos y retorna el
                                                                         // resultado

                    // Como ya hemos operados los primeros dos elementos, la pila estÃ¡ vacÃ­a, pero
                    // debemos agregarle el resultado de la
                    // Operacion para que siga operando con los demÃ¡s datos
                    pila.push(String.valueOf(resultado));

                } else {
                    // Si el token no es un operando entonces es un nÃºmero, por lo que se agrega a
                    // la pila
                    pila.push(token);
                }
                contadorAux++;
            }

            resultados = pila.pop(); // Se aÃ±ade el resultado final (que es el dato que se queda en la pila al final)
                                     // a la lista de los resultados.

            contadorAux = 0;
            contador++;
        }

        return resultados;
    }

    public static void verificarExpresion(String infija) throws ExpException {
        int contador = 0;
        int contAbreParentesis = 0;
        if (esOperador(Character.toString(infija.charAt(infija.length() - 1))) // Aqui verificamos si se empieza con un
                                                                               // operador o con ")" para lanzar la
                                                                               // excepcion
                && prioridadExp(Character.toString(infija.charAt(infija.length() - 1))) != 6 ||
                esOperador(Character.toString(infija.charAt(contador)))
                        && prioridadExp(Character.toString(infija.charAt(contador))) != 5) {
            throw new ExpException("Error sintaxis en la expresiÃ³n: " + infija);
        }
        while (contador < infija.length()) {
            String simbolo = Character.toString(infija.charAt(contador));
            if (contador + 1 != infija.length()) {
                if (esOperador(simbolo)) {
                    if (prioridadExp(simbolo) == 5) {
                        contAbreParentesis++;
                    } else {
                        String netxSimbolo = Character.toString(infija.charAt(contador + 1));
                        if (!esNumero(netxSimbolo)) {
                            if (prioridadExp(netxSimbolo) != 5) {
                                if (prioridadExp(simbolo) == 6 && contAbreParentesis > 0) {
                                    contAbreParentesis--;
                                } else {
                                    throw new ExpException("Error sintaxis en la expresiÃ³n: " + infija);
                                }
                            }
                        }
                    }
                }
            } else {
                if(prioridadExp(simbolo) == 6 && contAbreParentesis == 1){
                }else if (contAbreParentesis > 1) {
                    throw new ExpException("Error sintaxis en la expresiÃ³n: " + infija);
                }else if (prioridadExp(simbolo) == 6 && contAbreParentesis == 0) {
                    throw new ExpException("Error sintaxis en la expresiÃ³n: " + infija);
                }                          
            }
            contador++;
        }

    }

    public static int operar(String numIzquierda, String numDerecha, String operacion) { // Recibe dos numeros y un
                                                                                         // operando para poder hacer la
                                                                                         // operacion
        int resultado = 0;

        // Cada variable estÃ¡ en String por lo que deben ser Casteadas para hacer la
        // operaciÃ³n
        switch (operacion) {
            case "+":
                resultado = Integer.parseInt(numIzquierda) + Integer.parseInt(numDerecha);
                break;
            case "-":
                resultado = Integer.parseInt(numIzquierda) - Integer.parseInt(numDerecha);
                break;
            case "*":
                resultado = Integer.parseInt(numIzquierda) * Integer.parseInt(numDerecha);
                break;
            case "/":
                resultado = Integer.parseInt(numIzquierda) / Integer.parseInt(numDerecha);
                break;
            case "^":
                resultado = (int) Math.pow(Integer.parseInt(numIzquierda), Integer.parseInt(numDerecha));
                break;
        }

        return resultado;
    }

    public static boolean validarDosDigitosConsecutivos(String numero) {

        switch (numero) {

            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                return true;
        }

        return false;
    }

    public static ArrayList<String> unirDigito(String expresionAritmetica) {
        int contador = 0;
        ArrayList<String> listaExpresion = new ArrayList<String>();

        while (contador < expresionAritmetica.length()) {

            String simbolo = Character.toString(expresionAritmetica.charAt(contador)); // Unidad de la expresion que
                                                                                       // serÃ¡ evaluada
            if ((contador + 1) != expresionAritmetica.length()) {
                while (validarDosDigitosConsecutivos(Character.toString(expresionAritmetica.charAt(contador + 1)))
                        && validarDosDigitosConsecutivos(Character.toString(expresionAritmetica.charAt(contador)))) { // Unir
                                                                                                                      // los
                                                                                                                      // numeros
                                                                                                                      // en
                                                                                                                      // un
                                                                                                                      // string
                                                                                                                      // para
                                                                                                                      // despues
                                                                                                                      // ser
                                                                                                                      // evaluado
                    simbolo += Character.toString(expresionAritmetica.charAt(contador + 1));
                    contador++;
                }
            }
            listaExpresion.add(simbolo);

            expresionAritmetica.charAt(contador);

            contador++;
        }

        return listaExpresion;

    }

}

