<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:template match="from-db">
		<bean id="producer"
			class="plenix.record.jdbc.JDBCRecordSource">
			<property name="sqlText" value="string(statement)" />
			<property name="dataSource">
				<bean id="dataSource"
					class="org.springframework.jdbc.datasource.DriverManagerDataSource">
					<property name="driverClassName"
						value="{string(datasource/driver)}" />
					<property name="url"
						value="{string(datasource/url)}" />
					<property name="username"
						value="{string(datasource/username)}" />
					<property name="password"
						value="{string(datasource/password)}" />
				</bean>
			</property>
		</bean>
	</xsl:template>

	<xsl:template match="to-db">
		<bean id="consumer"
			class="plenix.record.jdbc.JDBCRecordDestination">
			<property name="sqlText">
				<value>
					<xsl:value-of select="string(statement)" />
				</value>
			</property>
			<property name="fieldNames">
				<list>
					<xsl:apply-templates select="bind" />
				</list>
			</property>
			<xsl:if test="mode">
				<xsl:if test="@batch-size">
					<property name="batchSize" value="{mode/@batch-size}" />
				</xsl:if>
				<xsl:if test="mode/@commit">
					<property name="commit" value="{mode/@commit}" />
				</xsl:if>
			</xsl:if>
			<property name="dataSource">
				<bean id="dataSource"
					class="org.springframework.jdbc.datasource.DriverManagerDataSource">
					<property name="driverClassName"
						value="{string(datasource/driver)}" />
					<property name="url"
						value="{string(datasource/url)}" />
					<property name="username"
						value="{string(datasource/username)}" />
					<property name="password"
						value="{string(datasource/password)}" />
				</bean>
			</property>
		</bean>
	</xsl:template>

	<xsl:template match="to-db/bind">
		<value>
			<xsl:value-of select="@field" />
		</value>
	</xsl:template>

</xsl:stylesheet>
