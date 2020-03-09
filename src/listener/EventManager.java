package listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class EventManager implements Listener {

    Plugin plugin;

    public EventManager(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerSpawnBlock(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        Action action = e.getAction();

        if(action == Action.RIGHT_CLICK_AIR && p.getInventory().getItemInMainHand().getType().isBlock())
        {
//            Vector v = p.getLocation().getDirection().normalize();
            ItemStack item = p.getInventory().getItemInMainHand();
            Location loc = p.getEyeLocation();

            p.sendMessage("block fountain starts in 3seconds.");

            int taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @SuppressWarnings("deprecation")
				@Override
                public void run() {

                    FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, item.getType(), item.getData().getData());
                    double x = -0.5 + Math.random();
                    double y = 1;
                    double z = -0.5 + Math.random();
                    fb.setVelocity(new Vector(x, y, z));
                    
                    // remove fallingBlock 
                    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							// TODO: ���� �߸� �������� ������ȭ �Ȱ͵��� ������ ��
							fb.remove();
							
							Location loc = fb.getLocation();
							Block b = loc.getBlock();
							b.setType(Material.AIR);
							
						}
					}, 20L * 3);
                }
            }, 20L * 3, 2L);
            
            Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					Bukkit.getServer().getScheduler().cancelTask(taskId);
				}
			}, 20L * 10);

        }
    }

}
