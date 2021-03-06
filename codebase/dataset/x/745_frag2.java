        pw.println("                               .Q(write_addrgray[3]));      ");

        pw.println("   ");

        pw.println("   ");

        pw.println("   /************************************************************\\");

        pw.println("    *                                                            *");

        pw.println("    *  Alternative generation of FIFOstatus outputs.  Used to    *");

        pw.println("    *  determine how full FIFO is, based on how far the Write    *");

        pw.println("    *  pointer is ahead of the Read pointer.  read_truegray      *   ");

        pw.println("    *  is synchronized to write_clock (rag_writesync), converted *");

        pw.println("    *  to binary (ra_writesync), and then subtracted from the    *");

        pw.println("    *  pipelined write_addr (write_addrr) to find out how many   *");

        pw.println("    *  words are in the FIFO (fifostatus).  The top bits are     *   ");

        pw.println("    *  then 1/2 full, 1/4 full, etc. (not mutually exclusive).   *");

        pw.println("    *  fifostatus has a one-cycle latency on write_clock; or,    *");

        pw.println("    *  one cycle after the write address is incremented on a     *   ");

        pw.println("    *  write operation, fifostatus is updated with the new       *   ");

        pw.println("    *  capacity information.  There is a two-cycle latency on    *");

        pw.println("    *  read operations.                                          *");

        pw.println("    *                                                            *");

        pw.println("    *  If read_clock is much faster than write_clock, it is      *   ");

        pw.println("    *  possible that the fifostatus counter could drop several   *");

        pw.println("    *  positions in one write_clock cycle, so the low-order bits *");

        pw.println("    *  of fifostatus are not as reliable.                        *");

        pw.println("    *                                                            *");

        pw.println("    *  The above description if mirrored for a read clock        *");

        pw.println("    *  synchronized verfion of the same value.                   *");

        pw.println("    \\************************************************************/");

        pw.println("   ");

        pw.println("   wire [3:0]   read_truegray, rag_writesync, write_addrr;");

        pw.println("   wire [3:0]   ra_writesync;");

        pw.println("   wire [3:0]   fifostatus_write_int;");

        pw.println("");

        pw.println("   FDR read_truegray_0_inst (.C(read_clock),");

        pw.println("                             .R(read_reset_in),");

        pw.println("                             .D(read_addr[1] ^ read_addr[0]),");

        pw.println("                             .Q(read_truegray[0]));   ");

        pw.println("   ");

        pw.println("   FDR read_truegray_1_inst (.C(read_clock),");

        pw.println("                             .R(read_reset_in),");

        pw.println("                             .D(read_addr[2] ^ read_addr[1]),");

        pw.println("                             .Q(read_truegray[1]));   ");

        pw.println("   ");
