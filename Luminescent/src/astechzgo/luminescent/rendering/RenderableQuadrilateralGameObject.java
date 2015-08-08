package astechzgo.luminescent.rendering;



import java.awt.Color;

import org.lwjgl.opengl.GL11;

import astechzgo.luminescent.textures.Texture;
import astechzgo.luminescent.utils.DisplayUtils;
import astechzgo.luminescent.utils.RenderingUtils;

public class RenderableQuadrilateralGameObject implements IRenderedObject {

	private Color colour = new Color(0, 0, 0);
	
	protected Texture texture;
	
	protected int x;
	protected int y;
	
	protected int width;
	protected int height;
	
	protected int scaledX;
	protected int scaledY;
	
	protected int scaledWidth;
	protected int scaledHeight;
	
	protected int oldGameWidth = DisplayUtils.getDisplayWidth() - DisplayUtils.widthOffset * 2;
	protected int oldGameHeight = DisplayUtils.getDisplayHeight() - DisplayUtils.heightOffset * 2;
	
	public RenderableQuadrilateralGameObject(int x, int y, Texture texture) {
		this.texture = texture;
		
		this.x = x;
		this.y = y;
		
		this.width = texture.getAsBufferedImage().getWidth();
		this.height = texture.getAsBufferedImage().getHeight();
	}
	
	public RenderableQuadrilateralGameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void render() {
		resize();
		
		GL11.glColor3f((float)colour.getRed() / 256, (float)colour.getGreen() / 256, (float)colour.getBlue() / 256);
		if(texture != null) {
			RenderingUtils.RenderQuad(scaledX, scaledY, scaledWidth, scaledHeight, texture);
		}
		else {
			RenderingUtils.RenderQuad(scaledX, scaledY, scaledWidth, scaledHeight);
		}
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Texture getTexture() {
		return texture;
	}
	
	@Override
	public void setColour(Color colour) {
		this.colour = colour;
	}

	@Override
	public Color getColour() {
		return colour;
	}

	@Override
	public void resize() {
		if((DisplayUtils.getDisplayWidth() - DisplayUtils.widthOffset * 2 == oldGameWidth) && (DisplayUtils.getDisplayHeight() - DisplayUtils.heightOffset * 2 == oldGameHeight))
			return;
		
		System.out.println("Old Resolution: 1920 x 1080, Old Pos: " + x + ", " + y + ", Old Width & Height: " + width + ", " + height);
		
		scaledX = ((int)Math.round((double)x / 1920 * (DisplayUtils.getDisplayWidth() - DisplayUtils.widthOffset * 2))) + DisplayUtils.widthOffset;
		scaledY = ((int)Math.round((double)y / 1080 * (DisplayUtils.getDisplayHeight() - DisplayUtils.heightOffset * 2))) + DisplayUtils.heightOffset;
		
		scaledWidth = (int)Math.round((double)width / 1920 * (DisplayUtils.getDisplayWidth() - DisplayUtils.widthOffset * 2));
		scaledHeight = (int)Math.round((double)height / 1080 * (DisplayUtils.getDisplayHeight() - DisplayUtils.heightOffset * 2));
		
		oldGameWidth = DisplayUtils.getDisplayWidth() - DisplayUtils.widthOffset * 2;
		oldGameHeight = DisplayUtils.getDisplayHeight() - DisplayUtils.heightOffset * 2;
		
		System.out.println("New Resolution: " + (DisplayUtils.getDisplayWidth() - DisplayUtils.widthOffset) + " x " + (DisplayUtils.getDisplayHeight() - DisplayUtils.heightOffset * 2) + ", New Pos: " + scaledX + ", " + scaledY + ", New Width & Height: " + scaledWidth + ", " + scaledHeight);
	}
}
