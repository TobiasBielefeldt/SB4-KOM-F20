/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author tobia
 */
public class CollisionPart implements EntityPart
{
    private int toughness;
    
    public CollisionPart(int toughness)
    {
        this.toughness = toughness;
    }
    
    
    public int getToughness()
    {
        return toughness;
    }
    
    public void setToughness(int toughness)
    {
        this.toughness = toughness;
    }
    

    @Override
    public void process(GameData gameData, Entity entity)
    {
        
    }
    
}
