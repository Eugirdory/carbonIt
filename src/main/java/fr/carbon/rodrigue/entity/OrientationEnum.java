package fr.carbon.rodrigue.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrientationEnum {
    NORD("N","O","E"),
    SUD("S","E","O"),
    EST("E","N","S"),
    OUEST("O","S","N");

    private final String code;
    private final String tournerGauche;
    private final String tournerDroite;

    public static OrientationEnum recupererOrientationEnumParLibelle(String code) {
        return Arrays.stream(OrientationEnum.values())
                .filter(tae -> code.equals(tae.getCode()))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Orientation non valide"));
    }
}
