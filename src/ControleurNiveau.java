import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    private Pendu pendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu, Pendu pendu) {
        this.modelePendu=modelePendu;
        this.pendu=pendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        System.out.println(nomDuRadiobouton);

        switch (nomDuRadiobouton) {
            case "Facile":
                modelePendu.setNiveau(MotMystere.FACILE);
                break;
            case "Medium":
                modelePendu.setNiveau(MotMystere.MOYEN);
                break;
            case "Difficile":
                modelePendu.setNiveau(MotMystere.DIFFICILE);
                break;
            case "Expert":
            modelePendu.setNiveau(MotMystere.EXPERT);
                break;
        }
        pendu.activerBoutonLancerPartie();
        this.modelePendu.setMotATrouver();
    }
}
