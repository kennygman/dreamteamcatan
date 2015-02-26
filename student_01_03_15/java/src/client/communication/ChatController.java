package client.communication;

import client.base.Controller;

/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	private ChatState state;
	
	public ChatController(IChatView view) {
		
		super(view);
		state = new ChatState(this);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		state.sendMessage(message);
	}
	
}

