<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="xml" indent="yes"
		doctype-public="-//SPRING//DTD BEAN//EN"
		doctype-system="http://www.springframework.org/dtd/spring-beans.dtd" />

	<xsl:template match="script-transformer">
		<bean id="transformer"
			class="plenix.record.transformer.ScriptingRecordTransformer">
			<xsl:if test="@language">
				<property name="languageName" value="{@language}" />
			</xsl:if>
			<property name="scriptText">
				<value>
					<xsl:value-of select="string(.)" />
				</value>
			</property>
		</bean>
	</xsl:template>

</xsl:stylesheet>
