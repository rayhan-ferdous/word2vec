        } catch (IOException e) {

            mojoInfo.getLog().error("Error copying " + sourceFile.getAbsolutePath(), e);

            throw new NsisActionExecutionException("Error copying " + sourceFile.getAbsolutePath(), e);

        }

    }



    public List getRelativeFilePath(SetBase setBase, MojoInfo mojoInfo) throws NsisActionExecutionException {

        File file = getFile((FileItem) setBase, mojoInfo);

        List relativePaths = new ArrayList(1);
