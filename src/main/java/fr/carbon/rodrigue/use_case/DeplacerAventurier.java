package fr.carbon.rodrigue.use_case;

import fr.carbon.rodrigue.entity.Aventurier;
import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.entity.OrientationEnum;
import fr.carbon.rodrigue.entity.Position;
import fr.carbon.rodrigue.entity.Tresor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static fr.carbon.rodrigue.entity.OrientationEnum.recupererOrientationEnumParLibelle;

@Component
@RequiredArgsConstructor
public class DeplacerAventurier {
    String MOUVEMENT_INVALIDE = "Mouvement invalide";

    public Carte executer(Carte carte) {
        if (Objects.isNull(carte)) {
            throw new RuntimeException("La carte n'existe pas");
        }

        int nombreTour = Stream.ofNullable(carte.getAventuriers())
                .flatMap(Collection::stream)
                .map(Aventurier::recupererListeMouvement)
                .map(List::size)
                .mapToInt(v -> v)
                .max()
                .orElseThrow(() -> new NoSuchElementException("Aucun aventurier explore la carte"));

        for (int i = 0; i < nombreTour; i++) {
            int tour = i;
            carte.getAventuriers()
                    .forEach((aventurier) -> {
                        Character action = aventurier.recupererListeMouvement().get(tour);
                        executerAction(action, aventurier, carte);
                    });
        }
        return carte;
    }

    private void executerAction(char action, Aventurier aventurier, Carte carte) {
        switch (action) {
            case 'A' -> avancerAventurier(aventurier, carte);
            case 'D', 'G' -> tournerAventurier(aventurier, action);
            default -> throw new IllegalArgumentException(MOUVEMENT_INVALIDE);
        }

    }

    private void tournerAventurier(Aventurier aventurier, char sens) {
        OrientationEnum orientation;
        switch (sens) {
            case 'D' ->
                    orientation = recupererOrientationEnumParLibelle(aventurier.getOrientation().getTournerDroite());

            case 'G' ->
                    orientation = recupererOrientationEnumParLibelle(aventurier.getOrientation().getTournerGauche());

            default -> throw new IllegalArgumentException(MOUVEMENT_INVALIDE);
        }
        aventurier.setOrientation(orientation);
    }

    private void avancerAventurier(Aventurier aventurier, Carte carte) {
        Position position = calculerPositionSuivante(aventurier.getPosition(), aventurier.getOrientation());
        if (position.getHorizontale() < 0
                || position.getVertical() < 0
                || position.getVertical() > carte.getMatrice().length - 1
                || position.getHorizontale() > carte.getMatrice()[0].length - 1
                || carte.estMontagneRencontree(position)
                || carte.estAventurierRencontree(position)) {
            return;
        }
        recupererTresor(position, carte.getTresors(), aventurier);
        aventurier.setPosition(position);
    }

    private void recupererTresor(Position position, Map<Position, Tresor> tresors, Aventurier aventurier) {
        Optional.ofNullable(tresors)
                .map(map -> map.get(position))
                .ifPresent(tresor -> {
                    aventurier.setNombreTresors(aventurier.getNombreTresors() + 1);
                    int nombreTresorsRestant = tresor.getNombreTresorsRestant() - 1;
                    tresor.setNombreTresorsRestant(nombreTresorsRestant);
                    if (nombreTresorsRestant == 0) {
                        tresors.remove(position);
                    }
                });
    }

    private Position calculerPositionSuivante(Position position, OrientationEnum orientation) {
        switch (orientation) {
            case NORD -> {
                return Position.builder()
                        .vertical(position.getVertical() - 1)
                        .horizontale(position.getHorizontale())
                        .build();
            }
            case SUD -> {
                return Position.builder()
                        .vertical(position.getVertical() + 1)
                        .horizontale(position.getHorizontale())
                        .build();
            }
            case EST -> {
                return Position.builder()
                        .vertical(position.getVertical())
                        .horizontale(position.getHorizontale() + 1)
                        .build();
            }
            case OUEST -> {
                return Position.builder()
                        .vertical(position.getVertical())
                        .horizontale(position.getHorizontale() - 1)
                        .build();
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
