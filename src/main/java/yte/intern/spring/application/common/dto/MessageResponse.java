package yte.intern.spring.application.common.dto;

import lombok.AllArgsConstructor;
import yte.intern.spring.application.common.enums.MessageType;

@AllArgsConstructor
public class MessageResponse {
	public final String messsage;
	public final MessageType messageType;
}
