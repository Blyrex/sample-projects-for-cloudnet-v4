package com.github.moincraft.gradle.bukkit.plugin;

import eu.cloudnetservice.ext.platforminject.api.PlatformEntrypoint;
import eu.cloudnetservice.ext.platforminject.api.stereotype.Command;
import eu.cloudnetservice.ext.platforminject.api.stereotype.Dependency;
import eu.cloudnetservice.ext.platforminject.api.stereotype.PlatformPlugin;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
@PlatformPlugin(
    platform = "bukkit",
    pluginFileNames = "plugin.yml",
    name = "SamplePlugin",
    version = "1.0.0",
    description = "A sample plugin for Bukkit",
    authors = {"MoinCraft", "Blyrex"},
    dependencies = {@Dependency(name = "CloudNet-Bridge")},
    commands = {
      @Command(
          name = "quickjoin",
          description = "A sample command for quickjoin",
          usage = "/quickjoin [task]")
    })
public class SamplePlugin implements PlatformEntrypoint {

  private final JavaPlugin plugin;
  private final QuickJoinCommand quickJoinCommand;

  @Inject
  public SamplePlugin(JavaPlugin plugin, QuickJoinCommand quickJoinCommand) {
    this.plugin = plugin;
    this.quickJoinCommand = quickJoinCommand;
  }

  @Override
  public void onLoad() {
    this.plugin.getLogger().info("Sample plugin loaded");
    this.registerCommands();
  }

  private void registerCommands() {
    final var quickJoinPluginCommand = this.plugin.getCommand("quickjoin");
    if (quickJoinPluginCommand != null) {
      quickJoinPluginCommand.setExecutor(this.quickJoinCommand);
      quickJoinPluginCommand.setTabCompleter(this.quickJoinCommand);
    }
  }

  @Override
  public void onDisable() {
    this.plugin.getLogger().info("Sample plugin disabled");
  }
}
