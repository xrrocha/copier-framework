<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:template match="from-csv">
		<bean id="producer"
			class="plenix.record.csv.CSVRecordSource">
			<property name="filename"
				value="{normalize-space(string(filename))}" />
			<xsl:if test="@separator">
				<property name="separator" value="{@separator}" />
			</xsl:if>
			<xsl:if test="@quote">
				<property name="quote" value="{@quote}" />
			</xsl:if>
			<xsl:if test="@header-record">
				<property name="headerRecord" value="{@header-record}" />
			</xsl:if>
			<property name="fieldSpecs">
				<list>
					<xsl:apply-templates select="field" />
				</list>
			</property>
		</bean>
	</xsl:template>

	<xsl:template match="to-csv">
		<bean id="consumer"
			class="plenix.record.csv.CSVRecordDestination">
			<property name="separator" value="{@separator}" />
			<xsl:if test="@quote">
				<property name="quote" value="{@quote}" />
			</xsl:if>
			<xsl:if test="@include-headers">
				<property name="headerRecord"
					value="{@include-headers}" />
			</xsl:if>
			<property name="filename" value="{string(filename)}" />
			<property name="fieldSpecs">
				<list>
					<xsl:apply-templates select="field" />
				</list>
			</property>
		</bean>
	</xsl:template>

	<xsl:template match="from-csv/field|to-csv/field">
		<bean class="plenix.record.csv.XFieldSpec">
			<property name="name" value="{@name}" />
			<property name="type" value="{substring(@type, 1, 1)}" />
			<xsl:if test="@format">
				<property name="format">
					<xsl:choose>
						<xsl:when test="substring(@type, 1, 1) = 'n'">
							<xsl:if test="@format">
								<bean class="java.text.DecimalFormat">
									<constructor-arg>
										<value>
											<xsl:value-of
												select="@format" />
										</value>
									</constructor-arg>
								</bean>
							</xsl:if>
						</xsl:when>
						<xsl:when test="substring(@type, 1, 1) = 'd'">
							<bean class="java.text.SimpleDateFormat">
								<constructor-arg>
									<value>
										<xsl:value-of select="@format" />
									</value>
								</constructor-arg>
							</bean>
						</xsl:when>
					</xsl:choose>
				</property>
			</xsl:if>
		</bean>
	</xsl:template>

</xsl:stylesheet>
