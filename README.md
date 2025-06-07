# EvilBot 🤖 (Discontinued)

**EvilBot** was a lightweight, customizable Discord bot built in Java with a focus on simplicity and plugin extensibility. While development on EvilBot has been discontinued in favor of [QuoteVault](https://quotevault.org), it still serves as a strong foundation for command-based bots and plugin systems.

---

## ✨ Features

EvilBot supports a range of generic utility and entertainment commands out of the box:

| Command        | Description                                 |
|----------------|---------------------------------------------|
| `!coinflip`    | Flips a coin. Heads or Tails?               |
| `!*ball`       | Magic 8-ball style random responses.        |
| `!math`        | Solves basic math expressions.              |
| `!mctt`        | Converts Morse Code to Text.                |
| `!ttmc`        | Converts Text to Morse Code.                |
| `!rps`         | Play Rock, Paper, Scissors with the bot.   |
| `!translate`   | Translates text using an external API.      |
| `!uptime`      | Displays how long the bot has been running. |
| `!userstats`   | Shows stats for individual users.           |

---

## 🔌 Plugin System

EvilBot includes a **built-in plugin manager**, allowing developers to easily extend functionality without modifying the core codebase.

### Built-in Plugins

| Plugin     | Description |
|------------|-------------|
| **Movies** | `!moviesuggest <title>` — Search and get IMDB info using the OMDB API. |
| **Trivia** | `!trivia` — Play a trivia game powered by an external trivia API.      |
| **Quotes** | `!quote <message>` — Save memorable quotes into an SQLite database.    |
| *Testing*  | Various experimental plugins used during development.                 |

You can also create your own plugins and load them dynamically!

---

## 🛠️ Getting Started

> ⚠️ **This project is discontinued and no longer actively maintained.**

Still, you're welcome to explore the source code, fork it, or use it as a base for your own bot.

---

## 🧱 Plugin Development

Creating a plugin for EvilBot is simple and modular. Each plugin follows a defined structure, allowing you to:

- Register your own commands
- Handle Discord events
- Maintain isolated configurations and data

### 🔨 Basic Plugin Example

```java
public class Plugin implements EvilBotPlugin {

    @Override
    public String pluginName() {
        return "Plugin Name";
    }

    @Override
    public String pluginVersion() {
        return "1.0";
    }

    @Override
    public String pluginAuthor() {
        return "SynicallyEvil";
    }

    @Override
    public void onLoad() {
      System.out.println("Hello World.");
      EvilBotAPI api = EvilBot.getBot().getApi();
      // Add command: api.addCommand(new CustomCommand()); It must implement Command.
      // Add listener: api.addListener(new CustomListener()); It must extend JDA's ListenerAdapter.
    }
}
```

### Requirements

- Java 17+
- Maven or a Java IDE that supports Maven
- SQLite (used by some plugins like Quotes)

  ---

## 📦 Technologies Used

- **Java** — Core programming language.
- **JDA (Java Discord API)** — Handles Discord bot interaction.
- **Maven** — Build and dependency management.
- **SQLite** — Lightweight embedded database used in plugins like `Quotes`.
- **OMDB API** — Movie plugin uses this to fetch film metadata.

## 💬 Contact

Developed by [SynicallyEvil](https://github.com/SynicallyEvil)  
Want to reach out or collaborate? Feel free to open an issue or contact me through GitHub.
