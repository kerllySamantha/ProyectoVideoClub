package model;

import java.util.ArrayList;

public class Disco extends Multimedia {
    private ArrayList<Cancion> canciones;


    public Disco(String titulo, String artista, Formato formato, int anio) {
        super(titulo, artista, formato, anio);
        setCanciones(canciones);

    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    public void agregarCancion(Cancion cancion) {
        if(cancion.getAutor().equalsIgnoreCase(super.getAutor())){
            this.canciones.add(cancion);
        }else {
            throw new RuntimeException("No se puede añadir a ese disco");
        }
    }

    public String duracionDisco() {
        int duracionSeg = 0;
        for (Cancion cancion : canciones) {
            duracionSeg += cancion.getDuracionCancionSeg();
        }
        int duracionMin = duracionSeg / 60;
        int duracionSegFinal = duracionSeg % 60;
        return String.format("%02d:%02d", duracionMin, duracionSegFinal);
    }

    public String toString() {
        StringBuilder listaCaciones = new StringBuilder();
        for (model.Cancion cancion : this.canciones) {
            listaCaciones.append("\t").append(cancion.getTituloCancion()).append(" - ").append(cancion.getDuracionMinSeg()).append("\n");
        }
        return super.toString() +
                "\nDuración: " + duracionDisco() +
                "\nAutor: " + getAutor()+
                "\nCanciones: \n\t" + listaCaciones + "\n";
    }
}

