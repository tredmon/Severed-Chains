package legend.game.soundSfx;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import legend.game.unpacker.FileData;
import legend.game.unpacker.Unpacker;
import legend.game.unpacker.UnpackerException;

import java.util.List;

public final class SoundFactory {

  static BackgroundMusic backgroundMusic(final String path) {
    //TODO use Scus94491BpeSegment#loadDrgnDir
    final List<FileData> files = Unpacker.loadDirectory(path);

    final SshdParser sshdParser;
    final Sssq sssq;
    switch(files.size()) {
      case 4 -> {
        sshdParser = new SshdParser(files.get(2));

        sssq = new Sssq(files.get(1), sshdParser.createSoundFont(files.get(3)));
      }
      case 5 -> {
        sshdParser = new SshdParser(files.get(3));

        sssq = new Sssq(files.get(2), sshdParser.createSoundFont(files.get(4)));
      }
      default -> throw new RuntimeException("Unknown BGM type. File count " + files.size());
    }

    return new BackgroundMusic(sssq);
  }

  static SoundEffect soundEffect(final String path) {
    //TODO use Scus94491BpeSegment#loadDrgnDir
    final List<FileData> files = Unpacker.loadDirectory(path);

    final SshdParser sshdParser = new SshdParser(files.get(2));

    final SoundFont soundFont = sshdParser.createSoundFont(files.get(3));

    final Sssq sssq = sshdParser.getSssq(soundFont);

    return new SoundEffect();
  }


  private static class SshdParser {
    private final Int2ObjectMap<FileData> subfiles = new Int2ObjectArrayMap<>();
    private int instrumentsSubfileIndex;


    SshdParser(final FileData sshdData) {
      if(sshdData.readInt(12) != 0x64685353) {
        throw new UnpackerException("Not a SShd file!");
      }

      int lastOffset = sshdData.size();
      for(int i = 23;  i >= 0; i--) {
        final int offset = sshdData.readInt(16 + i * 4);

        if(offset != - 1) {
          this.subfiles.put(i, sshdData.slice(offset, lastOffset - offset));
          lastOffset = offset;
        }
      }
    }

    private Sssq getSssq(final SoundFont soundFont) {
      return new Sssq(this.subfiles.get(4), Sequence.getMultiSequence(this.subfiles.get(3)), soundFont);
    }

    private SoundFont createSoundFont(final FileData soundBankData) {

      return new SoundFont(this.subfiles.get(this.instrumentsSubfileIndex), new SoundBank(soundBankData));
    }
  }
}
