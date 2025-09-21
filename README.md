# GameLib

A utility library for Minecraft plugin development, providing reusable components, GUI helpers, inventory utilities, logging, timers, and more. Designed to simplify and speed up the creation of advanced minigames and server features.

**Required by several [pythoncraft minigames](https://github.com/orgs/pcminigames/repositories).**

## Installation

1. Download the latest release from the [releases page](https://github.com/pcminigames/gamelib/releases). You should get a `.jar` file.
2. Place the `.jar` file in your server's `plugins` folder.
3. Start or restart your server.

## Usage

- GameLib is a library plugin. Other plugins (such as minigames) will automatically use it if present.
- You do **not** need to configure GameLib directly.
- If a plugin requires GameLib, make sure it is present and up to date in your `plugins` folder.

## Features

- **Inventory utilities**: Easily create and manage custom kits, and item templates.
- **GUI system**: Build interactive GUIs for players with click events and custom layouts.
- **Logging**: Unified logger for plugin output.
- **Timers**: Simple timer utilities for scheduling tasks and game events.
- **Scoreboard management**: Create and update player scoreboards.
- **Compass tracking**: Utilities for player tracking and custom compasses.
- **Helper classes**: For chat formatting, item loading, and more.

## Notes

- GameLib is designed to be used as a dependency by other plugins. It does not provide gameplay features on its own.
- Make sure to keep GameLib updated if you use plugins that depend on it.
- For examples of plugins using GameLib, see [pythoncraft's minigames](https://github.com/orgs/pcminigames/repositories).

## Development

If you are a developer and want to use GameLib in your plugin, you can include it as a dependency in your build system (tutorial [here](https://github.com/pcminigames/gamelib/blob/main/IMPORT.md))

## Issues

If you find any issues or have suggestions for improvements, please report them on the [issues page](https://github.com/pcminigames/gamelib/issues).
