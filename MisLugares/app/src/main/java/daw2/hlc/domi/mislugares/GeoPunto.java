package daw2.hlc.domi.mislugares;

import java.lang.Math;import java.lang.String; /**
 * Created by 2DAW on 16/12/2015.
 */
public class GeoPunto {

    double longitud;
    double latitud;

    //Constructor
    public GeoPunto(double longitud, double latitud){
        this.longitud = longitud;
        this.latitud = latitud;
    }

    //Metodos
    public String toString() {
        return "Longitud: "+longitud + " - Latitud: "+ latitud; }

    public double distancia(GeoPunto punto){
        final double RADIO_TIERRA = 6371000; // en metros
        double dLat = Math.toRadians(latitud - punto.latitud);
        double dLon = Math.toRadians(longitud - punto.longitud);
        double lat1 = Math.toRadians(punto.latitud);
        double lat2 = Math.toRadians(latitud);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return c * RADIO_TIERRA;
    }
}
