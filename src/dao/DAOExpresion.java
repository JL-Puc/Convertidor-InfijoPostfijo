package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DAOExpresion {
    

    public ArrayList<String> leerTxt(String archivo){
        File fl = new File(archivo);
        ArrayList<String> expresiones = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(fl);
            while(sc.hasNextLine()){
                String linea = sc.nextLine();               
                StringTokenizer st = new StringTokenizer(linea, ";");
                while(st.hasMoreTokens()){  
                    String expresion = st.nextToken();
                    expresiones.add(expresion);  
                }
                             
            }
            sc.close();
        } catch (FileNotFoundException e) {
            
            e.printStackTrace();
        }
        return expresiones;
        
    }


    public void insertarTxt(ArrayList<String> expresiones, String archivo, String resultado){
        String expresionPostFija = "";
        try (FileWriter fw = new FileWriter(archivo, true)) {
            PrintWriter pw = new PrintWriter(fw);
            for(int i = 0; i < expresiones.size(); i++){

                if(expresiones.get(i).length() > 1) {
                    expresionPostFija += expresiones.get(i) + " ";
                } else {
                    expresionPostFija += expresiones.get(i);
                }
            }
                
            pw.format("%s; %s\n", expresionPostFija, "Eval:" + resultado);

        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
