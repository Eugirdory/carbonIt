package fr.carbon.rodrigue.use_case;

import fr.carbon.rodrigue.entity.Aventurier;
import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.entity.Position;
import fr.carbon.rodrigue.entity.Tresor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.carbon.rodrigue.entity.OrientationEnum.EST;
import static fr.carbon.rodrigue.entity.OrientationEnum.NORD;
import static fr.carbon.rodrigue.entity.OrientationEnum.OUEST;
import static fr.carbon.rodrigue.entity.OrientationEnum.SUD;
import static fr.carbon.rodrigue.fixture.AventurierTestBuilder.unAventurier;
import static fr.carbon.rodrigue.fixture.CarteTestBuilder.unCarte;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DeplacerAventurierUTest {
    @InjectMocks
    private DeplacerAventurier deplacerAventurier;

    @Test
    void deplacement_complexe() {

        Position position = Position.builder()
                .horizontale(1)
                .vertical(1)
                .build();

        String mouvements = "AADADAGGAGGAAAA";
        Aventurier aventurier = unAventurier()
                .parDefaut()
                .avecPosition(position)
                .avecOrientation(SUD)
                .avecMouvement(mouvements)
                .build();

        Carte carte = unCarte().parDefautMontagneEtTresors()
                .avecAventuriers(aventurier)
                .build();

        Carte result = deplacerAventurier.executer(carte);

        Position positionAttendu = Position.builder()
                .horizontale(0)
                .vertical(1)
                .build();
        Aventurier aventurierAttendu = unAventurier()
                .parDefaut()
                .avecPosition(positionAttendu)
                .avecOrientation(NORD)
                .avecMouvement(mouvements)
                .avecNombreTresors(3)
                .build();

        Position positionT = Position.builder()
                .horizontale(1)
                .vertical(3)
                .build();
        Tresor tresorAttendu = Tresor.builder()
                .position(positionT)
                .nombreTresorsRestant(2)
                .build();
        Map<Position, Tresor> tresors = new HashMap<>();
        tresors.put(positionT, tresorAttendu);

        Carte attendu = unCarte().parDefautMontagneEtTresors()
                .avecTresors(tresors)
                .avecAventuriers(aventurierAttendu)
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(attendu);
    }

    @Test
    void deplacement_simple() {
        String trajectoire = "AAAADA";
        Aventurier aventurierSud = unAventurier().parDefaut()
                .avecNom("Sud")
                .avecMouvement(trajectoire)
                .avecOrientation(SUD)
                .build();
        Aventurier aventurierNord = unAventurier().parDefaut()
                .avecNom("Nord")
                .avecMouvement(trajectoire)
                .avecOrientation(NORD)
                .build();
        Aventurier aventurierEst = unAventurier().parDefaut()
                .avecNom("Est")
                .avecMouvement(trajectoire)
                .avecOrientation(EST)
                .build();
        Aventurier aventurierOuest = unAventurier().parDefaut()
                .avecNom("Ouest")
                .avecMouvement(trajectoire)
                .avecOrientation(OUEST)
                .build();

        Position sud = Position.builder()
                .horizontale(0)
                .vertical(3)
                .build();

        Position nord = Position.builder()
                .horizontale(2)
                .vertical(0)
                .build();

        Position est = Position.builder()
                .horizontale(2)
                .vertical(2)
                .build();
        Position ouest = Position.builder()
                .horizontale(0)
                .vertical(0)
                .build();

        Aventurier attenduSud = unAventurier()
                .avecNom("Sud")
                .avecPosition(sud)
                .avecMouvement(trajectoire)
                .avecOrientation(OUEST)
                .build();
        Aventurier attenduNord = unAventurier()
                .avecNom("Nord")
                .avecPosition(nord)
                .avecMouvement(trajectoire)
                .avecOrientation(EST)
                .build();
        Aventurier attenduEst = unAventurier()
                .avecNom("Est")
                .avecPosition(est)
                .avecMouvement(trajectoire)
                .avecOrientation(SUD)
                .build();
        Aventurier attenduOuest = unAventurier()
                .avecNom("Ouest")
                .avecPosition(ouest)
                .avecMouvement(trajectoire)
                .avecOrientation(NORD)
                .build();

        Carte carte = unCarte().parDefaut()
                .avecAventuriers(List.of(aventurierEst, aventurierOuest, aventurierNord, aventurierSud))
                .build();

        Carte attendu = unCarte().parDefaut()
                .avecAventuriers(List.of(attenduEst, attenduOuest, attenduNord, attenduSud))
                .build();

        Carte result = deplacerAventurier.executer(carte);
        assertThat(result).usingRecursiveComparison().isEqualTo(attendu);

    }
    @Test
    void deplacement_hors_carte() {

        String avancerToutDroit = "AAAA";
        Aventurier aventurierSud = unAventurier().parDefaut()
                .avecNom("Sud")
                .avecMouvement(avancerToutDroit)
                .avecOrientation(SUD)
                .build();
        Aventurier aventurierNord = unAventurier().parDefaut()
                .avecNom("Nord")
                .avecMouvement(avancerToutDroit)
                .avecOrientation(NORD)
                .build();
        Aventurier aventurierEst = unAventurier().parDefaut()
                .avecNom("Est")
                .avecMouvement(avancerToutDroit)
                .avecOrientation(EST)
                .build();
        Aventurier aventurierOuest = unAventurier().parDefaut()
                .avecNom("Ouest")
                .avecMouvement(avancerToutDroit)
                .avecOrientation(OUEST)
                .build();

        Position sud = Position.builder()
                .horizontale(1)
                .vertical(3)
                .build();

        Position nord = Position.builder()
                .horizontale(1)
                .vertical(0)
                .build();

        Position est = Position.builder()
                .horizontale(0)
                .vertical(1)
                .build();
        Position ouest = Position.builder()
                .horizontale(2)
                .vertical(1)
                .build();

        Aventurier attenduSud = unAventurier()
                .avecNom("Sud")
                .avecPosition(sud)
                .avecMouvement(avancerToutDroit)
                .avecOrientation(SUD)
                .build();
        Aventurier attenduNord = unAventurier()
                .avecNom("Nord")
                .avecPosition(nord)
                .avecMouvement(avancerToutDroit)
                .avecOrientation(NORD)
                .build();
        Aventurier attenduEst = unAventurier()
                .avecNom("Est")
                .avecPosition(est)
                .avecMouvement(avancerToutDroit)
                .avecOrientation(EST)
                .build();
        Aventurier attenduOuest = unAventurier()
                .avecNom("Ouest")
                .avecPosition(ouest)
                .avecMouvement(avancerToutDroit)
                .avecOrientation(OUEST)
                .build();

        Carte carte = unCarte().parDefaut()
                .avecAventuriers(List.of(aventurierEst, aventurierOuest, aventurierNord, aventurierSud))
                .build();

        Carte attendu = unCarte().parDefaut()
                .avecAventuriers(List.of(attenduEst, attenduOuest, attenduNord, attenduSud))
                .build();

        Carte result = deplacerAventurier.executer(carte);
        assertThat(result).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(attendu);
    }
}