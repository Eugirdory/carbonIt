package fr.carbon.rodrigue.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Aventurier extends Case {
    private String nom;
    private OrientationEnum orientation;
    private String mouvement;
    private int nombreTresors;

    public List<Character> recupererListeMouvement() {
        return this.mouvement
                .chars()
                .mapToObj(e -> (char) e)
                .toList();
    }
}

