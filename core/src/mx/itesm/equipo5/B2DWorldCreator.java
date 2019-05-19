package mx.itesm.equipo5;

import static mx.itesm.equipo5.MasterScreen.PPM;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class B2DWorldCreator {
    public B2DWorldCreator(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for (MapObject object : map.getLayers().get("Paredes").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/PPM,(rect.getY()+rect.getHeight()/2)/PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/PPM,(rect.getHeight()/2)/PPM);
            fdef.shape = shape;

            body.createFixture(fdef);
        }

        for (MapObject object : map.getLayers().get("Puertas").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/PPM,(rect.getY()+rect.getHeight()/2)/PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/PPM,(rect.getHeight()/2)/PPM);
            fdef.shape = shape;

            body.createFixture(fdef);
        }
    }
}
