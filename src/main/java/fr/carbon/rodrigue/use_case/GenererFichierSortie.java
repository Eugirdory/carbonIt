package fr.carbon.rodrigue.use_case;

import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.entity.Case;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class GenererFichierSortie {
    private static final String REPERTOIRE = "src/main/resources/resultat/fichier.txt";

    public void executer(Carte carte) {
        if (Objects.isNull(carte)) {
            throw new RuntimeException("Pas de carte, pas de resultat");
        }
        try {
            FileWriter fileWriter = new FileWriter(REPERTOIRE, false);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            retranscrireCarte(carte, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void retranscrireCarte(Carte carte, BufferedWriter writer) throws IOException {

        writer.write(String.format("C - %s - %s", carte.getMatrice()[0].length, carte.getMatrice().length));
        writer.newLine();

        Stream.ofNullable(carte.getMontagnes())
                .flatMap(Collection::stream)
                .map(Case::getPosition)
                .forEach((mont) -> {
                    try {
                        writer.write(String.format("M - %s - %s", mont.getHorizontale(), mont.getVertical()));
                        writer.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException("Une erreur est survenue lors de l'ecriture de la montagne: " + mont, e);
                    }
                });
        Optional.ofNullable(carte.getTresors())
                .map(Map::values)
                .orElseGet(ArrayList::new)
                .forEach((tresor) -> {
                    try {
                        writer.write(String.format("T - %s - %s - %s", tresor.getPosition().getHorizontale(), tresor.getPosition().getVertical(), tresor.getNombreTresorsRestant()));
                        writer.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException("Une erreur est survenue lors de l'ecriture du tresor: " + tresor, e);
                    }
                });
        Stream.ofNullable(carte.getAventuriers())
                .flatMap(Collection::stream)
                .forEach((aventurier) -> {
                    try {
                        writer.write(String.format("A - %s - %s - %s - %s - %s", aventurier.getNom(), aventurier.getPosition().getHorizontale(), aventurier.getPosition().getVertical(), aventurier.getOrientation().getCode(), aventurier.getNombreTresors()));
                        writer.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException("Une erreur est survenue lors de l'ecriture dl'aventurier: " + aventurier, e);
                    }
                });
    }
}
