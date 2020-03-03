/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.BulletPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
//import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Phillip O
 */
//@ServiceProvider(service = IPostEntityProcessingService.class)
public class Collider implements IPostEntityProcessingService
{

    @Override
    public void process(GameData gameData, World world)
    {
        // two for loops for all entities in the world
        for (Entity entity : world.getEntities())
        {
            for (Entity collider : world.getEntities())
            {

                // if the two entities are identical, skip the iteration
                if (entity.getID().equals(collider.getID()))
                {
                    continue;
                }
                // get parts on all entities
                BulletPart entityBulletPart = entity.getPart(BulletPart.class);
                BulletPart colliderBulletPart = collider.getPart(BulletPart.class);
        
                LifePart entityLife = entity.getPart(LifePart.class);
                LifePart colliderPart = collider.getPart(LifePart.class);

                CollisionPart entityCollisionPart = entity.getPart(CollisionPart.class);
                CollisionPart colliderCollisionPart = collider.getPart(CollisionPart.class);

                //If the entity has a bullet part it is a bullet
                if (entityBulletPart != null)
                {
                    if(colliderBulletPart != null)
                    {
                        //If both are bullets and have the same creater ignore the collision and continue
                        if(colliderBulletPart.getCreaterTougness() == entityBulletPart.getCreaterTougness())
                        {
                            continue;
                        }
                    }
                    //If a bullet and an entity collides, we first check of the bullet was created by that entity, if not they both take dmg otherwise we ignore the collision
                    if (this.collides(entity, collider) && entityBulletPart.getCreaterTougness() != colliderCollisionPart.getToughness())
                    {
                        
                        entityLife.setLife(entityLife.getLife() - 99999);
                        colliderPart.setLife(colliderPart.getLife() - 99999);
                    }
                } else if (colliderBulletPart != null)
                {
                    if (this.collides(entity, collider) && colliderBulletPart.getCreaterTougness() != entityCollisionPart.getToughness())
                    {
                        
                        entityLife.setLife(entityLife.getLife() - 99999);
                        colliderPart.setLife(colliderPart.getLife() - 99999);
                    }
                } else
                {//If none if the entitys are bulelts we do the calc as normal and the lowest tougness takes the dmg
                    if (this.collides(entity, collider))
                    {
                        // if entity has been hit, and should have its life reduced
                        if (entityCollisionPart.getToughness() < colliderCollisionPart.getToughness())
                        {
                            //collision does alot of dmg
                            entityLife.setLife(entityLife.getLife() - 99999);
                        } else if (colliderCollisionPart.getToughness() < entityCollisionPart.getToughness())
                        {
                            colliderPart.setLife(entityLife.getLife() - 99999);
                        } else
                        {
                            //ignore same toughness mby do something in the future
                        }
                    }
                }

                // If the entitys have less than 0 life we remove them
                if (entityLife.getLife() <= 0)
                {
                    SplitterPart splitterPart = entity.getPart(SplitterPart.class);
                    if(splitterPart != null)
                    {
                        splitterPart.setShouldSplit(true);
                    }
                    else{
                        world.removeEntity(entity);
                    }
                    

                }

                if (colliderPart.getLife() <= 0)
                {
                    SplitterPart splitterPart = collider.getPart(SplitterPart.class);
                    if(splitterPart != null)
                    {
                        splitterPart.setShouldSplit(true);
                    }
                    else{
                        world.removeEntity(collider);
                    }
                    
                }
            }
        }
    }

    //collision math
    public Boolean collides(Entity entity, Entity entity2)
    {
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);
        float dx = (float) entMov.getX() - (float) entMov2.getX();
        float dy = (float) entMov.getY() - (float) entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance < (entity.getRadius() + entity2.getRadius()))
        {
            return true;
        }
        return false;
    }

}
