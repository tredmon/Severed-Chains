package legend.core.kernel;

import legend.core.cdrom.CdlLOC;
import legend.core.memory.Method;
import legend.core.memory.Value;
import legend.core.memory.types.Pointer;
import legend.core.memory.types.ProcessControlBlock;
import legend.core.memory.types.ThreadControlBlock;
import legend.game.Scus94491;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static legend.core.Hardware.CDROM;
import static legend.core.Hardware.MEMORY;
import static legend.core.kernel.Kernel.FileClose_Impl_B36;
import static legend.core.kernel.Kernel.FileOpen_Impl_B32;
import static legend.core.kernel.Kernel.FileRead_Impl_B34;
import static legend.core.kernel.Kernel.SysInitMemory_Impl_C08;
import static legend.core.kernel.Kernel.alloc_kernel_memory_Impl_B00;

public final class Bios {
  private Bios() { }

  private static final Logger LOGGER = LogManager.getFormatterLogger(Bios.class);

  public static final Pointer<ProcessControlBlock> ProcessControlBlockPtr_a0000108 = MEMORY.ref(4, 0xa0000108L, Pointer.of(4, ProcessControlBlock::new));
  public static final Value ProcessControlBlockSize_a000010c = MEMORY.ref(4, 0xa000010cL);
  public static final Value ThreadControlBlockAddr_a0000110 = MEMORY.ref(4, 0xa0000110L);
  public static final Value ThreadControlBlockSize_a0000114 = MEMORY.ref(4, 0xa0000114L);

  public static final Value EventControlBlockAddr_a0000120 = MEMORY.ref(4, 0xa0000120L);
  public static final Value EventControlBlockSize_a0000124 = MEMORY.ref(4, 0xa0000124L);

  public static final Value kernelStart_a0000500 = MEMORY.ref(4, 0xa0000500L);

  public static final Value randSeed_a0009010 = MEMORY.ref(4, 0xa0009010L);

  public static final Value _a0009154 = MEMORY.ref(4, 0xa0009154L);
  public static final Value _a0009158 = MEMORY.ref(4, 0xa0009158L);
  public static final Value _a000915c = MEMORY.ref(4, 0xa000915cL);
  public static final Value _a0009160 = MEMORY.ref(4, 0xa0009160L);

  public static final Value _a00091c4 = MEMORY.ref(4, 0xa00091c4L);
  public static final Value _a00091c8 = MEMORY.ref(4, 0xa00091c8L);
  public static final Value _a00091cc = MEMORY.ref(4, 0xa00091ccL);
  public static final Value _a00091f0 = MEMORY.ref(4, 0xa00091f0L);

  public static final Value _a00091fc = MEMORY.ref(4, 0xa00091fcL);

  public static final Value _a000958c = MEMORY.ref(4, 0xa000958cL);

  public static final Value _a00095b0 = MEMORY.ref(4, 0xa00095b0L);

  public static final Value _a00095bc = MEMORY.ref(4, 0xa00095bcL);

  public static final Value _a0009d70 = MEMORY.ref(4, 0xa0009d70L);
  public static final Value _a0009d74 = MEMORY.ref(4, 0xa0009d74L);
  public static final Value _a0009d78 = MEMORY.ref(4, 0xa0009d78L);
  public static final Value _a0009d7c = MEMORY.ref(4, 0xa0009d7cL);
  public static final Value _a0009d80 = MEMORY.ref(1, 0xa0009d80L);

  public static final Value _a0009e00 = MEMORY.ref(4, 0xa0009e00L);
  public static final Value _a0009e04 = MEMORY.ref(4, 0xa0009e04L);
  public static final Value _a0009e08 = MEMORY.ref(4, 0xa0009e08L);

  public static final Value _a0009e10 = MEMORY.ref(4, 0xa0009e10L);

  public static final Value _a0009e18 = MEMORY.ref(1, 0xa0009e18L);

  public static final Value _a000b070 = MEMORY.ref(4, 0xa000b070L);
  public static final Value _a000b071 = MEMORY.ref(1, 0xa000b071L);

  public static final Value _a000b078 = MEMORY.ref(4, 0xa000b078L);

  public static final Value _a000b080 = MEMORY.ref(1, 0xa000b080L);

  public static final Value _a000b0c0 = MEMORY.ref(4, 0xa000b0c0L);

  public static final Value _a000b0f4 = MEMORY.ref(4, 0xa000b0f4L);

  public static final Value _a000b0fc = MEMORY.ref(4, 0xa000b0fcL);

  public static final Value _a000b10e = MEMORY.ref(1, 0xa000b10eL);
  public static final Value _a000b10f = MEMORY.ref(1, 0xa000b10fL);
  public static final Value _a000b110 = MEMORY.ref(1, 0xa000b110L);
  public static final Value _a000b111 = MEMORY.ref(1, 0xa000b111L);

  public static final legend.core.kernel.EXEC exe_a000b870 = MEMORY.ref(4, 0xa000b870L, legend.core.kernel.EXEC::new);

  public static final Value exeName_a000b8b0 = MEMORY.ref(1, 0xa000b8b0L);

  public static final Value _a000b938 = MEMORY.ref(4, 0xa000b938L);
  public static final Value _a000b93c = MEMORY.ref(4, 0xa000b93cL);

  public static final Value kernelMemoryStart_a000e000 = MEMORY.ref(1, 0xa000e000L);

  public static final Value kernelStartRom_bfc10000 = MEMORY.ref(4, 0xbfc10000L);

  @Method(0xbfc00000L)
  public static void main() {
    LOGGER.info("Executing BIOS");

    bootstrapExecutable("cdrom:SYSTEM.CNF;1", "cdrom:PSX.EXE;1");
  }

  /**
   * See no$ "BIOS Memory Map"
   */
  @Method(0xbfc00420L)
  public static void copyKernelSegment2() {
    //LAB_bfc00434
    MEMORY.memcpy(kernelStart_a0000500.getAddress(), kernelStartRom_bfc10000.getAddress(), 0x8bf0);
    MEMORY.addFunctions(Kernel.class);
  }

  @Method(0xbfc02200L)
  public static int rand_Impl_A2f() {
    final long v1 = (randSeed_a0009010.get() * 0x41c6_4e6dL & 0xffff_ffffL) + 0x3039L;
    final long v0 = v1 >>> 16;
    randSeed_a0009010.setu(v1);
    return (int)(v0 & 0x7fff);
  }

  @Method(0xbfc02230L)
  public static void srand_Impl_A30(final long seed) {
    randSeed_a0009010.setu(seed);
  }

  @Method(0xbfc02b50L)
  public static long memcpy_Impl_A2a(final long dst, final long src, final int size) {
    if(dst == 0) {
      return 0;
    }

    if(size <= 0) {
      return dst;
    }

    MEMORY.memcpy(dst, src, size);
    return dst;
  }

  @Method(0xbfc03288L)
  public static int strcmp_Impl_A17(final String s1, final String s2) {
    return s1.compareToIgnoreCase(s2);
  }

  @Method(0xbfc03310L)
  public static int strncmp_Impl_A18(final String s1, final String s2, final int n) {
    return s1.substring(0, Math.min(s1.length(), n)).compareToIgnoreCase(s2.substring(0, Math.min(s2.length(), n)));
  }

  @Method(0xbfc03a18L)
  public static boolean LoadExeFile_Impl_A42(final String filename, final long header) {
    final int fd = open(filename, 1);
    if(fd < 0) {
      return false;
    }

    //LAB_bfc03a3c
    if(!readExeHeader(fd, header)) {
      close(fd);
      return false;
    }

    //LAB_bfc03a68
    read(fd, MEMORY.ref(4, header).offset(0x8L).get(), (int)MEMORY.ref(4, header).offset(0xcL).get());
    close(fd);

    //LAB_bfc03a94
    return true;
  }

  @Method(0xbfc03c90L)
  public static boolean readExeHeader(final int fd, final long header) {
    final int bytesRead = read(fd, _a000b070.getAddress(), 0x800);

    if(bytesRead >= 0x800) {
      memcpy_Impl_A2a(header, _a000b080.getAddress(), 0x3c);
      return true;
    }

    return false;
  }

  @Method(0xbfc04678L)
  public static long allocateEventControlBlock(final int count) {
    LOGGER.info("");
    LOGGER.info("Configuration : EvCB\t0x%02x\t\t", count);

    final int size = count * 28;
    final long addr = alloc_kernel_memory(size);
    if(addr == 0) {
      return 0;
    }

    EventControlBlockAddr_a0000120.setu(addr);
    EventControlBlockSize_a0000124.setu(size);

    for(int i = 0; i < count; i++) {
      MEMORY.ref(4, addr).offset(i * 28L).offset(0x4L).setu(0);
    }

    return size;
  }

  @Method(0xbfc0472cL)
  public static long allocateThreadControlBlock(final int processCount, final int threadCount) {
    LOGGER.info("TCB\t0x%02x", threadCount);

    final int processSize = processCount * 0x4;
    final int threadSize = threadCount * 0xc0;

    ProcessControlBlockSize_a000010c.setu(processSize);
    ThreadControlBlockSize_a0000114.setu(threadSize);

    final long processAddr = alloc_kernel_memory(processSize);
    if(processAddr == 0) {
      return 0;
    }

    //LAB_bfc04798
    final long threadAddr = alloc_kernel_memory(threadSize);
    if(threadAddr == 0) {
      return 0;
    }

    final ProcessControlBlock pcb = MEMORY.ref(4, processAddr, ProcessControlBlock::new);
    final ThreadControlBlock tcb = MEMORY.ref(4, threadAddr, ThreadControlBlock::new);

    //LAB_bfc047b8
    //LAB_bfc047d4
    for(int i = 0; i < processCount; i++) {
      MEMORY.ref(4, processAddr).offset(i * 4L).setu(0);
    }

    //LAB_bfc047e8
    //LAB_bfc0480c
    for(int i = 0; i < threadCount; i++) {
      MEMORY.ref(4, threadAddr).offset(i * 0xc0L).setu(0x1000L); // Uninitialized
    }

    //LAB_bfc0481c
    tcb.status.set(0x4000L); // Initialized
    pcb.threadControlBlockPtr.set(tcb);
    ProcessControlBlockPtr_a0000108.set(pcb);
    ThreadControlBlockAddr_a0000110.setu(threadAddr);

    //LAB_bfc0483c
    return processSize + threadSize;
  }

  @Method(0xbfc04910L)
  public static boolean CdInitSubFunc_Impl_A95() {
    _a00091c4.setu(0);
    _a00091c8.setu(0);
    _a00091cc.setu(0);

    _a000b938.setu(0x1L);
    _a000b93c.setu(0x1L);

    _a0009154.setu(0xffffL);
    _a0009158.setu(0xffffL);
    _a000915c.setu(0);
    _a0009160.setu(0);

    return true;
  }

  @Method(0xbfc067e8L)
  public static void bootstrapExecutable(final String cnf, final String exe) {
    LOGGER.info("Bootstrapping %s / %s", exe, cnf);

    copyKernelSegment2();

    SysInitMemory(kernelMemoryStart_a000e000.getAddress(), 0x2000);
    allocateEventControlBlock(10);
    allocateThreadControlBlock(1, 4);

    CdInit_Impl_A54();

    //LAB_bfc06a3c
    //LAB_bfc06af4
    exeName_a000b8b0.set("\\SCUS_944.91;1");

    //LAB_bfc06b7c
    //LAB_bfc06bb4
    if(!LoadExeFile_Impl_A42(exeName_a000b8b0.getString(), exe_a000b870.getAddress())) {
      throw new RuntimeException("Failed to load exe");
    }

    //LAB_bfc06be0
    //LAB_bfc06c6c
    Scus94491.main();
    LOGGER.info("Exiting");
    System.exit(0);
  }

  @Method(value = 0xbfc06fdcL, ignoreExtraParams = true)
  public static int noop() {
    return 0;
  }

  @Method(0xbfc073a0L)
  public static void CdInit_Impl_A54() {
    CdInitSubFunc();
    cdromPostInit();
  }

  @Method(0xbfc07410L)
  public static long cdromPostInit() {
    CDROM.readFromDisk(new CdlLOC().unpack(0x10L), 1, _a000b070.getAddress());

    //LAB_bfc0744c
    if(strncmp_Impl_A18(_a000b071.getString(), "CD001", 5) != 0) {
      assert false : "Bad CDROM sector";
      return 0;
    }

    //LAB_bfc07474
    _a0009d70.setu(_a000b0f4);
    _a0009d74.setu(_a000b0fc);
    _a0009d78.setu(_a000b111.get() << 24 | _a000b110.get() << 16 | _a000b10f.get() << 8 | _a000b10e.get());
    _a0009d7c.setu(_a000b0c0);

    CDROM.readFromDisk(new CdlLOC().unpack(_a000b0fc.get()), 1, _a000b070.getAddress());

    //LAB_bfc07500
    //LAB_bfc07518
    for(int i = 0; i < 0x800; i += 4) {
      _a0009d7c.xoru(_a000b070.offset(i));
    }

    long s0 = _a000b070.getAddress();
    long s2 = _a00095b0.getAddress();
    long s3 = 0x1L;

    //LAB_bfc07560
    do {
      if(MEMORY.ref(1, s0).get() == 0) {
        break;
      }

      //LAB_bfc07580
      long v0 = MEMORY.ref(1, s0).offset(0x5L).get() << 24 | MEMORY.ref(1, s0).offset(0x4L).get() << 16 | MEMORY.ref(1, s0).offset(0x3L).get() << 8 | MEMORY.ref(1, s0).offset(0x2L).get();
      MEMORY.ref(4, s2).offset(0x8L).setu(v0);
      MEMORY.ref(4, s2).setu(s3);
      MEMORY.ref(4, s2).offset(0x4L).setu(MEMORY.ref(1, s0).offset(0x6L).get() + MEMORY.ref(1, s0).offset(0x7L).get() & 0xffffL);

      final long size = MEMORY.ref(1, s0).get();
      memcpy_Impl_A2a(_a00095bc.getAddress(), _a000b078.getAddress(), (int)size);

      MEMORY.ref(1, s2).offset(size).offset(0xcL).setu(0);

      v0 = MEMORY.ref(1, s0).get();
      s0 += v0;
      if((v0 & 0x1L) == 0x1L) {
        s0 += 0x9L;
      } else {
        //LAB_bfc07604
        s0 += 0x8L;
      }

      //LAB_bfc0760c
      s2 += 0x2cL;
      s3++;
    } while(s0 < _a000b070.getAddress() + 0x800 && s3 != 0x2dL);

    //LAB_bfc07620
    //LAB_bfc07630
    _a0009e00.setu(s3 - 0x1L);
    _a0009e08.setu(0);

    //LAB_bfc07650
    return 0x1L;
  }

  @Method(0xbfc07664L)
  public static long FUN_bfc07664(final long a0, final String a1) {
    long s0 = _a00095b0.getAddress();
    long s1 = 0;

    //LAB_bfc0769c
    while(s1 < _a0009e00.get()) {
      if(MEMORY.ref(4, s0).offset(0x4L).get() == a0) {
        if(strcmp_Impl_A17(a1, MEMORY.ref(1, s0 + 0xcL).getString()) == 0) {
          return s1 + 0x1L;
        }
      }

      //LAB_bfc076c8
      s0 += 0x2cL;
      s1++;
    }

    //LAB_bfc076e0
    //LAB_bfc076e4
    return -0x1L;
  }

  @Method(0xbfc07700L)
  public static long FUN_bfc07700(final long a0) {
    if(a0 == _a0009e08.get()) {
      return 0x1L;
    }

    //LAB_bfc07720
    CDROM.readFromDisk(new CdlLOC().unpack(_a000958c.offset(a0 * 44).get()), 1, _a000b070.getAddress());

    //LAB_bfc07774
    long s1 = _a000b070.getAddress();
    long s2 = _a00091f0.getAddress();
    long count = 0;

    //LAB_bfc077a8
    do {
      if(MEMORY.ref(1, s1).get() == 0) {
        break;
      }

      //LAB_bfc077c8
      MEMORY.ref(4, s2).offset(0x4L).setu(MEMORY.ref(1, s1).offset(0x5L).get() << 24 | MEMORY.ref(1, s1).offset(0x4L).get() << 16 | MEMORY.ref(1, s1).offset(0x3L).get() << 8 | MEMORY.ref(1, s1).offset(0x2L).get());
      MEMORY.ref(4, s2).offset(0x8L).setu(MEMORY.ref(1, s1).offset(0xdL).get() << 24 | MEMORY.ref(1, s1).offset(0xcL).get() << 16 | MEMORY.ref(1, s1).offset(0xbL).get() << 8 | MEMORY.ref(1, s1).offset(0xaL).get());

      final int size = (int)MEMORY.ref(1, s1).offset(0x20L).get();
      memcpy_Impl_A2a(s2 + 0xcL, s1 + 0x21L, size);

      MEMORY.ref(1, s2).offset(size).offset(0xcL).setu(0);
      s1 += MEMORY.ref(1, s1).get();
      s2 += 0x18L;
      count++;
    } while(s2 < _a00095b0.getAddress() && s1 < _a000b070.getAddress() + 0x800);

    //LAB_bfc07860
    //LAB_bfc07870
    _a0009e08.setu(a0);
    _a0009e04.setu(count);

    //LAB_bfc07894
    return 0x1L;
  }

  @Method(0xbfc078a4L)
  public static int dev_cd_open_Impl_A5f(final long fcb, final String path, final int mode) {
    //LAB_bfc078f8
    final char[] temp = new char[path.length()];

    int s0 = 0;
    int s1 = 0;
    char c = Character.toUpperCase(path.charAt(0));
    temp[0] = c;

    //LAB_bfc07910
    while(c != 0 && s1 < path.length() - 1) {
      s1++;
      s0++;
      c = Character.toUpperCase(path.charAt(s1));
      temp[s0] = c;
    }

    //LAB_bfc07928
    s1 = 0;
    c = temp[s1];

    //LAB_bfc07940
    while(c != '\0' && c != ';') {
      s1++;
      c = temp[s1];
    }

    //LAB_bfc07958
    final String str;
    if(path.charAt(s1) == '\0') {
      str = new String(temp) + ";1";
    } else {
      str = new String(temp);
    }

    //LAB_bfc0797c
    final long v0 = FUN_bfc083cc(0, str);

    //LAB_bfc079a0
    final long v1 = _a00091f0.offset(v0 * 24).getAddress();
    MEMORY.ref(4, fcb).offset(0x24L).setu(MEMORY.ref(4, v1).offset(0x4L));
    MEMORY.ref(4, fcb).offset(0x20L).setu(MEMORY.ref(4, v1).offset(0x8L));
    MEMORY.ref(4, fcb).offset(0x10L).setu(0);
    MEMORY.ref(4, fcb).offset(0x18L).setu(0);
    MEMORY.ref(4, fcb).offset(0x4L).setu(_a0009d7c);

    //LAB_bfc079e0
    return 0;
  }

  @Method(0xbfc07a04L)
  public static int dev_cd_read_Impl_A60(final long fcb, final long dest, final int length) {
    //LAB_bfc07a34
    //LAB_bfc07aec
    //LAB_bfc07b08
    final int sectors = length / 0x800;

    CDROM.readFromDisk(new CdlLOC().unpack(MEMORY.ref(4, fcb).offset(0x24L).get() + MEMORY.ref(4, fcb).offset(0x10L).get() / 0x800L), sectors, dest);

    //LAB_bfc07b40
    final long v = MEMORY.ref(4, fcb).offset(0x10L).get();
    final long v0 = MEMORY.ref(4, fcb).offset(0x20L).get();
    final long v1;
    if(v0 < v + length) {
      v1 = v0 - v;
    } else {
      v1 = length;
    }

    //LAB_bfc07b68
    //LAB_bfc07b6c
    MEMORY.ref(4, fcb).offset(0x10L).setu(v + v1);

    //LAB_bfc07b78
    return (int)v1;
  }

  @Method(0xbfc08020L)
  public static long FUN_bfc08020(long a0, final String a1) {
    //LAB_bfc0803c
    long v0 = MEMORY.ref(1, a0).get();
    int charIndex = 0;
    while(v0 != 0) {
      final char c = a1.charAt(charIndex);
      if(c != '?' && c != v0) {
        return 0;
      }

      //LAB_bfc0805c
      v0 = MEMORY.ref(1, a0).offset(0x1L).get();
      charIndex++;
      a0++;
    }

    //LAB_bfc0806c
    final char c = a1.charAt(charIndex);
    if(c != '\0' && c != '?') {
      //LAB_bfc08090
      return 0;
    }

    //LAB_bfc08084
    return 0x1L;
  }

  @Method(0xbfc083ccL)
  public static long FUN_bfc083cc(final int a0, final String a1) {
    final String str;
    if(a1.charAt(0) == '\\') {
      //LAB_bfc08440
      str = a1;
    } else {
      str = _a0009d80.getString() + '\\' + a1;
    }

    _a0009e18.set(str);

    //LAB_bfc08454
    final char[] out = new char[str.length()];
    int outIndex;

    int index = 1;
    out[0] = '\0';
    long s4 = 0x1L;
    long s3 = 0;

    //LAB_bfc0846c
    do {
      char c = (char)_a0009e18.offset(index).get();
      outIndex = 0;

      //LAB_bfc08484
      while(c != '\0' && c != '\\') {
        out[outIndex] = c;
        index++;
        c = (char)_a0009e18.offset(index).get();
        outIndex++;
      }

      //LAB_bfc084a0
      if(c == '\0') {
        break;
      }

      index++;
      out[outIndex] = 0;
      final long v0 = FUN_bfc07664(s4, new String(out));
      s4 = v0;
      if(v0 == -0x1L) {
        out[0] = 0;
        break;
      }

      //LAB_bfc084cc
      s3++;
    } while(s3 < 0x8L);

    //LAB_bfc084d8
    //LAB_bfc084dc
    if(s3 >= 0x8L) {
      return -0x1L;
    }

    //LAB_bfc084f0
    if(out[0] == '\0') {
      return -0x1L;
    }

    //LAB_bfc08504
    out[outIndex] = '\0';
    if(FUN_bfc07700(s4) == 0) {
      return -0x1L;
    }

    //LAB_bfc08520
    final long s1 = _a0009e04.get();
    s3 = a0;
    if(a0 >= s1) {
      return -0x1L;
    }

    long addr = _a00091fc.offset(s3 * 24).getAddress();

    //LAB_bfc08554
    do {
      if(FUN_bfc08020(addr, new String(out)) != 0) {
        _a0009e10.setu(s3);
        return s3;
      }

      //LAB_bfc08574
      s3++;
      addr += 0x18L;
    } while(s3 < s1);

    //LAB_bfc08588
    return -0x1L;
  }

  @Method(0xbfc0d890L)
  public static int open(final String name, final int mode) {
    return FileOpen_Impl_B32(name, mode);
  }

  @Method(0xbfc0d8a0L)
  public static void close(final int fd) {
    FileClose_Impl_B36(fd);
  }

  @Method(0xbfc0d8b0L)
  public static int read(final int fd, final long buf, final int size) {
    return FileRead_Impl_B34(fd, buf, size);
  }

  @Method(0xbfc0dae0L)
  public static long alloc_kernel_memory(final int size) {
    return alloc_kernel_memory_Impl_B00(size);
  }

  @Method(0xbfc0db40L)
  public static void SysInitMemory(final long address, final int size) {
    SysInitMemory_Impl_C08(address, size);
  }

  @Method(0xbfc0dc10L)
  public static boolean CdInitSubFunc() {
    return CdInitSubFunc_Impl_A95();
  }
}
