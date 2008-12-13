<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" omit-xml-declaration="yes"/>

    <xsl:template match="page">
        <html>
            <head>
                <title>
                    <xsl:value-of select="string(title)"/>
                </title>
                <link href="page.css" rel="stylesheet" type="text/css" />
            </head>
            <body>
                <xsl:apply-templates select="body"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="image">
        <table border="1" align="center">
            <tr>
                <td>
                    <img src="{@src}"/>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="source">
        <table border="1" align="center">
            <tr>
                <td style="color: navy; background-color: #ffff99">
                    <pre>
                        <code style="font-family: Lucida Typewriter">
                            <strong><xsl:apply-templates/></strong>
                        </code>
                    </pre>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="@*|*|text()" priority="-1">
        <xsl:copy>
            <xsl:apply-templates select="@*|*|text()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
