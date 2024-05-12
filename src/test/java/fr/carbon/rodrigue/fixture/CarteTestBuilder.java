package fr.carbon.rodrigue.fixture;

import fr.carbon.rodrigue.entity.Aventurier;
import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.entity.Case;
import fr.carbon.rodrigue.entity.Position;
import fr.carbon.rodrigue.entity.Tresor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarteTestBuilder {

    private Case[][] matrice;
    private Map<Position, Tresor> tresors = new HashMap<>();
    private List<Case> montagnes = new ArrayList<>();
    private List<Aventurier> aventuriers = new ArrayList<>();

    public static CarteTestBuilder unCarte() {
        return new CarteTestBuilder();
    }

    public CarteTestBuilder parDefaut() {
        this.matrice = new Case[4][3];
        return this;
    }

    public CarteTestBuilder parDefautMontagneEtTresors() {
        Position positionT = Position.builder()
                .horizontale(0)
                .vertical(3)
                .build();

        Tresor tresor = Tresor.builder()
                .position(positionT)
                .nombreTresorsRestant(2)
                .build();

        Position positionT1 = Position.builder()
                .horizontale(1)
                .vertical(3)
                .build();

        Tresor tresor1 = Tresor.builder()
                .position(positionT1)
                .nombreTresorsRestant(3)
                .build();

        Map<Position, Tresor> tresors = new HashMap<>();
        tresors.put(positionT, tresor);
        tresors.put(positionT1, tresor1);

        Position positionM = Position.builder()
                .horizontale(0)
                .vertical(0)
                .build();

        Case mont = Case.builder()
                .position(positionM)
                .build();

        this.matrice = new Case[4][3];
        this.tresors = tresors;
        this.montagnes = List.of(mont);
        return this;
    }

    public CarteTestBuilder avecMatrice(Case[][] matrice) {
        this.matrice = matrice;
        return this;
    }

    public CarteTestBuilder avecTresors(Map<Position, Tresor> tresors) {
        this.tresors = tresors;
        return this;
    }

    public CarteTestBuilder avecMontagnes(List<Case> montagnes) {
        this.montagnes = montagnes;
        return this;
    }

    public CarteTestBuilder avecAventuriers(List<Aventurier> aventuriers) {
        this.aventuriers = aventuriers;
        return this;
    }

    public CarteTestBuilder avecTresors(Tresor tresor) {
        this.tresors.put(tresor.getPosition(), tresor);
        return this;
    }

    public CarteTestBuilder avecMontagnes( Case montagne) {
        this.montagnes.add(montagne);
        return this;
    }

    public CarteTestBuilder avecAventuriers(Aventurier aventurier) {
        this.aventuriers.add(aventurier);
        return this;
    }


    public Carte build() {
        return Carte.builder()
                .matrice(matrice)
                .tresors(tresors)
                .montagnes(montagnes)
                .aventuriers(aventuriers)
                .build();
    }
}