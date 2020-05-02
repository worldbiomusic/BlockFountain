package worldbiomusic.blockfountain.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHelper {
	public static String baseDirPath;
	
	File file;
	public FileConfiguration config;
	
	String basePath;
	
	public ConfigHelper()
	{
		
	}
	
	public void setBaseDirPathAndMakeBaseDir(String basePath)
	{
		ConfigHelper.baseDirPath = basePath;
		
		File baseDir = new File(basePath);
		if( ! baseDir.exists())
		{
			baseDir.mkdir();
		}
	}
	
	public FileConfiguration makeConfigFile(String fileName)
	{
		try 
		{
			file = new File(baseDirPath, fileName);
			if( ! file.exists())
			{
					file.createNewFile();
			}
				
			config = YamlConfiguration.loadConfiguration(file);
			
			save();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return config;
	}
	
	public void save()
	{
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public void setBasePath(String basePath)
//	{
//		this.basePath = basePath;
//	}
//	
//	public void setWithBasePath(Object key, Object value)
//	{
//		config.set(this.basePath + "." + key, value);
//		save();
//	}
//	
//	public Object getWithBasePath(Object key)
//	{
//		return config.get(this.basePath + "." + key);
//		
//	}
}












































