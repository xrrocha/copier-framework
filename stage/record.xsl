<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="xml" indent="yes"
		doctype-public="-//SPRING//DTD BEAN//EN"
		doctype-system="http://www.springframework.org/dtd/spring-beans.dtd" />

	<xsl:include href="csv.xsl"/>
	<xsl:include href="jdbc.xsl"/>
	<xsl:include href="transformer.xsl"/>
	<xsl:include href="xbase.xsl"/>

	<xsl:template match="copy">
		<beans>
			<bean id="streamer" class="plenix.copier.Copier">
				<property name="producer" ref="producer" />
				<xsl:if test="script-transformer">
					<property name="transformer" ref="transformer" />
				</xsl:if>
				<property name="consumer" ref="consumer" />
			</bean>
			<xsl:apply-templates />
		</beans>
	</xsl:template>

	<xsl:template match="text()" />
</xsl:stylesheet>
