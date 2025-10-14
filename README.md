FasterElytras
=============

FasterElytras is a Minecraft Fabric mod that enhances elytra flight mechanics 
with customizable speed control. The mod allows players to configure rocket flight 
speed based on altitude or set fixed speeds, providing a more dynamic and 
personalized flying experience.


Key Features
------------

- Altitude-based Speed Control: Flight speed automatically adjusts based on your Y-level
- Customizable Speed Ranges: Set minimum and maximum speeds with precise height thresholds
- Real-time Speedometer: Optional HUD display showing current flight speed
- Flexible Configuration: In-game GUI and command-based configuration
- Vertical Speed Calculation: Optional inclusion of vertical movement in speed calculations
- Rocket Boost Enhancement: Improved firework rocket boosts for better acceleration

Perfect for players who want more control over their elytra flight performance, 
whether for travel, exploration, or minigame mechanics.


Configuration Commands
----------------------

All commands require OP permission (level 2):

View Current Configuration:

    /fasterelytras config show

Modify Settings:

    # Enable/disable altitude-based speed
    /fasterelytras config set altitudeDeterminesSpeed <true|false>

    # Set speed thresholds
    /fasterelytras config set minSpeed <value>
    /fasterelytras config set maxSpeed <value>

    # Set height thresholds
    /fasterelytras config set minHeight <value>
    /fasterelytras config set maxHeight <value>

    # Toggle speedometer HUD (only client)
    /fasterelytras config set showSpeedometer <true|false>

    # Toggle vertical speed calculation (only client)
    /fasterelytras config set enableVertical <true|false>

Reset to Defaults:

    /fasterelytras config reset


In-Game GUI
-----------

Access the configuration screen via Mod Menu:
    - Go to Mods menu
    - Find "FasterElytras"
    - Click "Config icon" to open the settings interface


Default Values
--------------

- Altitude Determines Speed: true
- Min Speed: 36.0
- Max Speed: 39.0
- Min Height: 0.0
- Max Height: 256.0
- Show Speedometer: false
- Enable Vertical: false
