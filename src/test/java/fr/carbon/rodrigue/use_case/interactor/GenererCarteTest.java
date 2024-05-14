package fr.carbon.rodrigue.use_case.interactor;

import fr.carbon.rodrigue.entity.Aventurier;
import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.entity.Case;
import fr.carbon.rodrigue.entity.Position;
import fr.carbon.rodrigue.entity.Tresor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.carbon.rodrigue.fixture.AventurierTestBuilder.unAventurier;
import static fr.carbon.rodrigue.fixture.CarteTestBuilder.unCarte;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GenererCarteTest {

    @InjectMocks
    private GenererCarte genererCarte;
    
    @Test
    void executer_cas_passant() {
        int nommbreHorizontal = 12;
        int nommbreVertical = 20;

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

        Position positionM = Position.builder()
                .horizontale(0)
                .vertical(0)
                .build();

        Case mont = Case.builder()
                .position(positionM)
                .build();
        Position positionM1 = Position.builder()
                .horizontale(10)
                .vertical(13)
                .build();
        Case mont1 = Case.builder()
                .position(positionM1)
                .build();
        List<Case> montagnes = List.of(mont, mont1);

        Aventurier aventurier = unAventurier().parDefaut().build();
        Aventurier aventurier1 = unAventurier().parDefaut().build();
        List<Aventurier> aventuriers = List.of(aventurier, aventurier1);

        Case[][] matrice = new Case[nommbreVertical][nommbreHorizontal];

        Carte result = genererCarte.executer(matrice, tresors, montagnes, aventuriers);

        Carte attendu = unCarte()
                .avecMatrice(matrice)
                .avecTresors(tresor)
                .avecTresors(tresor1)
                .avecMontagnes(montagnes)
                .avecAventuriers(aventuriers)
                .build();

        assertThat(result).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(attendu);
    }

    @Test
    public void executer_cas_non_passant_matrice_null_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> genererCarte.executer(null, null,null,null));
    }

    @Test
    public void executer_cas_non_passant_matrice_vide_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> genererCarte.executer(new Case[0][0], null, null, null));
    }
}