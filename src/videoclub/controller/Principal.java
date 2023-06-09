package controller;

import view.*;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {

        GestionLogs.escribirRegistro(GestionLogs.registrarInicioPrograma());

        SwingUtilities.invokeLater((() -> {
            try {
                FormPrincipalVideoClub formPrincipalVideoClub = new FormPrincipalVideoClub();
                formPrincipalVideoClub.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                formPrincipalVideoClub.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            GestionLogs.escribirRegistro(GestionLogs.registroCerrarPrograma());
        }));
    }
}