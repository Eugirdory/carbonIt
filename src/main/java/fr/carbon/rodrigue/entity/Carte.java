package fr.carbon.rodrigue.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
}
