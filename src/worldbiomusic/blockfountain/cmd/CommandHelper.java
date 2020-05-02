package worldbiomusic.blockfountain.cmd;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import worldbiomusic.blockfountain.util.ConfigHelper;

public class CommandHelper implements CommandExecutor{

	Plugin plugin;
	ConfigHelper cfgHelper;
	FileConfiguration cfg;
	
	public CommandHelper(Plugin p)
	{
		this.plugin = p;
		
		cfgHelper = new ConfigHelper();
		cfg = cfgHelper.makeConfigFile("list.yml");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if( ! (sender instanceof Player))
		{
			sender.sendMessage("only player");
			return true;
		}
		
		Player p = (Player) sender;
		if( ! p.isOp()) 
		{
			sender.sendMessage("only op");
			return true;
		}
		
		// add, start, pause, remove, fix, tp
		String option = args[0];
		
		switch(option)
		{
		case "add":
			return add(p, args);
		case "start":
			return start(p, args);
		case "pause":
			return pause(p, args);
		case "remove":
			return remove(p, args);
		case "list":
			return list(p);
		case "tp":
			return tp(p, args);
		case "info":
			return info(p, args);
		}
		
		return false;
	}

	
	int startBlockFountain(Player p, String name, ItemStack block, double power, double period, double blockLifeTime, Location loc)
	{
		int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() 
		{
			@Override
			public void run() 
			{
				Location bLoc = loc;
				FallingBlock b = bLoc.getWorld().spawnFallingBlock(bLoc, block.getData());
				
				double x = -0.5 + Math.random();
	            double y = power;
	            double z = -0.5 + Math.random();
	            
	            b.setVelocity(new Vector(x, y, z));
	            
	            
	            // remove fallingBlock 
	            new BukkitRunnable() {
					@Override
					public void run() {
						b.remove();
						
						Location bLoc = b.getLocation();
						bLoc.getBlock().setType(Material.AIR);
					}
				}.runTaskLater(plugin, (long)(20L * blockLifeTime));
			}
		}, 20L, (long)(period * 20L));
		
		p.sendMessage(name + " created with taskId: " + taskId);
		
		return taskId;
	}
	
//	/bf 0:add 1:<name> 2:<block> 3:[power] 4:[period] 5:[blockLifeTime] [6:[x] 7:[y] 8:[z] | 6:[player]] 
	boolean add(Player p, String[] args)
	{
		String name;
		String blockName;
		ItemStack block;
		double power;
		double period;
		double blockLifeTime;
		double x, y, z;
		
		
		
		name = args[1];
		
		// bf add <name>
		if (args.length == 3) {
			blockName = args[2];
			block = new ItemStack(Material.getMaterial(blockName));
			power = 0.8;
			period = 0.5;
			blockLifeTime = 5;
			
			Location pLoc = p.getLocation();

			x = pLoc.getX();
			y = pLoc.getY();
			z = pLoc.getZ();
		} else {
			blockName = args[2];
			block = new ItemStack(Material.getMaterial(blockName));
			
			power = Double.parseDouble(args[3]);
			
			period = Double.parseDouble(args[4]);
			/*
			 * 1sec = 20tick
			 * 0.5sec = 10tick
			 * 0.25sec = 5tick
			 */
			
			blockLifeTime = Double.parseDouble(args[5]);
			
			// player
			if(args.length == 7) {
				String pName = args[6];
				Player targetP = Bukkit.getServer().getPlayer(pName);
				Location pLoc = targetP.getLocation();

				x = pLoc.getX();
				y = pLoc.getY();
				z = pLoc.getZ();
				
			} else if(args.length == 9) { // x, y, z
				x = Double.parseDouble(args[6]);
				y = Double.parseDouble(args[7]);
				z = Double.parseDouble(args[8]);
			} else {
				return false;
			}
		}
		
		
		p.sendMessage("BlockFountain: " + name + " added");
		
		// start
		int taskId = startBlockFountain(p, name, block, power, period, blockLifeTime, new Location(p.getWorld(), x, y, z));
		
		// save to config
		cfg.createSection(name);
		
		String path = name + ".";
		
		cfg.set(path + "block", block.getType().toString());
		cfg.set(path + "power", power);
		cfg.set(path + "period", period);
		cfg.set(path + "blockLifeTime", blockLifeTime);
		cfg.set(path + "x", x);
		cfg.set(path + "y", y);
		cfg.set(path + "z", z);
		cfg.set(path + "taskId", taskId);
		cfg.set(path + "state", true);
		cfg.set(path + "owner", p.getUniqueId().toString());
		
		
		// save
		cfgHelper.save();
		
		return true;
	}
	
//	/bf start <name>
	boolean start(Player p, String[] args)
	{
		String name = args[1];
		
		String path = name + ".";
		
		String blockName = (String) cfg.get(path + "block");
		ItemStack block = new ItemStack(Material.getMaterial(blockName));
		
		double power = cfg.getDouble(path + "power");
		
		double period = cfg.getDouble(path + "period");
		
		double blockLifeTime = cfg.getDouble(path + "blockLifeTime");
		
		double x = cfg.getDouble(path + "x");
		double y = cfg.getDouble(path + "y");
		double z = cfg.getDouble(path + "z");
		
		int taskId = startBlockFountain(p, name, block, power, period, blockLifeTime, new Location(p.getWorld(), x, y, z));
		
		// save config
		cfg.set(path + "taskId", taskId); // pause,remove 할때 taskId로 멈추기 때문에 update
		cfg.set(path + "state", true);
		
		cfgHelper.save();
		
		return true;
	}
	
	
//	/bf pause <name>
	boolean pause(Player p, String[] args)
	{
		String name = args[1];
		
		String path = name + ".";
		
		// scheduler를 직접 pause하는건 기능이 없음
		// canceltask를 하고 config에 저장된 정보를 이용해서 다시 task를 생성해야 함
		
		int taskId = cfg.getInt(path + "taskId");
		
		Bukkit.getScheduler().cancelTask(taskId);
		
		p.sendMessage(name + " paused");
		
		// change state off
		cfg.set(path + "state", false);
		cfgHelper.save();
		
		return true;
	}
	
//	/bf remove <name>
	boolean remove(Player p, String[] args)
	{
		/*
		 * pause and remove config key
		 */
		
		pause(p, args);
		
		String name = args[1];
		
		cfg.set(name, null);
		
		cfgHelper.save();
		
		p.sendMessage(name + " removed");
		
		return true;
	}
	
	boolean list(Player p)
	{
		p.sendMessage("=====Block Fountain LIST=====");
		
		Set<String> names = cfg.getKeys(false);
		Iterator<String> it = names.iterator();
		
		for(int i = 1; it.hasNext(); i++)
		{
			p.sendMessage(String.format("[%d] %s", i, it.next()));
		}
		
		return true;
	}
	
//	/bf tp <name>
	boolean tp(Player p, String[] args)
	{
		String name = args[1];
		
		String path = name + ".";
		double x = cfg.getDouble(path + "x");
		double y = cfg.getDouble(path + "y");
		double z = cfg.getDouble(path + "z");
		
		Location targetLoc = new Location(p.getWorld(), x, y, z); 
		p.teleport(targetLoc);
		
		return true;
	}
			
//	/bf info <name>: print info [name, block, power, period, blockLifeTime, x, y, z taskId, state, owner]
	boolean info(Player p, String[] args)
	{
		String name = args[1];
		
		String path = name + ".";
		
		String blockName = (String) cfg.get(path + "block");
		ItemStack block = new ItemStack(Material.getMaterial(blockName));
		
		double power = cfg.getDouble(path + "power");
		
		double period = cfg.getDouble(path + "period");
		
		double blockLifeTime = cfg.getDouble(path + "blockLifeTime");
		
		double x = cfg.getDouble(path + "x");
		double y = cfg.getDouble(path + "y");
		double z = cfg.getDouble(path + "z");
		
		int taskId = cfg.getInt(path + "taskId");
		
		boolean state = cfg.getBoolean(path + "state");
		
		String owner = cfg.getString(path + "owner");
		
		p.sendMessage("==========INFO==========");
		p.sendMessage("Name: " + name);
		p.sendMessage("Block: " + block.getType().toString());
		p.sendMessage("Power: " + power);
		p.sendMessage("Period: " + period);
		p.sendMessage("BlockLifeTime: " + blockLifeTime);
		p.sendMessage(String.format("X: %f Y: %f Z: %f", x, y, z));
		p.sendMessage("TaskId: " + taskId);
		p.sendMessage("State: " + state);
		p.sendMessage("Owner: " + owner);
		
		
		return true;
	}
}






































