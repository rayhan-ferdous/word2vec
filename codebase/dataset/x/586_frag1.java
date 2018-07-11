    public String getModificationText(Element el, String s) {

        try {

            int startOffset = el.getStartOffset();

            int endOffset = el.getEndOffset();

            int length = endOffset - startOffset;

            String text = docd.getText(startOffset, length);

            return text;

        } catch (Exception e) {

            return "";

        }

    }
