package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class AlienStory extends ScreenTemplate {

    private final SheepEm sheepEm;

    private Stage storyStage;

    private Texture background;

    private TextureRegion[] animationFrames;
    private TextureRegion[] animationFrames2;
    private Animation fadeInAnimation;
    private Animation fadeOutAnimation;
    private float elapsedTime;
    private float elapsedTime2;
    private Texture fadeIn;
    private ImageButton button;

    public AlienStory(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        loadTextures();
        createScene();
        Gdx.input.setInputProcessor(storyStage);

    }

    private void loadTextures() {
        background = new Texture("alien.png");
    }

    private void createScene() {

        storyStage = new Stage(view);
        TextureRegionDrawable trdBackground = new TextureRegionDrawable(new TextureRegion(background));
        Image imgBackground = new Image(trdBackground);
        imgBackground.setPosition(0,0);
        storyStage.addActor(imgBackground);

    }

    @Override
    public void render(float delta) {
        storyStage.draw();
        elapsedTime += Gdx.graphics.getDeltaTime();

        if(elapsedTime>=6.8){
            sheepEm.setScreen(new AlienLevel(sheepEm));
        }

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
}