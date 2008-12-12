<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="people">
        <people>
            <xsl:apply-templates select="person"/>
        </people>
    </xsl:template>
    <xsl:template match="person">
        <person>
            <code><xsl:value-of select="string(code)"/></code>
            <name>
                <first-name><xsl:value-of select="fname"/></first-name>
                <xsl:if test="mname">
	                <middle-name><xsl:value-of select="mname"/></middle-name>
                </xsl:if>
                <last-name><xsl:value-of select="lname"/></last-name>
            </name>
            <salary><xsl:value-of select="salary"/></salary>
            <hiredate><xsl:value-of select="hiredate"/></hiredate>
        </person>
    </xsl:template>
</xsl:stylesheet>