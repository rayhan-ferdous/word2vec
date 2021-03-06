package at.HexLib.GUI.IO;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingUtilities;
import sun.management.ManagementFactory;

public class LoadContent {

    int maxProgress = 50;

    private ProgressMonitor progMonitor;

    public void createMonitor(JFrame win) {
        progMonitor = getProgessMonitor(win);
        setProgress(0);
    }

    public static byte[] readFile(JFrame win, String file, int size) {
        int bytesRead;
        BufferedInputStream bis = null;
        MyOutputByteStream bosFile = null;
        try {
            ProgressMonitorInputStream progessbar = new ProgressMonitorInputStream(win, "Reading " + file, new FileInputStream(file));
            progessbar.getProgressMonitor().setMillisToDecideToPopup(50);
            progessbar.getProgressMonitor().setMillisToPopup(100);
            bis = new BufferedInputStream(progessbar);
            byte[] buffer = new byte[new MyBufferInput(null).getBufferSize()];
            bosFile = new MyOutputByteStream(size);
            while ((bytesRead = bis.read(buffer)) != -1) {
                bosFile.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            System.err.println("Out of Memory");
        } finally {
            try {
                if (bis != null) bis.close();
            } catch (IOException e) {
            }
        }
        return bosFile.getBuffer();
    }

    public boolean OutOfMemExceptionRaised = false;

    public byte[] readFileNIO(String file) {
        try {
            System.out.println("Start NIO #2: " + (System.currentTimeMillis()));
            progMonitor.setNote(file);
            setProgress(1);
            System.out.println("Start NIO #3: " + (System.currentTimeMillis()));
            FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
            System.out.println("Start NIO #4: " + (System.currentTimeMillis()));
            int sz = (int) fileChannel.size();
            if (sz == 0) {
                return new byte[0];
            }
            OutOfMemExceptionRaised = false;
            System.out.println("MEMORY INFO: Used c1: " + (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage()));
            byte[] buf = new byte[sz];
            int blockSize = sz / maxProgress;
            int curStep = 0;
            for (; curStep < maxProgress && !progMonitor.isCanceled(); curStep++) {
                int offSet = blockSize * curStep;
                MappedByteBuffer bb = fileChannel.map(FileChannel.MapMode.READ_ONLY, offSet, blockSize);
                byte[] readBytes = new byte[blockSize];
                bb.get(readBytes);
                System.arraycopy(readBytes, 0, buf, offSet, blockSize);
                setProgress(curStep);
            }
            if (progMonitor.isCanceled()) {
                try {
                    return Arrays.copyOf(buf, curStep * blockSize);
                } catch (OutOfMemoryError e) {
                    OutOfMemExceptionRaised = true;
                    return buf;
                }
            } else {
                int offSetRemaining = blockSize * maxProgress;
                if (sz - offSetRemaining > 0) {
                    MappedByteBuffer bb = fileChannel.map(FileChannel.MapMode.READ_ONLY, offSetRemaining, sz - offSetRemaining);
                    byte[] readBytes = new byte[sz - offSetRemaining];
                    bb.get(readBytes);
                    System.arraycopy(readBytes, 0, buf, offSetRemaining, sz - offSetRemaining);
                }
            }
            return buf;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public boolean isMonitorCancelled() {
        return progMonitor.isCanceled();
    }

    private void setProgress(final int curStep) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    progMonitor.setProgress(curStep);
                } catch (Exception e) {
                }
            }
        });
    }

    public void hideProgessMonitor() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                progMonitor.close();
            }
        });
    }

    private ProgressMonitor getProgessMonitor(JFrame win) {
        ProgressMonitor monitor = new ProgressMonitor(win, "Reading ", null, 0, maxProgress);
        monitor.setMillisToDecideToPopup(0);
        monitor.setMillisToPopup(0);
        return monitor;
    }
}
