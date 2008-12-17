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

    <xsl:template match="note">
        <blockquote style="font-size: 90%">
            <table border="0">
                <tr>
                    <td valign="top">
                        <img src="hand.png"/>
                    </td>
                    <td valign="top">
                        <i><xsl:apply-templates/></i>
                    </td>
                </tr>
            </table>
        </blockquote>
    </xsl:template>

    <xsl:template match="source">
        <table border="1" align="center">
            <tr>
                <td style="color: blue; background-color: #ffffaa">
                    <pre>
                        <div style="font-family: 'Lucida Typewriter'">
                            <xsl:apply-templates/>
                        </div>
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
