package com.insigma.resolver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * ʱ���ʽjava.util.date��String��ʽת����
 * @author wengsh
 *
 */
public class DateConverter implements Converter<String, Date> {
	@Override
	public Date convert(String source) {
		if(null!=source&&!source.equals("")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			try {
				return dateFormat.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}
}
