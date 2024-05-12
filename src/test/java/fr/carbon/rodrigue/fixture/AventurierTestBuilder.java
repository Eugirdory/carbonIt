package fr.carbon.rodrigue.fixture;

import fr.carbon.rodrigue.entity.Aventurier;
import fr.carbon.rodrigue.entity.OrientationEnum;
import fr.carbon.rodrigue.entity.Position;

public class AventurierTestBuilder {

    private String nom;
    private OrientationEnum orientation;
    private String mouvement;
    private int nombreTresors;
    private Position position;

    public static AventurierTestBuilder unAventurier() {
        return new AventurierTestBuilder();
    }

    public AventurierTestBuilder parDefaut() {
        this.position = Position.builder()
                .horizontale(1)
                .vertical(1)
                .build();
        return this;
    }

    public AventurierTestBuilder avecNom(String nom) {
        this.nom = nom;
        return this;
    }

    public AventurierTestBuilder avecOrientation(OrientationEnum orientation) {
        this.orientation = orientation;
        return this;
    }

    public AventurierTestBuilder avecMouvement(String mouvement) {
        this.mouvement = mouvement;
        return this;
    }

    public AventurierTestBuilder avecNombreTresors(int nombreTresors) {
        this.nombreTresors = nombreTresors;
        return this;
    }

    public AventurierTestBuilder avecPosition(Position position) {
        this.position = position;
        return this;
    }


    public Aventurier build() {
        return Aventurier.builder()
                .nom(nom)
                .orientation(orientation)
                .mouvement(mouvement)
                .nombreTresors(nombreTresors)
                .position(position)
                .build();
    }
}