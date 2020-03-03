/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.BulletPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author tobia
 */
public class BulletProcessing implements IPostEntityProcessingService
{

    @Override
    public void process(GameData gd, World world)
    {
        for (Entity entity : world.getEntities())
        {
            ShootingPart entityShootingPart = entity.getPart(ShootingPart.class);
            if (entityShootingPart != null)
            {
                entityShootingPart.increaseElapsedReloadTime(gd.getDelta());
                if (entityShootingPart.getIsShooting() && entityShootingPart.getReloadTime() < entityShootingPart.getElapsedReloadTime())
                {
                    world.addEntity(createNewBullet(gd, entity));
                    entityShootingPart.resetElapsedRealoadTime();
                }
            }
        }

        for (Entity bullet : world.getEntities(Bullet.class))
        {
            
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            BulletPart bulletPart = bullet.getPart(BulletPart.class);
            
            bulletPart.increaseCurrentTimer(gd.getDelta());
            if(bulletPart.getCurrentTimer() > bulletPart.getMaxTimer())
            {
                world.removeEntity(bullet);
            }
            
            movingPart.setUp(true);

            
            
            movingPart.process(gd, bullet);
            positionPart.process(gd, bullet);

            updateShape(bullet);
        }

    }

    private void updateShape(Entity bullet)
    {

        PositionPart positionPart = bullet.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        float[] shapex = new float[2];
        float[] shapey = new float[2];
        shapex[0] = (float) (x + Math.cos(radians) * 3);
        shapey[0] = (float) (y + Math.sin(radians) * 3);

        shapex[1] = (float) (x + Math.cos(radians)* (- 3));
        shapey[1] = (float) (y + Math.sin(radians)* (- 3));

        bullet.setShapeX(shapex);
        bullet.setShapeY(shapey);
    }

    private Entity createNewBullet(GameData gameData, Entity creater)
    {
        PositionPart createrPositionPart = creater.getPart(PositionPart.class);
        CollisionPart collisionPart = creater.getPart(CollisionPart.class);
        ShootingPart shootingPart = creater.getPart(ShootingPart.class);
        
        float deacceleration = 0;
        float acceleration = 2000000000;
        float maxSpeed = 150;
        float rotationSpeed = 5;
        float x = createrPositionPart.getX();
        float y = createrPositionPart.getY();
        float radians = createrPositionPart.getRadians();

        Entity bullet = new Bullet();
        bullet.setRadius(3);
        
        
        
        
        bullet.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new LifePart(1, 1));
        bullet.add(new CollisionPart(10));
        bullet.add(new BulletPart(collisionPart.getToughness(),shootingPart.getShootFlyTime()));
        

        return bullet;
    }

}
