import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.util.List;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Label motCrypte; 
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Label leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.chrono = new Chronometre();
        niveaux= new ArrayList<>();
        niveaux.add("Facile");
        niveaux.add("Medium");
        niveaux.add("Difficile");
        niveaux.add("Expert");

        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(this.modelePendu, this));

    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        panelCentral =new BorderPane();

        fenetre.setTop(titre());
        fenetre.setCenter(this.panelCentral);

        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre() {
        // Conteneur principal
        BorderPane banniere = new BorderPane();
        banniere.setStyle("-fx-background-color: lightgray;"); 
        banniere.setMinHeight(70); 

        Label titre = new Label("Jeu du Pendu");
        titre.setStyle("-fx-font-size: 45px;"); 
        HBox titreBox = new HBox(titre);
        titreBox.setAlignment(Pos.CENTER_LEFT); 
        titreBox.setPadding(new Insets(0, 20, 0, 20));

        Image homeImage = new Image("file:img/home.png");
        Image parametresImage = new Image("file:img/parametres.png");
        Image infoImage = new Image("file:img/info.png");

        ImageView homeImageView = new ImageView(homeImage);
        ImageView parametresImageView = new ImageView(parametresImage);
        ImageView infoImageView = new ImageView(infoImage);

        homeImageView.setFitWidth(30);
        homeImageView.setFitHeight(30);
        parametresImageView.setFitWidth(30);
        parametresImageView.setFitHeight(30);
        infoImageView.setFitWidth(30);
        infoImageView.setFitHeight(30);

        this.boutonMaison = new Button("",homeImageView);
        this.boutonParametres = new Button("",parametresImageView);
        Button boutonInfo = new Button("",infoImageView);

        HBox boutonsBox = new HBox(boutonMaison, boutonParametres, boutonInfo); 
        boutonsBox.setAlignment(Pos.CENTER_RIGHT);       
        boutonsBox.setPadding(new Insets(0, 20, 0, 20)); 
        banniere.setLeft(titreBox);
        banniere.setRight(boutonsBox);

        boutonMaison.setOnAction(new RetourAccueil(modelePendu, this));
        boutonInfo.setOnAction(new ControleurInfos(this));
        


        return banniere;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono(){
        TitledPane chronom = new TitledPane("Chronometre", this.chrono);
        return chronom;
    }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    // private Pane fenetreJeu(){
        // A implementer
        // Pane res = new Pane();
        // return res;
    // }

     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    // private Pane fenetreAccueil(){
        // A implementer    
        // Pane res = new Pane();
        // return res;
    // }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }


    /**
    fenetre de l'accueil
    */
    public void modeAccueil(){
        panelCentral.getChildren().clear();
        VBox vBox = new VBox(20); 
        boutonMaison.setDisable(true);
    
        bJouer = new Button("Lancer une partie");
        bJouer.setDisable(true);
        bJouer.setOnAction(new ControleurLancerPartie(modelePendu, this));
        
        RadioButton nvFacile = new RadioButton("Facile");
        RadioButton nvMedium = new RadioButton("Medium");
        RadioButton nvDifficile = new RadioButton("Difficile");
        RadioButton nvExpert = new RadioButton("Expert");
    
        ToggleGroup group = new ToggleGroup();
        nvFacile.setToggleGroup(group);
        nvMedium.setToggleGroup(group);
        nvDifficile.setToggleGroup(group);
        nvExpert.setToggleGroup(group);

    
        nvFacile.setOnAction(new ControleurNiveau(modelePendu,this));
        nvMedium.setOnAction(new ControleurNiveau(modelePendu,this));
        nvDifficile.setOnAction(new ControleurNiveau(modelePendu,this));
        nvExpert.setOnAction(new ControleurNiveau(modelePendu,this));
    
        TitledPane titledPane = new TitledPane("Niveau de difficulté", new VBox(10, nvFacile, nvMedium, nvDifficile, nvExpert));
        BorderPane.setMargin(titledPane, new Insets(20));
        
        vBox.getChildren().addAll(bJouer, titledPane);
    
        BorderPane borderPane = new BorderPane(vBox); 
        borderPane.setPadding(new Insets(20)); 
    
        panelCentral.setCenter(borderPane); 
    }
    
    /**
    re activer le bouton lancer partie apres avoir choisis un niveau
    */
    public void activerBoutonLancerPartie(){
        bJouer.setDisable(false);
    }
    

    /**
    fenetre de jeu
    */
    public void modeJeu() {
        panelCentral.getChildren().clear();
        boutonMaison.setDisable(false);
        boutonParametres.setDisable(true);
    
        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(20);
    
        motCrypte = new Label(this.modelePendu.getMotCrypte());
        motCrypte.setStyle("-fx-font-size: 20px;");
        GridPane.setMargin(motCrypte, new Insets(0, 0, 0, 180)); 
        pane.add(motCrypte, 3, 1);
    
        leNiveau = new Label();
        leNiveau.setText("Niveau " + niveaux.get(modelePendu.getNiveau()));
        leNiveau.setStyle("-fx-font-size: 20px;");
        pane.add(leNiveau, 7, 1);
    
        dessin = new ImageView();
        dessin.setImage(lesImages.get(0));
        dessin.setFitHeight(600);
        dessin.setFitWidth(500);
        pane.add(dessin, 3, 2,1,5);
    
        TitledPane chrono = this.leChrono();
        pane.add(chrono, 7, 4);

        Button nouveauMot = new Button("Nouveau mot");
        nouveauMot.setOnAction(new ControleurNouveauMot(this.modelePendu, this));
        pane.add(nouveauMot, 7, 5);

        pg= new ProgressBar();
        GridPane.setMargin(pg, new Insets(0, 0, 0, 180));
        pane.add(pg, 3, 7);

        pane.add(clavier, 3, 8);

        panelCentral.setCenter(pane);
    }
    
    

    public void modeParametres(){
        // A implementer
    }

    /** lance une partie */
    public void lancePartie(){
        this.modeJeu();
        chrono.resetTime();
        chrono.start();
        clavier.resetClavier();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage() {
        motCrypte.setText(modelePendu.getMotCrypte());
    
        int nbErreurs = modelePendu.getNbErreursMax() - modelePendu.getNbErreursRestants();
        if (nbErreurs >= 0 && nbErreurs < lesImages.size()) {
            dessin.setImage(lesImages.get(nbErreurs));
        }
    
        double progress = (double) modelePendu.getNbEssais() / modelePendu.getNbErreursMax();
        pg.setProgress(progress);
    
        String niveau = niveaux.get(modelePendu.getNiveau());
        leNiveau.setText("Niveau : " + niveau);
    

    
    }
    

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        return this.chrono;
    }

    /**
    pop up partie en cours lors de l'interremption
    */
    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\nEtes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }


    /**
    pop up lancer une partie
    */
    public Alert popUpLancerPartie(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Une partie va se lancer !\nEtes-vous sûr de vouloir continuer ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
    
    /**
    pop up regle du jeu
    */
    public Alert popUpReglesDuJeu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu");
        alert.setHeaderText("Bienvenue dans le jeu du Pendu !");
        alert.setContentText("Le jeu du Pendu est un jeu dans lequel vous devez deviner un mot en essayant différentes lettres. " +
                "Chaque fois que vous essayez une lettre qui ne fait pas partie du mot, vous perdez une chance. " +
                "Vous avez un nombre limité de chances avant de perdre la partie.\n\n" +
                "Les niveaux de difficulté sont les suivants :\n" +
                "- Facile : La première et la dernière lettre du mot sont affichées.\n" +
                "- Moyen : La première lettre et les traits d'union du mot sont affichés.\n" +
                "- Difficile : Seuls les traits d'union du mot sont affichés.\n" +  
                "- Expert : Aucune information n'est donnée sur le mot.\n\n" +
                "Bonne chance !");
        alert.getDialogPane().setPrefSize(1000,300);

        return alert;
    }
    
    /**
    pop up partie gagner
    */
    public Alert popUpMessageGagne() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bravo ! Vous avez gagné !", ButtonType.OK);
        alert.setHeaderText("Vous avez gagné :)");
        alert.setTitle("Jeu du pendu");
        return alert;
    }
    
    /**
    pop up partie perdu
    */
    public Alert popUpMessagePerdu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez perdu\nLe mot à trouver était " + this.modelePendu.getMotATrouve(), ButtonType.OK);
        alert.setHeaderText("Vous avez perdu :(");
        alert.setTitle("Jeu du pendu");
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
