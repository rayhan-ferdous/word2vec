package org.stanwood.media.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is a help class that is used to perform operations on files.
 */
public class FileHelper {

    private static final Log log = LogFactory.getLog(FileHelper.class);

    /** A Line separator property value */
    public static final String LS = System.getProperty("line.separator");

    /** Stores the current users home directory */
    public static final File HOME_DIR = new File(System.getProperty("user.home"));

    private static final char HEX_DIGITS[] = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /** Time to sleep between retries */
    public static final long RETRY_SLEEP_TIME = 5000;

    /** Number of retries to retry fetching of URL's  */
    public static final int MAX_RETRIES = 3;

    /**
	 * This will create a temporary directory using the given name.
	 *
	 * @param name The name of the directory to create
	 * @return A file object pointing to the directory that was created
	 * @throws IOException Thrown if their is a problme creating the directory
	 */
    public static File createTmpDir(String name) throws IOException {
        File dir = createTempFile(name, "");
        if (!dir.delete()) {
            throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_DELETE_FILE"), dir.getAbsolutePath()));
        }
        if (!dir.mkdir()) {
            throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_CREATE_DIR"), dir.getAbsolutePath()));
        }
        return dir;
    }

    /**
	 * Used to copy a source file or a directory to a destination file or directory.
	 *
	 * @param src The source file or directory
	 * @param dst The destination file or directory
	 * @throws IOException Thrown if their is a problem copying the file or directory
	 */
    public static void copy(File src, File dst) throws IOException {
        if (dst.exists()) {
            throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_COPY_ALREADY_EXISTS"), src, dst));
        }
        if (src.isDirectory()) {
            if (!dst.mkdir() && !dst.exists()) {
                throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_CREATE_DIR"), dst));
            }
            File[] files = src.listFiles();
            for (File f : files) {
                copy(f, new File(dst, f.getName()));
            }
        } else {
            copyFile(src, dst);
        }
    }

    private static void copyFile(File src, File dst) throws FileNotFoundException, IOException {
        InputStream in = null;
        try {
            in = new FileInputStream(src);
            copy(in, dst);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(Messages.getString("FileHelper.UNABLE_CLOSE_INPUT_STREAM"), e);
                }
            }
        }
    }

    /**
	 * Used to copy the contents of a input stream to a destination file.
	 *
	 * @param in The input stream
	 * @param dst The destination file
	 * @throws IOException Thrown if their is a problem copying the file
	 */
    public static void copy(InputStream in, File dst) throws IOException {
        if (in == null) {
            throw new NullPointerException("Stream is null");
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(dst);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(Messages.getString("FileHelper.UNABLE_CLOSE_OUTPUT_STREAM"), e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(Messages.getString("FileHelper.UNABLE_CLOSE_INPUT_STREAM"), e);
                }
            }
        }
    }

    /**
	 * Used to copy the contents of a input stream to a destination file.
	 *
	 * @param in The input stream
	 * @param dst The destination file
	 * @param params Parameters which are replaced with values when the file is copied
	 * @throws IOException Thrown if their is a problem copying the file
	 */
    public static void copy(InputStream in, File dst, Map<String, String> params) throws IOException {
        PrintStream out = null;
        BufferedReader bin = null;
        try {
            out = new PrintStream(new FileOutputStream(dst));
            bin = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bin.readLine()) != null) {
                for (Entry<String, String> e : params.entrySet()) {
                    line = line.replaceAll("\\$" + e.getKey() + "\\$", e.getValue());
                }
                out.println(line);
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (bin != null) {
                bin.close();
            }
        }
    }

    /**
	 * This will copy a file from the web to a destination file on the local system
	 * @param url The url to read from the file from
	 * @param dest The file to be created on the location system
	 * @return A MD5 sum of the file
	 * @throws IOException Thrown if their is a problem reading or wring the file
	 */
    public static String copy(URL url, File dest) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("Fetching: " + url);
        }
        IOException error = null;
        for (int retries = 0; retries < MAX_RETRIES; retries++) {
            try {
                OutputStream out = null;
                InputStream is = null;
                try {
                    out = new FileOutputStream(dest);
                    if (url.getProtocol().equals("http")) {
                        is = new WebFileInputStream(url);
                    } else {
                        is = url.openStream();
                    }
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = is.read(buf)) > 0) {
                        out.write(buf, 0, len);
                        md.update(buf, 0, len);
                    }
                    out.flush();
                    return bytesToHexString(md.digest());
                } catch (ConnectException e) {
                    if (error == null) {
                        error = e;
                    }
                    if (retries < MAX_RETRIES - 1) {
                        log.error(MessageFormat.format("Unable to fetch URL {0}, connection timed out. Will retry...", url.toExternalForm()));
                        try {
                            Thread.sleep(FileHelper.RETRY_SLEEP_TIME);
                        } catch (InterruptedException e2) {
                        }
                    }
                } catch (SocketTimeoutException e) {
                    if (error == null) {
                        error = e;
                    }
                    if (retries < MAX_RETRIES - 1) {
                        log.error(MessageFormat.format("Unable to fetch URL {0}, timed out. Will retry...", url.toExternalForm()));
                        try {
                            Thread.sleep(FileHelper.RETRY_SLEEP_TIME);
                        } catch (InterruptedException e2) {
                        }
                    }
                } catch (IOException e) {
                    if (dest.exists()) {
                        try {
                            FileHelper.delete(dest);
                        } catch (IOException e1) {
                            log.error(MessageFormat.format(Messages.getString("FileHelper.UNABLE_DELETE_FILE"), dest), e1);
                        }
                    }
                    throw e;
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            log.error(Messages.getString("FileHelper.UNABLE_CLOSE_STREAM"), e);
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            log.error(Messages.getString("FileHelper.UNABLE_CLOSE_STREAM"), e);
                        }
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_DOWNLOAD_URL"), url), e);
            }
        }
        throw error;
    }

    /**
	 * Used to generate a MD5 checksum string for a file
	 * @param file The file
	 * @return The checksum
	 * @throws IOException Thrown if their are any problems
	 */
    public static String getMD5Checksum(File file) throws IOException {
        try {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    md.update(buf, 0, len);
                }
                return bytesToHexString(md.digest());
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_GET_CHECKSUM_FILE"), file), e);
        }
    }

    private static String bytesToHexString(byte data[]) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (int buc = 0; buc < data.length; buc++) {
            sb.append(HEX_DIGITS[(data[buc] >> 4) & 0x0F]);
            sb.append(HEX_DIGITS[data[buc] & 0x0F]);
        }
        return sb.toString();
    }

    /**
	 * Used to delete a directory and all it's children
	 *
	 * @param dir The directory to delete
	 * @return True if successful, otherwise false
	 */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
	 * Used to display the contents of a file
	 *
	 * @param file The file to display
	 * @param os The output stream to display it to
	 * @throws IOException Thrown if their is a problem reading or displaying the file
	 */
    public static void displayFile(File file, PrintStream os) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        while ((str = in.readLine()) != null) {
            os.println(str);
        }
        in.close();
    }

    /**
	 * Used to display the contents of a file
	 * @param file The file to display
	 * @param startLine The line to start displaying from
	 * @param endLine The line to finish displaying from
	 * @param os The output stream used to print the file to
	 * @throws IOException Thrown if their is a problem reading the file
	 */
    public static void displayFile(File file, int startLine, int endLine, OutputStream os) throws IOException {
        PrintStream ps = new PrintStream(os);
        if (startLine < 0) {
            startLine = 0;
        }
        int line = 1;
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        while ((str = in.readLine()) != null) {
            if (line >= startLine && line <= endLine) {
                ps.println(line + ": " + str);
            }
            line++;
        }
        in.close();
    }

    /**
	 * Used to read the contents of a file into a string
	 *
	 * @param file The file to read
	 * @return The contents of the file
	 * @throws IOException Thrown if their is a problem reading the file
	 */
    public static String readFileContents(File file) throws IOException {
        StringBuilder results = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                results.append(str + LS);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return results.toString();
    }

    /**
	 * Used to read the contents of a stream into a string
	 *
	 * @param inputStream The input stream
	 * @return The contents of the file
	 * @throws IOException Thrown if their is a problem reading the file
	 */
    public static String readFileContents(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException(Messages.getString("FileHelper.INPUT_STREAM_IS_NULL"));
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder results = new StringBuilder();
            String str;
            while ((str = in.readLine()) != null) {
                results.append(str + LS);
            }
            return results.toString();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
	 * Used to list all the directories in a directory and it's sub directories.
	 * @param dir The directory to list the files of
	 * @return The files in the directory
	 */
    public static List<File> listDirectories(File dir) {
        List<File> files = new ArrayList<File>();
        listDirectories(dir, files);
        Collections.sort(files);
        return files;
    }

    private static void listDirectories(File dir, List<File> dirs) {
        if (dir.isDirectory()) {
            for (File d : dir.listFiles()) {
                listDirectories(d, dirs);
            }
            dirs.add(dir);
        }
    }

    /**
	 * Used to list all the files in a directory and it's sub directories.
	 * @param dir The directory to list the files of
	 * @return The files in the directory
	 */
    public static List<File> listFiles(File dir) {
        List<File> files = new ArrayList<File>();
        listFiles(dir, files);
        Collections.sort(files);
        return files;
    }

    /**
	 * Used to list all the files in a directory and it's sub directiories.
	 * File files are returned as a list of absolute paths.
	 * @param dir The directory to list the files of
	 * @return The files in the directory
	 */
    public static List<String> listFilesAsStrings(File dir) {
        List<String> files = new ArrayList<String>();
        List<File> files2 = listFiles(dir);
        for (File f : files2) {
            files.add(f.getAbsolutePath());
        }
        return files;
    }

    private static void listFiles(File file, List<File> files) {
        if (file.isDirectory()) {
            for (File d : file.listFiles()) {
                listFiles(d, files);
            }
        } else {
            files.add(file);
        }
    }

    /**
	 * Used to add contents to a file
	 * @param file The file to add contetns to
	 * @param contents The contents
	 * @throws IOException Thrown if their is a IO problem
	 */
    public static void appendContentsToFile(File file, StringBuilder contents) throws IOException {
        PrintStream ps = null;
        try {
            FileOutputStream os = new FileOutputStream(file);
            ps = new PrintStream(os);
            ps.print(contents.toString());
        } finally {
            ps.close();
        }
    }

    /**
	 * Used to unzip a file to a directory
	 * @param is The input stream containing the file
	 * @param destDir The directory to unzip to
	 * @throws IOException Thrown if their are any problems
	 */
    public static void unzip(InputStream is, File destDir) throws IOException {
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(is);
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    if (!file.mkdir() && file.exists()) {
                        throw new IOException("Unable to create directory: " + file);
                    }
                } else {
                    BufferedOutputStream out = null;
                    try {
                        int count;
                        byte data[] = new byte[1000];
                        out = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())), 1000);
                        if (log.isDebugEnabled()) {
                            log.debug("Unzipping " + entry.getName() + " with size " + entry.getSize());
                        }
                        while ((count = zis.read(data, 0, 1000)) != -1) {
                            out.write(data, 0, count);
                        }
                        out.flush();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                }
            }
        } finally {
            if (zis != null) {
                zis.close();
            }
        }
    }

    /**
	 * Used to move a directory or file from once location to another
	 * @param from The old name of the file or directory
	 * @param to The new name of the file or directory
	 * @throws IOException Thrown if their are any problems
	 */
    public static void move(File from, File to) throws IOException {
        if (!from.exists()) {
            throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_MOVE_FILE_SRC_NOT_FOUND"), from, to));
        }
        if (to.exists()) {
            throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_MOVE_FILE_DEST_ALREADY_EXISTS"), from, to));
        }
        copy(from, to);
        delete(from);
    }

    /**
	 * Used to delete a file or a directory tree. If a directory is given, then all it's contents are also deleted recusrsivly.
	 * @param file The file or directory to delete
	 * @throws IOException Thrown if their are any problems
	 */
    public static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            FileHelper.deleteDir(file);
        } else {
            if (!file.delete() && file.exists()) {
                throw new IOException(MessageFormat.format(Messages.getString("FileHelper.UNABLE_DELETE_FILE"), file));
            }
        }
    }

    /**
	 * Used to create a temporary file with the give contents
	 * @param testConfig The contents to put in the file
	 * @return A reference to the file
	 * @throws IOException Thrown if their are any problems
	 */
    public static File createTmpFileWithContents(StringBuilder testConfig) throws IOException {
        File configFile = createTempFile("config", ".xml");
        configFile.deleteOnExit();
        FileHelper.appendContentsToFile(configFile, testConfig);
        return configFile;
    }

    /**
	 * Used to get a stream to a URL. If their is a socket timeout exception, then
	 * this method will wait 5 seconds and try again. It does this 3 times before throwing the
	 * exception out if the method
	 * @param url The URL of the stream
	 * @return The stream
	 * @throws IOException Thrown if their are any problems
	 */
    public static Stream getInputStream(URL url) throws IOException {
        SocketTimeoutException e = null;
        for (int tryCount = 0; tryCount < 3; tryCount++) {
            try {
                WebFileInputStream is = new WebFileInputStream(url);
                String MIME = is.getMIMEType();
                if (MIME.equals("application/zip")) {
                    return new Stream(new ZipInputStream(is), MIME, is.getCharset(), url.toExternalForm(), url);
                } else {
                    return new Stream(is, MIME, is.getCharset(), url.toExternalForm(), url);
                }
            } catch (SocketTimeoutException e1) {
                log.warn(MessageFormat.format("Timed out fetching URL ''{0}'', going to retry..", url.toExternalForm()));
                if (e == null) {
                    e = e1;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e2) {
                }
            }
        }
        throw e;
    }

    /**
	 * Used a temporary file that will be deleted when the JVM exits
	 * @param name name of file
	 * @param ext extension of the file
	 * @return The file
	 * @throws IOException Thrown if their is a problem creating the file
	 */
    public static File createTempFile(String name, String ext) throws IOException {
        final File file = File.createTempFile(name, ext);
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                if (file.exists()) {
                    try {
                        FileHelper.delete(file);
                    } catch (IOException e) {
                        log.error(MessageFormat.format(Messages.getString("FileHelper.UNABLE_DELETE_TEMP_FILE"), file), e);
                    }
                }
            }
        });
        return file;
    }

    /**
	 * Used to get the extension of the file
	 * @param file The file
	 * @return The extension
	 */
    public static String getExtension(File file) {
        String fileName = file.getAbsolutePath();
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) {
            return "";
        }
        return fileName.substring(pos + 1);
    }

    /**
	 * Used to get the name of the file
	 * @param file The file
	 * @return The name
	 */
    public static String getName(File file) {
        String fileName = file.getName();
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) {
            return fileName;
        }
        return fileName.substring(0, pos);
    }

    /**
	 * Used to get the current working directory
	 * @return the current working directory
	 */
    public static File getWorkingDirectory() {
        return new File(System.getProperty("user.dir"));
    }

    /**
	 * Used to convert relative paths to absolute paths. This will remove .. from the path
	 * @param path The relative path
	 * @return The absolute path
	 */
    public static File resolveRelativePaths(File path) {
        String segments[] = path.getAbsolutePath().split(Pattern.quote(File.separator));
        List<String> newSegments = new ArrayList<String>();
        for (String seg : segments) {
            if (seg.equals("..")) {
                newSegments.remove(newSegments.size() - 1);
            } else {
                newSegments.add(seg);
            }
        }
        File result = null;
        for (String seg : newSegments) {
            if (result == null) {
                result = new File(seg);
            } else {
                result = new File(result, seg);
            }
        }
        return result;
    }

    /**
	 * Used to rename a file. This handles the case where java's {@link File#renameTo(File)} fails. It that
	 * occurs it copies the file and deletes the original
	 * @param oldFile The old file name
	 * @param newFile The new file name
	 * @throws IOException Thrown if their are any problems
	 */
    public static void rename(File oldFile, File newFile) throws IOException {
        if (!oldFile.renameTo(newFile) && !newFile.exists()) {
            FileHelper.copy(oldFile, newFile);
            if (newFile.exists() && oldFile.length() == newFile.length()) {
                FileHelper.delete(oldFile);
            } else {
                throw new IOException(MessageFormat.format("Unable to copy file {0} to {1}", oldFile, newFile));
            }
        }
    }
}
