package ru.kubankredit.qiwi.serializers;

public class XSLTRemoveNamespaces {

    /**
     * XSLT документ, с помощью которого можно убрать все namespaces из документа XML, оставив аттрибуты.
     */
    public static final String REMOVE_XML_NAMESPACES = "<?xml version=\"1.0\"?>\n" +
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
            "\n" +
            "    <xsl:output indent=\"yes\" method=\"xml\" encoding=\"utf-8\" omit-xml-declaration=\"yes\"/>\n" +
            "\n" +
            "    <!-- Stylesheet to remove all namespaces from a document -->\n" +
            "    <!-- NOTE: this will lead to attribute name clash, if an element contains\n" +
            "        two attributes with same local name but different namespace prefix -->\n" +
            "    <!-- Nodes that cannot have a namespace are copied as such -->\n" +
            "\n" +
            "    <!-- template to copy elements -->\n" +
            "    <xsl:template match=\"*\">\n" +
            "        <xsl:element name=\"{local-name()}\">\n" +
            "            <xsl:apply-templates select=\"@* | node()\"/>\n" +
            "        </xsl:element>\n" +
            "    </xsl:template>\n" +
            "\n" +
            "    <!-- template to copy attributes -->\n" +
            "    <xsl:template match=\"@*\">\n" +
            "        <xsl:attribute name=\"{local-name()}\">\n" +
            "            <xsl:value-of select=\".\"/>\n" +
            "        </xsl:attribute>\n" +
            "    </xsl:template>\n" +
            "\n" +
            "    <!-- template to copy the rest of the nodes -->\n" +
            "    <xsl:template match=\"comment() | text() | processing-instruction()\">\n" +
            "        <xsl:copy/>\n" +
            "    </xsl:template>\n" +
            "\n" +
            "</xsl:stylesheet>";
}
