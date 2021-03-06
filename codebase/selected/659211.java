package frost.threads;

import java.awt.Frame;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;
import frost.*;
import frost.crypt.SignMetaData;
import frost.fcp.*;
import frost.fileTransfer.upload.FrostUploadItem;
import frost.gui.MessageUploadFailedDialog;
import frost.gui.objects.FrostBoardObject;
import frost.identities.FrostIdentities;
import frost.messages.*;

/**
 * Uploads a message to a certain message board
 */
public class MessageUploadThread extends BoardUpdateThreadObject implements BoardUpdateThread {

    private static java.util.ResourceBundle LangRes = java.util.ResourceBundle.getBundle("res.LangRes");

    private static Logger logger = Logger.getLogger(MessageUploadThread.class.getName());

    private FrostBoardObject board;

    private String destinationBase;

    private Frame frameToLock;

    private String keypool;

    private MessageObject message;

    private File messageFile;

    private int messageUploadHtl;

    private String privateKey;

    private String publicKey;

    private boolean secure;

    private byte[] signMetadata;

    private File zipFile;

    /**Constructor*/
    public MessageUploadThread(FrostBoardObject board, MessageObject mo, FrostIdentities newIdentities) {
        super(board, newIdentities);
        this.board = board;
        this.message = mo;
        if (mo.getDate() == "") {
            mo.setTime(DateFun.getFullExtendedTime() + "GMT");
            mo.setDate(DateFun.getDate());
        }
        messageUploadHtl = MainFrame.frostSettings.getIntValue("tofUploadHtl");
        keypool = MainFrame.frostSettings.getValue("keypool.dir");
        frameToLock = MainFrame.getInstance();
        String uploadMe = new StringBuffer().append(MainFrame.frostSettings.getValue("unsent.dir")).append("unsent").append(String.valueOf(System.currentTimeMillis())).append(".xml").toString();
        messageFile = new File(uploadMe);
    }

    /**
	 * This method compares the message that is to be uploaded with
	 * a local message to see if they are equal
	 * @param localFile the local message to compare the message to
	 *  	   be uploaded with.
	 * @return true if they are equal. False otherwise.
	 */
    private boolean checkLocalMessage(File localFile) {
        try {
            MessageObject localMessage = new MessageObject(localFile);
            if (!localMessage.getContent().equals(message.getContent())) {
                return false;
            }
            if (!localMessage.getSubject().equals(message.getSubject())) {
                return false;
            }
            if (!localMessage.getFrom().equals(message.getFrom())) {
                return false;
            }
            AttachmentList attachments1 = message.getAttachmentList();
            AttachmentList attachments2 = localMessage.getAttachmentList();
            if (attachments1.size() != attachments2.size()) {
                return false;
            }
            Iterator iterator1 = attachments1.iterator();
            Iterator iterator2 = attachments2.iterator();
            while (iterator1.hasNext()) {
                Attachment attachment1 = (Attachment) iterator1.next();
                Attachment attachment2 = (Attachment) iterator2.next();
                if (attachment1.compareTo(attachment2) != 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Exception while loading the local file in checkLocalMessage()", exception);
            return false;
        }
    }

    /**
	 * This method is called when there has been a key collision. It checks
	 * if the remote message with that key is the same as the message that is
	 * being uploaded
	 * @param upKey the key of the remote message to compare with the message
	 * 		   that is being uploaded.
	 * @return true if the remote message with the given key equals the 
	 * 			message that is being uploaded. False otherwise.
	 */
    private boolean checkRemoteFile(String key) {
        File remoteFile = new File(messageFile.getPath() + ".coll");
        remoteFile.delete();
        remoteFile.deleteOnExit();
        FcpResults res = FcpRequest.getFile(key, null, remoteFile, messageUploadHtl, false, false);
        if (remoteFile.length() > 0) {
            byte[] unzippedXml = FileAccess.readZipFileBinary(remoteFile);
            FileAccess.writeByteArray(unzippedXml, remoteFile);
            return checkLocalMessage(remoteFile);
        } else {
            return false;
        }
    }

    /**
	 * This method composes the downloading key for the message, given a
	 * certain index number
	 * @param index index number to use to compose the key
	 * @return they composed key
	 */
    private String composeDownKey(int index) {
        String key;
        if (secure) {
            key = new StringBuffer().append(publicKey).append("/").append(board.getBoardFilename()).append("/").append(message.getDate()).append("-").append(index).append(".xml").toString();
        } else {
            key = new StringBuffer().append("KSK@frost/message/").append(MainFrame.frostSettings.getValue("messageBase")).append("/").append(message.getDate()).append("-").append(board.getBoardFilename()).append("-").append(index).append(".xml").toString();
        }
        return key;
    }

    /**
	 * This method composes the uploading key for the message, given a
	 * certain index number
	 * @param index index number to use to compose the key
	 * @return they composed key
	 */
    private String composeUpKey(int index) {
        String key;
        if (secure) {
            key = new StringBuffer().append(privateKey).append("/").append(board.getBoardFilename()).append("/").append(message.getDate()).append("-").append(index).append(".xml").toString();
        } else {
            key = new StringBuffer().append("KSK@frost/message/").append(MainFrame.frostSettings.getValue("messageBase")).append("/").append(message.getDate()).append("-").append(board.getBoardFilename()).append("-").append(index).append(".xml").toString();
        }
        return key;
    }

    /**
	 * This method returns the base path from which we look for
	 * existing files while looking for the next available index to use.
	 * That directory is also created if it doesn't exist.
	 * @return the base path to use when looking for existing files while
	 * 			looking for the next index.
	 */
    private String getDestinationBase() {
        if (destinationBase == null) {
            String fileSeparator = System.getProperty("file.separator");
            destinationBase = new StringBuffer().append(keypool).append(board.getBoardFilename()).append(fileSeparator).append(DateFun.getDate()).append(fileSeparator).toString();
            File makedir = new File(destinationBase);
            if (!makedir.exists()) {
                makedir.mkdirs();
            }
        }
        return destinationBase;
    }

    public int getThreadType() {
        return BoardUpdateThread.MSG_UPLOAD;
    }

    /**
	 * This method performs several tasks before uploading the message.
	 * @return true if the initialization was successful. False otherwise.
	 */
    private boolean initialize() {
        if (board.isWriteAccessBoard()) {
            privateKey = board.getPrivateKey();
            publicKey = board.getPublicKey();
            secure = true;
        } else {
            secure = false;
        }
        logger.info("TOFUP: Uploading message to board '" + board.toString() + "' with HTL " + messageUploadHtl);
        if (!saveMessage(message, messageFile)) {
            logger.severe("This was a HARD error and the file to upload is lost, please report to a dev!");
            return false;
        }
        if (!uploadAttachments(message, messageFile)) {
            return false;
        }
        zipFile = new File(messageFile.getPath() + ".upltmp");
        zipFile.delete();
        zipFile.deleteOnExit();
        FileAccess.writeZipFile(FileAccess.readByteArray(messageFile), "entry", zipFile);
        String sender = message.getFrom();
        String myId = identities.getMyId().getUniqueName();
        if (sender.equals(myId) || sender.equals(Mixed.makeFilename(myId))) {
            byte[] zipped = FileAccess.readByteArray(zipFile);
            SignMetaData md = new SignMetaData(zipped, identities.getMyId());
            signMetadata = XMLTools.getRawXMLDocument(md);
        }
        return true;
    }

    public void run() {
        notifyThreadStarted(this);
        boolean retry = true;
        try {
            if (initialize()) {
                while (retry) {
                    retry = uploadMessage();
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "ERROR: MessageUploadThread.run(): unexpected IOException, terminating thread ...", ex);
        } catch (MessageAlreadyUploadedException exception) {
            logger.info("The message had already been uploaded. Therefore it will not be uploaded again.");
            messageFile.delete();
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "Oo. EXCEPTION in MessageUploadThread", t);
        }
        notifyThreadFinished(this);
    }

    /**
	 * This method saves a message to disk in XML format 
	 * @param msg the MessageObject to save
	 * @param file the file whose path will be used to save the message
	 * @return true if successful. False otherwise.
	 */
    private boolean saveMessage(MessageObject msg, File file) {
        File tmpFile = new File(file.getPath() + ".tmp");
        boolean success = false;
        try {
            Document doc = XMLTools.createDomDocument();
            doc.appendChild(msg.getXMLElement(doc));
            success = XMLTools.writeXmlFile(doc, tmpFile.getPath());
        } catch (Throwable ex) {
            logger.log(Level.SEVERE, "Exception thrown in saveMessage()", ex);
        }
        if (success && tmpFile.length() > 0) {
            messageFile.delete();
            tmpFile.renameTo(messageFile);
            return true;
        } else {
            tmpFile.delete();
            return false;
        }
    }

    /**
	 * This inserts an attached SharedFileObject into freenet
	 * @param attachment the SharedFileObject to upload
	 * @return true if successful. False otherwise.
	 */
    private boolean uploadAttachment(SharedFileObject attachment) {
        assert attachment.getFile() != null : "message.getOfflineFiles() failed!";
        String[] result = { "", "" };
        int uploadHtl = MainFrame.frostSettings.getIntValue("htlUpload");
        logger.info("TOFUP: Uploading attachment " + attachment.getFile().getPath() + " with HTL " + uploadHtl);
        int maxTries = 3;
        int tries = 0;
        while (tries < maxTries && !result[0].equals("KeyCollision") && !result[0].equals("Success")) {
            try {
                result = FcpInsert.putFile("CHK@", attachment.getFile(), null, uploadHtl, true, new FrostUploadItem(null, null));
            } catch (Exception ex) {
                result = new String[1];
                result[0] = "Error";
            }
            tries++;
        }
        if (result[0].equals("KeyCollision") || result[0].equals("Success")) {
            logger.info("TOFUP: Upload of attachment '" + attachment.getFile().getPath() + "' was successful.");
            String chk = result[1];
            attachment.setKey(chk);
            attachment.setFilename(attachment.getFile().getName());
            if (attachment instanceof FECRedirectFileObject) {
                logger.fine("attaching redirect to file " + attachment.getFile().getName());
                FecSplitfile splitFile = new FecSplitfile(attachment.getFile());
                if (!splitFile.uploadInit()) throw new Error("file was just uploaded, but .redirect missing!");
                ((FECRedirectFileObject) attachment).setRedirect(new String(FileAccess.readByteArray(splitFile.getRedirectFile())));
                splitFile.finishUpload(true);
            } else logger.fine("not attaching redirect");
            attachment.setFile(null);
            return true;
        } else {
            logger.warning("TOFUP: Upload of attachment '" + attachment.getFile().getPath() + "' was NOT successful.");
            return false;
        }
    }

    /**
	 * Uploads all the attachments of a MessageObject and updates its
	 * XML representation on disk
	 * @param msg the MessageObject whose attachments will be uploaded
	 * @param file file whose path will be used to save the MessageObject to disk.
	 * @return true if successful. False otherwise.
	 */
    private boolean uploadAttachments(MessageObject msg, File file) {
        boolean success = true;
        List fileAttachments = msg.getOfflineFiles();
        Iterator i = fileAttachments.iterator();
        while (i.hasNext()) {
            SharedFileObject attachment = (SharedFileObject) i.next();
            if (uploadAttachment(attachment)) {
                saveMessage(msg, file);
            } else {
                success = false;
            }
        }
        if (!success) {
            JOptionPane.showMessageDialog(frameToLock, "One or more attachments failed to upload.\n" + "Will retry to upload attachments and message on next startup.", "Attachment upload failed", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    /**
	 * @return
	 */
    private boolean uploadMessage() throws IOException, MessageAlreadyUploadedException {
        boolean success = false;
        int index = 0;
        int tries = 0;
        int maxTries = 5;
        boolean error = false;
        boolean tryAgain;
        while (!success) {
            String testFilename = new StringBuffer().append(getDestinationBase()).append(message.getDate()).append("-").append(board.getBoardFilename()).append("-").append(index).append(".xml").toString();
            File testMe = new File(testFilename);
            if (testMe.exists() && testMe.length() > 0) {
                if (checkLocalMessage(testMe)) {
                    throw new MessageAlreadyUploadedException();
                } else {
                    index++;
                }
            } else {
                File lockRequestIndex = new File(testMe.getPath() + ".lock");
                boolean lockFileCreated = false;
                lockFileCreated = lockRequestIndex.createNewFile();
                if (lockFileCreated == false) {
                    index++;
                    logger.fine("TOFUP: Other thread tries this index, increasing index to " + index);
                    continue;
                } else {
                    lockRequestIndex.deleteOnExit();
                }
                String[] result = new String[2];
                String upKey = composeUpKey(index);
                String downKey = composeDownKey(index);
                try {
                    result = FcpInsert.putFile(upKey, zipFile, signMetadata, messageUploadHtl, false);
                } catch (Throwable t) {
                    logger.log(Level.SEVERE, "TOFUP - Error in run()/FcpInsert.putFile", t);
                }
                if (result[0] == null || result[1] == null) {
                    result[0] = "Error";
                    result[1] = "Error";
                }
                if (result[0].equals("Success")) {
                    success = true;
                } else {
                    if (result[0].equals("KeyCollision")) {
                        if (checkRemoteFile(downKey)) {
                            throw new MessageAlreadyUploadedException();
                        } else {
                            index++;
                            logger.fine("TOFUP: Upload collided, increasing index to " + index);
                        }
                    } else {
                        if (tries > maxTries) {
                            success = true;
                            error = true;
                        } else {
                            logger.info("TOFUP: Upload failed (try no. " + tries + " of " + maxTries + "), retrying index " + index);
                            tries++;
                        }
                    }
                }
                if (lockFileCreated == true) {
                    lockRequestIndex.delete();
                }
            }
        }
        if (!error) {
            messageFile.delete();
            zipFile.delete();
            logger.info("*********************************************************************\n" + "Message successfuly uploaded to board '" + board.toString() + "'.\n" + "*********************************************************************");
            tryAgain = false;
        } else {
            logger.warning("TOFUP: Error while uploading message.");
            MessageUploadFailedDialog faildialog = new MessageUploadFailedDialog(frameToLock, 10, LangRes.getString("Upload of message failed"), LangRes.getString("I was not able to upload your message."), LangRes.getString("Retry"), "Retry on next startup", "Discard message");
            int answer = faildialog.startDialog();
            if (answer == 1) {
                logger.info("TOFUP: Will try to upload again.");
                tryAgain = true;
            } else if (answer == 2) {
                zipFile.delete();
                logger.info("TOFUP: Will try to upload again on next startup.");
                tryAgain = false;
            } else if (answer == 3) {
                zipFile.delete();
                messageFile.delete();
                logger.warning("TOFUP: Will NOT try to upload message again.");
                tryAgain = false;
            } else {
                logger.warning("TOFUP: Paranoia - will try to upload message again.");
                tryAgain = false;
            }
            faildialog.dispose();
        }
        logger.info("TOFUP: Upload Thread finished");
        return tryAgain;
    }
}
