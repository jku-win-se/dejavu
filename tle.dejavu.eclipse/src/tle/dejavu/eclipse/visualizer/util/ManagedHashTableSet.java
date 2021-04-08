package tle.dejavu.eclipse.visualizer.util;


import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * 
 
 * 
 * @author Michael Vierhauser
 * @param <K>
 *          Key type.
 * @param <V>
 *          Value type.
 * 
 */
public class ManagedHashTableSet<K, V> extends Hashtable<K, Set<V>> implements Serializable{

	private static final long serialVersionUID = -5755802656779154509L;

	public synchronized boolean removeAll(K key) {
		Set<V> list = super.get(key);
		if(list!=null && list.size()>0){
			list.clear();
				return true;
		}
		return false;
	}

	@Override
	public synchronized Set<V> get(Object key) {
		if (super.get(key) == null) {
			return Collections.emptySet();
		} else {
			return super.get(key);
		}
	}

	public synchronized void add(K hashCode, V elem) {
		if (super.get(hashCode) == null) {
			Set<V> l = new HashSet<>();
			l.add(elem);
			super.put(hashCode, l);
		} else {
			Set<V> l = super.get(hashCode);
			l.add(elem);
			// super.put(hashCode, l);
		}

	}
}
