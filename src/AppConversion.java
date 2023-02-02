import java.util.ArrayList;

import excepciones.ExpException;
import excepciones.StackException;
import dao.DAOExpresion;
import dominio.Convertidor;

public class AppConversion {
    public static void main(String[] args) {
        ArrayList<String> expresiones = new ArrayList<String>();
        ArrayList<ArrayList> conversiones = new ArrayList<ArrayList>();
        String resultados = "";
        String archivoSalida = "InfijoAPostfijoApp exp_infijas.txt";
        String archivoEntrada = "exp_infijas.txt";
        DAOExpresion dao = new DAOExpresion();
        expresiones = dao.leerTxt(archivoEntrada);
        conversiones = Convertidor.getExpresionesP(expresiones);
        if (!conversiones.isEmpty()) {
            for (int contador = 0; contador < conversiones.size(); contador++) {
                try {
                    resultados = Convertidor.resultados(conversiones.get(contador));
                    dao.insertarTxt(conversiones.get(contador), archivoSalida, resultados);
                } catch (StackException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}

