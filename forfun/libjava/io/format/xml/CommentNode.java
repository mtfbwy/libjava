package libjava.io.format.xml;

import libjava.io.format.FormattingConfig;

public class CommentNode extends Node {

    CharSequence content = null; // trimmed

    public boolean isEmpty() {
        return content == null || content.length() == 0;
    }

    @Override
    public StringBuilder toString(StringBuilder o, FormattingConfig c) {
        assert o != null;
        if (!isEmpty()) {
            appendIndent(o, c).append("<!-- ").append(content).append(" -->");
        }
        return o;
    }
}