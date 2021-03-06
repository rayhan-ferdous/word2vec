package com.ibm.JikesRVM;

/**
 * Low level memory management functions.
 *
 * Note that this class is "uninterruptible" - calling its methods will never 
 * cause the current thread to yield the cpu to another thread (one that
 * might cause a gc, for example).
 *
 * @author Dave Grove
 * @author Derek Lieber
 * @author Kris Venstermans
 */
public class VM_Memory implements VM_Uninterruptible, VM_SizeConstants {

    /** 
   * How many bytes is considered large enough to justify the transition to
   * C code to use memcpy?
   */
    private static final int NATIVE_THRESHOLD = 256;

    /**
   * Low level copy of len elements from src[srcPos] to dst[dstPos].
   * Assumptions: src != dst || (scrPos >= dstPos + 4) and
   *              src and dst are 8Bit arrays.
   * @param src     the source array
   * @param srcPos  index in the source array to begin copy
   * @param dst     the destination array
   * @param dstPos  index in the destination array to being copy
   * @param len     number of array elements to copy
   */
    public static void arraycopy8Bit(Object src, int srcPos, Object dst, int dstPos, int len) throws VM_PragmaInline {
        if (len > NATIVE_THRESHOLD) {
            memcopy(VM_Magic.objectAsAddress(dst).add(dstPos), VM_Magic.objectAsAddress(src).add(srcPos), len);
        } else {
            if (len >= BYTES_IN_ADDRESS && (srcPos & (BYTES_IN_ADDRESS - 1)) == (dstPos & (BYTES_IN_ADDRESS - 1))) {
                int byteStart = srcPos;
                int wordStart = alignUp(srcPos, BYTES_IN_ADDRESS);
                int wordEnd = alignDown(srcPos + len, BYTES_IN_ADDRESS);
                int byteEnd = srcPos + len;
                int startDiff = wordStart - byteStart;
                int endDiff = byteEnd - wordEnd;
                int wordLen = wordEnd - wordStart;
                if (startDiff == 1) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (startDiff == 2) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (startDiff == 3) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (startDiff == 4) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (startDiff == 5) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (startDiff == 6) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (startDiff == 7) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                }
                internalAligned32Copy(VM_Magic.objectAsAddress(dst).add(dstPos), VM_Magic.objectAsAddress(src).add(srcPos), wordLen);
                srcPos += wordLen;
                dstPos += wordLen;
                if (endDiff == 1) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (endDiff == 2) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (endDiff == 3) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (endDiff == 4) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (endDiff == 5) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (endDiff == 6) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                } else if (endDiff == 7) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                }
            } else {
                for (int i = 0; i < len; i++) {
                    VM_Magic.setByteAtOffset(dst, dstPos++, VM_Magic.getByteAtOffset(src, srcPos++));
                }
            }
        }
    }

    /**
   * Low level copy of len elements from src[srcPos] to dst[dstPos].
   * Assumption src != dst || (srcPos >= dstPos + 2).
   * 
   * @param src     the source array
   * @param srcPos  index in the source array to begin copy
   * @param dst     the destination array
   * @param dstPos  index in the destination array to being copy
   * @param len     number of array elements to copy
   */
    public static void arraycopy(short[] src, int srcPos, short[] dst, int dstPos, int len) throws VM_PragmaInline {
        if (len > (NATIVE_THRESHOLD >> LOG_BYTES_IN_SHORT)) {
            memcopy(VM_Magic.objectAsAddress(dst).add(dstPos << LOG_BYTES_IN_SHORT), VM_Magic.objectAsAddress(src).add(srcPos << LOG_BYTES_IN_SHORT), len << LOG_BYTES_IN_SHORT);
        } else {
            if (len >= (BYTES_IN_ADDRESS >>> LOG_BYTES_IN_SHORT) && (srcPos & ((BYTES_IN_ADDRESS - 1) >>> LOG_BYTES_IN_SHORT)) == (dstPos & ((BYTES_IN_ADDRESS - 1) >>> LOG_BYTES_IN_SHORT))) {
                int byteStart = srcPos << LOG_BYTES_IN_SHORT;
                int wordStart = alignUp(byteStart, BYTES_IN_ADDRESS);
                int wordEnd = alignDown(byteStart + (len << LOG_BYTES_IN_SHORT), BYTES_IN_ADDRESS);
                int byteEnd = byteStart + (len << LOG_BYTES_IN_SHORT);
                int startDiff = wordStart - byteStart;
                int endDiff = byteEnd - wordEnd;
                int wordLen = wordEnd - wordStart;
                for (; startDiff > 0; startDiff -= BYTES_IN_SHORT) {
                    dst[dstPos++] = src[srcPos++];
                }
                internalAligned32Copy(VM_Magic.objectAsAddress(dst).add(dstPos << LOG_BYTES_IN_SHORT), VM_Magic.objectAsAddress(src).add(srcPos << LOG_BYTES_IN_SHORT), wordLen);
                wordLen = wordLen >>> LOG_BYTES_IN_SHORT;
                srcPos += wordLen;
                dstPos += wordLen;
                for (; endDiff > 0; endDiff -= BYTES_IN_SHORT) {
                    dst[dstPos++] = src[srcPos++];
                }
            } else {
                for (int i = 0; i < len; i++) {
                    dst[dstPos + i] = src[srcPos + i];
                }
            }
        }
    }

    /**
   * Low level copy of len elements from src[srcPos] to dst[dstPos].
   * Assumption src != dst || (srcPos >= dstPos + 2).
   * 
   * @param src     the source array
   * @param srcPos  index in the source array to begin copy
   * @param dst     the destination array
   * @param dstPos  index in the destination array to being copy
   * @param len     number of array elements to copy
   */
    public static void arraycopy(char[] src, int srcPos, char[] dst, int dstPos, int len) throws VM_PragmaInline {
        if (len > (NATIVE_THRESHOLD >> LOG_BYTES_IN_CHAR)) {
            memcopy(VM_Magic.objectAsAddress(dst).add(dstPos << LOG_BYTES_IN_CHAR), VM_Magic.objectAsAddress(src).add(srcPos << LOG_BYTES_IN_CHAR), len << LOG_BYTES_IN_CHAR);
        } else {
            if (len >= (BYTES_IN_ADDRESS >>> LOG_BYTES_IN_CHAR) && (srcPos & ((BYTES_IN_ADDRESS - 1) >>> LOG_BYTES_IN_CHAR)) == (dstPos & ((BYTES_IN_ADDRESS - 1) >>> LOG_BYTES_IN_CHAR))) {
                int byteStart = srcPos << LOG_BYTES_IN_CHAR;
                int wordStart = alignUp(byteStart, BYTES_IN_ADDRESS);
                int wordEnd = alignDown(byteStart + (len << LOG_BYTES_IN_CHAR), BYTES_IN_ADDRESS);
                int byteEnd = byteStart + (len << LOG_BYTES_IN_CHAR);
                int startDiff = wordStart - byteStart;
                int endDiff = byteEnd - wordEnd;
                int wordLen = wordEnd - wordStart;
                for (; startDiff > 0; startDiff -= BYTES_IN_CHAR) {
                    dst[dstPos++] = src[srcPos++];
                }
                internalAligned32Copy(VM_Magic.objectAsAddress(dst).add(dstPos << LOG_BYTES_IN_CHAR), VM_Magic.objectAsAddress(src).add(srcPos << LOG_BYTES_IN_CHAR), wordLen);
                wordLen = wordLen >>> LOG_BYTES_IN_CHAR;
                srcPos += wordLen;
                dstPos += wordLen;
                for (; endDiff > 0; endDiff -= BYTES_IN_CHAR) {
                    dst[dstPos++] = src[srcPos++];
                }
            } else {
                for (int i = 0; i < len; i++) {
                    dst[dstPos + i] = src[srcPos + i];
                }
            }
        }
    }

    /**
   * Copy numbytes from src to dst.
   * Assumption either the ranges are non overlapping, or src >= dst + 4.
   * Also, src and dst are 4 byte aligned and numBytes is a multiple of 4.
   * @param dst the destination addr
   * @param src the source addr
   * @param numBytes the number of bytes top copy
   */
    public static void aligned32Copy(VM_Address dst, VM_Address src, int numBytes) throws VM_PragmaInline {
        if (numBytes > NATIVE_THRESHOLD) {
            memcopy(dst, src, numBytes);
        } else {
            internalAligned32Copy(dst, src, numBytes);
        }
    }

    /**
   * Copy numbytes from src to dst.
   * Assumption either the ranges are non overlapping, or src >= dst + 4.
   * @param dst the destination addr
   * @param src the source addr
   * @param numBytes the number of bytes top copy
   */
    private static void internalAligned32Copy(VM_Address dst, VM_Address src, int numBytes) throws VM_PragmaInline {
        for (int i = 0; i < numBytes; i += 4) {
            VM_Magic.setMemoryInt(dst.add(i), VM_Magic.getMemoryInt(src.add(i)));
        }
    }

    /**
   * Copy a region of memory.
   * Taken:    destination address
   *           source address
   *           number of bytes to copy
   * Returned: nothing
   * Assumption: source and destination regions do not overlap
   */
    static void memcopy(VM_Address dst, VM_Address src, int cnt) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        VM_SysCall.call_I_A_A_I(bootRecord.sysCopyIP, dst, src, cnt);
    }

    /**
   * Fill a region of memory.
   * Taken:    destination address
   *           pattern
   *           number of bytes to fill with pattern
   * Returned: nothing
   */
    static void fill(VM_Address dst, byte pattern, int cnt) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        VM_SysCall.call_I_A_I_I(bootRecord.sysFillIP, dst, pattern, cnt);
    }

    /**
   * Zero a region of memory.
   * Taken:    start of address range (inclusive)
   *           end of address range   (exclusive)
   * Returned: nothing
   */
    public static void zero(VM_Address start, VM_Address end) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        VM_SysCall.call_I_A_I(bootRecord.sysZeroIP, start, end.diff(start).toInt());
    }

    public static void zero(VM_Address start, int len) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        VM_SysCall.call_I_A_I(bootRecord.sysZeroIP, start, len);
    }

    public static void zero(VM_Address start, VM_Extent len) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        VM_SysCall.call_I_A_I(bootRecord.sysZeroIP, start, len.toInt());
    }

    /**
   * Zero a range of pages of memory.
   * Taken:    start address       (must be a page address)
   *           number of bytes     (must be multiple of page size)
   * Returned: nothing
   */
    public static void zeroPages(VM_Address start, int len) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(start) && isPageMultiple(len));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        VM_SysCall.call_I_A_I(bootRecord.sysZeroPagesIP, start, len);
    }

    /**
   * Synchronize a region of memory: force data in dcache to be written out to main 
   * memory so that it will be seen by icache when instructions are fetched back.
   * Taken:    start of address range
   *           size of address range (bytes)
   * Returned: nothing
   */
    public static void sync(VM_Address address, int size) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        VM_SysCall.call_I_A_I(bootRecord.sysSyncCacheIP, address, size);
    }

    public static final int PROT_NONE = 0;

    public static final int PROT_READ = 1;

    public static final int PROT_WRITE = 2;

    public static final int PROT_EXEC = 4;

    public static final int MAP_SHARED = 1;

    public static final int MAP_PRIVATE = 2;

    public static final int MAP_FIXED = 16;

    public static final int MAP_ANONYMOUS = 32;

    public static final int MS_ASYNC = 1;

    public static final int MS_INVALIDATE = 2;

    public static final int MS_SYNC = 4;

    public static final int PROT_NONE = 0;

    public static final int PROT_READ = 1;

    public static final int PROT_WRITE = 2;

    public static final int PROT_EXEC = 4;

    public static final int MAP_SHARED = 1;

    public static final int MAP_PRIVATE = 2;

    public static final int MAP_FIXED = 256;

    public static final int MAP_ANONYMOUS = 16;

    public static final int MS_ASYNC = 16;

    public static final int MS_INVALIDATE = 32;

    public static final int MS_SYNC = 64;

    public static boolean isPageMultiple(int val) {
        int pagesizeMask = getPagesize() - 1;
        return ((val & pagesizeMask) == 0);
    }

    public static boolean isPageMultiple(long val) {
        int pagesizeMask = getPagesize() - 1;
        return ((val & ((long) pagesizeMask)) == 0);
    }

    public static boolean isPageAligned(VM_Address addr) {
        VM_Word pagesizeMask = VM_Word.fromIntZeroExtend(getPagesize() - 1);
        return addr.toWord().and(pagesizeMask).isZero();
    }

    public static int roundDownPage(int size) {
        size &= ~(getPagesize() - 1);
        return size;
    }

    public static VM_Address roundDownPage(VM_Address addr) {
        return VM_Memory.alignDown(addr, getPagesize());
    }

    public static int roundUpPage(int size) {
        return roundDownPage(size + getPagesize() - 1);
    }

    public static VM_Address roundUpPage(VM_Address addr) {
        return VM_Memory.alignUp(addr, getPagesize());
    }

    /**
   * Do mmap general memory mapping call (not implemented)
   * Taken:    start of address range (VM_Address)
   *           size of address range
   *           protection (int)
   *           flags (int)
   *           fd (int)
   *           offset (long)
   * Returned: VM_Address (of region)
   */
    public static VM_Address mmap(VM_Address address, int size, int prot, int flags, int fd, long offset) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(address) && isPageMultiple(size) && isPageMultiple(offset));
        return VM_Address.max();
    }

    /**
   * Do mmap file memory mapping call
   * Taken:    start of address range (VM_Address)
   *           size of address range
   *           file name (char *)
   * Returned: VM_Address (of region)
   */
    public static VM_Address mmapFile(VM_Address address, int size, int fd, int prot) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(address) && isPageMultiple(size));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_A_A_I_I_I(bootRecord.sysMMapGeneralFileIP, address, size, fd, prot);
    }

    /**
   * Do mmap non-file memory mapping call
   * Taken:    start of address range (VM_Address)
   *           size of address range 
   *           protection (int)
   *           flags (int)
   * Returned: VM_Address (of region) if successful; errno (1 to 127) otherwise
   */
    public static VM_Address mmap(VM_Address address, int size, int prot, int flags) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(address) && isPageMultiple(size));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_A_A_I_I_I(bootRecord.sysMMapNonFileIP, address, size, prot, flags);
    }

    /**
   * Do mmap demand zero fixed address memory mapping call
   * Taken:    start of address range (VM_Address)
   *           size of address range 
   * Returned: VM_Address (of region)
   */
    public static VM_Address mmap(VM_Address address, int size) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(address) && isPageMultiple(size));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_A_A_I(bootRecord.sysMMapDemandZeroFixedIP, address, size);
    }

    /**
   * Do mmap demand zero any address memory mapping call
   * Taken:    size of address range (VM_Address)
   * Returned: VM_Address (of region)
   */
    public static VM_Address mmap(int size) {
        if (VM.VerifyAssertions) VM._assert(isPageMultiple(size));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_A_I(bootRecord.sysMMapDemandZeroAnyIP, size);
    }

    /**
   * Do munmap system call
   * Taken:    start of address range (VM_Address)
   *           size of address range 
   * Returned: 0 if successfull; errno otherwise
   */
    public static int munmap(VM_Address address, int size) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(address) && isPageMultiple(size));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_I_A_I(bootRecord.sysMUnmapIP, address, size);
    }

    /**
   * Do mprotect system call
   * Taken:    start of address range (VM_Address)
   *           size of address range 
   *           protection (int)
   * Returned: true if success
   */
    public static boolean mprotect(VM_Address address, int size, int prot) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(address) && isPageMultiple(size));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_I_A_I_I(bootRecord.sysMProtectIP, address, size, prot) == 0;
    }

    /**
   * Do msync system call
   * Taken:    start of address range (VM_Address)
   *           size of address range 
   *           flags (int)
   * Returned: true if success
   */
    public static boolean msync(VM_Address address, int size, int flags) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(address) && isPageMultiple(size));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_I_A_I_I(bootRecord.sysMSyncIP, address, size, flags) == 0;
    }

    /**
   * Do madvise system call (UNIMPLEMENTED IN LINUX)
   * Taken:    start of address range (VM_Address)
   *           size of address range 
   *           advice (int)
   * Returned: true if success
   */
    public static boolean madvise(VM_Address address, int size, int advice) {
        if (VM.VerifyAssertions) VM._assert(isPageAligned(address) && isPageMultiple(size));
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_I_A_I_I(bootRecord.sysMAdviseIP, address, size, advice) == 0;
    }

    public static final int SHMGET_IPC_CREAT = 1 * 512;

    public static final int SHMGET_IPC_EXCL = 2 * 512;

    public static final int SHMGET_IRUSR = 4 * 64;

    public static final int SHMGET_IWUSR = 2 * 64;

    public static final int SHMGET_IRGRP = 4 * 8;

    public static final int SHMGET_IWGRP = 2 * 8;

    public static final int SHMGET_IROTH = 4;

    public static final int SHMGET_IWOTH = 2;

    public static final int SHMAT_MAP = 4 * 512;

    public static final int SHMAT_LBA = 268435456;

    public static final int SHMAT_RDONLY = 1 * 4096;

    public static final int SHMAT_RND = 2 * 4096;

    public static final int SHMCTL_IPC_RMID = 0;

    public static final int SHMGET_IPC_CREAT = 1 * 512;

    public static final int SHMGET_IPC_EXCL = 2 * 512;

    public static final int SHMGET_IPC_NOWAIT = 4 * 512;

    public static final int SHMGET_IRUSR = 4 * 64;

    public static final int SHMGET_IWUSR = 2 * 64;

    public static final int SHMGET_IRGRP = 4 * 8;

    public static final int SHMGET_IWGRP = 2 * 8;

    public static final int SHMGET_IROTH = 4;

    public static final int SHMGET_IWOTH = 2;

    public static final int SHMAT_RDONLY = 1 * 4096;

    public static final int SHMAT_RND = 2 * 4096;

    public static final int SHMAT_REMAP = 4 * 4096;

    public static final int SHMCTL_IPC_RMID = 0;

    /**
   * Do shmget call
   * Taken:    secret key or IPC_PRIVATE
   *           size of address range
   *           segment attributes
   * Returned: shared memory segment id 
   */
    public static int shmget(int key, int size, int flags) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call3(bootRecord.sysShmgetIP, key, size, flags);
    }

    /**
   * Do shmat call
   * Taken:    shmid obtained from shmget
   *           size of address range
   *           access attributes
   * Returned: address of attached shared memory segment 
   */
    public static VM_Address shmat(int shmid, VM_Address addr, int flags) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_A_I_A_I(bootRecord.sysShmatIP, shmid, addr, flags);
    }

    /**
   * Do shmdt call
   * Taken:    address of mapped region
   * Returned: shared memory segment id 
   */
    public static int shmdt(VM_Address addr) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_I_A(bootRecord.sysShmdtIP, addr);
    }

    /**
   * Do shmctl call
   * Taken:    shmid obtained from shmget
   *           command
   *           missing buffer argument
   * Returned: shared memory segment id 
   */
    public static VM_Address shmctl(int shmid, int command) {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        return VM_SysCall.call_A_I_I(bootRecord.sysShmctlIP, shmid, command);
    }

    /**
   * Do getpagesize call
   * Taken:    none
   * Returned: page size
   */
    private static int pagesize = -1;

    private static int pagesizeLog = -1;

    public static int getPagesize() {
        VM_BootRecord bootRecord = VM_BootRecord.the_boot_record;
        if (pagesize == -1) {
            pagesize = VM_SysCall.call0(bootRecord.sysGetPageSizeIP);
            pagesizeLog = -1;
            int temp = pagesize;
            while (temp > 0) {
                temp >>>= 1;
                pagesizeLog++;
            }
            if (VM.VerifyAssertions) VM._assert((1 << pagesizeLog) == pagesize);
        }
        return pagesize;
    }

    static int getPagesizeLog() {
        if (pagesize == -1) getPagesize();
        return pagesizeLog;
    }

    public static void dumpMemory(VM_Address start, int beforeBytes, int afterBytes) {
        beforeBytes = alignDown(beforeBytes, BYTES_IN_ADDRESS);
        afterBytes = alignUp(afterBytes, BYTES_IN_ADDRESS);
        VM.sysWrite("---- Dumping memory from ");
        VM.sysWrite(start.sub(beforeBytes));
        VM.sysWrite(" to ");
        VM.sysWrite(start.add(afterBytes));
        VM.sysWrite(" ----\n");
        for (int i = -beforeBytes; i < afterBytes; i += BYTES_IN_ADDRESS) {
            VM.sysWrite(i, ": ");
            VM.sysWrite(start.add(i));
            VM_Word value = VM_Magic.getMemoryWord(start.add(i));
            VM.sysWriteln("  ", value);
        }
    }

    static void dumpMemory(VM_Address start, int afterBytes) {
        dumpMemory(start, 0, afterBytes);
    }

    static void test_mmap() {
        int psize = VM_Memory.getPagesize();
        int size = 1024 * 1024;
        int ro = VM_Memory.PROT_READ;
        VM_Address base = VM_Address.fromIntZeroExtend(0x38000000);
        VM_Address addr = VM_Memory.mmap(base, size);
        VM.sysWrite("page size = ");
        VM.sysWrite(psize);
        VM.sysWrite("\n");
        VM.sysWrite("requested ");
        VM.sysWrite(size);
        VM.sysWrite(" bytes at ");
        VM.sysWrite(base);
        VM.sysWrite("\n");
        VM.sysWrite("mmap call returned ");
        VM.sysWrite(addr);
        VM.sysWrite("\n");
        if (addr.NE(VM_Address.fromIntSignExtend(-1))) {
            VM_Magic.setMemoryInt(addr, 17);
            if (VM_Magic.getMemoryInt(addr) == 17) {
                VM.sysWrite("write and read in memory region succeeded\n");
            } else {
                VM.sysWrite("read in memory region did not return value written\n");
            }
            if (!VM_Memory.mprotect(addr, size, ro)) {
                VM.sysWrite("mprotect failed\n");
            } else {
                VM.sysWrite("mprotect succeeded!\n");
            }
            if (VM_Magic.getMemoryInt(addr) == 17) {
                VM.sysWrite("read in memory region succeeded\n");
            } else {
                VM.sysWrite("read in memory region did not return value written\n");
            }
            if (VM_Memory.munmap(addr, size) == 0) VM.sysWrite("munmap succeeded!\n"); else VM.sysWrite("munmap failed\n");
        }
        addr = VM_Memory.mmap(size);
        VM.sysWrite("requested ");
        VM.sysWrite(size);
        VM.sysWrite(" bytes at any address\n");
        VM.sysWrite("mmap call returned ");
        VM.sysWrite(addr);
        VM.sysWrite("\n");
        if (addr.NE(VM_Address.fromIntSignExtend(-1))) {
            VM_Magic.setMemoryInt(addr, 17);
            if (VM_Magic.getMemoryInt(addr) == 17) {
                VM.sysWrite("write and read in memory region succeeded\n");
            } else {
                VM.sysWrite("read in memory region did not return value written\n");
            }
            if (!VM_Memory.mprotect(addr, size, ro)) {
                VM.sysWrite("mprotect failed\n");
            } else {
                VM.sysWrite("mprotect succeeded!\n");
            }
            if (VM_Magic.getMemoryInt(addr) == 17) {
                VM.sysWrite("read in memory region succeeded\n");
            } else {
                VM.sysWrite("read in memory region did not return value written\n");
            }
            if (VM_Memory.munmap(addr, size) == 0) VM.sysWrite("munmap succeeded!\n"); else VM.sysWrite("munmap failed\n");
        }
        VM.sysWrite("mmap tests done\n");
    }

    /**
  * @depricated use alignUp(..) instead
  */
    public static VM_Address align(VM_Address address, int alignment) throws VM_PragmaInline {
        return alignUp(address, alignment);
    }

    /**
  * @depricated use alignUp(..) instead
  */
    public static int align(int address, int alignment) throws VM_PragmaInline {
        return alignUp(address, alignment);
    }

    public static VM_Address alignUp(VM_Address address, int alignment) throws VM_PragmaInline {
        return address.add(alignment - 1).toWord().and(VM_Word.fromIntSignExtend(~(alignment - 1))).toAddress();
    }

    public static VM_Address alignDown(VM_Address address, int alignment) throws VM_PragmaInline {
        return address.toWord().and(VM_Word.fromIntSignExtend(~(alignment - 1))).toAddress();
    }

    public static int alignUp(int address, int alignment) throws VM_PragmaInline {
        return ((address + alignment - 1) & ~(alignment - 1));
    }

    public static int alignDown(int address, int alignment) throws VM_PragmaInline {
        return (address & ~(alignment - 1));
    }
}
