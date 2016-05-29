package dev.larndt.rpg.states;

import java.awt.Graphics;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.ui.ClickListener;
import dev.larndt.rpg.ui.UIImageButton;
import dev.larndt.rpg.ui.UIManager;

public class MenuState extends State{
	
	private UIManager uiManager;
	
	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(200,200,128,64, Assets.start_button, new ClickListener(){
			
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				StateManager.setState(handler.getGame().getGameState());
			}
		}));
	}
	
	@Override
	public void tick() {
		uiManager.tick();
		
		//Skip the menu 
		handler.getMouseManager().setUIManager(null);
		StateManager.setState(handler.getGame().getGameState());
	}

	@Override
	public void render(Graphics g) {
		uiManager.render(g);
		
	}

}
