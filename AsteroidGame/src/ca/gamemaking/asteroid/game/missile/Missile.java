/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.missile;

import ca.gamemaking.asteroid.Launcher;
import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Maxime Lajoie
 */
public class Missile {
    
    final double TTL = 5.0;
    double aliveTime = 0.0;
    
    double speed = 675;
    
    double angle;
    
    Point2D.Double position;
    Point2D.Double direction;
    
    int nbPoints = 5;
    Point2D.Double[] shape;
    
    public Missile(double posX, double posY, double offset, double angle){
        direction = new Point2D.Double();
        direction.x = Math.cos(Math.toRadians(angle));
        direction.y = Math.sin(Math.toRadians(angle));
        position = new Point2D.Double(posX + direction.x * offset, posY + direction.y * offset);
        
        this.angle = angle;
        CreateShape();
    }
    
    private void CreateShape(){
        shape = new Point2D.Double[nbPoints];
        
        //TODO change shape and scale it
        shape[0] = new Point2D.Double(0, 4 * Settings.SCALE);
        shape[1] = new Point2D.Double(0, -4 * Settings.SCALE);
        shape[2] = new Point2D.Double(20 * Settings.SCALE, 4 * Settings.SCALE);
        shape[3] = new Point2D.Double(27 * Settings.SCALE, 0);
        shape[4] = new Point2D.Double(20 * Settings.SCALE, -4 * Settings.SCALE);
    }
    
    private double RotateX(double x,double y, double angle){
        return (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle)));
    }
    
    private double RotateY(double x,double y, double angle){
        return (x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle)));
    }
    
    public void paint(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1 * Settings.SCALE));
        
        double x,y;
        x = RotateX(shape[0].x, shape[0].y, angle) + position.x;
        y = RotateY(shape[0].x, shape[0].y, angle) + position.y;
        
        Path2D.Double path = new Path2D.Double();
        path.moveTo(x,y);
        for (int i = 1; i < nbPoints; i++) {
            x = RotateX(shape[i].x, shape[i].y, angle) + position.x;
            y = RotateY(shape[i].x, shape[i].y, angle) + position.y;
            path.lineTo(x, y);
        }
        path.closePath();
        
        g2d.draw(path);
    }
    
    public void Update(double delta){
        aliveTime += delta;
        if (aliveTime >= TTL)
            Launcher.getGameFrame().missiles.remove(this);
        
        position.setLocation(position.x + (direction.x * (speed * delta)), position.y + (direction.y * (speed * delta)));
    }
    
}
