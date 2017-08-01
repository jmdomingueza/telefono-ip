package com.jmdomingueza.telefonoip.presentacion.beans;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase que implementa una categoria que se usa en ResourceService. Una
 * categoria se traduce en una carpeta dentro de resources que contiene el
 * properties, los iconos, imagenes, etc.
 * 
 * @author jmdomingueza
 *
 */
public class CategoryResourceImpl implements CategoryResource {

	private static final long serialVersionUID = -2981704604413059764L;

	/**
	 * Logger of the class
	 */
	private static final Log logger = LogFactory.getLog(CategoryResourceImpl.class);

	/**
	 * Loader donde crea el ResourceBoulder
	 */
	private ClassLoader loader = null;

	/**
	 * Recursos que cargan de la carpetad de resources
	 */
	private ResourceBundle resources = null;

	/**
	 * Mapa con los iconos que tenemos en el ResourceBoulder
	 */
	private static Map<String, Icon> iconCache = new HashMap<String, Icon>();

	/**
	 * Mapa que contine la imagenes que tenemos en el ResourceBoulder
	 */
	private static Map<String, Image> imageCache = new HashMap<String, Image>();

	/**
	 * Constructor de la clase que crea una categoria a partir pasando el nombre
	 * de la carpeta. Se crea sobre el LocaleDefault
	 *
	 * @param resourceRoot
	 *            nombre de la carpeta donde estan los recursos
	 */
	public CategoryResourceImpl(String resourceRoot) {
		this(resourceRoot, Locale.getDefault());
		logger.trace("Enter CategoryResourceImpl - resourceRoot");
		logger.trace("Exit CategoryResourceImpl - resourceRoot");
	}

	/**
	 * Constructor de la clase que crea una categoria a partir pasando el nombre
	 * de la carpeta y el locale del idioma que buscar.
	 *
	 * @param resourceRoot
	 *            Nombre de la carpeta donde estan los recursos
	 * @param locale
	 *            Idioma sobre el que se crea la carpeta donde estan los
	 *            recursos
	 */
	public CategoryResourceImpl(String resourceRoot, Locale locale) {
		this(resourceRoot, locale, CategoryResourceImpl.class.getClassLoader());
		logger.trace("Enter CategoryResourceImpl - resourceRoot,  locale");
		logger.trace("Exit CategoryResourceImpl - resourceRoot,  locale");
	}

	/**
	 * Constructor de la clase que crea una categoria a partir pasando el nombre
	 * de la carpeta y el locale del idioma que buscar.
	 *
	 * @param resourceRoot
	 *            nombre de la carpeta donde estan los recursos
	 * @param locale
	 *            Idioma sobre el que se crea la carpeta donde estan los
	 *            recursos
	 * @param loader
	 *            Loader donde crea el ResourceBoulder
	 */
	public CategoryResourceImpl(String resourceRoot, Locale locale, ClassLoader loader) {
		logger.trace("Enter CategoryResourceImpl - resourceRoot,  locale,  loader");
		this.loader = loader;
		this.resources = ResourceBundle.getBundle(resourceRoot, locale);
		logger.trace("Exit CategoryResourceImpl - resourceRoot,  locale,  loader");
	}

	@Override
	public Icon getIcon(String iconName) {
		Icon icon;

		logger.trace("Enter getIcon");

		icon = iconCache.get(iconName);

		if (icon == null) {
			String property = getProperty(iconName);

			logger.debug(property);

			if (property != null) {
				URL url = loader.getResource(property);

				logger.debug(url);

				if (url != null) {
					icon = new ImageIcon(url);
					iconCache.put(iconName, icon);
				} else {
					logger.warn("url de icono no encontrada: " + property);
				}
			}
		}
		logger.trace("Exit getIcon");
		return icon;
	}

	@Override
	public Image getImage(String imageName) {
		Image image;

		logger.trace("Enter getImage");

		image = imageCache.get(imageName);

		if (image == null) {
			String property = getProperty(imageName);

			logger.debug(property);

			if (property != null) {
				URL url = loader.getResource(property);

				logger.debug(url);

				if (url != null) {
					image = Toolkit.getDefaultToolkit().getImage(url);
					imageCache.put(imageName, image);
				} else {
					logger.warn("url de imagen no encontrada: " + property);
				}
			}
		}
		logger.trace("Enter getImage");
		return image;
	}

	@Override
	public String getProperty(String key) {
		String property;

		logger.trace("Enter getProperty");

		property = null;
		try {
			property = resources.getString(key);
		} catch (MissingResourceException e) {
			logger.warn(e);
		}

		logger.trace("Enter getProperty");
		return property;
	}
}
