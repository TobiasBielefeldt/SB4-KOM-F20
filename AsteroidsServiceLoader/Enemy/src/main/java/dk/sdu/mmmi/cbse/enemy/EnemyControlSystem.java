/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author tobia
 */
public class EnemyControlSystem implements IEntityProcessingService
{

    @Override
    public void process(GameData gd, World world)
    {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);

            if(Math.random() < 0.2)
            {
                movingPart.setLeft(true);
                movingPart.setRight(false);
                movingPart.setUp(false);
            }else if(Math.random() < 0.4)
            {
                movingPart.setRight(true);
                movingPart.setLeft(false);
                movingPart.setUp(false);
            }else
            {
                movingPart.setUp(true);
            }
            
            movingPart.process(gd, enemy);
            positionPart.process(gd, enemy);

            updateShape(enemy);
        }

    }
    
    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 12);
        shapey[0] = (float) (y + Math.sin(radians) * 12);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 12);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 12);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 7);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 7);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 12);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 12);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
    
}
