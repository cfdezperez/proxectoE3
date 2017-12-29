/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import java.util.Scanner;

/**
 *
 * @author tomas
 */
public class ConsolaNormal implements Consola {
    Scanner escanear = new Scanner(System.in);
    @Override
    public void imprimir(String s) {
        System.out.print(s);
    }

    @Override
    public String leer(String descripcion) {
        imprimir(descripcion);
        return escanear.nextLine();
    }
    
}
