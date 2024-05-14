package fr.carbon.rodrigue.use_case;

import fr.carbon.rodrigue.entity.Aventurier;
import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.entity.Case;
import fr.carbon.rodrigue.entity.Position;
import fr.carbon.rodrigue.entity.Tresor;
import fr.carbon.rodrigue.use_case.interactor.GenererCarte;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static fr.carbon.rodrigue.entity.OrientationEnum.recupererOrientationEnumParLibelle;

@Component
@RequiredArgsConstructor
public class LireFichierEntree {
    private static final String REPERTOIRE_DEPOT = "src/main/resources/depot/";
    private static final String SEPARATEUR = "-";
    private final GenererCarte genererCarte;

    private final List<Case> montagnes = new ArrayList<>();
    private final List<Aventurier> aventuriers = new ArrayList<>();
    private final List<Tresor> tresors = new ArrayList<>();
    private int nommbreHorizontal = 0;
    private int nommbreVertical = 0;

    public Carte executer() {

        File repertoire = new File(REPERTOIRE_DEPOT);
        File fichier = Objects.requireNonNull(repertoire.listFiles())[0];
        if (!fichier.isFile()) {
            throw new RuntimeException("Fichier non valide: " + fichier.getName());
        }
        try {
        FileReader fr = new FileReader(fichier);
        BufferedReader br = new BufferedReader(fr);
        String ligne;
            while ((ligne = br.readLine()) != null) {
                List<String> collecte = parserLigne(ligne);
                String typeElement = collecte.remove(0);
                creerElement(typeElement, collecte, this.montagnes, this.aventuriers, this.tresors);
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException("Fichier non conforme: " + fichier.getName(),e);
        }
        Case[][] matrice = new Case[this.nommbreVertical][this.nommbreHorizontal];
        return genererCarte.executer(matrice, this.tresors, this.montagnes, this.aventuriers);
    }

    private ArrayList<String> parserLigne(String ligne) {
        return new ArrayList<>(Stream.of(ligne.replaceAll("\\s", Strings.EMPTY).split(SEPARATEUR, -1))
                .toList());
    }

    private void creerElement(String typeElement, List<String> collect, List<Case> montagnes, List<Aventurier> aventuriers, List<Tresor> tresors) {
        switch (typeElement) {
            case "C" -> {
                this.nommbreHorizontal = Integer.parseInt(collect.get(0));
                this.nommbreVertical = Integer.parseInt(collect.get(1));
            }
            case "M" -> {
                Case build = Case.builder()
                        .position(Position.builder()
                                .horizontale(Integer.parseInt(collect.get(0)))
                                .vertical(Integer.parseInt(collect.get(1)))
                                .build())
                        .build();
                montagnes.add(build);
            }
            case "T" -> {
                Tresor build = Tresor.builder()
                        .position(Position.builder()
                                .horizontale(Integer.parseInt(collect.get(0)))
                                .vertical(Integer.parseInt(collect.get(1)))
                                .build())
                        .nombreTresorsRestant(Integer.parseInt(collect.get(2)))
                        .build();
                tresors.add(build);
            }
            case "A" -> {
                Aventurier build = Aventurier.builder()
                        .nom(collect.get(0))
                        .position(Position.builder()
                                .horizontale(Integer.parseInt(collect.get(1)))
                                .vertical(Integer.parseInt(collect.get(2)))
                                .build())
                        .orientation(recupererOrientationEnumParLibelle(collect.get(3)))
                        .mouvement(collect.get(4))
                        .build();
                aventuriers.add(build);
            }
        }
    }
}
