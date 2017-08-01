package com.jmdomingueza.telefonoip.presentacion.services;

import java.awt.Color;
import java.awt.Image;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.Icon;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jmdomingueza.telefonoip.presentacion.beans.CategoryResource;
import com.jmdomingueza.telefonoip.presentacion.factories.BeanFactory;


/**
 * Clase que implementa la interfaz ResourceService El Servicio ResourceService
 * se encarga de obtener los recursos(textos, iconos,imagenes,etc) que tenemos
 * en resouces. Dependiendo del idioma que este cargado carga un resource o
 * otro.
 * 
 * @author jmdomingueza
 *
 */
@Service(value="resourceService")
public class ResourceServiceImpl implements ResourceService{

	/**
	 * Logger de la clase
	 */
	private static final Logger logger = Logger.getLogger(ResourceServiceImpl.class);

	/**
	 * Locale España
	 */
	public final static Locale LOCALE_SPAIN = new Locale("es", "ES");

	/**
	 * Array con todos los idioma permitidos
	 */
	public final static Locale[] locales = new Locale[] { LOCALE_SPAIN };

	/**
	 * Mapa con todas las categorias indexadas por idiomas
	 */
	private Map<Locale, Map<String, CategoryResource>> localeCategories;

	/**
	 * Construtor del servicio
	 */
	public ResourceServiceImpl() {

		logger.trace("Enter ResourceServiceImpl");
		
		localeCategories = new HashMap<Locale, Map<String, CategoryResource>>();

		if (localeCategories.isEmpty()) {
			for (int x = 0; x < locales.length; x++) {
				Map<String, CategoryResource> categories = new HashMap<String, CategoryResource>();

				categories.put("main",
						loadCategory("com/jmdomingueza/telefonoip/presentacion/main/resources", locales[x]));
				categories.put("bean",
						loadCategory("com/jmdomingueza/telefonoip/presentacion/bean/resources", locales[x]));
				categories.put("model",
						loadCategory("com/jmdomingueza/telefonoip/presentacion/model/resources", locales[x]));

				localeCategories.put(locales[x], categories);

			}
		}
		logger.trace("Exit ResourceServiceImpl");
	}

	@Override
	public void loadLocale(Locale locale) {
		logger.trace("Enter loadLocale");

		Locale.setDefault(locale);

		logger.trace("Exit loadLocale");
	}

	@Override
	public boolean getBoolean(String key) {
		boolean value;

		logger.trace("Enter getBoolean");

		try {
			value = Boolean.valueOf(getProperty(key)).booleanValue();
		} catch (Exception e) {
			logger.error("Error al obtener el boolean para la propiedad (" + key + ") ponemos false", e);
			value = false;
		}

		logger.trace("Exit getBoolean");
		return value;
	}

	@Override
	public Icon getIcon(String key) {
		Icon icon;

		logger.trace("Enter getIcon");

		try {
			icon = null;
			CategoryResource category = localeCategories.get(Locale.getDefault()).get(getCategory(key));

			if (category != null) {
				icon = category.getIcon(key);
			}
		} catch (Exception e) {
			logger.error("Error al obtener el icon para la propiedad (" + key + ") devolvemos null", e);
			icon = null;
		}

		logger.trace("Exit getIcon");
		return icon;
	}

	@Override
	public Image getImage(String categoryName, String key) {
		Image image;

		logger.trace("Enter getImage");

		try {
			image = null;
			CategoryResource category = localeCategories.get(Locale.getDefault()).get(categoryName);

			if (category != null) {
				image = category.getImage(key);
			}
		} catch (Exception e) {
			logger.error("Error al obtener el image para la propiedad (" + key + ") devolvemos null", e);

			image = null;
		}

		logger.trace("Exit getImage");

		return image;
	}

	@Override
	public int getInt(String key) {
		int value;

		logger.trace("Enter getInt");

		try {
			value = Integer.parseInt(getProperty(key));
		} catch (Exception e) {
			logger.error("Error al obtener el int para la propiedad (" + key + ") devolvemos 0", e);
			value = 0;
		}

		logger.trace("Exit getInt");

		return value;
	}

	@Override
	public Color getColor(String key) {
		Color color;

		logger.trace("Enter getColor");

		try {

			color = Color.decode(getProperty(key));
		} catch (Exception e) {
			logger.error("Error al obtener el color para la propiedad (" + key + ") devolvemos WHITE", e);
			color = Color.WHITE;
		}

		logger.trace("Exit getColor");

		return color;
	}

	@Override
	public String getString(String key, Object... objs) {
		String property, value;

		logger.trace("Enter getString");

		try {
			value = "???" + key + "???";
			property = getProperty(key);
			if (property != null) {
				value = MessageFormat.format(property, objs);
			}
		} catch (Exception e) {
			logger.error("Error al obtener el color para la propiedad (" + key + ") devolvemos \"???\" + key + \"???\"",
					e);
			value = "???" + key + "???";
		}

		logger.trace("Exit getString");

		return value;
	}

	@Override
	public String getProperty(String key) {
		String property;
		CategoryResource category;
		
		logger.trace("Enter getProperty");

		try {
			property = "???" + key + "???";
			category = localeCategories.get(Locale.getDefault()).get(getCategory(key));

			if (category != null) {
				property = category.getProperty(key);
			}
		} catch (Exception e) {
			logger.error("Error al obtener el color para la propiedad (" + key + ") devolvemos \"???\" + key + \"???\"",
					e);
			property = "???" + key + "???";
		}

		logger.trace("Exit getProperty");

		return property;
	}

	
	/**
	 * Metodo que carga la categoria del idioma y la ruta resourceRoot que
	 * pasamos
	 * 
	 * @param resourceRoot
	 *            ruta donde esta el resource
	 * @param locale
	 * @return
	 */
	private CategoryResource loadCategory(String resourceRoot, Locale locale) {
		CategoryResource categoryResource;

		logger.trace("Enter loadCategory");

		categoryResource = BeanFactory.createCategoryResource(resourceRoot, locale);

		logger.trace("Exit loadCategory");
		return categoryResource;
	}
	
	/**
	 * Metodo que devuelve la categoria de una clave
	 * @param key
	 * @return
	 */
	private String getCategory(String key) {
		String category;
		
		logger.trace("Enter getCategory");
		
		int index = key.indexOf('.');
		category = null;
		if (index > 0) {
			category = key.substring(0, index);
		}
		
		logger.trace("Exit getCategory");

		return category;
	}

}
