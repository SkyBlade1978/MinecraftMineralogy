package com.mcmoddev.mineralogy.ioc;

import java.util.concurrent.ConcurrentHashMap;

import com.mcmoddev.lib.interfaces.IDynamicTabProvider;
import com.mcmoddev.mineralogy.Mineralogy;
import com.mcmoddev.mineralogy.factory.TabProviderFactory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * This is the IoC (Inversion of Control) container for Mineralogy
 * @author SkyBlade1978
 *
 */
public class MinIoC {
	protected ConcurrentHashMap<HashTuple2<Class<?>, String>, Object> container = new ConcurrentHashMap<>();
	protected static MinIoC _instance;
	
	protected MinIoC() {
	
	}
	
	/**
	 * Register a concrete into the IoC against a desired type
	 * @param key class type to be registered
	 * @param value concrete to be registered to that type
	 * @param <K> Key value type
	 * @param <V> The value type
	 * @return was the registration successful
	 */
	public <K, V extends K> boolean register(Class<K> key, V value) {
	    return _instance.container.put(new HashTuple2<Class<?>, String>(key, ""), value) == null;
	}

	/**
	 * Register a concrete into the IoC against a desired type and key using ResourceLocation
	 * @param key class type to be registered
	 * @param value concrete to be registered to that type
	 * @param resourceLocation the resource location to use as a key
     * @param <K> Key value type
	 * @param <V> The value type
	 * @return was the registration successful
	 */
	public <K, V extends K> boolean register(Class<K> key, V value, ResourceLocation resourceLocation) {
	    return _instance.container.put(new HashTuple2<Class<?>, String>(key, resourceLocation.toString()), value) == null;
	}
	
	/**
	 * Register a concrete into the IoC against a desired type and key using MMDResourceLocation
	 * @param key class type to be registered
	 * @param value concrete to be registered to that type
	 * @param resourceLocation the MMD resource location to use as a key
	 * @param <K> Key value type
	 * @param <V> The value type
	 * @return was the registration successful
	 */
	public <K, V extends K> boolean register(Class<K> key, V value, MMDResourceLocation resourceLocation) {
	    return _instance.container.put(new HashTuple2<Class<?>, String>(key, resourceLocation.getResourcePath()), value) == null;
	}
	
	/**
	 * Register a concrete into the IoC against a desired type and a string key
	 * @param key class type to be registered
	 * @param value concrete to be registered to that type
	 * @param instanceName the resource location to use as a key
	 * @param modID the resource location to use as a key
	 * @param <K> Key value type
	 * @param <V> The value type
	 * @return was the registration successful
	 */
	public <K, V extends K> boolean register(Class<K> key, V value, String instanceName, String modID) {
	    return _instance.container.put(new HashTuple2<Class<?>, String>(key, modID + ":" + instanceName), value) == null;
	}
	
	/**
	 * Resolve a concrete from the IoC against the desired type
	 * @param keyObject class type to locate
	 * @param <K> Key value type
	 * @param <V> The value type
	 * @return resolved concrete
	 */
	@SuppressWarnings("unchecked")
	public <K, V extends K> V resolve(Class<K> keyObject) {
	    return (V) this.resolve(keyObject, "");
	}
	
	/**
	 * Resolve a concrete from the IoC against the desired type and resource location
	 * @param keyObject class type to locate
	 * @param resourceLocation resource location to use as location key
	 * @param <K> Key value type
	 * @param <V> The value type
	 * @return resolved concrete
	 */
	@SuppressWarnings("unchecked")
	public <K, V extends K> V resolve(Class<K> keyObject, ResourceLocation resourceLocation) {
		return (V) this.resolve(keyObject, resourceLocation.toString());
	}
	
	/**
	 * Resolve a concrete from the IoC against the desired type and mmd resource location
	 * @param keyObject class type to locate
	 * @param resourceLocation resource location to use as location key
	 * @param <K> Key value type
	 * @param <V> The value type
	 * @return resolved concrete
	 */
	@SuppressWarnings("unchecked")
	public <K, V extends K> V resolve(Class<K> keyObject, MMDResourceLocation resourceLocation) {
		return (V) this.resolve(keyObject, resourceLocation.getResourcePath());
	}
	
	/**
	 * Resolve a concrete from the IoC against the desired type and string key
	 * @param keyObject  class type to locate
	 * @param instanceName string key to locate
	 * @param modID mod ID
	 * @param <K> Key value type
	 * @param <V> The value type
	 * @return resolved concrete
	 */
	@SuppressWarnings("unchecked")
	public <K, V extends K> V resolve(Class<K> keyObject, String instanceName, String modID) {
	    return (V) this.resolve(keyObject, modID + ":" + instanceName);
	}
	
	@SuppressWarnings("unchecked")
	private <K, V extends K> V resolve(Class<K> keyObject, String instanceName) {
	    return (V) container.get(new HashTuple2<Class<?>, String>(keyObject, instanceName));
	}
	
	/**
	 * create relationships between class/interface type and concretes
	 */
	public void wireup() {
		this.register(ItemStack.class, new ItemStack(net.minecraft.init.Items.IRON_PICKAXE), "defaultIcon", Mineralogy.MODID);
		
		this.register(IDynamicTabProvider.class, TabProviderFactory.getTabProvider());
	}
	
	public static MinIoC getInstance() {
		return getInstance(true);
	}
	
	public static MinIoC getInstance(boolean autoWirup) {
	      if(_instance == null) {
	         _instance = new MinIoC();
	         
	         if (autoWirup)
	        	 _instance.wireup();
	      }
	      
	      return _instance;
	   }
}