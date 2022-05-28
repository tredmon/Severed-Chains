package legend.game.types;

import legend.core.gte.SVECTOR;
import legend.core.memory.Value;
import legend.core.memory.types.ArrayRef;
import legend.core.memory.types.MemoryRef;
import legend.core.memory.types.Pointer;
import legend.core.memory.types.ShortRef;
import legend.core.memory.types.UnsignedByteRef;
import legend.core.memory.types.UnsignedIntRef;
import legend.core.memory.types.UnsignedShortRef;

public class BtldScriptData27c implements MemoryRef {
  private final Value ref;

  public final UnsignedIntRef _00;
  public final ArrayRef<ShortRef> _04; // Note: overlaps all the way to _144
  public final UnsignedShortRef _08;
  public final ShortRef _0a;

  public final UnsignedShortRef _0e;
  public final UnsignedShortRef _10;

  public final UnsignedShortRef _14;

  public final ShortRef _1c;

  public final SVECTOR _78;

  public final Pointer<BattleStruct1a8> _144;
  public final BigStruct _148;

  //TODO maybe part of previous struct
  public final UnsignedByteRef _1e4;
  public final UnsignedByteRef _1e5;
  public final ShortRef _1e6;

  public final UnsignedByteRef _1ea;

  public final UnsignedByteRef _214;
  public final UnsignedByteRef _215;

  public final UnsignedIntRef _254;

  public final UnsignedIntRef _25c;

  public final ShortRef _26c;
  public final ShortRef _26e;
  public final ShortRef _270;
  public final ShortRef _272;
  public final ShortRef _274;
  public final ShortRef _276;
  public final UnsignedByteRef _278;

  public BtldScriptData27c(final Value ref) {
    this.ref = ref;

    this._00 = ref.offset(4, 0x00L).cast(UnsignedIntRef::new);
    this._04 = ref.offset(2, 0x04L).cast(ArrayRef.of(ShortRef.class, 0xa0, 2, ShortRef::new));
    this._08 = ref.offset(2, 0x08L).cast(UnsignedShortRef::new);
    this._0a = ref.offset(2, 0x0aL).cast(ShortRef::new);

    this._0e = ref.offset(2, 0x0eL).cast(UnsignedShortRef::new);
    this._10 = ref.offset(2, 0x10L).cast(UnsignedShortRef::new);

    this._14 = ref.offset(2, 0x14L).cast(UnsignedShortRef::new);

    this._1c = ref.offset(2, 0x1cL).cast(ShortRef::new);

    this._78 = ref.offset(2, 0x78L).cast(SVECTOR::new);

    this._144 = ref.offset(4, 0x144L).cast(Pointer.deferred(4, BattleStruct1a8::new));
    this._148 = ref.offset(4, 0x148L).cast(BigStruct::new);

    this._1e4 = ref.offset(1, 0x1e4L).cast(UnsignedByteRef::new);
    this._1e5 = ref.offset(1, 0x1e5L).cast(UnsignedByteRef::new);
    this._1e6 = ref.offset(2, 0x1e6L).cast(ShortRef::new);

    this._1ea = ref.offset(1, 0x1eaL).cast(UnsignedByteRef::new);

    this._214 = ref.offset(1, 0x214L).cast(UnsignedByteRef::new);
    this._215 = ref.offset(1, 0x215L).cast(UnsignedByteRef::new);

    this._254 = ref.offset(4, 0x254L).cast(UnsignedIntRef::new);

    this._25c = ref.offset(4, 0x25cL).cast(UnsignedIntRef::new);

    this._26c = ref.offset(2, 0x26cL).cast(ShortRef::new);
    this._26e = ref.offset(2, 0x26eL).cast(ShortRef::new);
    this._270 = ref.offset(2, 0x270L).cast(ShortRef::new);
    this._272 = ref.offset(2, 0x272L).cast(ShortRef::new);
    this._274 = ref.offset(2, 0x274L).cast(ShortRef::new);
    this._276 = ref.offset(2, 0x276L).cast(ShortRef::new);
    this._278 = ref.offset(1, 0x278L).cast(UnsignedByteRef::new);
  }

  @Override
  public long getAddress() {
    return this.ref.getAddress();
  }
}