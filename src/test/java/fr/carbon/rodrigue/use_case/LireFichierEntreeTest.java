package fr.carbon.rodrigue.use_case;

import fr.carbon.rodrigue.entity.Aventurier;
import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.entity.Case;
import fr.carbon.rodrigue.entity.Position;
import fr.carbon.rodrigue.entity.Tresor;
import fr.carbon.rodrigue.use_case.interactor.GenererCarte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static fr.carbon.rodrigue.entity.OrientationEnum.SUD;
import static fr.carbon.rodrigue.fixture.AventurierTestBuilder.unAventurier;
import static fr.carbon.rodrigue.fixture.CarteTestBuilder.unCarte;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LireFichierEntreeTest {

    @InjectMocks
    private LireFichierEntree lireFichierEntree;
    @Mock
    private GenererCarte genererCarte;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(lireFichierEntree, "REPERTOIRE_DEPOT", "src/test/resources");
    }

    @Test
    public void executer_cas_passant() {

        Aventurier aventurierAttendu = unAventurier()
                .parDefaut()
                .avecNom("Lara")
                .avecOrientation(SUD)
                .avecMouvement("AADADAGGA")
                .build();
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

        List<Tresor> tresors = List.of(tresor, tresor1);
        Case mont = Case.builder()
                .position(Position.builder()
                        .horizontale(1)
                        .vertical(0)
                        .build())
                .build();
        Case mont1 = Case.builder()
                .position(Position.builder()
                        .horizontale(2)
                        .vertical(1)
                        .build())
                .build();

        List<Case> montagnes = List.of(mont, mont1);
        Carte attendu = unCarte().parDefautMontagneEtTresors()
                .avecMontagnes(montagnes)
                .avecAventuriers(aventurierAttendu)
                .build();

        when(genererCarte.executer(eq(new Case[4][3]), eq(tresors), eq(montagnes), eq(List.of(aventurierAttendu)))).thenReturn(attendu);
        Carte result = lireFichierEntree.executer();
        assertThat(result).usingRecursiveComparison().isEqualTo(attendu);

    }
}