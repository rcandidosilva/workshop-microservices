package boot.lab10;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public Message send(Message message) throws Exception {
	    String time = dateFormat.format(new Date());
	    return Message.builder()
	    		.from(message.getFrom())
	    		.text(message.getText())
	    		.time(time).build();
	}
	
}