package fr.carbon.rodrigue.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Carte {
    private Case[][] matrice;
    private Map<Position, Tresor> tresors;
    private List<Case> montagnes;
    private List<Aventurier> aventuriers;

    public boolean estMontagneRencontree(Position position) {
        return Stream.ofNullable(this.montagnes)
                .flatMap(Collection::stream)
                .map(Case::getPosition)
                .anyMatch(mont -> Objects.equals(mont,position));
    }

    public boolean estAventurierRencontree(Position position) {
        return Stream.ofNullable(this.aventuriers)
                .flatMap(Collection::stream)
                .map(Aventurier::getPosition)
                .anyMatch(p -> Objects.equals(p,position));
    }
    public void afficher() {
        Optional.ofNullable(tresors)
                .map(Map::keySet)
                .orElseGet(HashSet::new)
                .forEach(position -> matrice[position.getVertical()][position.getHorizontale()] = new Tresor());
        Stream.ofNullable(montagnes)
                .flatMap(Collection::stream)
                .map(Case::getPosition)
                .forEach(position -> matrice[position.getVertical()][position.getHorizontale()] = new Case());
        Stream.ofNullable(aventuriers)
                .flatMap(Collection::stream)
                .map(Aventurier::getPosition)
                .forEach(position -> matrice[position.getVertical()][position.getHorizontale()] = new Aventurier());

        for (Case[] cases : this.matrice) {
            for (Case aCase : cases) {
                if (aCase == null) {
                    System.out.print("-");
                } else if (aCase instanceof Aventurier) {
                    System.out.print("A");
                } else if (aCase instanceof Tresor) {
                    System.out.print("T");
                } else {
                    System.out.print("M");
                }
            }
            System.out.println();
        }
    }
}
