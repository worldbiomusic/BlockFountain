package worldbiomusic.blockfountain.main;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import worldbiomusic.blockfountain.cmd.CommandHelper;
import worldbiomusic.blockfountain.util.ConfigHelper;

public class Main extends JavaPlugin {
	
	Server server = getServer();
	
	
    @Override
    public void onEnable() {
    	// notify on to server
    	server.getConsoleSender().sendMessage(ChatColor.GREEN + "BlockFountain ON");
    	server.getConsoleSender().sendMessage(ChatColor.GREEN + "datafolder: " + this.getDataFolder());
    	// setup
    	setup();
        
    }
    
    void setup()
    {
    	// setup ConfigHelper
    	ConfigHelper configHelper = new ConfigHelper();
    	configHelper.setBaseDirPathAndMakeBaseDir(this.getDataFolder().getPath());
    	
    	addCommands();
    	registerEventListeners();
    	
    	
    }
    
    void addCommands()
    {
    	getCommand("bf").setExecutor(new CommandHelper(this));
    }
    
    void registerEventListeners()
    {
//    	getServer().getPluginManager().registerEvents(new EventManager(this), this);
    }
}