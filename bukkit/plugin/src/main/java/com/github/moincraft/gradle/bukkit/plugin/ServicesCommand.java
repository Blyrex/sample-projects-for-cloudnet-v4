package com.github.moincraft.gradle.bukkit.plugin;

import dev.derklaro.aerogel.Inject;
import dev.derklaro.aerogel.Singleton;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.provider.CloudServiceProvider;
import eu.cloudnetservice.driver.provider.ServiceTaskProvider;
import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.driver.service.ServiceInfoSnapshot;
import eu.cloudnetservice.driver.service.ServiceTask;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Singleton
public class ServicesCommand implements CommandExecutor {

  private final CloudServiceProvider cloudServiceProvider;

  @Inject
  public ServicesCommand(CloudServiceProvider cloudServiceProvider) {
    this.cloudServiceProvider = cloudServiceProvider;
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    if (args.length != 0) {
      sender.sendMessage("Wrong arguments.");
      return false;
    }
    // Alternatively, utilize `InjectionLayer.ext().instance(CloudServiceProvider.class)` to retrieve the CloudServiceProvider.
    String services =
        this.cloudServiceProvider.services().stream()
            .map(serviceInfoSnapshot -> serviceInfoSnapshot.serviceId().name())
            .collect(Collectors.joining(","));
    sender.sendMessage(services);
    return true;
  }
}
