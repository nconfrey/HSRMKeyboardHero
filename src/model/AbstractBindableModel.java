/**
 * abstract class with property change support
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Abstract class to provide PropertyChangeSupport for JavaBeans.
 * 
 */
abstract class AbstractBindableModel {

	/**
	 * Should be transient to not conflict with serialization.
	 */
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/**
	 * Adds a property change listener.
	 * 
	 * @param listener The property change listener
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a property change listener.
	 * 
	 * @param listener The property change listener
	 */
	public void removePropertyChangeListener(
			final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Convenience method to fire property change events.
	 * 
	 * @param propertyName Name of the property
	 * @param oldValue Old value of the property
	 * @param newValue New value of the property
	 */
	protected void firePropertyChange(final String propertyName,
			final Object oldValue, final Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}

}