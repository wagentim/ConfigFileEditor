package de.etas.tef.config.settings;

public interface ISettingObject<K, V>
{
	void saveSetting(K key, V value);
	
	V getSettingObject(K key);
}
