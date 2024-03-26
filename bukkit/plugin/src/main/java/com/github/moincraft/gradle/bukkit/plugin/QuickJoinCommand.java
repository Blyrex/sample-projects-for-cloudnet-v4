package com.github.moincraft.gradle.bukkit.plugin;

import dev.derklaro.aerogel.Singleton;
import eu.cloudnetservice.driver.provider.ServiceTaskProvider;
import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.driver.service.ServiceTask;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Singleton
public class QuickJoinCommand implements TabExecutor {

  private final ServiceRegistry serviceRegistry;
  private final ServiceTaskProvider serviceTaskProvider;

  public QuickJoinCommand(
      ServiceRegistry serviceRegistry, ServiceTaskProvider serviceTaskProvider) {
    this.serviceRegistry = serviceRegistry;
    this.serviceTaskProvider = serviceTaskProvider;
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    if (args.length != 1) {
      sender.sendMessage("Wrong arguments.");
      return false;
    }
    String task = args[0];
    if (sender instanceof Player player) {
      var playerManager = serviceRegistry.firstProvider(PlayerManager.class);
      playerManager.playerExecutor(player.getUniqueId()).connect(task);
    }
    return true;
  }

  @Nullable
  @Override
  public List<String> onTabComplete(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    if (args.length != 1) {
      return null;
    }
    return serviceTaskProvider.serviceTasks().stream().map(ServiceTask::name).toList();
  }
}
