package me.opd02.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin implements Listener{
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
    public static int i = 4;
    public static BukkitTask task = null;
    public ArrayList<Ghast> list = new ArrayList<Ghast>();
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e){
		if(e.getRightClicked().getType()==EntityType.GHAST){
			//Ghast g = (Ghast) e.getRightClicked();
			//LivingEntity pig = (LivingEntity) Bukkit.getWorld("world").spawnEntity(g.getLocation(), EntityType.ZOMBIE);
			//pig.setAI(false);
			//pig.setSilent(true);
			//pig.setPassenger(e.getPlayer());
			//g.setPassenger(pig);
		}
	}
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e){
		Projectile p = e.getEntity();
		if(e.getEntityType()==EntityType.FIREBALL){
			if(!(p.getShooter().toString()=="CraftGhast")){
				return;
			}
			Ghast g = (Ghast) e.getEntity().getShooter();
			Fireball ball = (Fireball) p;
			ball.setYield(7);
			g.setMaxHealth(100);
			if(list.contains(g)){
				return;
			}
			list.add(g);
	        task = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
	            @Override
	            public void run() {
	                if(i != 0) {
	                    Projectile n = g.launchProjectile(Fireball.class, g.getVelocity());
	                    Fireball b =  (Fireball) n;
	                    b.setYield(7);
	                 //   Vector v = target.getLocation().add(0.0, target.getHeight(), 0.0).toVector().subtract(n.getLocation().toVector()).normalize();
	                  //  n.setVelocity(v.multiply(1.2));
	                   // p.setVelocity(v.multiply(1.2));
	                   // n.getLocation().setDirection(v);
	                    Bukkit.getWorld(p.getWorld().getName()).playSound(g.getLocation(), Sound.GHAST_FIREBALL, 3, 3);
	                    //Target N Arrow
	                    i--;
	                } else {
	                    i=4;
	                    list.remove(g);
	                    task.cancel();
	                }
	            }
	        }, 5, 7);
		}
	}
}