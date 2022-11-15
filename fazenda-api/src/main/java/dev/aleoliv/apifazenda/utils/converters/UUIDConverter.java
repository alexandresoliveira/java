package dev.aleoliv.apifazenda.utils.converters;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UUIDConverter implements Converter<String, UUID> {

	@Override
	public UUID convert(String source) {
		return UUID.fromString(source);
	}

}
