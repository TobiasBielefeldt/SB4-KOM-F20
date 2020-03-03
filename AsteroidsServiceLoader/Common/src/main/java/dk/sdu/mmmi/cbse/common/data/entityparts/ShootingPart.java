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
public class ShootingPart implements EntityPart
{
    private float shootFlyTime;
    private boolean isShooting;
    private float reloadTime;
    private float elapsedReloadTime;
    
    public ShootingPart(float reloadTime,float shootFlyTime)
    {
        this.reloadTime = reloadTime;
        this.shootFlyTime = shootFlyTime;
    }
    
    public float getShootFlyTime()
    {
        return shootFlyTime;
    }
    
    public boolean getIsShooting()
    {
        return isShooting;
    }
    
    
    public void setIsShooting(boolean isShooting)
    {
        this.isShooting = isShooting;
    }
    
    public void increaseElapsedReloadTime(float dt)
    {
        this.elapsedReloadTime += dt;
    }
    
    public float getElapsedReloadTime()
    {
        return this.elapsedReloadTime;
    }
    
    public void resetElapsedRealoadTime()
    {
        this.elapsedReloadTime = 0;
    }
    
    public float getReloadTime()
    {
        return reloadTime;
    }
    
    @Override
    public void process(GameData gameData, Entity entity)
    {
       
    }
    
}
