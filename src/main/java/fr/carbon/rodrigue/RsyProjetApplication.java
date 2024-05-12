package fr.carbon.rodrigue;

import fr.carbon.rodrigue.entity.Carte;
import fr.carbon.rodrigue.use_case.DeplacerAventurier;
import fr.carbon.rodrigue.use_case.GenererFichierSortie;
import fr.carbon.rodrigue.use_case.LireFichierEntree;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RsyProjetApplication {

	@Autowired
	private LireFichierEntree lireFichierEntree;
	@Autowired
	private DeplacerAventurier deplacerAventurier;
	@Autowired
	private GenererFichierSortie genererFichierSortie;

	public static void main(String[] args) {
		SpringApplication.run(RsyProjetApplication.class, args);
	}

	@PostConstruct
	public void initialize()  {
		Carte carte = lireFichierEntree.executer();
		Carte carteFinal = deplacerAventurier.executer(carte);
		genererFichierSortie.executer(carteFinal);
	}
}
