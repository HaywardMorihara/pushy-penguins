# "Pushy Penguins" Reference Application

I started this as a prototype project to understand all the parts of developing and releasing a game.

But I lost interest in the game concept itself, which made it difficult to make progress.

So I'm going to leave it in its current state and use it as a reference application, maybe adding
onto it when I want to prototype and learn how to implement a new feature (phone controllers, mobile
compatible, etc.)

## Not To Do:
- Phone controller
- Make mobile
- Network multiplayer
- Release to store
- Multiplayer (win condition, teams?)
- Polishing (Music, Sound Effects, Sprites, Animations, Transitions)
- Difficulty, make it fun
  - More discrete Beaver sizes? A few big ones...and then mostly small
  - Make the map smaller?
- More Polishing (Concept, Main Menu, Pause Menu, Music, Sound Effects, Map, Sprites, 
    Animations, Transitions, ToDos, Refactor)
- More features? (network multiplayer, ToDos, weapons, power ups...)
- Write tests

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
