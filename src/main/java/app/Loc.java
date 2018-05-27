package app;

public enum Loc {
    CLASSNAME("className"),
    CSSSELECTOR("cssSelector"),
    ID("id"),
    LINKTEXT("linkText"),
    NAME("name"),
    PARTIALLINKTEXT("partialLinkText"),
    TAGNAME("tagName"),
    XPATH("xpath")
    ;

    private final String text;

    Loc(final String text) {
        this.text = text;
    }
}
