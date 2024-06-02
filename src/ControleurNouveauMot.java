import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;

/**
 * Contrôleur pour le bouton "Nouveau Mot"
 */
public class ControleurNouveauMot implements EventHandler<ActionEvent> {
    private MotMystere modelePendu;
    private Pendu vuePendu;

    /**
     * Constructeur du contrôleur pour le bouton "Nouveau Mot"
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public ControleurNouveauMot(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * Actions à effectuer lorsque le bouton "Nouveau Mot" est cliqué
     * @param actionEvent événement Action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Optional<ButtonType> result = vuePendu.popUpLancerPartie().showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.YES)) {
            modelePendu.setMotATrouver();
            vuePendu.lancePartie();
        }        
        vuePendu.majAffichage();
    }
}
