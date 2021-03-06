package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by josepablo on 9/14/17.
 */

public class ArcadeMode extends ScreenTemplate {

    private final SheepEm sheepEm;

    private boolean isUp;

    /*****************************************/
    private ImageButton noMusicBtn;
    private ImageButton musicBtn;
    private ImageButton fxBtn;
    private ImageButton noFxBtn;

    /*****************************************/

    // Texturas/Parte Gráfica ----------------------------------------------------------------------
    private Texture continueButton;
    private Texture pauseButton;
    private Texture homeButton;
    private Texture background;
    private Stage escenaJuego;
    private Texture time;
    private Texture life;
    private Texture life_lost;
    private BitmapFont font;

    // Texturas de colores
    private Texture oveArrBlue;
    private Texture oveArrRed;
    private Texture oveArrWhite;
    private Texture oveArrYellow;
    private Texture oveArrMovBlue;
    private Texture oveArrMovRed;
    private Texture oveArrMovWhite;
    private Texture oveArrMovYellow;

    private Texture oveAbBlue;
    private Texture oveAbMovBlue;
    private Texture oveAbRed;
    private Texture oveAbMovRed;
    private Texture oveAbWhite;
    private Texture oveAbMovWhite;
    private Texture oveAbYellow;
    private Texture oveAbMovYellow;

    private Texture oveIzqBlue;
    private Texture oveIzqMovBlue;
    private Texture oveIzqRed;
    private Texture oveIzqMovRed;
    private Texture oveIzqWhite;
    private Texture oveIzqMovWhite;
    private Texture oveIzqYellow;
    private Texture oveIzqMovYellow;

    private Texture oveDerBlue;
    private Texture oveDerMovBlue;
    private Texture oveDerRed;
    private Texture oveDerMovRed;
    private Texture oveDerWhite;
    private Texture oveDerMovWhite;
    private Texture oveDerYellow;
    private Texture oveDerMovYellow;

    private Texture oveArrRainbow;
    private Texture oveArrMovRainbow;
    private Texture oveAbRainbow;
    private Texture oveAbMovRainbow;
    private Texture oveIzqRainbow;
    private Texture oveIzqMovRainbow;
    private Texture oveDerRainbow;
    private Texture oveDerMovRainbow;

    private Texture oveEstaticBlue;
    private Texture oveEstaticRed;
    private Texture oveEstaticWhite;
    private Texture oveEstaticYellow;
    private Texture oveEstaticRainbow;

    private Boolean played = false;

    private Music sheep;

    // Arreglo de ovejas ---------------------------------------------------------------------------
    private Array<Sheep> arrOvejas;
    private Sheep ovejaMoviendo = null;
    private int ovejaMovX;
    private int ovejaMovY;
    private final int cantOve = 1000;
    private int contOvejas = 0;
    private String arrColores[] = {"WHITE","BLUE","RED","YELLOW","RAINBOW"};
    private String arrTipos[] = {"NORMAL","ALIEN","RAINBOW"};

    private float salida;
    private float velocidadOve = 1.0f;
    private int lifes;

    private float totalTime =120; // In seconds

    private EstadoJuego estado;

    // Escenas -------------------------------------------------------------------------------------

    private endScene endScene;
    private EscenaPausa escenaPausa;



    private float tiempo;
    private float sheepTimer;
    private Texture barn;
    private Texture cr;
    private Texture barn_shadow;

    private BitmapFont numberFont;

    private int sheepCounter;
    private Texture arcadeTop;
    private Texture opaque;


    public ArcadeMode(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        cargarTexturas();
        cargarOvejas();
        crearEscenaJuego();
        font = new BitmapFont(Gdx.files.internal("Intro.fnt"));
        numberFont = new BitmapFont(Gdx.files.internal("Fonts/numbersFont.fnt"));
        endScene = new endScene(view,batch);
        estado = EstadoJuego.JUGANDO;
        Gdx.input.setInputProcessor(escenaJuego);
        Gdx.input.setCatchBackKey(true);
        sheepTimer = 1.5f;
        sheep = Gdx.audio.newMusic(Gdx.files.internal("SFX/sheep_sound.mp3"));
    }

    private void crearEscenaJuego() {

        escenaJuego = new Stage(view);

        escenaJuego.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode==Input.Keys.BACK){
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    sheepEm.pauseLevelOneMusic();
                }
                return true;
            }
        });


        // Botón de pausa --------------------------------------------------------------------------

        Texture pressedPauseButton = new Texture("Buttons/pressed/pressedPauseButton.png");
        TextureRegionDrawable trdPausepr = new TextureRegionDrawable(new TextureRegion(pressedPauseButton));
        TextureRegionDrawable trdPause = new TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton imPause = new ImageButton(trdPause, trdPausepr);
        imPause.setPosition(887, 1743);
        escenaJuego.addActor(imPause);

        imPause.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estado = EstadoJuego.PAUSADO;
                escenaPausa = new EscenaPausa(view,batch);
                detenerOveja(true);
                Gdx.input.setInputProcessor(escenaPausa);
            }
        } );

        // Tomar y arrastrar ovejas ----------------------------------------------------------------

        escenaJuego.addListener(new DragListener(){
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
                for (Sheep sheep : arrOvejas){
                    if (!cordenadasCorral(x,y, sheep.getColor()) && !sheep.isEnLlamas()){
                        if (sheep.comparar(x,y)){
                            ovejaMoviendo = sheep;
                            ovejaMovX = (int) ovejaMoviendo.getx();
                            ovejaMovY = (int) ovejaMoviendo.gety();
                            ovejaMoviendo.setEstado(Sheep.Estado.MOVIENDO);
                            break;
                        }
                    }
                }
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                isUp = true;
                if (ovejaMoviendo == null){ return; }
                if(pref.getBoolean("fxOn")){
                    sheep.setVolume(0.8f);
                    sheep.play();
                }
                ovejaMoviendo.setX(x - ovejaMoviendo.getAncho()/2);
                ovejaMoviendo.setY(y - ovejaMoviendo.getAlto()/2);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                if(ovejaMoviendo != null){
                    // verificar si está en el corral
                    if(cordenadasCorral(x,y,ovejaMoviendo.getColor())){
                        if (ovejaMoviendo.getColor().equals("RAINBOW")){
                            ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                            //lifes++;
                            contOvejas++;
                            totalTime += 10;
                            Gdx.app.log("oveja","en corral: " + contOvejas);
                            ovejaMoviendo = null;
                        }else {
                            ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                            contOvejas++;
                            Gdx.app.log("oveja","en corral: " + contOvejas);
                            ovejaMoviendo = null;
                        }
                    }else{
                        if(!cordenadasLineales(x,y)){
                            ovejaMoviendo.setEstado(Sheep.Estado.BOOM);
                            //lifes--;
                            contOvejas--;
                            Gdx.app.log("corral", "Corral incorrecto");
                            ovejaMoviendo = null;
                        }else{
                            ovejaMoviendo.setSeMovio(false);
                            ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                            ovejaMoviendo.setX(ovejaMovX);
                            ovejaMoviendo.setY(ovejaMovY);
                            ovejaMoviendo = null;
                        }
                    }
                }
            }

        });

    }

    // Validar corral correcto ---------------------------------------------------------------------
    public boolean cordenadasCorral(float xP, float yP, String color) {
        if ((xP >= 0 && xP <= 405 && yP >= 110 && yP <= 720
                && (color.equals("RED") || color.equals("RAINBOW"))) ||
                (xP >= 677 && xP <= 1080 && yP >= 110 && yP <= 720
                        && (color.equals("BLUE") || color.equals("RAINBOW"))) ||
                (xP >= 0 && xP <= 405 && yP >= 1105 && yP <= 1662
                        && (color.equals("WHITE") || color.equals("RAINBOW"))) ||
                (xP >= 677 && xP <= 1080 && yP >= 1105 && yP <= 1662
                        && (color.equals("YELLOW") || color.equals("RAINBOW")))){
            return true;
        }
        return false;
    }

    // Validar camino de ovejas --------------------------------------------------------------------
    public boolean cordenadasLineales(float xP, float yP){
        if ( (xP >= 406 && xP <= 676 && yP >= 0 && yP <= 1920) ||
                (xP >= 0 && xP <= 1080 && yP >= 721 && yP <= 1104) ){
            return true;
        }
        return false;
    }

    // Detener ovejas en el sheepEm ------------------------------------------------------------------
    private void detenerOveja(boolean stop) {
        if (stop){
            for (Sheep sheep : arrOvejas){
                sheep.setEstado(Sheep.Estado.STOP);
            }
        }else{
            for (Sheep sheep : arrOvejas){
                sheep.setEstado(Sheep.Estado.CONTINUAR);
            }
        }
    }

    // Método que carga las ovejas en el sheepEm -----------------------------------------------------
    private void cargarOvejas(){
        // Crear arreglo Ovejas si no existe
        if (arrOvejas == null) {
            arrOvejas = new Array<Sheep>(cantOve);

            Sheep rainbowArr = new Sheep(oveArrRainbow, oveArrMovRainbow, oveEstaticRainbow,
                    Sheep.Estado.ARRIBA, arrColores[4], arrTipos[2]);
            arrOvejas.add(rainbowArr);

            Sheep rainbowAb = new Sheep(oveAbRainbow, oveAbMovRainbow, oveEstaticRainbow,
                    Sheep.Estado.ABAJO, arrColores[4], arrTipos[2]);
            arrOvejas.add(rainbowAb);

            Sheep rainbowIzq = new Sheep(oveIzqRainbow, oveIzqMovRainbow, oveEstaticRainbow,
                    Sheep.Estado.IZQUIERDA, arrColores[4], arrTipos[2]);
            arrOvejas.add(rainbowIzq);

            Sheep rainbowDer = new Sheep(oveDerRainbow, oveDerMovRainbow, oveEstaticRainbow,
                    Sheep.Estado.DERECHA, arrColores[4], arrTipos[2]);
            arrOvejas.add(rainbowDer);
        }
        // Llenar arreglo de ovejas por tiempo
        if (arrOvejas.size < cantOve){

            Sheep ove;

            for (int i = 4; i < 5; i++){
                int random = (int) (Math.random()*4)+1;
                int randomColor = (int) (Math.random()*4)+1;

                if (random == 1){
                    switch (randomColor){
                        case 1:
                            ove = new Sheep(oveArrWhite, oveArrMovWhite, oveEstaticWhite,
                                    Sheep.Estado.ARRIBA, arrColores[0], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 2:
                            ove = new Sheep(oveArrBlue, oveArrMovBlue, oveEstaticBlue,
                                    Sheep.Estado.ARRIBA, arrColores[1], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 3:
                            ove = new Sheep(oveArrRed, oveArrMovRed, oveEstaticRed,
                                    Sheep.Estado.ARRIBA, arrColores[2], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 4:
                            ove = new Sheep(oveArrYellow, oveArrMovYellow, oveEstaticYellow,
                                    Sheep.Estado.ARRIBA, arrColores[3], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                    }
                }else if (random == 2){
                    switch (randomColor){
                        case 1:
                            ove = new Sheep(oveAbWhite, oveAbMovWhite, oveEstaticWhite,
                                    Sheep.Estado.ABAJO, arrColores[0], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 2:
                            ove = new Sheep(oveAbBlue, oveAbMovBlue, oveEstaticBlue,
                                    Sheep.Estado.ABAJO, arrColores[1], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 3:
                            ove = new Sheep(oveAbRed, oveAbMovRed, oveEstaticRed,
                                    Sheep.Estado.ABAJO, arrColores[2], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 4:
                            ove = new Sheep(oveAbYellow, oveAbMovYellow, oveEstaticYellow,
                                    Sheep.Estado.ABAJO, arrColores[3], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                    }
                }else if (random == 3){
                    switch (randomColor){
                        case 1:
                            ove = new Sheep(oveIzqWhite, oveIzqMovWhite, oveEstaticWhite,
                                    Sheep.Estado.IZQUIERDA, arrColores[0], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 2:
                            ove = new Sheep(oveIzqBlue, oveIzqMovBlue, oveEstaticBlue,
                                    Sheep.Estado.IZQUIERDA, arrColores[1], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 3:
                            ove = new Sheep(oveIzqRed, oveIzqMovRed, oveEstaticRed,
                                    Sheep.Estado.IZQUIERDA, arrColores[2], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 4:
                            ove = new Sheep(oveIzqYellow, oveIzqMovYellow, oveEstaticYellow,
                                    Sheep.Estado.IZQUIERDA, arrColores[3], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                    }
                }else if (random == 4){
                    switch (randomColor){
                        case 1:
                            ove = new Sheep(oveDerWhite, oveDerMovWhite, oveEstaticWhite,
                                    Sheep.Estado.DERECHA, arrColores[0], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 2:
                            ove = new Sheep(oveDerBlue, oveDerMovBlue, oveEstaticBlue,
                                    Sheep.Estado.DERECHA, arrColores[1], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 3:
                            ove = new Sheep(oveDerRed, oveDerMovRed, oveEstaticRed,
                                    Sheep.Estado.DERECHA, arrColores[2], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 4:
                            ove = new Sheep(oveDerYellow, oveDerMovYellow, oveEstaticYellow,
                                    Sheep.Estado.DERECHA, arrColores[3], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                    }
                }
            }
        }
    }

    // Método que elimina las ovejas en el sheepEm ---------------------------------------------------
    private void eliminarOveja(){
        for (int i = 0; i < arrOvejas.size; i++){
            if (arrOvejas.get(i).getEstado().equals(Sheep.Estado.ARRIBA)){
                if (arrOvejas.get(i).gety() <= -100){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    lifes--;
                    playLost();
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Sheep.Estado.ABAJO)){
                if (arrOvejas.get(i).gety() >= 2100){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    lifes--;
                    playLost();
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Sheep.Estado.IZQUIERDA)){
                if (arrOvejas.get(i).getx() >= 1180){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    lifes--;
                    playLost();
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Sheep.Estado.DERECHA)){
                if (arrOvejas.get(i).getx() <= -200){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    lifes--;
                    playLost();
                    break;
                }
            }
        }
    }

    private void playLost() {
        if(pref.getBoolean("fxOn")){
            sheepEm.lostSheep.play();
        }
    }


    // Método que carga todas las texturas del sheepEm -----------------------------------------------
    private void cargarTexturas() {
        background = new Texture("gBg.png");
        pauseButton = new Texture("Buttons/unpressed/pauseButton.png");
        time = new Texture("time.png");
        life = new Texture("life.png");
        life_lost = new Texture("life_lost.png");
        barn = new Texture("day_barn.png");
        cr = new Texture("cr.png");
        barn_shadow = new Texture("shadow.png");

        //ovejas de colores
        oveArrBlue = new Texture("Sheep/Level 1/Blue/sheep_down_blue.png");
        oveArrMovBlue = new Texture("Sheep/Level 1/Blue/sheep_moving_down_blue.png");
        oveArrRed = new Texture("Sheep/Level 1/Red/sheep_down_red.png");
        oveArrMovRed = new Texture("Sheep/Level 1/Red/sheep_moving_down_red.png");
        oveArrWhite = new Texture("Sheep/Level 1/White/sheep_down_white.png");
        oveArrMovWhite = new Texture("Sheep/Level 1/White/sheep_moving_down_white.png");
        oveArrYellow = new Texture("Sheep/Level 1/Yellow/sheep_down_yellow.png");
        oveArrMovYellow = new Texture("Sheep/Level 1/Yellow/sheep_moving_down_yellow.png");

        oveAbBlue = new Texture("Sheep/Level 1/Blue/sheep_up_blue.png");
        oveAbMovBlue = new Texture("Sheep/Level 1/Blue/sheep_moving_up_blue.png");
        oveAbRed = new Texture("Sheep/Level 1/Red/sheep_up_red.png");
        oveAbMovRed = new Texture("Sheep/Level 1/Red/sheep_moving_up_red.png");
        oveAbWhite = new Texture("Sheep/Level 1/White/sheep_up_white.png");
        oveAbMovWhite = new Texture("Sheep/Level 1/White/sheep_moving_up_white.png");
        oveAbYellow = new Texture("Sheep/Level 1/Yellow/sheep_up_yellow.png");
        oveAbMovYellow = new Texture("Sheep/Level 1/Yellow/sheep_moving_up_yellow.png");

        oveIzqBlue = new Texture("Sheep/Level 1/Blue/sheep_left_blue.png");
        oveIzqMovBlue = new Texture("Sheep/Level 1/Blue/sheep_moving_left_blue.png");
        oveIzqRed = new Texture("Sheep/Level 1/Red/sheep_left_red.png");
        oveIzqMovRed = new Texture("Sheep/Level 1/Red/sheep_moving_left_red.png");
        oveIzqWhite = new Texture("Sheep/Level 1/White/sheep_left_white.png");
        oveIzqMovWhite = new Texture("Sheep/Level 1/White/sheep_moving_left_white.png");
        oveIzqYellow = new Texture("Sheep/Level 1/Yellow/sheep_left_yellow.png");
        oveIzqMovYellow = new Texture("Sheep/Level 1/Yellow/sheep_moving_left_yellow.png");

        oveDerBlue = new Texture("Sheep/Level 1/Blue/sheep_right_blue.png");
        oveDerMovBlue = new Texture("Sheep/Level 1/Blue/sheep_moving_right_blue.png");
        oveDerRed = new Texture("Sheep/Level 1/Red/sheep_right_red.png");
        oveDerMovRed = new Texture("Sheep/Level 1/Red/sheep_moving_right_red.png");
        oveDerWhite = new Texture("Sheep/Level 1/White/sheep_right_white.png");
        oveDerMovWhite = new  Texture("Sheep/Level 1/White/sheep_moving_right_white.png");
        oveDerYellow = new Texture("Sheep/Level 1/Yellow/sheep_right_yellow.png");
        oveDerMovYellow = new Texture("Sheep/Level 1/Yellow/sheep_moving_right_yellow.png");

        // oveja Rainbow
        oveArrRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_down.png");
        oveArrMovRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_moving_down.png");
        oveAbRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_up.png");
        oveAbMovRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_moving_up.png");
        oveIzqRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_left.png");
        oveIzqMovRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_moving_left.png");
        oveDerRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_right.png");
        oveDerMovRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_moving_right.png");

        // oveja estática
        oveEstaticBlue = new Texture("Sheep/Level 1/Blue/sheep_grazing.png");
        oveEstaticRed = new Texture("Sheep/Level 1/Red/sheep_grazing.png");
        oveEstaticWhite = new Texture("Sheep/Level 1/White/sheep_grazing.png");
        oveEstaticYellow = new Texture("Sheep/Level 1/Yellow/sheep_grazing.png");
        oveEstaticRainbow = new Texture("Sheep/Level 2/Rainbow/sheep_grazing.png");

        arcadeTop =  new Texture("arcadeTop.png");
        opaque = new Texture("opaque.png");
    }



    @Override
    public void render(float delta) {


        clearScreen(0, 0, 0);
        batch.setProjectionMatrix(camera.combined);


        int minutes = ((int)totalTime) / 60;
        int seconds = ((int)totalTime) % 60;


        if (estado == EstadoJuego.JUGANDO) {
            salida += Gdx.graphics.getDeltaTime();
            tiempo += Gdx.graphics.getDeltaTime();
            if(totalTime>=1) totalTime -=Gdx.graphics.getDeltaTime();
            sheepTimer -= Gdx.graphics.getDeltaTime();
        }

        if (sheepTimer<=0){
            cargarOvejas();
            if(tiempo>60){
                sheepTimer = 0.8f;
            }
            if(tiempo<60){
                sheepTimer = 1.3f;
            }
            if(tiempo>90){
                sheepTimer = 0.3f;
            }
            if(tiempo>110){
                sheepTimer = 0.1f;
            }

        }

        if(seconds<=0 && minutes<=0){
            estado = EstadoJuego.TERMINADO;
        }

        if(estado ==  EstadoJuego.TERMINADO){
            Gdx.input.setInputProcessor(endScene);
            if(contOvejas>pref.getInteger("highscore")){
                pref.putInteger("highscore",contOvejas);
                pref.flush();
            }

            endScene.draw();
            detenerOveja(true);
            if(pref.getBoolean("musicOn")){
                sheepEm.win.play();
                sheepEm.win.setLooping(true);
            }
            /*------------------------HIGH SCORE SAVE ---------------------*/

        }

        /*------------------------BATCH BEGIN ---------------------*/

        batch.begin();

        if(estado == EstadoJuego.JUGANDO || estado == EstadoJuego.PAUSADO){
            batch.draw(background,0,0);

            batch.draw(barn_shadow,467,1709);

            if (tiempo >= 30.0){ // a los 30 seg sale la oveja arcoiris arriba
                arrOvejas.get(0).setVelocidad(velocidadOve);
                arrOvejas.get(0).render(batch);
            }

            if (tiempo >= 60.0){ // a los 60 seg sale la oveja arcoiris abajo
                arrOvejas.get(1).setVelocidad(velocidadOve);
                arrOvejas.get(1).render(batch);
            }

            if (tiempo >= 90.0){ // a los 90 seg sale la oveja arcoiris izquierda
                arrOvejas.get(2).setVelocidad(velocidadOve);
                arrOvejas.get(2).render(batch);
            }

            if (tiempo >= 110.0){ // a los 110 seg sale la oveja arcoiris derecha
                arrOvejas.get(3).setVelocidad(velocidadOve);
                arrOvejas.get(3).render(batch);
            }


            for (int i = 4; i < arrOvejas.size; i++) {
                if (salida <= 10) {
                    arrOvejas.get(i).setVelocidad(velocidadOve);
                    arrOvejas.get(i).render(batch);
                } else {
                    velocidadOve += 0.5f;
                    salida = 0;
                }

            }

            batch.draw(barn,1,1709);

            batch.draw(cr,0,1617);

            batch.draw(arcadeTop,40,1764);

        /*------------------------------- SHEEP COUNTER ON SCREEN --------------------------------*/

            font.draw(batch, Integer.toString(contOvejas), 200, 1848);

        /*-------------------------------- COUNTBACK ON SCREEN -----------------------------------*/

            if(minutes>0 && minutes<2){
                if(seconds<10){
                    font.draw(batch,Integer.toString(minutes)+ ":0"+ Integer.toString(seconds),715,1848);
                }
                else{
                    font.draw(batch,Integer.toString(minutes)+ ":"+ Integer.toString(seconds),715,1848);
                }

            }else{
                if(seconds<10){
                    font.draw(batch,Integer.toString(minutes)+ ":0"+ Integer.toString(seconds),699,1848);
                }
                else{
                    font.draw(batch,Integer.toString(minutes)+ ":"+ Integer.toString(seconds),710,1848);
                }

            }
        }

        /*-------------------------------- FINAL SCORE ON SCREEN ---------------------------------*/
        if(estado==EstadoJuego.TERMINADO) {

            int x = 390;
            if (contOvejas < 10) {
                numberFont.draw(batch, "00" + Integer.toString(contOvejas), x, 1169);
            }
            if (contOvejas > 9 && contOvejas < 100) {
                numberFont.draw(batch, "0" + Integer.toString(contOvejas), x, 1169);
            } else {
                numberFont.draw(batch, Integer.toString(contOvejas), 380, 1169);
            }


        /*-------------------------------- HIGH SCORE ON SCREEN ---------------------------------*/

            if (pref.getInteger("highscore") < 10) {
                font.draw(batch, "00" + Integer.toString(pref.getInteger("highscore")), 470, 570);
            }
            if (pref.getInteger("highscore") > 9 && pref.getInteger("highscore") < 100) {
                font.draw(batch, "0" + Integer.toString(pref.getInteger("highscore")), 470, 570);
            }
            if (pref.getInteger("highscore") > 99) {
                font.draw(batch, Integer.toString(pref.getInteger("highscore")), 470, 570);
            }

        }

        batch.end();
        /*------------------------BATCH END ---------------------*/


        if(estado == EstadoJuego.JUGANDO || estado == EstadoJuego.PAUSADO){
            escenaJuego.draw();
        }




        if (estado == EstadoJuego.PAUSADO) {
            escenaPausa.draw();

            if(pref.getBoolean("musicOn")){
                musicBtn.setPosition(373,431);
                escenaPausa.addActor(musicBtn);
                noMusicBtn.remove();

            }
                if(!pref.getBoolean("musicOn")){
                    musicBtn.setPosition(373,431);
                escenaPausa.addActor(noMusicBtn);
                musicBtn.remove();
            }

            if(pref.getBoolean("fxOn")){
                fxBtn.setPosition(561,431);
                escenaPausa.addActor(fxBtn);
                noFxBtn.remove();

            }
            if(!pref.getBoolean("fxOn")){
                fxBtn.setPosition(561,431);
                escenaPausa.addActor(noFxBtn);
                fxBtn.remove();
            }

        }




        if(pref.getBoolean("musicOn")){
            if(estado == EstadoJuego.JUGANDO){
                sheepEm.playLevelOneMusic();
            }else{
                sheepEm.pauseLevelOneMusic();
            }

        }
        if(!pref.getBoolean("musicOn")){
            sheepEm.pauseLevelOneMusic();
        }
        eliminarOveja();

        pref.flush();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }

    enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        TERMINADO
    }
    // Escena para el menú de pausa ----------------------------------------------------------------
    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista,batch);


            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);

            Texture pauseRectangle = new Texture("pauseRectangle.png");
            TextureRegionDrawable trdRect = new TextureRegionDrawable(new TextureRegion(pauseRectangle));
            Image rectangle = new Image(trdRect);
            rectangle.setPosition(71,253);
            this.addActor(rectangle);


            Texture pressedContinueButton = new Texture("Buttons/pressed/pressedContinueButton.png");
            TextureRegionDrawable trdContinuepr = new TextureRegionDrawable(new TextureRegion(pressedContinueButton));
            continueButton = new Texture("Buttons/unpressed/continueButton.png");
            TextureRegionDrawable trdContinue = new TextureRegionDrawable(new TextureRegion(continueButton));
            ImageButton btnContinue = new ImageButton(trdContinue,trdContinuepr);
            btnContinue.setPosition(383,968);
            btnContinue.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Cambio el estado de sheepEm a JUGANDO y regreso el poder a la escenaJuego
                    estado = EstadoJuego.JUGANDO;
                    detenerOveja(false);
                    sheepEm.playLevelOneMusic();
                    Gdx.input.setInputProcessor(escenaJuego);
                }
            });
            this.addActor(btnContinue);


            Texture pressedHomeButton = new Texture("Buttons/pressed/PressedLevelsMenuButton.png");
            TextureRegionDrawable trdHomepr = new TextureRegionDrawable(new
                    TextureRegion(pressedHomeButton));
            homeButton = new Texture("Buttons/unpressed/LevelsMenuButton.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(
                    new TextureRegion(homeButton));
            ImageButton homeBtn = new ImageButton(trdHome, trdHomepr);
            homeBtn.setPosition(285,695);
            homeBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    sheepEm.stopLevelOneMusic();
                }
            });
            this.addActor(homeBtn);

            Texture pressedRestartButton = new Texture("Buttons/pressed/PressedRetryLevelButton.png");
            TextureRegionDrawable trdRestartpr =  new TextureRegionDrawable(new TextureRegion(pressedRestartButton));
            Texture restartButton = new Texture("Buttons/unpressed/RetryLevelButton.png");
            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));
            ImageButton restartBtn = new ImageButton(trdRestart, trdRestartpr);
            restartBtn.setPosition(586,695);
            restartBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    sheepEm.stopLevelOneMusic();
                    sheepEm.setScreen(new ArcadeMode(sheepEm));
                    sheepEm.playLevelOneMusic();
                }
            });
            this.addActor(restartBtn);

            Texture pauseMusicButton = new Texture("Buttons/unpressed/MusicPause.png");
            TextureRegionDrawable pauseMusicButtonTrd = new TextureRegionDrawable(new TextureRegion(pauseMusicButton));
            Texture pauseMusicButtonPr = new Texture("Buttons/pressed/PressedMusicPause.png");
            TextureRegionDrawable pauseMusicButtonPrTrd = new TextureRegionDrawable(new TextureRegion(pauseMusicButtonPr));

            Texture pauseNoMusicButton = new Texture("Buttons/unpressed/noMusicPause.png");
            TextureRegionDrawable pauseNoMusicButtonTrd = new TextureRegionDrawable(new TextureRegion(pauseNoMusicButton));
            Texture pauseNoMusicButtonPr = new Texture("Buttons/pressed/PressedNoMusicPause.png");
            TextureRegionDrawable pauseNoMusicButtonPrTrd = new TextureRegionDrawable(new TextureRegion(pauseNoMusicButtonPr));

            musicBtn = new ImageButton(pauseMusicButtonTrd,pauseMusicButtonPrTrd);
            musicBtn.setPosition(373,431);
            musicBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("musicOn",false);
                    pref.flush();
                }
            } );
            noMusicBtn = new ImageButton(pauseNoMusicButtonTrd,pauseNoMusicButtonPrTrd);
            noMusicBtn.setPosition(373,431);
            noMusicBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("musicOn",true);
                    pref.flush();
                }
            } );

            Texture fxPause =  new Texture("Buttons/unpressed/fxPause.png");
            TextureRegionDrawable fxPauseTr = new TextureRegionDrawable(new TextureRegion(fxPause));
            Texture fxPausePr = new Texture("Buttons/pressed/PressedFxPause.png");
            TextureRegionDrawable fxPausePrTr = new TextureRegionDrawable(new TextureRegion(fxPausePr));

            Texture noFxPause = new Texture("Buttons/unpressed/NoFxPause.png");
            TextureRegionDrawable noFxPauseTr = new TextureRegionDrawable(new TextureRegion(noFxPause));
            Texture noFxPausePr = new Texture("Buttons/pressed/PressedNoFxPause.png");
            TextureRegionDrawable noFxPausePrTr = new TextureRegionDrawable(new TextureRegion(noFxPausePr));

            fxBtn = new ImageButton(fxPauseTr,fxPausePrTr);
            fxBtn.setPosition(561,431);
            fxBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("fxOn",false);
                    pref.flush();
                }
            } );

            noFxBtn = new ImageButton(noFxPauseTr,noFxPausePrTr);
            noFxBtn.setPosition(561,431);
            noFxBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("fxOn",true);
                    pref.flush();
                }
            } );


        }
    }

    // Escena para la pantalla de ganar ------------------------------------------------------------
    private class endScene extends Stage{
        public endScene(Viewport vista, SpriteBatch batch){
            super(vista,batch);

            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);

            Texture winRectangle = new Texture("arcadeScreen.png");
            TextureRegionDrawable winRectTrd = new TextureRegionDrawable(new TextureRegion(winRectangle));
            Image winRect = new Image(winRectTrd);
            winRect.setPosition(40,346);
            this.addActor(winRect);



            Texture retryLevel = new Texture("Buttons/unpressed/restartButton.png");
            Texture retryLevelPr = new Texture("Buttons/pressed/pressedRestartButton.png");
            TextureRegionDrawable retryLevelTrd = new TextureRegionDrawable(new TextureRegion(retryLevel));
            TextureRegionDrawable retryLevelPrTrd = new TextureRegionDrawable(new TextureRegion(retryLevelPr));

            ImageButton retryLevelButton = new ImageButton(retryLevelTrd, retryLevelPrTrd);
            retryLevelButton.setPosition(586,705);
            retryLevelButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.setScreen(new ArcadeMode(sheepEm));
                    if(pref.getBoolean("musicOn")){
                        sheepEm.win.stop();
                    }
                    sheepEm.stopLevelOneMusic();
                }
            });
            this.addActor(retryLevelButton);


            Texture levelsMenu = new Texture("Buttons/unpressed/levelsButton.png");
            Texture levelsMenuPr = new Texture("Buttons/pressed/PressedLevelsButton.png");
            TextureRegionDrawable levelsMenuTrd = new TextureRegionDrawable(new TextureRegion(levelsMenu));
            TextureRegionDrawable levelsMenuPrTrd = new TextureRegionDrawable(new TextureRegion(levelsMenuPr));

            ImageButton levelsButton = new ImageButton(levelsMenuTrd,levelsMenuPrTrd);
            levelsButton.setPosition(285,705);
            levelsButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    if(pref.getBoolean("musicOn")){
                        sheepEm.win.stop();
                    }
                    sheepEm.stopLevelOneMusic();
                }
            });
            this.addActor(levelsButton);
        }
    }

}