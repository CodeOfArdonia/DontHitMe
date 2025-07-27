# Don't Hit Me!

This mod can prevent 2 type of kicks which are usually caused by mods:

- `Attempting to attack an invalid entity` (When installed on server): This mod can prevent this kick and print the
  entity attacked in log for debugging.
- `Cannot cast xxx to SnifferEntity` (When installed on client): This mod will redirect to correct logic.

**These kicks usually don't happened in vanilla, so you may only need this in modpacks.**

## Suggestions for developers

- Do not make item entities, projectiles and experience orbs interactable.
- Prevent using entity status 63.