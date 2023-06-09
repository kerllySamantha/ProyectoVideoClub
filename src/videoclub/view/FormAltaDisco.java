package view;

import controller.GestionBasesDatos;
import controller.GestionLogs;
import controller.GestionMultimedia;
import model.Formato;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormAltaDisco extends JFrame {


    private JPanel panelAltaDisco;
    private JLabel lblTitulo;
    private JLabel lblAutor;
    private JLabel lblFormato;
    private JButton btnAniadirCanciones;
    private JRadioButton rbCD;
    private JRadioButton rbBlueRay;
    private JRadioButton rbArchivo;
    private JRadioButton rbDvd;
    private JButton btnAltaDisco;
    private JTextArea txtResumenAlta;
    private JTextField txtTituloDisco;
    private JTextField txtAutorDisco;
    private JComboBox cbAnioDisco;
    private ButtonGroup grupoFormato;
    static Disco disco;


    public FormAltaDisco() {
        super.setContentPane(panelAltaDisco);
        super.setLocationRelativeTo(null);
        super.setJMenuBar(MenuBar.crearMenuBar());
        txtResumenAlta.setEditable(false);
        altaDisco();
        mostrarDatos();
        MenuBar.gestionDeVentanas();
    }

    public void altaDisco() {
        btnAniadirCanciones.addActionListener(actionEvent -> {
            try {
                String tituloDisco = txtTituloDisco.getText().toUpperCase();
                String autorDisco = txtAutorDisco.getText().toUpperCase();
                int anioDisco = 0;
                Formato formato = null;
                boolean datosCorrectos = true;
                boolean tituloCorrecto = GestionMultimedia.comprobarMultiemdia(GestionMultimedia.multimedias, tituloDisco);


                if (rbArchivo.isSelected()) {
                    formato = Formato.ARCHIVO;
                } else if (rbBlueRay.isSelected()) {
                    formato = Formato.BLUERAY;
                } else if (rbCD.isSelected()) {
                    formato = Formato.CD;
                } else if (rbDvd.isSelected()) {
                    formato = Formato.DVD;
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar uno de los formatos");
                    datosCorrectos = false;
                }

                if (txtTituloDisco.getText().equals("") && txtAutorDisco.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "No puedes dejar los campos vacio");
                } else if (tituloCorrecto) {
                    JOptionPane.showMessageDialog(null, "El titulo del Disco ya exixte");
                    datosCorrectos = false;
                } else if (txtTituloDisco.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "No puedes dejar el título vacio");
                } else if (txtAutorDisco.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "No puedes dejar el autor vacio");
                } else {
                    tituloDisco = txtTituloDisco.getText().toUpperCase();
                    autorDisco = txtAutorDisco.getText().toUpperCase();
                    anioDisco = Integer.parseInt(cbAnioDisco.getSelectedItem().toString());
                }


                if (datosCorrectos) {
                    disco = new Disco(tituloDisco, autorDisco, formato, anioDisco);
                    GestionMultimedia.multimedias.add(disco);
                    FormAniadirCanciones canciones = new FormAniadirCanciones();
                    canciones.setVisible(true);
                    canciones.setBounds((MenuBar.ANCHO_PANTALLA/2)-200, (MenuBar.ALTO_PANTALLA/2)-225, 400,400);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void recibirCanciones(ArrayList<Cancion> canciones) {
        disco.setCanciones(canciones);
    }

    public void mostrarDatos() {
        btnAltaDisco.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (disco != null) {
                    txtResumenAlta.setText(disco.toString());
                    limpiarCampos();
                    GestionBasesDatos.insertDisco(disco.getTitulo(), disco.getAutor(), disco.getFormato(), disco.getAnio(), disco.duracionDisco());
                    aniadirCancionesBaseDatos();
                    GestionLogs.escribirRegistro(GestionLogs.registroAltaDisco(disco.getTitulo()));
                    JOptionPane.showMessageDialog(null, "Se ha añadido un nuevo disco");
                } else {
                    JOptionPane.showMessageDialog(null, "Antes de dar de alta el disco se deben añadir canciones");
                }
            }
        });
    }

    public void aniadirCancionesBaseDatos() {
        try {
            for (Cancion cancion: disco.getCanciones()) {
                GestionBasesDatos.insertCancion(cancion.getTituloCancion(), cancion.getAutor(),
                        cancion.getDuracionMinSeg(), cancion.getDuracionCancionSeg(), disco.getTitulo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void limpiarCampos() {
        cbAnioDisco.setSelectedItem("2023");
        txtAutorDisco.setText("");
        txtTituloDisco.setText("");
        grupoFormato.clearSelection();
    }
}

