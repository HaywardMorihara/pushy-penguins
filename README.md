# "Pushy Penguins" (Name TBD)

Haven't quite decided on the concept yet, but a hectic survival gameMode similar to Mario Party 5's 
"Pushy Penguins".

## Goals/Scope:
Develop & release an end-to-end gameMode (code, art, music) in 2020
### Why Am I Doing This?
1. Because I want to better understand all the parts of developing a gameMode. What I like, how long it 
takes to do the different parts, learn how to use LibGDX, learn how to do the networking controllers...
2. Because I want a small gameMode to develop to completion.
3. Because I think Pushy Penguins is a fun, simple gameMode that can be played with other people.

## To Do:
1. (Feb) Single player
  a. Make high score file if it does not exist
  b. Write to high score file on game over
  c. Game over enter text name of player
2. (Feb) Difficulty, make it fun
3. (Mar, Apr) Controls
4. (May) Multiplayer (win condition, teams?)
5. (Jun, Jul, Aug) Polishing (Concept, Main Menu, Pause Menu, Music, Sound Effects, Map, Sprites, 
Animations, Transitions, ToDos, Refactor)
6. (Sep, Oct) Release to store
7. (Nov, Dec) More features? (ToDos, weapons, power ups...)
8. (?) Write tests
### Done:

## Tools:
- Java on [LibGDX](https://github.com/libgdx/libgdx/wiki)
- IntelliJ IDE
- Paint.NET
- [Piskel](https://www.piskelapp.com/user/5469409993293824) if on Mac
- [Tiled](https://www.mapeditor.org/) tile map editor

## Assets
- Grass and water tilesets from 
https://www.spriters-resource.com/game_boy_advance/pokemonfireredleafgreen/sheet/3863/
- Pokemon Font
https://www.dafont.com/pkmn-rbygsc.font

## History
- Tried Go's Ebiten, because wanted to learn Go better and wanted something lighter than Java and 
wanted to maybe contribute to the library. But it wasn't a _very_ robust library and kept running 
into compile-related errors because of GOPATH and stuff so just went with LibGDX/Java (familiar and 
robust library, that can generate executables for mobile, web, and desktop)
- Didn't use Javascript + Phaser because wanted mobile, not just web. And if I ever do make a bigger 
gameMode, I don't think Javascript will be sufficient (I would like to become more familiar, though. And 
its easy to get running in a browser, the most accessible platform)
- Unity just seemed like there was too much domain specific knowledge needed, and I wanted to be 
learning some coding while I'm at it
- Tiled because seemed to be the first hit for any tile map editor recommendation 
(https://www.google.com/search?client=firefox-b-1-d&q=best+free+tile+map+editor, 
https://www.gamefromscratch.com/post/2014/04/15/A-quick-look-at-Tiled-An-open-source-2D-level-editor.aspx, 
https://github.com/libgdx/libgdx/wiki/Tile-maps) and because works cross platform
