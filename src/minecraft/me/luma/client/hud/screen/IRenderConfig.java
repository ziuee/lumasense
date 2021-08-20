package me.luma.client.hud.screen;

/*
 * by eric dude
 */

public interface IRenderConfig {
	public void save(ScreenPosition pos);

	public ScreenPosition load();
}