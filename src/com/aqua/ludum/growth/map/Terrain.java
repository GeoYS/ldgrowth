/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aqua.ludum.growth.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;

import java.util.List;

/**
 *
 * @author duane_000
 */
public class Terrain {
    
    public Terrain(Map map) {
    }
    
    public void update(float delta) {
        for (Plant plant : this.plants) {
            plant.update(delta);
        }
        for (Rock rock : this.rocks) {
            rock.update(delta);
        }
        for (Light light : this.lights) {
            light.update(delta);
        }
        for (Nutrients nutrient : this.nutrients) {
            nutrient.update(delta);
        }
        for (Water water : this.waters) {
            water.update(delta);
        }
    }
    
    public void render(SpriteBatch batch) {
        // render itself
        for (Plant plant : this.plants) {
            plant.render(batch);
        }
        for (Rock rock : this.rocks) {
            rock.render(batch);
        }
        for (Light light : this.lights) {
            light.render(batch);
        }
        for (Nutrients nutrient : this.nutrients) {
            nutrient.render(batch);
        }
        for (Water water : this.waters) {
            water.render(batch);
        }
    }
    
    private List<Rock> rocks;
    private List<Light> lights;
    private List<Nutrients> nutrients;
    private List<Water> waters;
    
    private List<Plant> plants;
    
}
