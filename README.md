# <img src="https://emojis.slackmojis.com/emojis/images/1643510883/48608/banhammer.png?1643510883" width="30"/> NationsDeathBan

NationsDeathBan is a Minecraft server plugin I made for my event Nations. 

What it does is ban players when they die, it also hides the killer and strikes lightning at the death location.

<br>

## What happens when a player dies?

When a player dies:
- They will get banned for "Your fight is over.", to get someone back you can use the vanilla Minecraft command `/pardon <player>`.
- Lightning will strike at the death location of a player.
- If the player dies to a player kill the killer's name will be hidden.

<br>

## Permissions and config

### Permissions
- `nations.deathban.bypass` - Anyone with this permission will not get banned on death.
- `nations.deathban.viewkiller` - Anyone with this permission will be able to see the name of killers on player kills.

### Config
- `lightning-on-death: true` - When this is set to true lightning will strike on death.
- `death-message: "Your fight is over."` - Set the message that should be used when a player gets banned after they die.
