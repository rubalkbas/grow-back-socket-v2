package com.nezztech.apuesta.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import lombok.extern.slf4j.Slf4j;

/**
 * The Class Util.
 */
@Slf4j
public class Util {


    
	public static final String FORMATO_YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String FORMATO_DD_MM_YYYY = "dd-MM-yyyy";
    
	/**
	 * Checks if is empty.
	 *
	 * @param val the val
	 * @return true, if is empty
	 */
	public static boolean isEmpty(Object val) {
		if(val==null) {
			return true;
		}
		if(val instanceof String) {
			return val.equals("") || val.equals("null");
		}
		return false;
	}
    
	/**
	 * Gets the double rs.
	 *
	 * @param val the val
	 * @return the double rs
	 */
	public static BigDecimal getBigDecimalRs(Object val) {
		String valor = getStringRs(val);
		return isEmpty(valor) ? null : new BigDecimal(valor);
	}
	
	/**
	 * Gets the long rs.
	 *
	 * @param val the val
	 * @return the long rs
	 */
	public static Long getLongRs(Object val) {
		String valor = getStringRs(val);
		return isEmpty(valor) ? null : Long.valueOf(valor);
	}
	
	/**
	 * Gets the integer rs.
	 *
	 * @param val the val
	 * @return the integer rs
	 */
	public static Integer getIntegerRs(Object val) {
		String valor = getStringRs(val);
		return isEmpty(valor) ? null : Integer.valueOf(valor);
	}
	
	/**
	 * Gets the date rs.
	 *
	 * @param val the val
	 * @return the date rs
	 * @throws ParseException the parse exception
	 */
	public static Date getDateRs(Object val) {
		String valor = getStringRs(val);
		try {
			return isEmpty(valor) ? null : convertirStringAFecha(valor);
		} catch (ParseException e) {
			log.info("val: {}", val);
			log.info(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the string rs.
	 *
	 * @param val the val
	 * @return the string rs
	 */
	public static String getStringRs(Object val) {
		return isEmpty(val) ? null : String.valueOf(val);
	}
	
	public static Date convertirStringAFecha(String fecha, String formatoentrada) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat(formatoentrada);
		return formato.parse(fecha);
	}
	
	public static Date convertirStringAFecha(String fecha) throws ParseException {
		if (fecha.equals("-")) return null;
		return convertirStringAFecha(fecha, FORMATO_YYYY_MM_DD);
	}	

	
}
