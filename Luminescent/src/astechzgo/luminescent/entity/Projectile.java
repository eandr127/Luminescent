package astechzgo.luminescent.entity;

import java.awt.Color;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import astechzgo.luminescent.main.Luminescent;

public class Projectile extends RectangularEntity {
	
	private static final double speed = 2.5;
	
	private boolean dead = false;
	
	// Used to get Direction for Shooting
	private double lastDelta;
	private double rotation;

	// The Class
	// Constructor for projectile
	public Projectile(double x, double y) {
		super(x, y, 5, 5);
		super.setColour(new Color(0.5f, 0.6f, 0.2f));
		lastDelta = GLFW.glfwGetTime() * 1000;
		rotation = Luminescent.thePlayer.setRotation();
		
		this.x = (int) (x + (22.5 + super.width / 2) * Math.cos(Math.toRadians(270 - rotation)));
		this.y = (int) (y + (22.5 + super.height / 2)  * Math.sin(Math.toRadians(270 - rotation)));
	}

	// Called every tick from Luminescent class, shoots bullet in direction
	public void fireBullet(List<Double> verticalEdges, List<Double> horizontalEdges) {
		double delta = ((GLFW.glfwGetTime() * 1000) - lastDelta);
		
		lastDelta = GLFW.glfwGetTime() * 1000;
		
		double speed = Projectile.speed * delta;
		
		double x = 0;
		double y = 0;
		
		boolean bx = false;
		boolean by = false;
		
		boolean initX = true;
		boolean initY = true;
		
		for(double verticalEdge : verticalEdges) {
			double projectedX = this.x + speed * Math.cos(Math.toRadians(rotation - 270));
			
			if(!(this.x > verticalEdge - width) && (projectedX >= verticalEdge - width)) {
				bx = true;
				
				double temp = verticalEdge - width;
				if(temp > x || initX) {
					x = temp;
					initX = false;
				}
			}
			else if(!(this.x < verticalEdge) && (projectedX <= verticalEdge)) {
				bx = true;
				
				double temp = verticalEdge;
				if(temp > x || initX) {
					x = temp;
					initX = false;
				}
			}					
		}
		
		if(bx) {
			this.x = x;
			dead = true;
		}
		else
			this.x = this.x + speed * Math.cos(Math.toRadians(rotation - 270));
		
		for(double horizontalEdge : horizontalEdges) {
			double projectedY = this.y - speed * Math.sin(Math.toRadians(rotation - 270));
			
			if(!(this.y > horizontalEdge - height) && (projectedY >= horizontalEdge - height)) {
				by = true;
				
				double temp = horizontalEdge - height;
				if(temp > y || initY) {
					y = temp;
					initY = false;
				}
			}
			else if(!(this.y < horizontalEdge) && (projectedY <= horizontalEdge)) {
				by = true;
				
				double temp = horizontalEdge;
				if(temp > y || initY) {
					y = temp;
					initY = false;
				}
			}					
		}
		
		if(by) {
			this.y = y;
			dead = true;
		}
		else
			this.y = this.y - speed * Math.sin(Math.toRadians(rotation - 270));		
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public boolean isDead() {
		return dead;
	}
}
