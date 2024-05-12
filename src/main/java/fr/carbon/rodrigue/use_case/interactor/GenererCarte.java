package fr.carbon.rodrigue.use_case.interactor;

import fr.carbon.rodrigue.entity.Aventurier;
import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.entity.Case;
import fr.carbon.rodrigue.entity.Position;
import fr.carbon.rodrigue.entity.Tresor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenererCarte {
    public Carte executer(Case[][] matrice, List<Tresor> tresors, List<Case> montagnes, List<Aventurier> aventuriers) {
        if (Objects.isNull(matrice) ||
                matrice.length == 0 ||
                matrice[0].length == 0) {
            throw new RuntimeException("La carte n'existe pas");
        }
        Map<Position, Tresor> mapTresors = tresors.stream()
                .collect(Collectors.toMap(
                        Case::getPosition,
                        Function.identity()
                ));
        return Carte.builder()
                .matrice(matrice)
                .tresors(mapTresors)
                .montagnes(montagnes)
                .aventuriers(aventuriers)
                .build();
    }
}
