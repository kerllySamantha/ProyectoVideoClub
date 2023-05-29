package view;

import controller.GestionLogs;
import controller.GestionSocioVideoClub;
import model.Multimedia;
import model.Socio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class FormDevoluciones extends JFrame{

    GestionSocioVideoClub lista;
    private JPanel panelDevoluciones;
    private JTextField txtFieldNifSocio;
    private JButton btnComprobarSocio;
    private JLabel lblNifSocio;
    private JList listaDevolver;
    private JButton btnDevolver;
    private String socioNIF;
    private boolean existeNif;

    public FormDevoluciones() {
        super.setContentPane(panelDevoluciones);
        super.setLocationRelativeTo(null);
        super.setJMenuBar(MenuBar.crearMenuBar());
        MenuBar.gestionDeVentanas();


        btnComprobarSocio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                socioNIF = txtFieldNifSocio.getText().toUpperCase();
                existeNif = GestionSocioVideoClub.comprobarNif(GestionSocioVideoClub.socios,socioNIF);
                if(existeNif) {
                    JOptionPane.showMessageDialog(null,"No existe el NIF introducido");
                } else {
                    //listaDevolver.setListData(lista.devolverMult(multimedia,socios));
                    listaDevolver.setModel(GestionSocioVideoClub.mostarMultSocio(txtFieldNifSocio.getText()));
                }
            }
        });

        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(null,"Estás seguro que quieres devolver-lo?","Alerta!",JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    GestionSocioVideoClub.devolverMultimedia(txtFieldNifSocio.getText(), listaDevolver.getSelectedValue().toString());
                    //GestionLogs.escribirRegistro(GestionLogs.registroDevolucionMuolt(txtFieldNifSocio.getText(), listaDevolver.getSelectedValue().toString()));
                }
            }
        });
    }

}
